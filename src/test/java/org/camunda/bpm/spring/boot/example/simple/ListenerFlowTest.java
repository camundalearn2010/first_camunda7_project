package org.camunda.bpm.spring.boot.example.simple;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.taskService;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;

@Deployment(resources = "bpmn/Listeners_Flow.bpmn")
public class ListenerFlowTest extends AbstractProcessEngineRuleTest {

  @Test
  public void createTaskListenerCanSetProcessVariable() {
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("ListenerFlow");

    Task managerTask = taskService().createTaskQuery()
        .processInstanceId(processInstance.getId())
        .singleResult();

    assertThat(managerTask).isNotNull();
    assertThat(runtimeService().getVariable(processInstance.getId(), "approver")).isEqualTo("demo");
    assertThat(managerTask.getPriority()).isEqualTo(50);

    taskService().complete(managerTask.getId());

    assertThat(runtimeService().getVariable(processInstance.getId(), "isApproved")).isEqualTo(true);
  }
}
