package com.example.danazone04.employeesdana;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PC on 1/17/2018.
 */

public class SessionManager {
    private static final String SHARED_PREFERENCES_NAME = "com.example.danazone04.employeesdana";
    private static final String KEY_SAVE_NAME = "key_save_name";
    private static final String KEY_SAVE_PHONE = "key_save_phone";
    private static final String KEY_SAVE_PASS = "key_save_pass";

    private static SessionManager sInstance;

    private SharedPreferences sharedPref;

    public synchronized static SessionManager getInstance() {
        if (sInstance == null) {
            sInstance = new SessionManager();
        }
        return sInstance;
    }

    private SessionManager() {
        // no instance
    }

    public void init(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Set key save email
     *
     * @param token
     */
    public void setKeyName(String token) {
        sharedPref.edit().putString(KEY_SAVE_NAME, token).apply();
    }
    public String getKeySaveName() {
        return sharedPref.getString(KEY_SAVE_NAME, "");
    }

    /**
     * get key save email
     *
     * @return
     */
    public String getKeySavePhone() {
        return sharedPref.getString(KEY_SAVE_PHONE, "");
    }

    /**
     * Set key save email
     *
     * @param token
     */
    public void setKeyPhone(String token) {
        sharedPref.edit().putString(KEY_SAVE_PHONE, token).apply();
    }




    /**
     * get key save pass
     *
     * @return
     */
    public String getKeySavePass() {
        return sharedPref.getString(KEY_SAVE_PASS, "");
    }

    public void setKeySavePass(String token) {
        sharedPref.edit().putString(KEY_SAVE_PASS, token).apply();
    }


}

