package com.dana.danazone04.employeesdana.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dana.danazone04.employeesdana.BaseDialog;
import com.dana.danazone04.employeesdana.R;
import com.dana.danazone04.employeesdana.SessionManager;
import com.dana.danazone04.employeesdana.utils.CircleImageView;

import java.io.IOException;

public class DialogLogo extends BaseDialog implements View.OnClickListener {
    // private Bitmap bitmap;
    private String sl;
    private ExifInterface exifInterface;
    private CheckBox mCbLogo;

    /**
     * Interface dialog click listener
     */
    public interface OnDialogClickListener {
        void onCallSerVice();
    }

    private OnDialogClickListener mListener;


    public DialogLogo(Context context, String sl, OnDialogClickListener listener) {
        super(context);
        mListener = listener;
        //this.bitmap = bitmap;
        this.sl = sl;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        ImageView mImgCancelDialog = (ImageView) findViewById(R.id.mImgCancelDialog);
        ImageView mImgCheckin = (ImageView) findViewById(R.id.mImgCheckin);
        TextView mTvCall = (TextView) findViewById(R.id.mTvSubmit);
        mCbLogo = (CheckBox) findViewById(R.id.mCbLogo);

        mImgCancelDialog.setOnClickListener(this);
        mTvCall.setOnClickListener(this);
        mCbLogo.setOnClickListener(this);

        if (SessionManager.getInstance().getKeySaveLogo().equals("")) {
            mCbLogo.setChecked(false);
        }

        if(!SessionManager.getInstance().getKeySaveLogo().equals("")){
            if (!SessionManager.getInstance().getKeySaveLogo().equals(sl)) {
                SessionManager.getInstance().updateLogo(sl);
            }
        }

//        try {
//            exifInterface = new ExifInterface(sl);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int oritation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        // Bitmap bm = rotateBitmap(bitmap, oritation);
        if (sl != null) {
            mImgCheckin.setImageBitmap(BitmapFactory.decodeFile(sl));
        } else {
            mImgCheckin.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_logo));
        }
        // Glide.with(getContext()).load(bitmap).error(R.drawable.image1).into(mImgCheckin);

    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_logo;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mImgCancelDialog:
                dismiss();
                break;

            case R.id.mTvSubmit:
                mListener.onCallSerVice();
                dismiss();
                break;

            case R.id.mCbLogo:
                if (mCbLogo.isChecked()) {
                    if (SessionManager.getInstance().getKeySaveLogo().equals("")) {
                        SessionManager.getInstance().setKeySaveLogo(sl);
                    }

                } else {
                    SessionManager.getInstance().removeSaveLogo();

                }
                break;
        }
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int oritation) {
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


