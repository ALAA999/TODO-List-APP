package com.alaa.todolistapp.list;

import android.os.Bundle;
import android.view.View;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.common.BaseActivity;
import com.alaa.todolistapp.common.Constants;
import com.alaa.todolistapp.databinding.ActivityViewTaskBinding;
import com.alaa.todolistapp.models.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewTaskActivity extends BaseActivity implements View.OnClickListener {

    private ActivityViewTaskBinding binding;
    private DatabaseReference mDatabase;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityViewTaskBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        task = (Task) getIntent().getSerializableExtra(Constants.TASK);

        super.setOnClickListeners(new View[]{binding.toolbar.back, binding.delete}, this);
        binding.toolbar.pageTitle.setText(getString(R.string.view_task));
        binding.toolbar.save.setVisibility(View.VISIBLE);
        binding.taskName.setText(task.getName());
        binding.description.setText(task.getDescription());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            onBackPressed();
        } else if (view.getId() == R.id.delete) {

        }
    }
}