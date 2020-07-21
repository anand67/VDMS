package com.example.vdsuser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vdsuser.utils.GlobalPreference;

import java.util.HashMap;
import java.util.Map;

public class AddVehicleActivity extends AppCompatActivity {

    private static final String TAG = "AddVehicleActivity";

    private EditText vehicleModelET, vehicleNumberET, vehicleColorET;
    private TextView addVehicleButtonTV;
    private String url;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        String ip = globalPreference.RetriveIP();
        uid = globalPreference.getUID();
        url = "http://"+ ip + "/vds/addUserVehicle.php";

        init();

        addVehicleButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVehicle();
            }
        });


    }

    private void addVehicle() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(AddVehicleActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                if(response.contains("Failed")){
                    Toast.makeText(AddVehicleActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else {

                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("vehicleModel",vehicleModelET.getText().toString());
                params.put("vehicleNumber",vehicleNumberET.getText().toString());
                params.put("vehicleColor",vehicleColorET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddVehicleActivity.this);
        requestQueue.add(stringRequest);

    }

    private void init() {
        vehicleModelET = (EditText) findViewById(R.id.vehicleModelEditText);
        vehicleNumberET = (EditText) findViewById(R.id.vehicleNumberEditText);
        vehicleColorET = (EditText) findViewById(R.id.vehicleColorEditText);
        addVehicleButtonTV = (TextView) findViewById(R.id.addVehicleButtonTextView);
    }
}
