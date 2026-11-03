package org.camunda.bpm.spring.boot.example.simple.kumar.delegates;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CheckCreditScore implements JavaDelegate {
    @Override
    public void execute(org.camunda.bpm.engine.delegate.DelegateExecution execution) throws Exception {
        // Your custom logic here
        System.out.println("Executing from CheckCreditScore Delegate");
        try{
           Integer creditScore = (Integer) execution.getVariable("CreditScore");

            if (creditScore > 700) {
                execution.setVariable("isApproved", true);  
                }
                else if (creditScore > 300 && creditScore <= 700) {
                execution.setVariable("isApproved", false);     
                }
                else  {
                throw new BpmnError("CREDIT_ERROR", "Credit score is below the acceptable threshold  / undefined.");
                }

            System.out.println("Credit score is : " + creditScore);
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            throw new BpmnError("ANY_ERROR", "Error in CheckCreditScore Delegate: " + e.getMessage());
        }

        
    }

}
