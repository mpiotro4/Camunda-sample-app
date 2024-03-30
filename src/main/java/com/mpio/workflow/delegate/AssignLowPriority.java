package com.mpio.workflow.delegate;

import com.mpio.workflow.model.Task;
import com.mpio.workflow.service.ToDoTaskService;
import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

@Named
public class AssignLowPriority implements JavaDelegate {
    private final ToDoTaskService toDoTaskService;
    @Autowired
    public AssignLowPriority(ToDoTaskService toDoTaskService){
        this.toDoTaskService = toDoTaskService;
    }
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String priority = (String) delegateExecution.getVariable("priority");
        String description = (String) delegateExecution.getVariable("description");
        String taskName = (String) delegateExecution.getVariable("taskName");
        System.out.println("Creating new task: " + taskName + " " + description + " assigning " + priority + " priority");
        Task task = new Task();
        task.setPriority(priority);
        task.setDescription(description);
        task.setName(taskName);
        toDoTaskService.createTask(task);
    }
}
