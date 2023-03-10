package cn.lzgabel.camunda.converter.processing.task;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.task.ServiceTaskDefinition;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ServiceTaskBuilder;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;

/**
 * 〈功能简述〉<br>
 * 〈ServiceTask节点类型详情设置〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public class ServiceTaskProcessor
    implements BpmnElementProcessor<ServiceTaskDefinition, AbstractFlowNodeBuilder> {

  @Override
  public String onComplete(AbstractFlowNodeBuilder flowNodeBuilder, ServiceTaskDefinition flowNode)
      throws InvocationTargetException, IllegalAccessException {

    String nodeType = flowNode.getNodeType();
    String nodeName = flowNode.getNodeName();

    ServiceTask serviceTask = (ServiceTask) createInstance(flowNodeBuilder, nodeType);
    serviceTask.setName(nodeName);

    ServiceTaskBuilder serviceTaskBuilder = serviceTask.builder();

    // set implementation
    Optional.ofNullable(flowNode.getTopic())
        .filter(StringUtils::isNotBlank)
        .ifPresent(serviceTaskBuilder::camundaExternalTask);

    Optional.ofNullable(flowNode.getExpression())
        .filter(StringUtils::isNotBlank)
        .map(
            expression -> {
              serviceTaskBuilder.camundaExpression(expression);
              return flowNode.getResultVariable();
            })
        .filter(StringUtils::isNotBlank)
        .ifPresent(serviceTaskBuilder::camundaResultVariable);

    Optional.ofNullable(flowNode.getDelegateExpression())
        .filter(StringUtils::isNotBlank)
        .ifPresent(serviceTaskBuilder::camundaDelegateExpression);

    Optional.ofNullable(flowNode.getJavaClass())
        .filter(StringUtils::isNotBlank)
        .ifPresent(serviceTaskBuilder::camundaClass);

    // create execution listener
    createExecutionListener(serviceTaskBuilder, flowNode);

    String id = serviceTask.getId();
    // 如果当前任务还有后续任务，则遍历创建后续任务
    BaseDefinition nextNode = flowNode.getNextNode();
    if (Objects.nonNull(nextNode)) {
      return onCreate(moveToNode(flowNodeBuilder, id), nextNode);
    } else {
      return id;
    }
  }
}
