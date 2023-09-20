package com.example.taskmanagermvvm.ui.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanagermvvm.databinding.ItemTaskBinding
import com.example.taskmanagermvvm.model.TaskModel

class TaskAdapter(val deleteTask: (position:Int) -> Unit) :
    Adapter<TaskAdapter.TaskViewHolder>() {

    private var list = listOf<TaskModel>()

    fun addData(list: List<TaskModel>) {
        this.list = list
        notifyDataSetChanged()
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

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {

        fun onBind(taskModel: TaskModel) {
            binding.tvTaskTitle.text = taskModel.title
            binding.cbIsDone.isChecked = taskModel.isDone

            itemView.setOnLongClickListener {
                deleteTask(adapterPosition)
                false
            }


        }

    }

}