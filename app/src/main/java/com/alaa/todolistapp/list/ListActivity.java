package com.alaa.todolistapp.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.common.BaseActivity;
import com.alaa.todolistapp.common.Constants;
import com.alaa.todolistapp.databinding.ActivityListBinding;
import com.alaa.todolistapp.databinding.ActivityLogInBinding;
import com.alaa.todolistapp.list.adapter.ToDoListAdapter;
import com.alaa.todolistapp.models.ToDoList;
import com.alaa.todolistapp.utils.AppController;
import com.alaa.todolistapp.utils.UIUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity implements View.OnClickListener, ToDoListAdapter.ListItemClickListener, TextWatcher {

    private ActivityListBinding binding;
    private List<ToDoList> toDoLists = new ArrayList<>(), searchedToDoList = new ArrayList<>();
    private ToDoListAdapter toDoListAdapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityListBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding.toolbar.back.setOnClickListener(this);
        binding.createList.setOnClickListener(this);
        binding.listSearch.addTextChangedListener(this);
        binding.toolbar.pageTitle.setText(getString(R.string.lists_todo));

        setToDoListAdapter(toDoLists);
        setToDoListsListener();
    }

    private void setToDoListsListener() {
        mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @androidx.annotation.Nullable String s) {
                ToDoList toDoList = dataSnapshot.getValue(ToDoList.class);
                toDoList.setId(dataSnapshot.getKey());
                toDoLists.add(toDoList);
                toDoListAdapter.notifyDataSetChanged();
                binding.todoList.scrollToPosition(toDoLists.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @androidx.annotation.Nullable String s) {
                ToDoList toDoList = dataSnapshot.getValue(ToDoList.class);
                toDoList.setId(dataSnapshot.getKey());
                for (int i = 0; i < toDoLists.size(); i++) {
                    if (toDoLists.get(i).getId().equals(toDoList.getId())) {
                        toDoLists.get(i).setName(toDoList.getName());
                        toDoLists.get(i).setTasks(toDoList.getTasks());
                        toDoListAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ToDoList toDoList = dataSnapshot.getValue(ToDoList.class);
                toDoList.setId(dataSnapshot.getKey());
                for (int i = 0; i < toDoLists.size(); i++) {
                    if (toDoLists.get(i).getId().equals(toDoList.getId())) {
                        toDoLists.remove(i);
                        toDoListAdapter.notifyItemRemoved(i);
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

    private void setToDoListAdapter(List<ToDoList> toDoList) {
        toDoListAdapter = new ToDoListAdapter(this, toDoList, this);
        binding.todoList.setAdapter(toDoListAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            onBackPressed();
        } else if (view.getId() == R.id.create_list) {
            showNewListDialog();
        }
    }

    private void showNewListDialog() {
        final EditText listEditText = new EditText(this);
        listEditText.setHint(R.string.list_name);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.create_new_list))
                .setView(listEditText)
                .setPositiveButton(getString(R.string.create), null)
                .setNegativeButton(getString(R.string.cancel), null)
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (UIUtil.EditTextsFilled(new EditText[]{listEditText}, ListActivity.this)) {
                dialog.dismiss();
                pushToDoToFirebase(listEditText.getText().toString());
            }
        });
    }

    public void pushToDoToFirebase(String name) {
        ToDoList toDoList = new ToDoList();
        toDoList.setName(name);
        String key = mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).push().getKey();
        mDatabase.child(Constants.TODO_TABLE_NAME).child(AppController.getInstance().getAppPreferences().getUserUId()).child(key).setValue(toDoList);
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