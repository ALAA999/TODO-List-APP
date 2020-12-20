package com.alaa.todolistapp.list;

import android.os.Bundle;
import android.view.View;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.common.BaseActivity;
import com.alaa.todolistapp.databinding.ActivityViewTaskBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewTaskActivity extends BaseActivity implements View.OnClickListener {

    private ActivityViewTaskBinding binding;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityViewTaskBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding.toolbar.back.setOnClickListener(this);
        binding.toolbar.pageTitle.setText(getString(R.string.view_task));
    }

    @Override
    public void onClick(View view) {

    }
}