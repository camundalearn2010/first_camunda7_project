package org.camunda.bpm.spring.boot.example.simple.kumar.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class TestJavaClassExample implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Your custom logic here
        System.out.println("Executing from Java Test Class Example");
        String city = (String) execution.getVariable("City");
        System.out.println("Print the city value from InputVariable of this Java Class task: " + city);

        execution.setVariable("City_1", "New York");
    }

}
