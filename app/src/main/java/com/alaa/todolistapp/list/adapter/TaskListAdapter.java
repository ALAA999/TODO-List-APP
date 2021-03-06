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
import com.alaa.todolistapp.common.Constants;
import com.alaa.todolistapp.databinding.ItemTaskListBinding;
import com.alaa.todolistapp.list.ViewTaskActivity;
import com.alaa.todolistapp.models.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    final private ListItemClickListener mOnClickListener;
    private Context context;
    private List<Task> list;
    private String todoListId;

    public TaskListAdapter(Context context, List<Task> list, String todoListId, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.list = list;
        this.todoListId = todoListId;
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
        viewHolder.binding.type.setText(task.getType());
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
            this.binding.checkImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.check_image) {
                mOnClickListener.onTaskListClicked(getAdapterPosition());
            } else if (view.getId() == R.id.task_layout) {
                Intent intent = new Intent(context, ViewTaskActivity.class);
                intent.putExtra(Constants.TASK, list.get(getAdapterPosition()));
                intent.putExtra(Constants.TODO_LIST_ID, todoListId);
                context.startActivity(intent);
            }
        }
    }
}