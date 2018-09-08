package com.danazone.danazone04.employeesdana;

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
import android.graphics.Matrix;
import android.media.ExifInterface;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.danazone.danazone04.employeesdana.register.RegisterActivity_;
import com.danazone.danazone04.employeesdana.spalsh.SplashActivity;

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
    private static int REQUEST_PERMISSIONS = 101;
    private static final int REQUEST_GET_SINGLE_FILE = 102;
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
    String mWeb;
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
    @ViewById
    TextView mTvOut;
    @ViewById
    TextView mTvWeb;
    @ViewById
    LinearLayout mLnSum;
    @ViewById
    TextView mTvPhoto;

    private ContentValues values;
    private Uri imageUri;
    private Bitmap bitmap;
    boolean boolean_permission;
    boolean boolean_save;
    private String filename;
    private AlertDialog alertDialog;
    private String sl;
    private ExifInterface exifInterface;

    @Override
    protected void afterView() {
        getSupportActionBar().hide();
        alertDialog = new SpotsDialog(this);
        alertDialog.show();
        alertDialog.setMessage("Đang lưu ảnh...");

        mTvAddress.setVisibility(View.VISIBLE);
        mTvName.setMaxLines(1);
        mTvPhone.setMaxLines(1);
        mTvAddress.setMaxLines(1);
        mTvPrice.setMaxLines(1);
        mTvYou.setMaxLines(1);
        mTvWeb.setMaxLines(1);
        if (mName.length() > 15) {
            mTvYou.setText(mName.substring(0, 15) + "...");
        } else {
            mTvYou.setText(mName);
        }

        if (mWeb.length() > 15) {
            mTvWeb.setText(mWeb.substring(0, 15) + "...");
        } else {
            mTvWeb.setText(mWeb);
        }

        mTvName.setText(mProduct);
        mTvPhone.setText(mPhone);
        mTvAddress.setText(mAddress);
        mTvPrice.setText(mPrice);
        //mTvYou.setText(mName);


        if (mProduct.equals("") || mName.equals("") || mPhone.equals("")) {
            mLnSum.setWeightSum(2);
        }

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
        if (mWeb.equals("")) {
            mTvWeb.setVisibility(View.GONE);
        }


        if (mUri != null) {
            filename = String.valueOf(Random());
            fn_permission();

            try {
                exifInterface = new ExifInterface(mUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int oritation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            // Bitmap bm = rotateBitmap(bitmap, oritation);
            // mImgCheckin.setImageBitmap(bm);

            mImgMain.setImageBitmap(rotateBitmap(BitmapFactory.decodeFile(mUri), oritation));
            Runnable mActivityStarter = new Runnable() {
                @Override
                public void run() {
                    alertDialog.show();
                    alertDialog.setTitle("Đang lưu ảnh...");
                    hello();
                }
            };
            mHandler.postDelayed(mActivityStarter, 2000);

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
                alertDialog.show();
                alertDialog.setTitle("Đang lưu ảnh...");
                if (requestCode == MY_CAMERA_REQUEST_CODE)
                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(
                                    getContentResolver(), imageUri);

                            try {
                                exifInterface = new ExifInterface(sl);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            int oritation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                            // Bitmap bm = rotateBitmap(bitmap, oritation);
                            // mImgCheckin.setImageBitmap(bm);

                            mImgMain.setImageBitmap(rotateBitmap(bitmap, oritation));
                           // mImgMain.setImageBitmap(bitmap);

                            if (bitmap != null) {
                                Runnable mActivityStarter = new Runnable() {
                                    @Override
                                    public void run() {
                                        hello();
                                    }
                                };
                                mHandler.postDelayed(mActivityStarter, 2000);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        alertDialog.dismiss();
                        finish();
                    }
                break;
            case REQUEST_GET_SINGLE_FILE:
                alertDialog.show();
                alertDialog.setTitle("Đang lưu ảnh...");
                if (requestCode == REQUEST_GET_SINGLE_FILE && resultCode == Activity.RESULT_OK

                        && null != data) {

                    Uri selectedImage = data.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,

                            filePathColumn, null, null, null);

                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                    String uri = cursor.getString(columnIndex);
                    cursor.close();
                    fn_permission();

                    try {
                        exifInterface = new ExifInterface(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int oritation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                    // Bitmap bm = rotateBitmap(bitmap, oritation);
                    // mImgCheckin.setImageBitmap(bm);

                    mImgMain.setImageBitmap(rotateBitmap(BitmapFactory.decodeFile(uri), oritation));
                    // mImgMain.setImageBitmap(bitmap);

                    //  mImgMain.setImageBitmap();
                    Runnable mActivityStarter = new Runnable() {
                        @Override
                        public void run() {
                            hello();
                        }
                    };
                    mHandler.postDelayed(mActivityStarter, 2000);


                } else {
                    alertDialog.dismiss();
                }
                break;
        }
    }


    @Click({R.id.mTvHide, R.id.mTvSave, R.id.mTvNext, R.id.mTvView, R.id.mTvOut, R.id.mTvPhoto})
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
                filename = String.valueOf(Random());
                fn_permission();
                dispatchTakenPictureIntent();
                break;

            case R.id.mTvOut:
                MainActivity_.intent(this).flags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK).mKey(1).start();
                finish();
                break;

            case R.id.mTvPhoto:
                filename = String.valueOf(Random());
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, REQUEST_GET_SINGLE_FILE);
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
        int num = rand.nextInt(1000000000);
        return num;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int oritation) {
        Matrix matrix = new Matrix();
        switch (oritation) {
            case ExifInterface.ORIENTATION_NORMAL:

                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                return bitmap;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);

                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.setScale(-1, 1);

                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;

            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;

            default:
                return bitmap;
        }
        try {
            Bitmap bmRotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotate;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
}
