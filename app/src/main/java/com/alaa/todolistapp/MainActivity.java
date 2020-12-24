package com.alaa.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alaa.todolistapp.auth.LogInActivity;
import com.alaa.todolistapp.auth.SignUpActivity;
import com.alaa.todolistapp.common.BaseActivity;
import com.alaa.todolistapp.databinding.ActivityMainBinding;
import com.alaa.todolistapp.list.ListActivity;
import com.alaa.todolistapp.utils.AppController;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);

        binding.next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next) {
            if (AppController.getInstance().getAppPreferences().getUserUId().isEmpty()) {
                startActivity(new Intent(this, LogInActivity.class));
            } else {
                startActivity(new Intent(this, ListActivity.class));
            }
            finish();
        }
    }
}