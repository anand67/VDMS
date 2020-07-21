package com.example.vdsuser;

import androidx.appcompat.app.AppCompatActivity;

import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    EditText userNameET, userAddressET, userPhoneET, userEmailET, userUsernameET, userPasswordET;
    private Spinner userTypeSpinner;
    TextView submitButtonTV;
    private String ip;
    private String type;
    private String url;
    String[] userType = { "User", "Parent", "Guest"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        ip = globalPreference.RetriveIP();

        url = "http://"+ ip + "/vds/userRegistration.php";

        init();

        submitButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(SignupActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                if(response.contains("Failed")){
                    Toast.makeText(SignupActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else {

                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(SignupActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("name",userNameET.getText().toString());
                params.put("address",userAddressET.getText().toString());
                params.put("phone",userPhoneET.getText().toString());
                params.put("email",userEmailET.getText().toString());
                params.put("username",userUsernameET.getText().toString());
                params.put("password",userPasswordET.getText().toString());
                params.put("type",userTypeSpinner.getSelectedItem().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
        requestQueue.add(stringRequest);

    }

    private void init() {

        userNameET = (EditText) findViewById(R.id.userNameTextView);
        userAddressET = (EditText) findViewById(R.id.userAddressTextView);
        userPhoneET = (EditText) findViewById(R.id.userPhoneTextView);
        userEmailET = (EditText) findViewById(R.id.userEmailTextView);
        userUsernameET = (EditText) findViewById(R.id.userUsernameTextView);
        userPasswordET = (EditText) findViewById(R.id.userPasswordTextView);
        submitButtonTV = (TextView) findViewById(R.id.submitButtonTextView);
        userTypeSpinner = (Spinner) findViewById(R.id.userTypeSpinner);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,userType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(aa);

    }
}
