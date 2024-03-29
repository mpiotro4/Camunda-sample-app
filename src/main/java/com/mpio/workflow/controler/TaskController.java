package com.mpio.workflow.controler;

import com.mpio.workflow.model.Task;
import com.mpio.workflow.service.ToDoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private ToDoTaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @PostMapping("/delete/{id}")
    public RedirectView deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new RedirectView("/start-task-process-form", true);
    }

    @PostMapping("/updateCompletionStatus/{id}")
    public ResponseEntity<String> updateTaskCompletionStatus(@PathVariable Long id, @RequestParam boolean completed) {
        taskService.updateTaskCompletionStatus(id, completed);
        return ResponseEntity.ok("Task completion status updated successfully");
    }
}

