package com.example.vdsuser.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GlobalPreference {

    private SharedPreferences prefs;
    private Context context;
    SharedPreferences.Editor editor;

    public GlobalPreference(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void addIP(String ip) {
        editor.putString(Constants.IP, ip);
        editor.apply();
    }

    public String RetriveIP() {
        return prefs.getString(Constants.IP, "");
    }

    public void addUID(String uid) {
        editor.putString(Constants.UID, uid);
        editor.apply();
    }

    public String getUID() {
        return prefs.getString(Constants.UID, "");
    }

 }