package com.alaa.todolistapp.list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.databinding.ActivityDailyBinding;
import com.alaa.todolistapp.databinding.ActivityListBinding;
import com.alaa.todolistapp.list.adapter.TaskListAdapter;
import com.alaa.todolistapp.list.adapter.ToDoListAdapter;
import com.alaa.todolistapp.models.Task;
import com.alaa.todolistapp.models.ToDoList;

import java.util.ArrayList;
import java.util.List;

public class DailyActivity extends AppCompatActivity implements View.OnClickListener, TaskListAdapter.ListItemClickListener {

    private ActivityDailyBinding binding;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDailyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.back.setOnClickListener(this);
        binding.createTask.setOnClickListener(this);
        binding.delete.setOnClickListener(this);
        binding.toolbar.pageTitle.setText(getString(R.string.lists_todo));

        // dummy data added to list for filling up the screen until connecting to firebase
        taskList = new ArrayList<>();
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.get(2).setChecked(true);
        taskListAdapter = new TaskListAdapter(this, taskList, this);
        binding.tasks.setAdapter(taskListAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            onBackPressed();
        } else if (view.getId() == R.id.create_list) {

        } else if (view.getId() == R.id.delete) {

        }
    }

    @Override
    public void onTaskListClicked(int position) {
    }
}