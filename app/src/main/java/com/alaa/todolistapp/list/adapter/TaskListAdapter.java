package com.alaa.todolistapp.list.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.databinding.ItemTaskListBinding;
import com.alaa.todolistapp.databinding.ItemTodoListBinding;
import com.alaa.todolistapp.list.ViewTaskActivity;
import com.alaa.todolistapp.models.Task;
import com.alaa.todolistapp.models.ToDoList;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    final private ListItemClickListener mOnClickListener;
    Context context;
    List<Task> list;

    public TaskListAdapter(Context context, List<Task> list, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTaskListBinding binding = ItemTaskListBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new TaskListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Task task = list.get(i);
        viewHolder.binding.name.setText(task.getName());
        if (task.isChecked()) {
            viewHolder.binding.name.setPaintFlags(viewHolder.binding.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.binding.checkImage.setImageResource(R.drawable.checkbox_checked);
            viewHolder.binding.name.setTextColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            viewHolder.binding.name.setPaintFlags(viewHolder.binding.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.binding.checkImage.setImageResource(R.drawable.checkbox_unchecked);
            viewHolder.binding.name.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ListItemClickListener {
        void onTaskListClicked(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemTaskListBinding binding;

        ViewHolder(ItemTaskListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.taskLayout.setOnClickListener(this);
            this.binding.edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.task_layout) {
                boolean isChecked = list.get(getAdapterPosition()).isChecked();
                list.get(getAdapterPosition()).setChecked(!isChecked);
                notifyItemChanged(getAdapterPosition());
                mOnClickListener.onTaskListClicked(getAdapterPosition());
            } else if (view.getId() == R.id.edit) {
                context.startActivity(new Intent(context, ViewTaskActivity.class));
            }
        }
    }
}