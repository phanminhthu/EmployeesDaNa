package com.dana.danazone04.employeesdana.spalsh;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Handler;
import android.util.Base64;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.dana.danazone04.employeesdana.BaseActivity;
import com.dana.danazone04.employeesdana.MainActivity;
import com.dana.danazone04.employeesdana.MainActivity_;
import com.dana.danazone04.employeesdana.R;
import com.dana.danazone04.employeesdana.SessionManager;
import com.dana.danazone04.employeesdana.callVersion.GooglePlayStoreAppVersionNameLoader;
import com.dana.danazone04.employeesdana.callVersion.WSCallerVersionListener;
import com.dana.danazone04.employeesdana.register.RegisterActivity_;
import com.dana.danazone04.employeesdana.utils.ConnectionUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressLint("Registered")
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements WSCallerVersionListener {
    @ViewById
    ImageView mImgLogo;
    private Handler mHandler = new Handler();
    private boolean isForceUpdate = true;

    @Override
    protected void afterView() {
        getSupportActionBar().hide();
        if (!ConnectionUtil.isConnected(this)) {
            showAlertDialog(getResources().getString(R.string.internet));
            return;
        }
        new GooglePlayStoreAppVersionNameLoader(getApplicationContext(), this).execute();
        Animation mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(400);
        mAnimation.setRepeatCount(android.view.animation.Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mImgLogo.startAnimation(mAnimation);


        if (!SessionManager.getInstance().getKeySavePhone().equals("") && !SessionManager.getInstance().getKeySavePass().equals("")) {
            MainActivity_.intent(SplashActivity.this).flags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK).start();
            finish();
        } else {
            Runnable mActivityStarter = new Runnable() {
                @Override
                public void run() {
                    RegisterActivity_.intent(SplashActivity.this).start();
                    finish();
                }
            };
            mHandler.postDelayed(mActivityStarter, 2000);
        }
    }

    private void printKeyHash() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.dana.danazone04.employeesdana",
                    getPackageManager().GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest ms = null;
                try {
                    ms = MessageDigest.getInstance("SHA");
                    System.out.println("111111111111111: " + Base64.encodeToString(ms.digest(), Base64.DEFAULT));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                ms.update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetResponse(boolean isUpdateAvailable) {
        System.out.println("999999999999999999999999");
        if (isUpdateAvailable) {
            showUpdateDialog();
        }
    }

    /**
     * Method to show update dialog
     */
    public void showUpdateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this);

        alertDialogBuilder.setTitle(SplashActivity.this.getString(R.string.app_name));
        alertDialogBuilder.setMessage(SplashActivity.this.getString(R.string.app_upto_date));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isForceUpdate) {
                    finish();
                }
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }
}
