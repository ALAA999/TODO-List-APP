package com.alaa.todolistapp.list.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.databinding.ItemTodoListBinding;
import com.alaa.todolistapp.models.ToDoList;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    final private ListItemClickListener mOnClickListener;
    Context context;
    List<ToDoList> list;

    public ToDoListAdapter(Context context, List<ToDoList> list, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public ToDoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTodoListBinding binding = ItemTodoListBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new ToDoListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ToDoList toDoList = list.get(i);
        viewHolder.binding.name.setText(toDoList.getName());
        Log.e("task" , toDoList.getTasks().toString());
        viewHolder.binding.tasksNumber.setText(context.getString(R.string.tasks, toDoList.getTasks().size()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ListItemClickListener {
        void onToDoListClicked(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       private ItemTodoListBinding binding;

        ViewHolder(ItemTodoListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.todoListLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onToDoListClicked(getAdapterPosition());
        }
    }
}