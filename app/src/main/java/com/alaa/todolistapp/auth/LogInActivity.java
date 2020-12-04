package com.alaa.todolistapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alaa.todolistapp.MainActivity;
import com.alaa.todolistapp.R;
import com.alaa.todolistapp.databinding.ActivityLogInBinding;
import com.alaa.todolistapp.databinding.ActivitySignUpBinding;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.login.setOnClickListener(this);
        binding.createProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_profile) {
            startActivity(new Intent(this, SignUpActivity.class));
        } else if (view.getId() == R.id.login) {
            // TODO login firebase call
        }
    }
}