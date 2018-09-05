package com.example.danazone04.employeesdana;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danazone04.employeesdana.dialog.AddressDialog;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @ViewById
    TextView mTvSubmit;
    @ViewById
    TextView mTvAddress;
    @ViewById
    EditText mEdtPhone;
    @ViewById
    EditText mEdtName;

    @Override
    protected void afterView() {
        checkPermissions();
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

    @Click({R.id.mTvSubmit, R.id.mTvAddress})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.mTvSubmit:
                checkPermissions();
                getData();
                break;

            case R.id.mTvAddress:
                new AddressDialog(MainActivity.this, new AddressDialog.OnDialogClickListener() {
                    @Override
                    public void onAdd1() {
                        mTvAddress.setText("226/20B Trưng Nữ Vương, Q. Hải Châu");
                    }

                    @Override
                    public void onAdd2() {
                        mTvAddress.setText("106 Ỷ Lan Nguyên Phi, Q. Hải Châu");
                    }

                    @Override
                    public void onAdd3() {
                        mTvAddress.setText("71 Hà Tông Quyền, Q. Cẩm Lệ");
                    }

                    @Override
                    public void onAdd4() {
                        mTvAddress.setText("80 Bùi Dương Lịch. Lô 01 Cổng Chợ Nại Hiên Đông");
                    }

                    @Override
                    public void onAdd5() {
                        mTvAddress.setText("Kiot 07, Chợ Lê Trạch, Hòa Tiến");
                    }
                }).show();
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

        if (name.equals("")) {
            showAlertDialog("Vui lòng nhập tên nhân viên");
            return;
        }
        if (phone.equals("")) {
            showAlertDialog("Vui lòng nhập số điện thoại");
            return;
        }
        if (address.equals("Chi nhánh")) {
            showAlertDialog("Vui lòng nhập chi nhánh");
            return;
        }
        TakeImageActivity_.intent(MainActivity.this).mName(name).mPhone(phone).mAddress(address).start();
        finish();

    }
}
