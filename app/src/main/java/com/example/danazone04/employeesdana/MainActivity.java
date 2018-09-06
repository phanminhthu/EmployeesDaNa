package com.example.danazone04.employeesdana;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
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

import com.example.danazone04.employeesdana.dialog.AddressDialog;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_GET_SINGLE_FILE = 101;

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

    private String uri;


    @Override
    protected void afterView() {
        getSupportActionBar().hide();
        setData();
        checkPermissions();

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

    @Click({R.id.mTvSubmit, R.id.mTvPhoto})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.mTvSubmit:
                checkPermissions();
                getData();
                break;

//            case R.id.mTvAddress:
//                new AddressDialog(MainActivity.this, new AddressDialog.OnDialogClickListener() {
//                    @Override
//                    public void onAdd1() {
//                        mTvAddress.setText("226/20B Trưng Nữ Vương, Q. Hải Châu");
//                    }
//
//                    @Override
//                    public void onAdd2() {
//                        mTvAddress.setText("106 Ỷ Lan Nguyên Phi, Q. Hải Châu");
//                    }
//
//                    @Override
//                    public void onAdd3() {
//                        mTvAddress.setText("71 Hà Tông Quyền, Q. Cẩm Lệ");
//                    }
//
//                    @Override
//                    public void onAdd4() {
//                        mTvAddress.setText("80 Bùi Dương Lịch. Lô 01 Cổng Chợ Nại Hiên Đông");
//                    }
//
//                    @Override
//                    public void onAdd5() {
//                        mTvAddress.setText("Kiot 07, Chợ Lê Trạch, Hòa Tiến");
//                    }
//                }).show();
//                break;

            case R.id.mTvPhoto:
                checkPermissions();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, REQUEST_GET_SINGLE_FILE);

                break;
        }

    }

    @Click({R.id.mCbProduct, R.id.mCbName, R.id.mCbPhone, R.id.mCbAddress, R.id.mCbMoney})
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
        }
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
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
        if (mCbName.isChecked()) {
            if (name.equals("")) {
                showAlertDialog("Vui lòng nhập tên nhân viên");
                return;
            } else {
                name = mEdtName.getText().toString();
                SessionManager.getInstance().setKeySaveYou(name);
            }
        } else {
            name = "";
        }

        if (mCbPhone.isChecked()) {
            if (phone.equals("")) {
                showAlertDialog("Vui lòng nhập số điện thoại");
                return;
            } else {
                phone = mEdtPhone.getText().toString();
                SessionManager.getInstance().setKeySaveNumber(phone);
            }
        } else {
            phone = "";
        }

        if (mCbAddress.isChecked()) {
            if (address.equals("Địa chỉ")) {
                showAlertDialog("Vui lòng nhập địa chỉ");
                return;
            } else {
                address = mTvAddress.getText().toString();
                SessionManager.getInstance().setKeySaveAddress(address);
            }
        } else {
            address = "";
        }

        if (mCbProduct.isChecked()) {
            if (product.equals("")) {
                showAlertDialog("Vui lòng nhập tên sản phẩm");
                return;
            } else {
                product = mEdtProduct.getText().toString();
                SessionManager.getInstance().setKeySaveProduct(product);
            }
        } else {
            product = "";
        }

        if (mCbMoney.isChecked()) {
            if (price.equals("")) {
                showAlertDialog("Vui lòng nhập giá tiền");
                return;
            } else {
                price = mEdtMoney.getText().toString();
                SessionManager.getInstance().setKeySavePrice(price);
            }
        } else {
            price = "";
        }

        TakeImageActivity_.intent(MainActivity.this)
                .mName(name)
                .mPhone(phone)
                .mAddress(address)
                .mProduct(product)
                .mPrice(price)
                .start();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == REQUEST_GET_SINGLE_FILE && resultCode == RESULT_OK

                    && null != data) {

                Uri selectedImage = data.getData();

                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,

                        filePathColumn, null, null, null);

                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                uri = cursor.getString(columnIndex);

                cursor.close();

           //     ImageView imgView = (ImageView) findViewById(R.id.imgView);
                TakeImageActivity_.intent(MainActivity.this).mUri(uri).start();
                // Set the Image in ImageView after decoding the String

            } else {

                Toast.makeText(this, "You haven't picked Image",

                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }


}
