package com.alaa.todolistapp.auth;

import android.os.Bundle;

import com.alaa.todolistapp.common.BaseActivity;
import com.alaa.todolistapp.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends BaseActivity {

    private ActivityForgetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);


    }

}