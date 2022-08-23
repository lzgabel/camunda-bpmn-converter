package cn.lzgabel.camunda.converter.bean;

import java.util.Arrays;
import java.util.Optional;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public enum BpmnElementType {
  // Default
  UNSPECIFIED(null, null),

  // Containers
  PROCESS("process", Process.class),
  SUB_PROCESS("subProcess", SubProcess.class),
  EVENT_SUB_PROCESS(null, null),

  // Events
  START_EVENT("startEvent", StartEvent.class),
  INTERMEDIATE_CATCH_EVENT("intermediateCatchEvent", IntermediateCatchEvent.class),
  INTERMEDIATE_THROW_EVENT("intermediateThrowEvent", IntermediateThrowEvent.class),
  BOUNDARY_EVENT("boundaryEvent", BoundaryEvent.class),
  END_EVENT("endEvent", EndEvent.class),

  // Tasks
  SERVICE_TASK("serviceTask", ServiceTask.class),
  RECEIVE_TASK("receiveTask", ReceiveTask.class),
  USER_TASK("userTask", UserTask.class),
  MANUAL_TASK("manualTask", ManualTask.class),

  // Gateways
  EXCLUSIVE_GATEWAY("exclusiveGateway", ExclusiveGateway.class),
  PARALLEL_GATEWAY("parallelGateway", ParallelGateway.class),
  EVENT_BASED_GATEWAY("eventBasedGateway", EventBasedGateway.class),

  // Other
  SEQUENCE_FLOW("sequenceFlow", SequenceFlow.class),
  MULTI_INSTANCE_BODY(null, null),
  CALL_ACTIVITY("callActivity", CallActivity.class),
  BUSINESS_RULE_TASK("businessRuleTask", BusinessRuleTask.class),
  SCRIPT_TASK("scriptTask", ScriptTask.class),
  SEND_TASK("sendTask", SendTask.class);

  private final String elementTypeName;
  private final Class<? extends ModelElementInstance> elementTypeClass;

  private BpmnElementType(
      final String elementTypeName, final Class<? extends ModelElementInstance> elementTypeClass) {
    this.elementTypeName = elementTypeName;
    this.elementTypeClass = elementTypeClass;
  }

  public Optional<String> getElementTypeName() {
    return Optional.ofNullable(this.elementTypeName);
  }

  public Optional<Class<? extends ModelElementInstance>> getElementTypeClass() {
    return Optional.ofNullable(this.elementTypeClass);
  }

  public static BpmnElementType bpmnElementTypeFor(final String elementTypeName) {
    return Arrays.stream(values())
        .filter(
            bpmnElementType ->
                bpmnElementType.elementTypeName != null
                    && bpmnElementType.elementTypeName.equals(elementTypeName))
        .findFirst()
        .orElseThrow(
            () -> new RuntimeException("Unsupported BPMN element of type " + elementTypeName));
  }
}
