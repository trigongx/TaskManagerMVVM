package com.example.taskmanagermvvm.presentation.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskmanagermvvm.data.model.TaskModel

class TaskViewModel : ViewModel() {

    private var _liveData = MutableLiveData<MutableList<TaskModel>>()
    private val data = mutableListOf<TaskModel>()
    private var _sort = TaskStatus.ALL
    val liveData: LiveData<MutableList<TaskModel>>
        get() = _liveData

    fun doSortedList(sort: TaskStatus) {
        _sort = sort
        setDataToShow()
    }

    private fun setDataToShow() {
        when (_sort) {
            TaskStatus.DONE -> showDoneTask()
            TaskStatus.NOT_DONE -> showNotDoneTask()
            else -> showAllTask()
        }
    }

    fun updateTask(task: TaskModel) {
        deleteTask(task)
        data.add(task.id!!, task)
        setDataToShow()
    }

    private fun showNotDoneTask() {
        val sortedList = mutableListOf<TaskModel>()
        data.forEach { task ->
            if (!task.state) sortedList.add(task)
        }
        _liveData.value = sortedList
    }

    private fun showDoneTask() {
        val sortedList = mutableListOf<TaskModel>()
        data.forEach { task ->
            if (task.state) sortedList.add(task)
        }
        _liveData.value = sortedList
    }

    fun addNewTask(title: String) {
        data.add(
            TaskModel(
                id = data.size,
                title = title
            )
        )
        setDataToShow()
    }

    private fun showAllTask() {
        _liveData.value = data
    }

    fun deleteTask(task: TaskModel) {
        data.removeAt(task.id!!)
        showAllTask()
    }

    fun setTaskDone(task: TaskModel) {
        data[task.id!!].state = true
        showAllTask()
    }
}