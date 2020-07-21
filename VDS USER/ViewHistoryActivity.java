package com.example.vdsuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vdsuser.Adapter.myHistoryListAdapter;
import com.example.vdsuser.Adapter.myVehicleListAdapter;
import com.example.vdsuser.ModelClass.MyHistoryListModelClass;
import com.example.vdsuser.ModelClass.MyVehicleListModelClass;
import com.example.vdsuser.utils.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewHistoryActivity extends AppCompatActivity {

    private static final String TAG = "ViewHistoryActivity";

    RecyclerView historyRV;
    private String ip;
    private String uid;

    private List<MyHistoryListModelClass> myHistoryList;
    private myHistoryListAdapter hsitoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        GlobalPreference globalPreference = new GlobalPreference(this);
        ip = globalPreference.RetriveIP();
        uid = globalPreference.getUID();

        historyRV = (RecyclerView) findViewById(R.id.historyRecyclerView);
        historyRV.setLayoutManager(new LinearLayoutManager(this));
        historyRV.setItemAnimator(new DefaultItemAnimator());

        loadHistory();
    }

    private void loadHistory() {

        myHistoryList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ip+"/vds/getUserHistoryList.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(ViewHistoryActivity.this, "No Vehicle Added", Toast.LENGTH_SHORT).show();
                }
                else {

                    try{

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for(int i=0; i< jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("name");
                            String vehicle_model = object.getString("vehicle_model");
                            String date_time = object.getString("date_time");
                            String status = object.getString("status");

                            myHistoryList.add(new MyHistoryListModelClass(String.valueOf(i+1),name,vehicle_model,date_time,status));
                        }

                        hsitoryListAdapter = new myHistoryListAdapter(ViewHistoryActivity.this, myHistoryList);
                        historyRV.setAdapter(hsitoryListAdapter);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(ViewHistoryActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ViewHistoryActivity.this);
        requestQueue.add(stringRequest);
    }
}
