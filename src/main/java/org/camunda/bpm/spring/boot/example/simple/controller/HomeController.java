package org.camunda.bpm.spring.boot.example.simple.controller;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/executeBPMN")
    public String executeBPMN() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstantiationBuilder instance = (ProcessInstantiationBuilder) processEngine.getRuntimeService().startProcessInstanceByKey("FirstProcess");
        instance.executeWithVariablesInReturn();
        return "executed BPMN";
    }

    @GetMapping("/executeMyFirstBPMN")
    public String executeMyFirstBPMN() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService().startProcessInstanceByKey("FirstProcess");
       // ProcessInstantiationBuilder instance = (ProcessInstantiationBuilder) 
       // processEngine.getRuntimeService().createProcessInstanceByKey("FirstProcess");
       /*Map<String, Object> vars = new HashMap<>();
        vars.put("orderId", "ORD-1001");
        vars.put("amount", 250.0);

        runtimeService.startProcessInstanceByKey("orderProcess", vars);
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> vars = new HashMap<>();
        vars.put("orderId", "ORD-1001");
        runtimeService = processEngine.getRuntimeService();
        runtimeService.startProcessInstanceByKey("FirstProcess", vars);
        runtimeService.setVariable("FirstProcess", "orderId", "ORD-1001");
        */
        
        
        return "executed MyFirstBPMN";  
    }
}