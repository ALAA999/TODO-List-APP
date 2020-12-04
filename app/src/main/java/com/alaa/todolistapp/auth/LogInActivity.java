package com.alaa.todolistapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alaa.todolistapp.MainActivity;
import com.alaa.todolistapp.R;
import com.alaa.todolistapp.databinding.ActivityLogInBinding;
import com.alaa.todolistapp.databinding.ActivitySignUpBinding;
import com.alaa.todolistapp.list.ListActivity;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            startActivity(new Intent(this, ListActivity.class));
        }
    }
}