package com.example.vdsuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vdsuser.Adapter.myVehicleListAdapter;
import com.example.vdsuser.ModelClass.MyVehicleListModelClass;
import com.example.vdsuser.utils.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView myVehicleRV;
    private String uid;
    private String ip;

    private List<MyVehicleListModelClass> myVehicleList;
    private myVehicleListAdapter vehicleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        GlobalPreference globalPreference = new GlobalPreference(this);
        ip = globalPreference.RetriveIP();
        uid = globalPreference.getUID();

        myVehicleRV = (RecyclerView) findViewById(R.id.myVehicleRecyclerView);
        myVehicleRV.setLayoutManager(new LinearLayoutManager(this));
        myVehicleRV.setItemAnimator(new DefaultItemAnimator());
        
        loadVehiclesList();

        LinearLayout addNewVehicleLL = (LinearLayout) findViewById(R.id.addNewVehicleLinearLayout);
        addNewVehicleLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddVehicleActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout viewHistoryLL = (LinearLayout) findViewById(R.id.viewHistoryLinearLayout);
        viewHistoryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadVehiclesList() {

        myVehicleList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ip+"/vds/getUserVehicleList.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(MainActivity.this, "No Vehicle Added", Toast.LENGTH_SHORT).show();
                }
                else {

                    try{

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for(int i=0; i< jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.d(TAG, "onResponse: "+object.getString("id"));
                            String id = object.getString("id");
                            String vehicle_number = object.getString("vehicle_number");
                            String vehicle_model = object.getString("vehicle_model");
                            String vehicle_color = object.getString("vehicle_color");

                            myVehicleList.add(new MyVehicleListModelClass(id,vehicle_number,vehicle_model,vehicle_color));
                        }

                        vehicleListAdapter = new myVehicleListAdapter(MainActivity.this, myVehicleList);
                        myVehicleRV.setAdapter(vehicleListAdapter);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadVehiclesList();
    }
}
