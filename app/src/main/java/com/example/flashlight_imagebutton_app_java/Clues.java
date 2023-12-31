package com.example.flashlight_imagebutton_app_java;

import android.os.Environment;

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


//    public Clues(String TAG, String extend) {

    public void SendLog(String TAG, String extend) {

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

    public void SendLog(String TAG, String extend, String actionType) {

        File StorageDir = getDir();
        clueFile = new File(StorageDir, "MoriartyClues.txt");
        data = new JsonObject();
        data.addProperty("Action", TAG);
        data.addProperty("ActionType", actionType);
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

    public void writeToFile() {
        try {
            logger = new FileWriter(clueFile, true);
//            logger.write(TAG + " " + extend + " at: " + System.currentTimeMillis() + "\n");
            logger.write(array.toString());
            logger.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getDir() {

        // File file = new File(Environment.getDataDirectory().getAbsolutePath());

        File sdCard = Environment.getExternalStorageDirectory().getAbsoluteFile();
        File file = new File(sdCard.getAbsolutePath() + "/" + "Moriarty_V17");
        if(!file.exists()){
            file.mkdir();
        }

        return file;
    }

}

