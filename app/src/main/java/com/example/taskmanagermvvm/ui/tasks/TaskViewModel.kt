package com.example.taskmanagermvvm.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskmanagermvvm.model.TaskModel

class TaskViewModel:ViewModel() {

    private var _liveData = MutableLiveData<MutableList<TaskModel>>(mutableListOf())
    private val data = mutableListOf<TaskModel>()
    val liveData: LiveData<List<TaskModel>>
        get() = _liveData as LiveData<List<TaskModel>>

    fun addTask(title : String){
        data.add(TaskModel(title = title))
        _liveData.value = data
    }

    fun deleteTask(position:Int){
        data.removeAt(position)
        _liveData.value = data
    }

    fun setTaskDone(position: Int){
        data[position].isDone = true
        _liveData.value = data
    }
}