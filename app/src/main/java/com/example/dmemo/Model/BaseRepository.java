package com.example.dmemo.Model;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.example.dmemo.Network.CommonNetworkCallback;
import com.example.dmemo.Utils.LoadingDialog;

public class BaseRepository {

    protected CommonNetworkCallback callback;
    protected LoadingDialog loadingDialog;
    android.os.Handler handler = new android.os.Handler();

    public void setCallback(CommonNetworkCallback callback) {
        this.callback = callback;
    }


    public void showLoading(Context context) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
        }
        loadingDialog.show();

    }


    public void dismissLoading(final Context context) {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing() && !((Activity) context).isFinishing()) {
                loadingDialog.dismiss();
            }
            try {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (loadingDialog.isShowing() && !((Activity) context).isFinishing()) {
                            loadingDialog.dismiss();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

