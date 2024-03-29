package com.mpio.workflow.controler;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProcessHistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/process-history")
    public String showProcessHistory(Model model) {
        // Pobieranie historii wszystkich procesów
        List<HistoricProcessInstance> historicProcesses = historyService.createHistoricProcessInstanceQuery().list();

        // Przekazywanie danych do widoku Thymeleaf
        model.addAttribute("historicProcesses", historicProcesses);
        return "process-history";
    }
}
