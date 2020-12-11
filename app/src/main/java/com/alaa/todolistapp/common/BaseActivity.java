package com.alaa.todolistapp.common;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.kaopiz.kprogresshud.KProgressHUD;

public class BaseActivity extends AppCompatActivity {

    public View rootView;
    protected KProgressHUD progressDialog;

    protected void setRootView(View rootView) {
        this.rootView = rootView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rootView);
        progressDialog = KProgressHUD.create(this).setDimAmount(0.5f);
    }

    protected void setOnClickListeners(View [] views, View.OnClickListener onClickListener) {
        for (View view : views) {
            view.setOnClickListener(onClickListener);
        }
    }
}