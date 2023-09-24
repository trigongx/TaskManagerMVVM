package com.example.taskmanagermvvm.presentation.tasks.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagermvvm.R
import com.example.taskmanagermvvm.databinding.ItemTaskBinding
import com.example.taskmanagermvvm.data.model.TaskModel

class TaskAdapter(
    val onDoneClick: (task: TaskModel) -> Unit,
    val onClickItem: (task: TaskModel) -> Unit,
    val onLongClickItem: (task: TaskModel) -> Unit
) :
    Adapter<TaskAdapter.TaskViewHolder>() {
    private val _tasks = mutableListOf<TaskModel>()
    private val tasks get() = _tasks

    fun addData(taskList: List<TaskModel>) {
        _tasks.clear()
        _tasks.addAll(taskList)
        notifyItemRangeInserted(_tasks.size, taskList.size - _tasks.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(tasks[position])
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {

        fun onBind(taskModel: TaskModel) {
            binding.tvTaskTitle.text = taskModel.title
            binding.cbTaskState.isChecked = taskModel.state
            if (binding.cbTaskState.isChecked) {
                binding.tvTaskTitle.setTextColor(Color.GRAY)
                binding.tvTaskState.setText(R.string.done)
            }
            initListener(taskModel)
        }

        private fun initListener(task: TaskModel) {
            binding.cbTaskState.setOnClickListener { onDoneClick(task) }
            itemView.setOnLongClickListener {
                onLongClickItem(task)
                true }
            itemView.setOnClickListener { onClickItem(task) }
        }
    }
}