package com.example.dmemo.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.example.dmemo.R;

public class LoadingDialog extends Dialog {

    private static LoadingDialog instance;

    public Context context;

    public LoadingDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
    }

    public static LoadingDialog getLoadingDialog(Context context) {

        if (instance == null) {
            instance = new LoadingDialog(context);
        }

        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_layout);
        setCancelable(false);
    }

    @Override
    public void show() {

        if (!this.isShowing() && !((Activity) context).isFinishing())
            super.show();

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                if (instance == null)
                    return;

                // 실행할 동작 코딩
                instance.dismiss();
            }
        }, 15000);

    }

    @Override
    public void dismiss() {

        if (this.isShowing()) {
            super.dismiss();
            instance = null;
        }

    }
}
