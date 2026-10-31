package org.camunda.bpm.spring.boot.example.simple.kumar.delegates;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class GetCollectionList implements JavaDelegate {
    @Override
    public void execute(org.camunda.bpm.engine.delegate.DelegateExecution execution) throws Exception {
        // Your custom logic here
        System.out.println("Executing from Java Collection Delegate");

        List<String> collectionList = Arrays.asList("Orange", "Apple", "Banana");
        List<String> vegcollectionList = Arrays.asList("Carrot", "Tomato", "Brinjal");
        execution.setVariable("CollectionVariableList", collectionList);
        execution.setVariable("VegCollectionVariableList", vegcollectionList);
        System.out.println("Collection Size: " + collectionList.size());
        System.out.println("Vegetable Collection Size: " + vegcollectionList.size());
    }

}
