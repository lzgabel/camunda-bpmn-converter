package cn.lzgabel.camunda.converter;

import cn.lzgabel.camunda.converter.bean.DecisionRefBindingType;
import cn.lzgabel.camunda.converter.bean.ProcessDefinition;
import cn.lzgabel.camunda.converter.bean.event.intermediate.MessageIntermediateCatchEventDefinition;
import cn.lzgabel.camunda.converter.bean.event.intermediate.TimerIntermediateCatchEventDefinition;
import cn.lzgabel.camunda.converter.bean.event.start.MessageStartEventDefinition;
import cn.lzgabel.camunda.converter.bean.event.start.TimerDefinitionType;
import cn.lzgabel.camunda.converter.bean.event.start.TimerStartEventDefinition;
import cn.lzgabel.camunda.converter.bean.gateway.BranchNode;
import cn.lzgabel.camunda.converter.bean.gateway.ExclusiveGatewayDefinition;
import cn.lzgabel.camunda.converter.bean.gateway.InclusiveGatewayDefinition;
import cn.lzgabel.camunda.converter.bean.gateway.ParallelGatewayDefinition;
import cn.lzgabel.camunda.converter.bean.listener.ExecutionListener;
import cn.lzgabel.camunda.converter.bean.listener.TaskListener;
import cn.lzgabel.camunda.converter.bean.subprocess.CallActivityDefinition;
import cn.lzgabel.camunda.converter.bean.subprocess.SubProcessDefinition;
import cn.lzgabel.camunda.converter.bean.task.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class BpmnBuilderTest {

  @Rule public TestName testName = new TestName();

  private static final String OUT_PATH = "target/out/";

  @Test
  public void timer_date_start_event_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Timer Start\",\n"
            + "        \"nodeType\":\"startEvent\",\n"
            + "        \"eventType\":\"timer\",\n"
            + "        \"timerDefinitionType\":\"date\",\n"
            + "        \"timerDefinition\":\"2019-10-01T12:00:00+08:00\",\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeName\":\"User Task A\",\n"
            + "            \"nodeType\":\"userTask\",\n"
            + "            \"nextNode\":null\n"
            + "        }\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void timer_cycle_start_event_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Timer Start\",\n"
            + "        \"nodeType\":\"startEvent\",\n"
            + "        \"eventType\":\"timer\",\n"
            + "        \"timerDefinitionType\":\"cycle\",\n"
            + "        \"timerDefinition\":\"R1/PT2M\",\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeName\":\"User Task A\",\n"
            + "            \"nodeType\":\"userTask\",\n"
            + "            \"assignee\":\"abc\",\n"
            + "            \"nextNode\":null\n"
            + "        }\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void message_start_event_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Message Start\",\n"
            + "        \"nodeType\":\"startEvent\",\n"
            + "        \"eventType\":\"message\",\n"
            + "        \"messageName\":\"test-message-name\",\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeName\":\"User Task A\",\n"
            + "            \"nodeType\":\"userTask\",\n"
            + "            \"assignee\":\"abc\",\n"
            + "            \"nextNode\":null\n"
            + "        }\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void intermediate_timer_catch_event_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"User Task A\",\n"
            + "        \"nodeType\":\"userTask\",\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeName\":\"Intermediate Timer Catch Event\",\n"
            + "            \"nodeType\":\"intermediateCatchEvent\",\n"
            + "            \"eventType\":\"timer\",\n"
            + "            \"timerDefinition\":\"PT4M\",\n"
            + "            \"nextNode\":null\n"
            + "        }\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void intermediate_message_catch_event_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"User Task A\",\n"
            + "        \"nodeType\":\"userTask\",\n"
            + "        \"assignee\":\"abc\",\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeName\":\"Intermediate Message Catch Event\",\n"
            + "            \"nodeType\":\"intermediateCatchEvent\",\n"
            + "            \"eventType\":\"message\",\n"
            + "            \"messageName\":\"message-name\",\n"
            + "            \"correlationKey\":\"=test-correlationKey\",\n"
            + "            \"nextNode\":null\n"
            + "        }\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void receive_task_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Receive Task A\",\n"
            + "        \"nodeType\":\"receiveTask\",\n"
            + "        \"messageName\":\"message-name\",\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeName\":\"Receive Task B\",\n"
            + "            \"nodeType\":\"receiveTask\",\n"
            + "            \"messageName\":\"message-name\",\n"
            + "            \"nextNode\":null\n"
            + "        }\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void script_task_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Script Task A\",\n"
            + "        \"nodeType\":\"scriptTask\",\n"
            + "        \"scriptFormat\":\"groovy\",\n"
            + "        \"scriptText\":\"a+b\",\n"
            + "        \"resultVariable\":\"res\",\n"
            + "        \"nextNode\":null\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void user_task_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"User Task A\",\n"
            + "        \"nodeType\":\"userTask\",\n"
            + "        \"candidateGroups\":\"lizhi,xiaoming\",\n"
            + "        \"nextNode\":null\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void user_task_with_execution_listener_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"User Task A\",\n"
            + "        \"nodeType\":\"userTask\",\n"
            + "        \"candidateGroups\":\"lizhi,xiaoming\",\n"
            + "        \"nextNode\":null,\n"
            + "        \"listeners\":[\n"
            + "            {\n"
            + "                \"eventType\":\"start\",\n"
            + "                \"javaClass\":\"com.lzgabel.Test\"\n"
            + "            },\n"
            + "            {\n"
            + "                \"eventType\":\"end\",\n"
            + "                \"javaClass\":\"com.lzgabel.Test\"\n"
            + "            }\n"
            + "        ]\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void user_task_with_task_listener_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"User Task A\",\n"
            + "        \"nodeType\":\"userTask\",\n"
            + "        \"candidateGroups\":\"lizhi,xiaoming\",\n"
            + "        \"nextNode\":null,\n"
            + "        \"taskListeners\":[{\n"
            + "            \"eventType\":\"create\",\n"
            + "            \"javaClass\":\"com.lzgabel.Test\"\n"
            + "        }]\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void service_task_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Service Task A\",\n"
            + "        \"nodeType\":\"serviceTask\",\n"
            + "        \"topic\":\"test\",\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeName\":\"Service Task B\",\n"
            + "            \"nodeType\":\"serviceTask\",\n"
            + "            \"javaClass\":\"cn.lzgabel.delegate.TestDelegate\",\n"
            + "            \"nextNode\":{\n"
            + "                \"nodeName\":\"Service Task C\",\n"
            + "                \"nodeType\":\"serviceTask\",\n"
            + "                \"expression\":\"${boundaryEventDelegate.taskExpired()}\",\n"
            + "                \"resultVariable\":\"test\",\n"
            + "                \"nextNode\":{\n"
            + "                    \"nodeName\":\"Service Task D\",\n"
            + "                    \"nodeType\":\"serviceTask\",\n"
            + "                    \"delegateExpression\":\"${beanName}\",\n"
            + "                    \"nextNode\":null\n"
            + "                }\n"
            + "            }\n"
            + "        }\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void business_rule_task_from_json() throws IOException {

    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Business Rule Task A\",\n"
            + "        \"nodeType\":\"businessRuleTask\",\n"
            + "        \"decisionRef\":\"test-dmn-table-id\",\n"
            + "        \"resultVariable\":\"res\",\n"
            + "        \"nextNode\":null\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void manual_task_from_json() throws IOException {

    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Manual Task A\",\n"
            + "        \"nodeType\":\"manualTask\",\n"
            + "        \"nextNode\":null\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void sub_process_from_json() throws IOException {

    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Sub Process A\",\n"
            + "        \"nodeType\":\"subProcess\",\n"
            + "        \"childNode\":{\n"
            + // sub process nested process
            "            \"nodeName\":\"User Task A\",\n"
            + "            \"assignee\":\"a\",\n"
            + "            \"nodeType\":\"userTask\",\n"
            + "            \"nextNode\":null\n"
            + "        },\n"
            + "        \"nextNode\":{\n"
            + // Node after sub process
            "            \"nodeName\":\"User Task B\",\n"
            + "            \"assignee\":\"b\",\n"
            + "            \"nodeType\":\"userTask\",\n"
            + "            \"nextNode\":null\n"
            + "        }\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void call_activity_from_json() throws IOException {

    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Call Activity A\",\n"
            + "        \"calledElement\":\"call-mediax-process-id\",\n"
            + "        \"nodeType\":\"callActivity\",\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeName\":\"Call Activity B\",\n"
            + "            \"calledElement\":\"call-magic-process-id\",\n"
            + "            \"propagateAllChildVariablesEnabled\":\"true\",\n"
            + "            \"nodeType\":\"callActivity\",\n"
            + "            \"nextNode\":null\n"
            + "        }\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void exclusive_gateway_from_json() throws IOException {

    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Exclusive Gateway Start\",\n"
            + "        \"nodeType\":\"exclusiveGateway\",\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeName\":\"Exclusive Gateway End\",\n"
            + "            \"nodeType\":\"exclusiveGateway\",\n"
            + "            \"nextNode\":{\n"
            + "                \"nodeName\":\"User Task C\",\n"
            + "                \"assignee\":\"abd\",\n"
            + "                \"nodeType\":\"userTask\",\n"
            + "                \"nextNode\":null\n"
            + "            }\n"
            + "        },\n"
            + "        \"branchNodes\":[\n"
            + "            {\n"
            + "                \"nodeName\":\"condition A\",\n"
            + "                \"conditionExpression\":\"${id>1}\",\n"
            + "                \"nextNode\":{\n"
            + "                    \"nodeName\":\"User Task A\",\n"
            + "                    \"assignee\":\"abd\",\n"
            + "                    \"nodeType\":\"userTask\",\n"
            + "                    \"nextNode\":null\n"
            + "                }\n"
            + "            },\n"
            + "            {\n"
            + "                \"nodeName\":\"condition B\",\n"
            + "                \"isDefault\": true,\n"
            + "                \"nextNode\":{\n"
            + "                    \"nodeName\":\"User Task B\",\n"
            + "                    \"nodeType\":\"userTask\",\n"
            + "                    \"assignee\":\"abc\",\n"
            + "                    \"nextNode\":null\n"
            + "                }\n"
            + "            }\n"
            + "        ]\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void parallel_gateway_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"Parallel Gateway Start\",\n"
            + "        \"nodeType\":\"parallelGateway\",\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeName\":\"Parallel Gateway End\",\n"
            + "            \"nodeType\":\"parallelGateway\",\n"
            + "            \"nextNode\":null\n"
            + "        },\n"
            + "        \"branchNodes\":[\n"
            + "            {\n"
            + "                \"nodeName\":\"branch A\",\n"
            + "                \"nextNode\":{\n"
            + "                    \"nodeName\":\"User Task A\",\n"
            + "                    \"assignee\":\"abd\",\n"
            + "                    \"nodeType\":\"userTask\",\n"
            + "                    \"nextNode\":null\n"
            + "                }\n"
            + "            },\n"
            + "            {\n"
            + "                \"nodeName\":\"branch B\",\n"
            + "                \"nextNode\":{\n"
            + "                    \"nodeName\":\"User Task B\",\n"
            + "                    \"nodeType\":\"userTask\",\n"
            + "                    \"assignee\":\"abc\",\n"
            + "                    \"nextNode\":null\n"
            + "                }\n"
            + "            },\n"
            + "            {\n"
            + "                \"nodeName\":\"branch C\",\n"
            + "                \"nextNode\":{\n"
            + "                    \"nodeName\":\"User Task C\",\n"
            + "                    \"nodeType\":\"userTask\",\n"
            + "                    \"assignee\":\"abc\",\n"
            + "                    \"nextNode\":null\n"
            + "                }\n"
            + "            }\n"
            + "        ]\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void inclusive_gateway_from_json() throws IOException {
    String json =
        "{\n"
            + "    \"process\":{\n"
            + "        \"processId\":\"process-id\",\n"
            + "        \"name\":\"process-name\"\n"
            + "    },\n"
            + "    \"processNode\":{\n"
            + "        \"nodeName\":\"inclusive gateway start\",\n"
            + "        \"nodeType\":\"inclusiveGateway\",\n"
            + "        \"incoming\":null,\n"
            + "        \"nextNode\":{\n"
            + "            \"nodeType\":\"parallelGateway\",\n"
            + "            \"nextNode\":{\n"
            + "                \"nodeName\":\"user d\",\n"
            + "                \"nodeType\":\"userTask\",\n"
            + "                \"incoming\":null,\n"
            + "                \"nextNode\":null,\n"
            + "                \"assignee\":\"lizhi03\",\n"
            + "            },\n"
            + "            \"branchNodes\":[\n"
            + "\n"
            + "            ]\n"
            + "        },\n"
            + "        \"branchNodes\":[\n"
            + "            {\n"
            + "                \"nodeName\":\"分支1\",\n"
            + "                \"conditionExpression\":\"${id>1}\",\n"
            + "                \"nextNode\":{\n"
            + "                    \"nodeName\":\"user a\",\n"
            + "                    \"nodeType\":\"userTask\",\n"
            + "                    \"incoming\":null,\n"
            + "                    \"nextNode\":null,\n"
            + "                    \"assignee\":\"lizhi01\",\n"
            + "                    \"candidateGroups\":null\n"
            + "                },\n"
            + "                \"default\":false\n"
            + "            },\n"
            + "            {\n"
            + "                \"nodeName\":\"分支2\",\n"
            + "                \"conditionExpression\":\"${id<1}\",\n"
            + "                \"nextNode\":{\n"
            + "                    \"nodeName\":\"user b\",\n"
            + "                    \"nodeType\":\"userTask\",\n"
            + "                    \"incoming\":null,\n"
            + "                    \"nextNode\":null,\n"
            + "                    \"assignee\":\"lizhi02\",\n"
            + "                    \"candidateGroups\":null\n"
            + "                },\n"
            + "                \"default\":false\n"
            + "            },\n"
            + "            {\n"
            + "                \"nodeName\":\"默认分支\",\n"
            + "                \"conditionExpression\":null,\n"
            + "                \"nextNode\":{\n"
            + "                    \"nodeName\":\"user c\",\n"
            + "                    \"nodeType\":\"userTask\",\n"
            + "                    \"incoming\":null,\n"
            + "                    \"nextNode\":null,\n"
            + "                    \"assignee\":\"lizhi03\",\n"
            + "                    \"candidateGroups\":null\n"
            + "                },\n"
            + "                \"default\":true\n"
            + "            }\n"
            + "        ]\n"
            + "    }\n"
            + "}";

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(json);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  // ------  from processDefinition --------
  @Test
  public void timer_cycle_start_event_from_process_definition() throws IOException {

    TimerStartEventDefinition processNode =
        TimerStartEventDefinition.builder()
            .timerDefinitionType(TimerDefinitionType.CYCLE.value())
            .timerDefinition("R1/PT5M")
            .nextNode(UserTaskDefinition.builder().nodeName("lizhi").assignee("lizhi").build())
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);

    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void message_start_event_from_process_definition() throws IOException {
    MessageStartEventDefinition processNode =
        MessageStartEventDefinition.builder()
            .nodeName("message start")
            .messageName("test-message-name")
            .nextNode(UserTaskDefinition.builder().nodeName("lizhi").assignee("lizhi").build())
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);

    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void intermediate_timer_catch_event_from_process_definition() throws IOException {
    UserTaskDefinition processNode =
        UserTaskDefinition.builder()
            .nodeName("lizhi")
            .assignee("lizhi")
            .nextNode(
                TimerIntermediateCatchEventDefinition.builder()
                    .nodeName("timer catch a")
                    .timerDefinition("PT4M")
                    .build())
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void intermediate_message_catch_event_from_process_definition() throws IOException {
    UserTaskDefinition processNode =
        UserTaskDefinition.builder()
            .nodeName("lizhi")
            .assignee("lizhi")
            .nextNode(
                MessageIntermediateCatchEventDefinition.builder()
                    .nodeName("catch message a")
                    .messageName("test-message-name")
                    .build())
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void receive_task_from_process_definition() throws IOException {

    ReceiveTaskDefinition processNode =
        ReceiveTaskDefinition.builder()
            .nodeName("receive task a")
            .messageName("test-receive-message-name")
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);

    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void script_task_from_process_definition() throws IOException {

    ScriptTaskDefinition processNode =
        ScriptTaskDefinition.builder()
            .nodeName("script task a")
            .resultVariable("res")
            .scriptFormat("groovy")
            .scriptText("a + b")
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void user_task_from_process_definition() throws IOException {

    UserTaskDefinition processNode =
        UserTaskDefinition.builder()
            .nodeName("user task a")
            .assignee("lizhi")
            .candidateUsers("lizhi,shuwen")
            .candidateGroups("admin,member")
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void user_task_with_execution_listener_from_process_definition() throws IOException {

    UserTaskDefinition processNode =
        UserTaskDefinition.builder()
            .nodeName("user task a")
            .assignee("lizhi")
            .candidateGroups("lizhi")
            .listener(
                () ->
                    new ExecutionListener().setEventType("start").setJavaClass("com.lzgabel.Test"))
            .listener(
                () -> new ExecutionListener().setEventType("end").setJavaClass("com.lzgabel.Test"))
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }

    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void user_task_with_task_listener_from_process_definition() throws IOException {

    List<TaskListener> listeners =
        Collections.singletonList(
            new TaskListener().setEventType("create").setJavaClass("com.lzgabel.Test"));

    UserTaskDefinition processNode =
        UserTaskDefinition.builder()
            .nodeName("user task a")
            .assignee("lizhi")
            .candidateGroups("lizhi")
            .taskListeners(listeners)
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }

    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void service_task_from_process_definition() throws IOException {

    ServiceTaskDefinition taskDefinition =
        ServiceTaskDefinition.builder().nodeName("service task a").topic("test").build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(taskDefinition)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void business_rule_task_from_process_definition() throws IOException {

    BusinessRuleTaskDefinition processNode =
        BusinessRuleTaskDefinition.builder()
            .nodeName("business rule task a")
            .decisionRef("test-id")
            .decisionRefBinding(DecisionRefBindingType.VERSION.value())
            .decisionRefVersion("1")
            .resultVariable("res")
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void manual_task_from_process_definition() throws IOException {

    ManualTaskDefinition processNode =
        ManualTaskDefinition.builder().nodeName("manual rule task a").build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void sub_process_from_process_definition() throws IOException {

    UserTaskDefinition processNode =
        UserTaskDefinition.builder()
            .nodeName("user task a")
            .assignee("lizhi")
            .nextNode(
                SubProcessDefinition.builder()
                    .nodeName("sub-process")
                    .childNode(
                        UserTaskDefinition.builder()
                            .nodeName("user task b")
                            .assignee("lizhi2")
                            .build())
                    .nextNode(
                        UserTaskDefinition.builder()
                            .nodeName("user task c")
                            .assignee("lizhi")
                            .build())
                    .build())
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void call_activity_from_process_definition() throws IOException {

    CallActivityDefinition processNode =
        CallActivityDefinition.builder()
            .nodeName("call mediax process")
            .calledElement("call-mediax-id")
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void exclusive_gateway_from_process_definition() throws IOException {

    BranchNode branchNode1 =
        BranchNode.builder()
            .nodeName("分支1")
            .conditionExpression("${id>1}")
            .nextNode(UserTaskDefinition.builder().nodeName("user a").assignee("lizhi01").build())
            .build();

    BranchNode branchNode2 =
        BranchNode.builder()
            .nodeName("分支2")
            .isDefault(true)
            .nextNode(UserTaskDefinition.builder().nodeName("user b").assignee("lizhi02").build())
            .build();

    ExclusiveGatewayDefinition processNode =
        ExclusiveGatewayDefinition.builder()
            .nodeName("exclusive gateway start")
            .branchNode(branchNode1)
            .branchNode(branchNode2)
            .nextNode(
                ExclusiveGatewayDefinition.builder()
                    .nodeName("exclusive gateway end")
                    .nextNode(
                        UserTaskDefinition.builder().nodeName("user c").assignee("lizhi03").build())
                    .build())
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void parallel_gateway_from_process_definition() throws IOException {

    BranchNode branchNode1 =
        BranchNode.builder()
            .nodeName("分支1")
            .nextNode(UserTaskDefinition.builder().nodeName("user a").assignee("lizhi01").build())
            .build();

    BranchNode branchNode2 =
        BranchNode.builder()
            .nodeName("分支2")
            .nextNode(UserTaskDefinition.builder().nodeName("user b").assignee("lizhi02").build())
            .build();

    ParallelGatewayDefinition processNode =
        ParallelGatewayDefinition.builder()
            .nodeName("parallel gateway start")
            .branchNode(branchNode1)
            .branchNode(branchNode2)
            .nextNode(
                ParallelGatewayDefinition.builder()
                    .nodeName("parallel gateway end")
                    .nextNode(
                        UserTaskDefinition.builder().nodeName("user c").assignee("lizhi03").build())
                    .build())
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }

  @Test
  public void inclusive_gateway_from_process_definition() throws IOException {

    BranchNode branchNode1 =
        BranchNode.builder()
            .nodeName("分支1")
            .conditionExpression("${id>1}")
            .nextNode(UserTaskDefinition.builder().nodeName("user a").assignee("lizhi01").build())
            .build();

    BranchNode branchNode2 =
        BranchNode.builder()
            .nodeName("分支2")
            .conditionExpression("${id<1}")
            .nextNode(UserTaskDefinition.builder().nodeName("user b").assignee("lizhi02").build())
            .build();

    BranchNode branchNode3 =
        BranchNode.builder()
            .nodeName("默认分支")
            .isDefault(true)
            .nextNode(UserTaskDefinition.builder().nodeName("user c").assignee("lizhi03").build())
            .build();

    InclusiveGatewayDefinition processNode =
        InclusiveGatewayDefinition.builder()
            .nodeName("inclusive gateway start")
            .branchNode(branchNode1)
            .branchNode(branchNode2)
            .branchNode(branchNode3)
            .nextNode(
                ParallelGatewayDefinition.builder()
                    .nextNode(
                        UserTaskDefinition.builder().nodeName("user d").assignee("lizhi03").build())
                    .build())
            .build();

    ProcessDefinition processDefinition =
        ProcessDefinition.builder()
            .name("process-name")
            .processId("process-id")
            .processNode(processNode)
            .build();

    BpmnModelInstance bpmnModelInstance = BpmnBuilder.build(processDefinition);
    Path path = Paths.get(OUT_PATH + testName.getMethodName() + ".bpmn");
    if (path.toFile().exists()) {
      path.toFile().delete();
    }
    Files.createDirectories(path.getParent());
    Bpmn.writeModelToFile(Files.createFile(path).toFile(), bpmnModelInstance);
  }
}
