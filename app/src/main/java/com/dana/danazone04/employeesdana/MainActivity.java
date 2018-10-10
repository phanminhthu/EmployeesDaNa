package com.dana.danazone04.employeesdana;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dana.danazone04.employeesdana.dialog.AddressDialog;
import com.dana.danazone04.employeesdana.dialog.DialogLogo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.thebluealliance.spectrum.SpectrumDialog;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.File;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_GET_SINGLE_FILE = 101;
    private static final int REQUEST_CHOOSE_PHOTO = 102;

    @ViewById
    TextView mTvSubmit;
    @ViewById
    EditText mTvAddress;
    @ViewById
    EditText mEdtPhone;
    @ViewById
    EditText mEdtName;
    @ViewById
    TextView mTvPhoto;
    @ViewById
    EditText mEdtProduct;
    @ViewById
    CheckBox mCbProduct;
    @ViewById
    CheckBox mCbName;
    @ViewById
    CheckBox mCbPhone;
    @ViewById
    CheckBox mCbAddress;
    @ViewById
    EditText mEdtMoney;
    @ViewById
    CheckBox mCbMoney;
    @ViewById
    EditText mEdtWeb;
    @ViewById
    CheckBox mCbWeb;
    @ViewById
    TextView mTvChooseLogo;

    @ViewById
    TextView mTvColorProduct;
    @ViewById
    TextView mTvColorUser;
    @ViewById
    TextView mTvColorPhone;
    @ViewById
    TextView mTvColorAddress;
    @ViewById
    TextView mTvColorPrice;
    @ViewById
    TextView mTvColorWebsite;

    private AdView adView1;

    @Extra
    int mKey;
    private String uri, uriLogo;
    private String names, phones, addresss, prices, products, webs;


    @Override
    protected void afterView() {
        getSupportActionBar().hide();

        if (mKey == 1) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finish();
        }

        MobileAds.initialize(getApplicationContext(), "com.dana.danazone04.employeesdanaca-app-pub-3827501771767729~3864359409");
        adView1 = findViewById(R.id.adView1);

        AdRequest adRequest1 = new AdRequest.Builder().build();
        adView1.loadAd(adRequest1);

        checkinCheckBox();
        setData();
        checkPermissions();

        if (!SessionManager.getInstance().getKeyColor1().equals("")) {
            mTvColorProduct.setBackgroundColor(Color.parseColor("#" + SessionManager.getInstance().getKeyColor1()));
        }

        if (!SessionManager.getInstance().getKeySaveColor2().equals("")) {
            mTvColorUser.setBackgroundColor(Color.parseColor("#" + SessionManager.getInstance().getKeySaveColor2()));
        }
        if (!SessionManager.getInstance().getKeySaveColor3().equals("")) {
            mTvColorPhone.setBackgroundColor(Color.parseColor("#" + SessionManager.getInstance().getKeySaveColor3()));
        }
        if (!SessionManager.getInstance().getKeyColor4().equals("")) {
            mTvColorAddress.setBackgroundColor(Color.parseColor("#" + SessionManager.getInstance().getKeyColor4()));
        }
        if (!SessionManager.getInstance().getKeyColor5().equals("")) {
            mTvColorPrice.setBackgroundColor(Color.parseColor("#" + SessionManager.getInstance().getKeyColor5()));
        }
        if (!SessionManager.getInstance().getKeyColor6().equals("")) {
            mTvColorWebsite.setBackgroundColor(Color.parseColor("#" + SessionManager.getInstance().getKeyColor6()));
        }

    }

    @Click({R.id.mTvColorProduct, R.id.mTvColorUser, R.id.mTvColorPhone, R.id.mTvColorAddress, R.id.mTvColorPrice, R.id.mTvColorWebsite})
    void colorClick(View v) {
        switch (v.getId()) {
            case R.id.mTvColorProduct:
                new SpectrumDialog.Builder(MainActivity.this)
                        .setColors(R.array.demo_colors)
                        .setDismissOnColorSelected(true)
                        .setOutlineWidth(2)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                                if (positiveResult) {
                                    if (SessionManager.getInstance().getKeyColor1().equals("")) {
                                        SessionManager.getInstance().setKeySaveColor1(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    if (!SessionManager.getInstance().getKeyColor1().equals(String.valueOf(Integer.toHexString(color).toUpperCase()))) {
                                        SessionManager.getInstance().updateColor1(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    mTvColorProduct.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(color).toUpperCase()));
                                }
                            }
                        }).build().show(getSupportFragmentManager(), null);
                break;

            case R.id.mTvColorUser:
                new SpectrumDialog.Builder(MainActivity.this)
                        .setColors(R.array.demo_colors)
                        .setDismissOnColorSelected(true)
                        .setOutlineWidth(2)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                                if (positiveResult) {
                                    if (SessionManager.getInstance().getKeySaveColor2().equals("")) {
                                        SessionManager.getInstance().setKeySaveColor2(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    if (!SessionManager.getInstance().getKeySaveColor2().equals(String.valueOf(Integer.toHexString(color).toUpperCase()))) {
                                        SessionManager.getInstance().updateColor2(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    mTvColorUser.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(color).toUpperCase()));
                                }
                            }
                        }).build().show(getSupportFragmentManager(), null);
                break;

            case R.id.mTvColorPhone:
                new SpectrumDialog.Builder(MainActivity.this)
                        .setColors(R.array.demo_colors)
                        .setDismissOnColorSelected(true)
                        .setOutlineWidth(2)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                                if (positiveResult) {
                                    if (SessionManager.getInstance().getKeySaveColor3().equals("")) {
                                        SessionManager.getInstance().setKeySaveColor3(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    if (!SessionManager.getInstance().getKeySaveColor3().equals(String.valueOf(Integer.toHexString(color).toUpperCase()))) {
                                        SessionManager.getInstance().updateColor3(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    mTvColorPhone.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(color).toUpperCase()));
                                }
                            }
                        }).build().show(getSupportFragmentManager(), null);
                break;

            case R.id.mTvColorAddress:
                new SpectrumDialog.Builder(MainActivity.this)
                        .setColors(R.array.demo_colors)
                        .setDismissOnColorSelected(true)
                        .setOutlineWidth(2)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                                if (positiveResult) {
                                    if (SessionManager.getInstance().getKeyColor4().equals("")) {
                                        SessionManager.getInstance().setKeySaveColor4(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    if (!SessionManager.getInstance().getKeyColor4().equals(String.valueOf(Integer.toHexString(color).toUpperCase()))) {
                                        SessionManager.getInstance().updateColor4(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    mTvColorAddress.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(color).toUpperCase()));
                                }
                            }
                        }).build().show(getSupportFragmentManager(), null);
                break;

            case R.id.mTvColorPrice:
                new SpectrumDialog.Builder(MainActivity.this)
                        .setColors(R.array.demo_colors)
                        .setDismissOnColorSelected(true)
                        .setOutlineWidth(2)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                                if (positiveResult) {
                                    if (SessionManager.getInstance().getKeyColor5().equals("")) {
                                        SessionManager.getInstance().setKeySaveColor5(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    if (!SessionManager.getInstance().getKeyColor5().equals(String.valueOf(Integer.toHexString(color).toUpperCase()))) {
                                        SessionManager.getInstance().updateColor5(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    mTvColorPrice.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(color).toUpperCase()));
                                }
                            }
                        }).build().show(getSupportFragmentManager(), null);
                break;

            case R.id.mTvColorWebsite:
                new SpectrumDialog.Builder(MainActivity.this)
                        .setColors(R.array.demo_colors)
                        .setDismissOnColorSelected(true)
                        .setOutlineWidth(2)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                                if (positiveResult) {
                                    if (SessionManager.getInstance().getKeyColor6().equals("")) {
                                        SessionManager.getInstance().setKeySaveColor1(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    if (!SessionManager.getInstance().getKeyColor6().equals(String.valueOf(Integer.toHexString(color).toUpperCase()))) {
                                        SessionManager.getInstance().updateColor6(String.valueOf(Integer.toHexString(color).toUpperCase()));
                                    }
                                    mTvColorWebsite.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(color).toUpperCase()));
                                }
                            }
                        }).build().show(getSupportFragmentManager(), null);
                break;
        }
    }

    private void checkinCheckBox() {
        if (SessionManager.getInstance().getKeySaveProduct().equals("")) {
            mCbProduct.setChecked(false);
        }
        if (SessionManager.getInstance().getKeySaveAddress().equals("")) {
            mCbAddress.setChecked(false);
        }

        if (SessionManager.getInstance().getKeySaveNumber().equals("")) {
            mCbPhone.setChecked(false);
        }
        if (SessionManager.getInstance().getKeySaveYou().equals("")) {
            mCbName.setChecked(false);
        }

        if (SessionManager.getInstance().getKeySavePrice().equals("")) {
            mCbMoney.setChecked(false);
        }
        if (SessionManager.getInstance().getKeySaveWeb().equals("")) {
            mCbWeb.setChecked(false);
        }
    }

    private void setData() {
        if (!SessionManager.getInstance().getKeySaveProduct().equals("")) {
            mEdtProduct.setText(SessionManager.getInstance().getKeySaveProduct());
        }

        if (!SessionManager.getInstance().getKeySaveYou().equals("")) {
            mEdtName.setText(SessionManager.getInstance().getKeySaveYou());
        }

        if (!SessionManager.getInstance().getKeySaveNumber().equals("")) {
            mEdtPhone.setText(SessionManager.getInstance().getKeySaveNumber());
        }

        if (!SessionManager.getInstance().getKeySaveAddress().equals("")) {
            mTvAddress.setText(SessionManager.getInstance().getKeySaveAddress());
        }

        if (!SessionManager.getInstance().getKeySavePrice().equals("")) {
            mEdtMoney.setText(SessionManager.getInstance().getKeySavePrice());
        }

        if (!SessionManager.getInstance().getKeySaveWeb().equals("")) {
            mEdtWeb.setText(SessionManager.getInstance().getKeySaveWeb());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //  getData();
            } else {
                Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Click({R.id.mTvSubmit, R.id.mTvPhoto, R.id.mTvChooseLogo})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.mTvSubmit:
                checkPermissions();
                getData();

                if (!SessionManager.getInstance().getKeySaveLogo().equals("")) {
                    uriLogo = SessionManager.getInstance().getKeySaveLogo();
                }


                TakeImageActivity_.intent(MainActivity.this)
                        .mName(names)
                        .mPhone(phones)
                        .mAddress(addresss)
                        .mProduct(products)
                        .mPrice(prices)
                        .mWeb(webs)
                        .mLogo(uriLogo)
                        .start();
                if (SessionManager.getInstance().getKeySaveLogo().equals("")) {
                    uriLogo = "";
                    SessionManager.getInstance().removeSaveLogo();
                }
                break;
            case R.id.mTvPhoto:
                checkPermissions();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, REQUEST_GET_SINGLE_FILE);
                break;

            case R.id.mTvChooseLogo:
                checkPermissions();
                Intent inntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(inntent, REQUEST_CHOOSE_PHOTO);
                break;
        }

    }

    @Click({R.id.mCbProduct, R.id.mCbName, R.id.mCbPhone, R.id.mCbAddress, R.id.mCbMoney, R.id.mCbWeb})
    void click(View v) {
        switch (v.getId()) {
            case R.id.mCbProduct:
                if (mCbProduct.isChecked()) {
                    SessionManager.getInstance().setKeySaveProduct(mEdtProduct.getText().toString());
                } else {
                    SessionManager.getInstance().removeSaveProduct();
                }
                break;

            case R.id.mCbName:
                if (mCbName.isChecked()) {
                    SessionManager.getInstance().setKeySaveYou(mEdtName.getText().toString());
                } else {
                    SessionManager.getInstance().removeSaveYou();
                }
                break;

            case R.id.mCbPhone:
                if (mCbPhone.isChecked()) {
                    SessionManager.getInstance().setKeySaveNumber(mEdtPhone.getText().toString());
                } else {
                    SessionManager.getInstance().removeSaveNumber();
                }
                break;

            case R.id.mCbAddress:
                if (mCbAddress.isChecked()) {
                    SessionManager.getInstance().setKeySaveAddress(mTvAddress.getText().toString());
                } else {
                    SessionManager.getInstance().removeSaveAddress();
                }
                break;

            case R.id.mCbMoney:
                if (mCbMoney.isChecked()) {
                    SessionManager.getInstance().setKeySavePrice(mEdtMoney.getText().toString());
                } else {
                    SessionManager.getInstance().removeSavePrice();
                }
                break;

            case R.id.mCbWeb:
                if (mCbWeb.isChecked()) {
                    SessionManager.getInstance().setKeySaveWeb(mEdtWeb.getText().toString());
                } else {
                    SessionManager.getInstance().removeSaveWeb();
                }
                break;
        }
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED
                ) {
            // getData();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
            }
        }
    }

    private void getData() {
        String name = mEdtName.getText().toString();
        String phone = mEdtPhone.getText().toString();
        String address = mTvAddress.getText().toString();
        String product = mEdtProduct.getText().toString();
        String price = mEdtMoney.getText().toString();
        String web = mEdtWeb.getText().toString();
        if (mCbName.isChecked()) {
            name = mEdtName.getText().toString();
            names = name;
            SessionManager.getInstance().setKeySaveYou(name);

        } else {
            name = "";
            names = name;
        }

        if (mCbPhone.isChecked()) {
            phone = mEdtPhone.getText().toString();
            phones = phone;
            SessionManager.getInstance().setKeySaveNumber(phone);
        } else {
            phone = "";
            phones = phone;
        }

        if (mCbAddress.isChecked()) {
            address = mTvAddress.getText().toString();
            addresss = address;
            SessionManager.getInstance().setKeySaveAddress(address);
        } else {
            address = "";
            addresss = address;
        }

        if (mCbProduct.isChecked()) {
            product = mEdtProduct.getText().toString();
            products = product;
            SessionManager.getInstance().setKeySaveProduct(product);
        } else {
            product = "";
            products = product;
        }

        if (mCbMoney.isChecked()) {
            price = mEdtMoney.getText().toString();
            prices = price;
            SessionManager.getInstance().setKeySavePrice(price);
        } else {
            price = "";
            prices = price;
        }

        if (mCbWeb.isChecked()) {
            web = mEdtWeb.getText().toString().trim();
            webs = web;
            SessionManager.getInstance().setKeySaveWeb(web);
        } else {
            web = "";
            webs = web;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case REQUEST_GET_SINGLE_FILE:
                    if (requestCode == REQUEST_GET_SINGLE_FILE && resultCode == Activity.RESULT_OK

                            && null != data) {

                        Uri selectedImage = data.getData();

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage,

                                filePathColumn, null, null, null);

                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                        uri = cursor.getString(columnIndex);

                        cursor.close();
                        getData();


                        if (!SessionManager.getInstance().getKeySaveLogo().equals("")) {
                            uriLogo = SessionManager.getInstance().getKeySaveLogo();
                        }

//                        if(SessionManager.getInstance().getKeySaveLogo().equals("")){
//                            uriLogo = "";
//                        }

                        TakeImageActivity_.intent(MainActivity.this)
                                .mUri(uri)
                                .mName(names)
                                .mPhone(phones)
                                .mAddress(addresss)
                                .mPrice(prices)
                                .mProduct(products)
                                .mWeb(webs)
                                .mLogo(uriLogo)
                                .start();
                        if (SessionManager.getInstance().getKeySaveLogo().equals("")) {
                            uriLogo = "";
                            SessionManager.getInstance().removeSaveLogo();
                        }


                    } else {

                    }
                    break;

                case REQUEST_CHOOSE_PHOTO:
                    if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == Activity.RESULT_OK

                            && null != data) {

                        Uri selectedImage = data.getData();

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage,

                                filePathColumn, null, null, null);

                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                        uriLogo = cursor.getString(columnIndex);

                        cursor.close();
//                        getData();
//                        TakeImageActivity_.intent(MainActivity.this)
//                                .mUri(uri)
//                                .mName(names)
//                                .mPhone(phones)
//                                .mAddress(addresss)
//                                .mPrice(prices)
//                                .mProduct(products)
//                                .mWeb(webs)
//                                .start();
                        new DialogLogo(MainActivity.this, uriLogo, new DialogLogo.OnDialogClickListener() {
                            @Override
                            public void onCallSerVice() {

                            }
                        }).show();

                    } else {

                    }
                    break;
            }

        } catch (Exception e) {

        }
    }
}
