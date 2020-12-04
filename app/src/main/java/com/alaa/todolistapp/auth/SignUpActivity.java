package com.alaa.todolistapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alaa.todolistapp.MainActivity;
import com.alaa.todolistapp.R;
import com.alaa.todolistapp.databinding.ActivityMainBinding;
import com.alaa.todolistapp.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.createProfile.setOnClickListener(this);
        binding.login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_profile) {
            // TODO sign up firebase call
        } else if (view.getId() == R.id.login) {
            onBackPressed();
        }
    }
}