package com.example.flashlightIITR;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {
    ImageButton imageButton;
    public static boolean isEvil;
    public static String version;
    public static int sessionID;

    private boolean flashlightStateChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        sessionID=sharedPref.getInt("loadSessionID",0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("loadSessionID",  sessionID+1);
        editor.apply();

        isEvil = false;
        version = "17";

        Clues myclues = new Clues();
        myclues.SendLog("App Mode Change","App entered onCreate()","Benign");
        Log.d("Main","Starting the main activity");


        imageButton = findViewById(R.id.imageButton3);
        CameraManager cameraManager = (CameraManager)getSystemService(CAMERA_SERVICE);

        imageButton.setOnClickListener(v -> {
            if(isFlashAvailable()){
              if (!flashlightStateChanged) {
                try {
                    String cameraIdForFlashlight = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraIdForFlashlight, true);
                    imageButton.setImageResource(R.drawable.on);
                    flashlightStateChanged = true;
                } catch (CameraAccessException exception) {
                   Log.d("Main","Turned On");
                }
            } else {
                try {
                    String cameraIdForFlashlight = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraIdForFlashlight, false);
                    imageButton.setImageResource(R.drawable.off);
                    flashlightStateChanged = false;
                } catch (CameraAccessException exception) {
                    Log.d("Main","Turned Off");
                }
            }
            } else {
                // Handle case where flashlight is not available
                // For example, display a message or take alternative action
                Toast.makeText(MainActivity.this, "Flashlight is not available", Toast.LENGTH_SHORT).show();
                Log.d("Main", "Flashlight not available");
            }
        });
    }

    private boolean isFlashAvailable() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}