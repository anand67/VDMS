package com.example.vdsuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class VehicleDetailsActivity extends AppCompatActivity {

    private static final String TAG = "VehicleDetailsActivity";

    private TextView vehicleModelTV, vehicleNumberTV, vehicleColorTV;
    private ImageView vehicleQrIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        Intent intent = getIntent();
        Bundle vehicle_bundle = intent.getExtras();
        String vehicleid = vehicle_bundle.getString("vehicleid");
        String vehicleModel = vehicle_bundle.getString("vehicleModel");
        String vehicleNumber = vehicle_bundle.getString("vehicleNumber");
        String vehicleColor = vehicle_bundle.getString("vehicleColor");

        Log.d(TAG, "onCreate: "+vehicleid+" "+vehicleModel);

        init();

        vehicleModelTV.setText(vehicleModel);
        vehicleNumberTV.setText(vehicleNumber);
        vehicleColorTV.setText(vehicleColor);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(vehicleid, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            vehicleQrIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void init() {

        vehicleModelTV = (TextView) findViewById(R.id.vehicleModelTextView);
        vehicleNumberTV = (TextView) findViewById(R.id.vehicleNumberTextView);
        vehicleColorTV = (TextView) findViewById(R.id.vehicleColorTextView);
        vehicleQrIV = (ImageView) findViewById(R.id.vehicleQrImageView);
    }
}
