package com.example.taskmanagermvvm.presentation.addtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.taskmanagermvvm.R
import com.example.taskmanagermvvm.data.model.TaskModel
import com.example.taskmanagermvvm.databinding.FragmentAddTaskBinding
import com.example.taskmanagermvvm.utils.Constants.KEY_ADD_TASK
import com.example.taskmanagermvvm.utils.Constants.KEY_DETAIL_TO_MAIN
import com.example.taskmanagermvvm.utils.Constants.KEY_MAIN_TO_DETAIL
import com.example.taskmanagermvvm.utils.Constants.KEY_SET_TASK
import com.example.taskmanagermvvm.utils.Constants.KEY_UPDATE_TASK


class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private var _task: TaskModel? = null
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
        initData()
    }

    private fun initData() {
        setFragmentResultListener(KEY_MAIN_TO_DETAIL){ _, bundle ->
            bundle.getSerializable(KEY_SET_TASK)?.let { task ->
                _task = task as TaskModel
                loadData()
            }

        }
    }

    private fun loadData() {
        _task?.let {task ->
            binding.btnSave.text = getString(R.string.update)
            binding.etTaskTitle.setText(task.title)
            binding.cbState.visibility = View.VISIBLE
            binding.cbState.isChecked = task.state
        }
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            if (_task == null) addNewTask()
            else {
                _task!!.title = binding.etTaskTitle.text.toString()
                _task!!.state = binding.cbState.isChecked
                updateTask()
            }
        }
    }

    private fun updateTask() {
        setFragmentResult(KEY_DETAIL_TO_MAIN, bundleOf(KEY_UPDATE_TASK to _task))
        findNavController().navigateUp()
    }

    private fun addNewTask() {
        setFragmentResult(
            KEY_DETAIL_TO_MAIN,
            bundleOf(KEY_ADD_TASK to binding.etTaskTitle.text.toString())
            )
        findNavController().navigateUp()

    }
}