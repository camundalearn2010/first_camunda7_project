package org.camunda.bpm.spring.boot.example.simple.kumar.delegates;

import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("kumarDelegateTestJavaDelegate")
public class TestJavaDelegate implements JavaDelegate {
    @Override
    public void execute(org.camunda.bpm.engine.delegate.DelegateExecution execution) throws Exception {
        // Your custom logic here
        System.out.println("Executing from Java Delegate");
    }

}
