package com.example.taskmanagermvvm.ui.addtask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanagermvvm.databinding.FragmentAddTaskBinding
import com.example.taskmanagermvvm.model.TaskModel
import com.example.taskmanagermvvm.ui.tasks.TaskViewModel


class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            setFragmentResult("task_key", bundleOf("bundle_key" to binding.etTaskTitle.text.toString()))
            findNavController().navigateUp()
        }
    }

}