package org.camunda.bpm.spring.boot.example.simple;

import java.util.Collections;

import jakarta.annotation.PreDestroy;
import org.camunda.bpm.client.ExternalTaskClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "demo.external-worker", name = "enabled", havingValue = "true")
public class ExternalTaskWorker {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExternalTaskWorker.class);

  private final String baseUrl;
  private final String topic;
  private ExternalTaskClient client;

  public ExternalTaskWorker(
      @Value("${demo.external-worker.base-url}") String baseUrl,
      @Value("${demo.external-worker.topic}") String topic) {
    this.baseUrl = baseUrl;
    this.topic = topic;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void start() {
    client = ExternalTaskClient.create()
        .baseUrl(baseUrl)
        .asyncResponseTimeout(10000L)
        .build();

    client.subscribe(topic)
        .lockDuration(30000L)
        .handler((externalTask, externalTaskService) -> {
          String name = externalTask.getVariable("name");
          String workerId = externalTask.getWorkerId();
          String processInstanceId = externalTask.getProcessInstanceId();
          //String workerId = externalTask.get();
          String greeting = "Hello, " + name + "! Worker ID: " + workerId + "; Process Instance ID: " + processInstanceId;
          
          externalTaskService.complete(externalTask, Collections.singletonMap("greeting", greeting));
          LOGGER.info("Completed external task {} with greeting '{}'", externalTask.getId(), greeting);
        })
        .open();

    LOGGER.info("External task worker subscribed to topic '{}' at {}", topic, baseUrl);
  }

  @PreDestroy
  public void stop() {
    if (client != null) {
      client.stop();
    }
  }
}
