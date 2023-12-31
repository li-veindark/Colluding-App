package com.example.calculator;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Clues {
    String TAG;
    String extend;
    FileWriter logger;
    JsonObject data;
    JsonArray array;
    File clueFile;

    // ... Other methods and variables ...

    public void SendLog(String TAG, String extend, String malicious) {

        File StorageDir = getDir();
        clueFile = new File(StorageDir, "MoriartyClues.txt");
        data = new JsonObject();
        data.addProperty("Action", TAG);
        data.addProperty("ActionType", "benign");
        data.addProperty("Details", extend);
        data.addProperty("UUID", System.currentTimeMillis());
        if (MainActivity.isEvil) {
            data.addProperty("SessionType", "malicious");
        } else data.addProperty("SessionType", "benign");
        data.addProperty("Version", MainActivity.version);
        data.addProperty("SessionID", MainActivity.sessionID);
        try {
            logger = new FileWriter(clueFile, true);
            if (clueFile.length() > 0) {
                logger.write("," + data.toString());
                logger.close();
            } else {
                logger.write(data.toString());
                logger.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Modify this method to send SMS details
    public void SendSMSLog(String TAG, String smsDetails) {
        File StorageDir = getDir();
        clueFile = new File(StorageDir, "MoriartyClues.txt");
        data = new JsonObject();
        data.addProperty("Action", TAG);
        data.addProperty("ActionType", "benign"); // Assuming SMS access is benign
        data.addProperty("Details", smsDetails);
        data.addProperty("UUID", System.currentTimeMillis());
        if (MainActivity.isEvil) {
            data.addProperty("SessionType", "malicious");
        } else data.addProperty("SessionType", "benign");
        data.addProperty("Version", MainActivity.version);
        data.addProperty("SessionID", MainActivity.sessionID);
        try {
            logger = new FileWriter(clueFile, true);
            if (clueFile.length() > 0) {
                logger.write("," + data.toString());
                logger.close();
            } else {
                logger.write(data.toString());
                logger.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check and request SMS permission if needed
    private void checkAndRequestSmsPermission(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_SMS}, MainActivity.PERMISSION_REQUEST_READ_SMS);
        }
    }


    // ... Other methods ...

    public static File getDir() {
        File sdCard = Environment.getExternalStorageDirectory().getAbsoluteFile();
        File file = new File(sdCard.getAbsolutePath() + "/" + "Moriarty_V17");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
}

