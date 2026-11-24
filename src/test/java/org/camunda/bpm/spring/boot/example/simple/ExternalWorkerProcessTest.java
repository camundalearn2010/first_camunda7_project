package org.camunda.bpm.spring.boot.example.simple;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.processEngine;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;

@Deployment(resources = "bpmn/externalWorkerExample.bpmn")
public class ExternalWorkerProcessTest extends AbstractProcessEngineRuleTest {

  @Test
  public void externalTaskWaitsForWorkerAndCompletesProcess() {
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
        "ExternalWorkerExample",
        Variables.putValue("name", "Ada"));

    assertThat(processInstance).isWaitingAt("ExternalTask_Greeting");

    List<LockedExternalTask> fetchedTasks = processEngine().getExternalTaskService()
        .fetchAndLock(1, "test-worker")
        .topic("greet-customer", 10000L)
        .execute();
    LockedExternalTask externalTask = fetchedTasks.get(0);
    assertNotNull(externalTask);
    assertEquals("greet-customer", externalTask.getTopicName());

    processEngine().getExternalTaskService().complete(
        externalTask.getId(),
        "test-worker",
        Collections.singletonMap("greeting", "Hello, Ada!"));

    assertThat(processInstance).isEnded();
    HistoricVariableInstance greeting = processEngine().getHistoryService()
        .createHistoricVariableInstanceQuery()
        .processInstanceId(processInstance.getId())
        .variableName("greeting")
        .singleResult();
    assertNotNull(greeting);
    assertEquals("Hello, Ada!", greeting.getValue());
  }
}
