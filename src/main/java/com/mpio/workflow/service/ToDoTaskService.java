package com.mpio.workflow.service;

import com.mpio.workflow.model.Task;
import com.mpio.workflow.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoTaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public ToDoTaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setDescription(taskDetails.getDescription());
            task.setPriority(taskDetails.getPriority());
            return taskRepository.save(task);
        }
        return null;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void updateTaskCompletionStatus(Long id, boolean completed) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        task.setCompleted(completed);
        taskRepository.save(task);
    }
}
