package com.alaa.todolistapp.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.common.Constants;
import com.alaa.todolistapp.databinding.ActivityListBinding;
import com.alaa.todolistapp.list.adapter.ToDoListAdapter;
import com.alaa.todolistapp.models.ToDoList;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements View.OnClickListener, ToDoListAdapter.ListItemClickListener, TextWatcher {

    private ActivityListBinding binding;
    private List<ToDoList> toDoLists, searchedToDoList;
    private ToDoListAdapter toDoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.back.setOnClickListener(this);
        binding.createList.setOnClickListener(this);
        binding.listSearch.addTextChangedListener(this);
        binding.toolbar.pageTitle.setText(getString(R.string.lists_todo));

        // dummy data added to list for filling up the screen until connecting to firebase
        toDoLists = new ArrayList<>();
        searchedToDoList = new ArrayList<>();
        toDoLists.add(new ToDoList());
        toDoLists.add(new ToDoList());
        toDoLists.add(new ToDoList());
        toDoLists.add(new ToDoList());
        setToDoListAdapter(toDoLists);
    }

    private void setToDoListAdapter(List<ToDoList> toDoList) {
        toDoListAdapter = new ToDoListAdapter(this, toDoList, this);
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
        Intent intent = new Intent(this, DailyActivity.class);
        intent.putExtra(Constants.TODO_LIST_ID, toDoLists.get(position).getId());
        startActivity(intent);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchedToDoList.clear();
        if (charSequence.equals("")) {
            setToDoListAdapter(toDoLists);
            return;
        }
        for (int j = 0; j < toDoLists.size(); j++) {
            if (toDoLists.get(j).getName().contains(charSequence)) {
                searchedToDoList.add(toDoLists.get(j));
            }
        }
        setToDoListAdapter(searchedToDoList);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}