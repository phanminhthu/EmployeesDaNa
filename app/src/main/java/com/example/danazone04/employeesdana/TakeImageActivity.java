package com.example.danazone04.employeesdana;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danazone04.employeesdana.register.RegisterActivity_;
import com.example.danazone04.employeesdana.spalsh.SplashActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import dmax.dialog.SpotsDialog;

@SuppressLint("Registered")
@EActivity(R.layout.activity_tacke_image)
public class TakeImageActivity extends BaseActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static int REQUEST_PERMISSIONS = 1;
    private Handler mHandler = new Handler();
    @Extra
    String mPhone;
    @Extra
    String mName;
    @Extra
    String mAddress;
    @Extra
    String mProduct;
    @Extra
    String mPrice;
    @Extra
    String mUri;

    @ViewById
    ImageView mImgMain;
    @ViewById
    TextView mTvName;
    @ViewById
    TextView mTvPhone;
    @ViewById
    TextView mTvAddress;
    @ViewById
    TextView mTvHide;
    @ViewById
    TextView mTvSave;
    @ViewById
    TextView mTvNext;
    @ViewById
    RelativeLayout mLnTake;
    @ViewById
    TextView mTvView;
    @ViewById
    TextView mTvPrice;
    @ViewById
    TextView mTvYou;

    private ContentValues values;
    private Uri imageUri;
    private String sl;
    private Bitmap bitmap;
    boolean boolean_permission;
    boolean boolean_save;
    private String filename;
    private AlertDialog alertDialog;

    @Override
    protected void afterView() {
        getSupportActionBar().hide();
        alertDialog = new SpotsDialog(this);
        alertDialog.show();
        alertDialog.setMessage("Đang lưu ảnh...");

        mTvAddress.setVisibility(View.VISIBLE);
        mTvName.setText(mProduct);
        mTvPhone.setText(mPhone);
        mTvAddress.setText(mAddress);
        mTvPrice.setText(mPrice);
        mTvYou.setText(mName);


        if (mProduct.equals("")) {
            mTvName.setVisibility(View.GONE);
        }
        if (mName.equals("")) {
            mTvYou.setVisibility(View.GONE);
        }
        if (mPhone.equals("")) {
            mTvPhone.setVisibility(View.GONE);
        }
        if (mAddress.equals("")) {
            mTvAddress.setVisibility(View.GONE);
        }

        if (mPrice.equals("")) {
            mTvPrice.setVisibility(View.GONE);
        }


        if (mUri != null) {
            filename = String.valueOf(Random());
            fn_permission();
            mImgMain.setImageBitmap(BitmapFactory.decodeFile(mUri));
            Runnable mActivityStarter = new Runnable() {
                @Override
                public void run() {
                    alertDialog.show();
                    alertDialog.setTitle("Đang lưu ảnh...");
                    hello();
                }
            };
            mHandler.postDelayed(mActivityStarter, 1500);

        } else {

            filename = String.valueOf(Random());
            fn_permission();
            if (ContextCompat.checkSelfPermission(this,

                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED
                    ) {
                dispatchTakenPictureIntent();
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

    }

    private void hello() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alertDialog.show();
            if (boolean_permission) {
                Bitmap bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());
                saveBitmap(bitmap1);
            } else {
                alertDialog.dismiss();
            }
        } else {
            Bitmap bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());
            saveBitmap(bitmap1);
        }
    }


    private void dispatchTakenPictureIntent() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            return;
        }
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        sl = getRealPathFromURI(imageUri);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);

    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakenPictureIntent();
            } else {
                Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
                dispatchTakenPictureIntent();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MY_CAMERA_REQUEST_CODE:
                if (requestCode == MY_CAMERA_REQUEST_CODE)
                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(
                                    getContentResolver(), imageUri);
                            mImgMain.setImageBitmap(bitmap);

                            Runnable mActivityStarter = new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.show();
                                    alertDialog.setTitle("Đang lưu ảnh...");
                                    hello();
                                }
                            };
                            mHandler.postDelayed(mActivityStarter, 1500);
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                if (boolean_permission) {
//                                    Bitmap bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());
//                                   saveBitmap(bitmap1);
//
//                                } else {
//
//                                }
//                            }else{
//                                 Bitmap bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());saveBitmap(bitmap1);
//                                saveBitmap(bitmap1);
//                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
        }
    }


    @Click({R.id.mTvHide, R.id.mTvSave, R.id.mTvNext, R.id.mTvView})
    void onClicks(View v) {
        switch (v.getId()) {
            case R.id.mTvHide:
                mTvAddress.setVisibility(View.GONE);
                mTvHide.setVisibility(View.GONE);
                mTvView.setVisibility(View.VISIBLE);
                break;

            case R.id.mTvView:
                mTvAddress.setVisibility(View.VISIBLE);
                mTvHide.setVisibility(View.VISIBLE);
                mTvView.setVisibility(View.GONE);
                break;

            case R.id.mTvSave:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alertDialog.show();
                    if (boolean_permission) {
                        Bitmap bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());
                        saveBitmap(bitmap1);
                    } else {
                        alertDialog.dismiss();
                    }
                } else {
                    Bitmap bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());
                    saveBitmap(bitmap1);
                }

                break;

            case R.id.mTvNext:
                dispatchTakenPictureIntent();
                break;
        }
    }

    private void saveBitmap(Bitmap bitmap) {

        File imagePath = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/" + filename + ".jpg");

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            boolean_save = true;
            if (boolean_save) {
                alertDialog.dismiss();
                //alertDialog.setTitle("Đang lưu ảnh");
                Toast.makeText(TakeImageActivity.this, "Hình ảnh đã lưu vào bộ sưu tập!", Toast.LENGTH_LONG).show();
            }
            // mTvSubmit.setText("Check image");

            Log.e("ImageSave", "Saveimage");
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{imagePath.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(TakeImageActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(TakeImageActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(TakeImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(TakeImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            boolean_permission = true;
        }
    }

    public int Random() {
        //tong tu 10 den 19
        Random rand = new Random();
        int num = rand.nextInt(10000000);
        return num;
    }
}
