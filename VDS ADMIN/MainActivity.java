package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.utils.GlobalPreference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    Button entryScanBT, exitScanBT;
    private IntentIntegrator qrScan;
    private String vid;
    private String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        GlobalPreference globalPreference = new GlobalPreference(this);
        globalPreference.addIP("192.168.43.95");
        globalPreference.addOID("1");*/

        init();

        //intializing scan object
        qrScan = new IntentIntegrator(this);

    }

    private void init() {
        entryScanBT = (Button) findViewById(R.id.entryScanButton);
        entryScanBT.setOnClickListener(this);
        exitScanBT = (Button) findViewById(R.id.exitScanButton);
        exitScanBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.entryScanButton:
                status = "entry";
                qrScan.initiateScan();
            break;
            case R.id.exitScanButton:
                status = "exit";
                qrScan.initiateScan();
            break;
        }
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                Log.d(TAG, "onActivityResult: "+result);
                vid = result.getContents().toString();
                Intent intent = new Intent(MainActivity.this,VehicleDetailsActivity.class);
                intent.putExtra("status",status);
                intent.putExtra("vid",vid);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
