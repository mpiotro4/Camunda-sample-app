package com.mpio.workflow.controler;

import com.mpio.workflow.model.Task;
import com.mpio.workflow.repository.TaskRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.camunda.bpm.engine.TaskService;


import java.util.List;

@Controller
public class CamundaController {
    private final RuntimeService runtimeService;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Autowired
    public CamundaController(RuntimeService runtimeService, TaskRepository taskRepository, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }
    @GetMapping("/start-simple-process-form")
    public String showStartProcessForm() {
        return "start-simple-process";
    }
    @GetMapping("/start-task-process-form")
    public String showStartTaskProcessForm(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "start-task-process";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/start-simple-process")
    public String startSimpleProcess(@RequestParam String businessKey, Model model) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("camunda_simple_process", businessKey);
        model.addAttribute("message", "Process has been started! ID: " + processInstance.getId());
        return "process-result";
    }

    @PostMapping("/start-task-process")
    public String startTaskProcess(@RequestParam String businessKey, @RequestParam String description,
                                   @RequestParam String priority, Model model) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("camunda_task_process", businessKey);
        runtimeService.setVariable(processInstance.getId(), "description", description);
        runtimeService.setVariable(processInstance.getId(), "priority", priority);
        org.camunda.bpm.engine.task.Task userTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        // Jeśli istnieje zadanie użytkownika, zakończ je
        if (userTask != null) {
            taskService.complete(userTask.getId());
        }
        model.addAttribute("message", "Process has been started! ID: " + processInstance.getId());
        return "process-result";
    }}
