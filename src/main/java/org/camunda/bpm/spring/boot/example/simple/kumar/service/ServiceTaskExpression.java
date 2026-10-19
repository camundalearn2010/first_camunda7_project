package org.camunda.bpm.spring.boot.example.simple.kumar.service;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.stereotype.Component;

@Component
public class ServiceTaskExpression {
    public String getMessage() {

        System.out.println("Service Task Expression invoked");

        return "Success";        
    }

}
