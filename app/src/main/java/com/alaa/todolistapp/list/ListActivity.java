package com.alaa.todolistapp.list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.databinding.ActivityListBinding;
import com.alaa.todolistapp.databinding.ActivityLogInBinding;
import com.alaa.todolistapp.list.adapter.ToDoListAdapter;
import com.alaa.todolistapp.models.ToDoList;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements View.OnClickListener, ToDoListAdapter.ListItemClickListener {

    private ActivityListBinding binding;
    private List<ToDoList> toDoLists;
    private ToDoListAdapter toDoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.back.setOnClickListener(this);
        binding.createList.setOnClickListener(this);
        binding.toolbar.pageTitle.setText(getString(R.string.lists_todo));

        toDoLists = new ArrayList<>();
        toDoLists.add(new ToDoList());
        toDoLists.add(new ToDoList());
        toDoLists.add(new ToDoList());
        toDoLists.add(new ToDoList());
        toDoListAdapter = new ToDoListAdapter(this, toDoLists, this);
        binding.todoList.setAdapter(toDoListAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            onBackPressed();
        } else if (view.getId() == R.id.create_list) {

        }
    }

    @Override
    public void onToDoListClicked(int position) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }
}