package com.alaa.todolistapp.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
import com.alaa.todolistapp.utils.UIUtil;
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
    private String todoListId, todoListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDailyBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        todoListId = getIntent().getStringExtra(Constants.TODO_LIST_ID);
        todoListName = getIntent().getStringExtra(Constants.TODO_LIST_NAME);
        binding.listName.setText(todoListName);
        binding.toolbar.back.setOnClickListener(this);
        binding.createTask.setOnClickListener(this);
        binding.toolbar.search.setOnClickListener(this);
        binding.toolbar.search.setVisibility(View.VISIBLE);
        binding.delete.setOnClickListener(this);
        binding.taskSearch.addTextChangedListener(this);
        binding.toolbar.pageTitle.setText(getString(R.string.lists_todo));

        setTaskListAdapter(taskList);
        setDailyTasksListener();
    }

    private void setDailyTasksListener() {
        mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).child(todoListId).child(Constants.TASK_TABLE_NAME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @androidx.annotation.Nullable String s) {
                Task task = dataSnapshot.getValue(Task.class);
                taskList.add(task);
                setTaskListAdapter(taskList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @androidx.annotation.Nullable String s) {
                Task task = dataSnapshot.getValue(Task.class);
                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getId().equals(task.getId())) {
                        taskList.get(i).setName(task.getName());
                        taskList.get(i).setChecked(task.isChecked());
                        taskList.get(i).setDescription(task.getDescription());
                        setTaskListAdapter(taskList);
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ToDoList toDoList = dataSnapshot.getValue(ToDoList.class);
                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getId().equals(toDoList.getId())) {
                        taskList.remove(i);
                        setTaskListAdapter(taskList);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @androidx.annotation.Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setTaskListAdapter(List<Task> taskList) {
        taskListAdapter = new TaskListAdapter(this, taskList, todoListId, this);
        binding.tasks.setAdapter(taskListAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            onBackPressed();
        } else if (view.getId() == R.id.create_task) {
            showNewTaskDialog();
        } else if (view.getId() == R.id.delete) {
            mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).child(todoListId).removeValue();
            onBackPressed();
        } else if (view.getId() == R.id.search) {
            binding.taskSearch.setVisibility(View.VISIBLE);
        }
    }

    private void showNewTaskDialog() {
        final EditText listEditText = new EditText(this);
        listEditText.setHint(R.string.task_name);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.create_new_task))
                .setView(listEditText)
                .setPositiveButton(getString(R.string.create), null)
                .setNegativeButton(getString(R.string.cancel), null)
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (UIUtil.EditTextsFilled(new EditText[]{listEditText}, DailyActivity.this)) {
                dialog.dismiss();
                pushTaskToFirebase(listEditText.getText().toString());
            }
        });
    }

    public void pushTaskToFirebase(String name) {
        Task task = new Task();
        task.setTime(System.currentTimeMillis() / 1000);
        task.setName(name);
        String key = mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).child(todoListId).push().getKey();
        task.setId(key);
        mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).child(todoListId).child(Constants.TASK_TABLE_NAME).child(key).setValue(task);
    }


    @Override
    public void onTaskListClicked(int position) {
        Task task = taskList.get(position);
        boolean isChecked = task.isChecked();
        task.setChecked(!isChecked);
        mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).child(todoListId).child(Constants.TASK_TABLE_NAME).child(task.getId()).setValue(task);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchedTaskList.clear();
        binding.listName.setText(getString(R.string.result));
        for (int j = 0; j < taskList.size(); j++) {
            if (taskList.get(j).getName().contains(charSequence)) {
                searchedTaskList.add(taskList.get(j));
            }
        }
        setTaskListAdapter(searchedTaskList);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 0) {
            binding.listName.setText(todoListName);
        }
    }
}