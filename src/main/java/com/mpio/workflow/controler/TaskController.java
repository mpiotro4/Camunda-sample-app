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
    private final ToDoTaskService toDoTaskService;
    @Autowired
    public TaskController(ToDoTaskService toDoTaskService) {
        this.toDoTaskService = toDoTaskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return toDoTaskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return toDoTaskService.getTaskById(id);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return toDoTaskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return toDoTaskService.updateTask(id, taskDetails);
    }

    @PostMapping("/delete/{id}")
    public RedirectView deleteTask(@PathVariable Long id) {
        toDoTaskService.deleteTask(id);
        return new RedirectView("/start-task-process-form", true);
    }

    @PostMapping("/updateCompletionStatus/{id}")
    public ResponseEntity<String> updateTaskCompletionStatus(@PathVariable Long id, @RequestParam boolean completed) {
        toDoTaskService.updateTaskCompletionStatus(id, completed);
        return ResponseEntity.ok("Task completion status updated successfully");
    }
}

