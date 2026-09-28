package org.camunda.bpm.spring.boot.example.simple.controller;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
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
       // ProcessInstantiationBuilder instance = (ProcessInstantiationBuilder) processEngine.getRuntimeService().startProcessInstanceByKey("FirstProcess");
       // instance.executeWithVariablesInReturn();
        return "executed MyFirstBPMN";  
    }
}