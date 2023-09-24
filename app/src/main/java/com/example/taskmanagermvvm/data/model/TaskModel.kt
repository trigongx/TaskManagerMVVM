package com.example.taskmanagermvvm.data.model

import java.io.Serializable

data class TaskModel(
    val id:Int?,
    var title:String,
    var state:Boolean = false
):Serializable
