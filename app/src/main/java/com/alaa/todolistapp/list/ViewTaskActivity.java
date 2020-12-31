package com.alaa.todolistapp.list;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.common.BaseActivity;
import com.alaa.todolistapp.common.Constants;
import com.alaa.todolistapp.databinding.ActivityViewTaskBinding;
import com.alaa.todolistapp.models.Task;
import com.alaa.todolistapp.utils.AppController;
import com.alaa.todolistapp.utils.DateUtil;
import com.alaa.todolistapp.utils.UIUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewTaskActivity extends BaseActivity implements View.OnClickListener {

    private ActivityViewTaskBinding binding;
    private DatabaseReference mDatabase;
    private Task task;
    private String todoListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityViewTaskBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        task = (Task) getIntent().getSerializableExtra(Constants.TASK);
        todoListId = getIntent().getStringExtra(Constants.TODO_LIST_ID);

        super.setOnClickListeners(new View[]{binding.toolbar.back, binding.delete, binding.toolbar.save}, this);
        binding.toolbar.pageTitle.setText(getString(R.string.view_task));
        binding.toolbar.save.setVisibility(View.VISIBLE);
        binding.taskName.setText(task.getName());
        binding.taskType.setText(task.getType());
        binding.description.setText(task.getDescription());
        binding.time.setText(DateUtil.getDate(task.getTime()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            onBackPressed();
        } else if (view.getId() == R.id.delete) {
            mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).child(todoListId).child(Constants.TASK_TABLE_NAME).child(task.getId()).removeValue();
            onBackPressed();
        } else if (view.getId() == R.id.save) {
            if (UIUtil.EditTextsFilled(new EditText[]{binding.taskName}, this)) {
                task.setName(binding.taskName.getText().toString());
                task.setType(binding.taskType.getText().toString());
                task.setDescription(binding.description.getText().toString());
                mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).child(todoListId).child(Constants.TASK_TABLE_NAME).child(task.getId()).setValue(task);
                onBackPressed();
            }
        }
    }
}