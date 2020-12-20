package com.alaa.todolistapp.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.common.BaseActivity;
import com.alaa.todolistapp.common.Constants;
import com.alaa.todolistapp.databinding.ActivityDailyBinding;
import com.alaa.todolistapp.databinding.ActivityListBinding;
import com.alaa.todolistapp.list.adapter.TaskListAdapter;
import com.alaa.todolistapp.list.adapter.ToDoListAdapter;
import com.alaa.todolistapp.models.Task;
import com.alaa.todolistapp.models.ToDoList;
import com.alaa.todolistapp.utils.AppController;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DailyActivity extends BaseActivity implements View.OnClickListener, TaskListAdapter.ListItemClickListener, TextWatcher {

    private ActivityDailyBinding binding;
    private List<Task> taskList = new ArrayList<>(), searchedTaskList = new ArrayList<>();
    private TaskListAdapter taskListAdapter;
    private DatabaseReference mDatabase;
    private String todoListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDailyBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        todoListId = getIntent().getStringExtra(Constants.TODO_LIST_ID);
        binding.toolbar.back.setOnClickListener(this);
        binding.createTask.setOnClickListener(this);
        binding.toolbar.search.setOnClickListener(this);
        binding.toolbar.search.setVisibility(View.VISIBLE);
        binding.delete.setOnClickListener(this);
        binding.taskSearch.addTextChangedListener(this);
        binding.toolbar.pageTitle.setText(getString(R.string.lists_todo));

        progressDialog.show();
        setTaskListAdapter(taskList);
        setDailyTasksListener();
    }

    private void setDailyTasksListener() {
        mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).child(todoListId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    progressDialog.dismiss();
                    taskList.clear();
                    ToDoList toDoList = snapshot.getValue(ToDoList.class);
                    binding.listName.setText(toDoList.getName());
                    taskList.addAll(toDoList.getTasks());
                    taskListAdapter.notifyDataSetChanged();
                    binding.tasks.scrollToPosition(taskList.size() - 1);
                }catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void setTaskListAdapter(List<Task> taskList) {
        taskListAdapter = new TaskListAdapter(this, taskList, this);
        binding.tasks.setAdapter(taskListAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            onBackPressed();
        } else if (view.getId() == R.id.create_task) {
            Intent intent = new Intent(this, ViewTaskActivity.class);
            intent.putExtra(Constants.TODO_LIST_ID, todoListId);
            startActivity(intent);
        } else if (view.getId() == R.id.delete) {
            mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).child(todoListId).removeValue();
            onBackPressed();
        } else if (view.getId() == R.id.search) {
            binding.taskSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTaskListClicked(int position) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchedTaskList.clear();
        if (charSequence.equals("")) {
            setTaskListAdapter(taskList);
            return;
        }
        for (int j = 0; j < taskList.size(); j++) {
            if (taskList.get(j).getName().contains(charSequence)) {
                searchedTaskList.add(taskList.get(j));
            }
        }
        setTaskListAdapter(searchedTaskList);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}