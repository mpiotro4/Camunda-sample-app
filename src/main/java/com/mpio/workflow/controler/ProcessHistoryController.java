package com.mpio.workflow.controler;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProcessHistoryController {
    private final HistoryService historyService;
    private final RepositoryService repositoryService;
    @Autowired
    public ProcessHistoryController(HistoryService historyService, RepositoryService repositoryService) {
        this.historyService = historyService;
        this.repositoryService = repositoryService;
    }
    @GetMapping("/process-history")
    public String showProcessHistory(Model model) {
        // Pobieranie historii wszystkich procesów
        List<HistoricProcessInstance> historicProcesses = historyService.createHistoricProcessInstanceQuery().list();

        // Przekazywanie danych do widoku Thymeleaf
        model.addAttribute("historicProcesses", historicProcesses);
        return "process-history";
    }

    @GetMapping("/processes-definitions")
    public String showProcesses(Model model) {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("invoice")
                .orderByProcessDefinitionVersion()
                .asc()
                .list();

        return "XD";
    }
}
