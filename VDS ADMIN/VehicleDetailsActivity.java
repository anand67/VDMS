package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.utils.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VehicleDetailsActivity extends AppCompatActivity {

    private static final String TAG = "VehicleDetailsActivity";

    private TextView nameTV, vehicleNumberTV, phoneNumberTV, emailTV, vehicleModelTV, userTypeTV;
    private String ip;
    private String oid;
    private String status;
    private String vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        init();

        GlobalPreference globalPreference = new GlobalPreference(this);
        ip = globalPreference.RetriveIP();
        oid = globalPreference.getOID();

        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        vid = intent.getStringExtra("vid");

        Log.d(TAG, "onCreate: "+ip+"\n"+oid+"\n"+status+"\n"+vid);

        registerStatus();

        getVehicleDetails();

    }

    private void registerStatus() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ip+"/vds/registerStatus.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(VehicleDetailsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(VehicleDetailsActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("oid",oid);
                params.put("vid",vid);
                params.put("status",status);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(VehicleDetailsActivity.this);
        requestQueue.add(stringRequest);

    }

    private void getVehicleDetails() {

        StringRequest request = new StringRequest(Request.Method.POST, "http://"+ip+"/vds/getVehicleDetails.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(VehicleDetailsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else {

                    try{

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        JSONObject object = jsonArray.getJSONObject(0);
                        String owner_name = object.getString("owner_name");
                        String vehicle_number = object.getString("vehicle_number");
                        String vehicle_model = object.getString("vehicle_model");
                        String phone_number = object.getString("phone_number");
                        String email_id = object.getString("email_id");
                        String type = object.getString("type");

                        Log.d(TAG, "onResponse: "+"\n"+owner_name+"\n"+vehicle_number+"\n"+phone_number+"\n"+email_id);

                        nameTV.setText(owner_name);
                        vehicleNumberTV.setText(vehicle_number);
                        phoneNumberTV.setText(phone_number);
                        emailTV.setText(email_id);
                        vehicleModelTV.setText(vehicle_model);
                        userTypeTV.setText(type);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(VehicleDetailsActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("vid",vid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(VehicleDetailsActivity.this);
        queue.add(request);

    }

    private void init() {
        nameTV = (TextView) findViewById(R.id.nameTextView);
        vehicleNumberTV = (TextView) findViewById(R.id.vehicleNumberTextView);
        vehicleModelTV = (TextView) findViewById(R.id.vehicleModelTextView);
        phoneNumberTV = (TextView) findViewById(R.id.phoneNumberTextView);
        userTypeTV = (TextView) findViewById(R.id.userTypeTextView);
        emailTV = (TextView) findViewById(R.id.emailTextView);

    }
}
