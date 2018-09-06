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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private ContentValues values;
    private Uri imageUri;
    private String sl;
    private Bitmap bitmap;
    boolean boolean_permission;
    boolean boolean_save;
    private String filename;
    private AlertDialog alertDialog;
    Bitmap bitmap1;

    @Override
    protected void afterView() {
        getSupportActionBar().hide();
        alertDialog = new SpotsDialog(this);
        mTvAddress.setVisibility(View.VISIBLE);

        if (mName != null && mPhone != null && mAddress != null) {
            mTvName.setText(mName);
            mTvPhone.setText(mPhone);
            mTvAddress.setText(mAddress);
        }

        if (mUri != null) {
            filename = String.valueOf(Random());
            System.out.println("222222222222222222222222222");
            mImgMain.setImageBitmap(BitmapFactory.decodeFile(mUri));
            //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               //   fn_permission();
           // boolean_permission = true;
            if (boolean_permission) {
                Bitmap bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());
                saveBitmap(bitmap1);
            } else {
            }
            // }else{
            //  Bitmap bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());
            // saveBitmap(bitmap1);
           // }
        } else {
            System.out.println("22222222222222222222222222244444");
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

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (boolean_permission) {
                                     bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());

                                    saveBitmap(bitmap1);
                                } else {
                                }
                            }else{
                                Bitmap bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());
                                saveBitmap(bitmap1);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
        }
    }


    @Click({R.id.mTvHide, R.id.mTvSave, R.id.mTvNext, R.id.mTvView})
    void onClick(View v) {
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
                        Toast.makeText(TakeImageActivity.this,"AAAAAAAAA", Toast.LENGTH_SHORT).show();
                        Bitmap bitmap1 = loadBitmapFromView(mLnTake, mLnTake.getWidth(), mLnTake.getHeight());
                        saveBitmap(bitmap1);
                    } else {
                        Toast.makeText(TakeImageActivity.this,"CCCCCCCCCC", Toast.LENGTH_SHORT).show();
                    }
                }else{
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
                alertDialog.setTitle("Đang lưu ảnh");
                showAlertDialog("Lưu ảnh thành công!");
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

    @Override
    protected void onResume() {
        super.onResume();
        if(bitmap1!=null){
            Toast.makeText(TakeImageActivity.this,"vvvvvvvvvvvvv", Toast.LENGTH_SHORT).show();
        }
    }
}
