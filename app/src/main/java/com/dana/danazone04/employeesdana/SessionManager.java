package com.dana.danazone04.employeesdana;

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
    private static final String KEY_SAVE_PRODUCT = "key_save_product";
    private static final String KEY_SAVE_ADDRESS = "key_save_address";
    private static final String KEY_SAVE_PRICE = "key_save_price";
    private static final String KEY_SAVE_NUMBER = "key_save_number";
    private static final String KEY_SAVE_YOU = "key_save_you";
    private static final String KEY_SAVE_WEB = "key_save_web";
    private static final String KEY_SAVE_LOGO = "key_save_logo";
    private static final String KEY_SAVE_COLOR_1 = "key_save_1";
    private static final String KEY_SAVE_COLOR_2 = "key_save_2";
    private static final String KEY_SAVE_COLOR_3 = "key_save_3";
    private static final String KEY_SAVE_COLOR_4 = "key_save_4";
    private static final String KEY_SAVE_COLOR_5 = "key_save_5";
    private static final String KEY_SAVE_COLOR_6 = "key_save_6";

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


    public void setKeyName(String token) {
        sharedPref.edit().putString(KEY_SAVE_NAME, token).apply();
    }

    public String getKeySaveName() {
        return sharedPref.getString(KEY_SAVE_NAME, "");
    }


    public String getKeySavePhone() {
        return sharedPref.getString(KEY_SAVE_PHONE, "");
    }

    public void setKeyPhone(String token) {
        sharedPref.edit().putString(KEY_SAVE_PHONE, token).apply();
    }


    public String getKeySavePass() {
        return sharedPref.getString(KEY_SAVE_PASS, "");
    }

    public void setKeySavePass(String token) {
        sharedPref.edit().putString(KEY_SAVE_PASS, token).apply();
    }

    public String getKeySaveProduct() {
        return sharedPref.getString(KEY_SAVE_PRODUCT, "");
    }

    public void setKeySaveProduct(String token) {
        sharedPref.edit().putString(KEY_SAVE_PRODUCT, token).apply();
    }

    public String getKeySaveAddress() {
        return sharedPref.getString(KEY_SAVE_ADDRESS, "");
    }

    public void setKeySaveAddress(String token) {
        sharedPref.edit().putString(KEY_SAVE_ADDRESS, token).apply();
    }

    public String getKeySavePrice() {
        return sharedPref.getString(KEY_SAVE_PRICE, "");
    }

    public void setKeySavePrice(String token) {
        sharedPref.edit().putString(KEY_SAVE_PRICE, token).apply();
    }

    public String getKeySaveNumber() {
        return sharedPref.getString(KEY_SAVE_NUMBER, "");
    }

    public void setKeySaveNumber(String token) {
        sharedPref.edit().putString(KEY_SAVE_NUMBER, token).apply();
    }

    public String getKeySaveYou() {
        return sharedPref.getString(KEY_SAVE_YOU, "");
    }

    public void setKeySaveYou(String token) {
        sharedPref.edit().putString(KEY_SAVE_YOU, token).apply();
    }

    public String getKeySaveWeb() {
        return sharedPref.getString(KEY_SAVE_WEB, "");
    }

    public void setKeySaveWeb(String token) {
        sharedPref.edit().putString(KEY_SAVE_WEB, token).apply();
    }

    public String getKeySaveLogo() {
        return sharedPref.getString(KEY_SAVE_LOGO, "");
    }

    public void setKeySaveLogo(String token) {
        sharedPref.edit().putString(KEY_SAVE_LOGO, token).apply();
    }

    public void removeSaveProduct() {
        sharedPref.edit().remove(KEY_SAVE_PRODUCT).commit();
    }

    public void removeSaveAddress() {
        sharedPref.edit().remove(KEY_SAVE_ADDRESS).commit();
    }

    public void removeSavePrice() {
        sharedPref.edit().remove(KEY_SAVE_PRICE).commit();
    }

    public void removeSaveNumber() {
        sharedPref.edit().remove(KEY_SAVE_NUMBER).commit();
    }

    public void removeSaveYou() {
        sharedPref.edit().remove(KEY_SAVE_YOU).commit();
    }

    public void removeSaveWeb() {
        sharedPref.edit().remove(KEY_SAVE_WEB).commit();
    }

    public void removeSaveLogo() {
        sharedPref.edit().remove(KEY_SAVE_LOGO).commit();
    }

    public void updateLogo(String token) {
        sharedPref.edit().putString(KEY_SAVE_LOGO, token).apply();
    }

    //save color
    public String getKeyColor1() {
        return sharedPref.getString(KEY_SAVE_COLOR_1, "");
    }

    public void setKeySaveColor1(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_1, token).apply();
    }

    //save color
    public String getKeySaveColor2() {
        return sharedPref.getString(KEY_SAVE_COLOR_2, "");
    }

    public void setKeySaveColor2(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_2, token).apply();
    }

    //save color
    public String getKeySaveColor3() {
        return sharedPref.getString(KEY_SAVE_COLOR_3, "");
    }

    public void setKeySaveColor3(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_3, token).apply();
    }

    //save color
    public String getKeyColor4() {
        return sharedPref.getString(KEY_SAVE_COLOR_4, "");
    }

    public void setKeySaveColor4(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_4, token).apply();
    }

    //save color
    public String getKeyColor5() {
        return sharedPref.getString(KEY_SAVE_COLOR_5, "");
    }

    public void setKeySaveColor5(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_5, token).apply();
    }

    //save color
    public String getKeyColor6() {
        return sharedPref.getString(KEY_SAVE_COLOR_6, "");
    }

    public void setKeySaveColor6(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_6, token).apply();
    }

    //update color
    public void updateColor1(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_1, token).apply();
    }

    public void updateColor2(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_2, token).apply();
    }

    public void updateColor3(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_3, token).apply();
    }

    public void updateColor4(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_4, token).apply();
    }

    public void updateColor5(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_5, token).apply();
    }

    public void updateColor6(String token) {
        sharedPref.edit().putString(KEY_SAVE_COLOR_6, token).apply();
    }
}
