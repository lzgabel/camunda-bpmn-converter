package cn.lzgabel.camunda.converter.processing.task;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.DecisionRefBindingType;
import cn.lzgabel.camunda.converter.bean.task.BusinessRuleTaskDefinition;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.BusinessRuleTaskBuilder;
import org.camunda.bpm.model.bpmn.instance.BusinessRuleTask;

/**
 * 〈功能简述〉<br>
 * 〈BusinessRuleTask节点类型详情设置〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public class BusinessRuleTaskProcessor
    implements BpmnElementProcessor<BusinessRuleTaskDefinition, AbstractFlowNodeBuilder> {

  @Override
  public String onComplete(
      AbstractFlowNodeBuilder flowNodeBuilder, BusinessRuleTaskDefinition flowNode)
      throws InvocationTargetException, IllegalAccessException {
    String nodeType = flowNode.getNodeType();
    String nodeName = flowNode.getNodeName();
    String decisionRef = flowNode.getDecisionRef();
    String decisionRefBinding = flowNode.getDecisionRefBinding();
    String decisionRefTenantId = flowNode.getDecisionRefTenantId();
    String resultVariable = flowNode.getResultVariable();

    // 创建 ReceiveTask
    BusinessRuleTask scriptTask = (BusinessRuleTask) createInstance(flowNodeBuilder, nodeType);
    BusinessRuleTaskBuilder taskBuilder = scriptTask.builder();
    taskBuilder
        .name(nodeName)
        .camundaDecisionRef(decisionRef)
        .camundaResultVariable(resultVariable);

    if (DecisionRefBindingType.VERSION.isEquals(decisionRefBinding)) {
      taskBuilder.camundaDecisionRefVersion(flowNode.getDecisionRefVersion());
    }

    if (DecisionRefBindingType.VERSION_TAG.isEquals(decisionRefBinding)) {
      taskBuilder.camundaDecisionRefVersionTag(flowNode.getDecisionRefVersionTag());
    }

    if (StringUtils.isNotBlank(decisionRefTenantId)) {
      taskBuilder.camundaDecisionRefTenantId(decisionRefTenantId);
    }

    // create execution listener
    createExecutionListener(taskBuilder, flowNode);

    String id = scriptTask.getId();

    // 如果当前任务还有后续任务，则遍历创建后续任务
    BaseDefinition nextNode = flowNode.getNextNode();
    if (Objects.nonNull(nextNode)) {
      return onCreate(moveToNode(flowNodeBuilder, id), nextNode);
    } else {
      return id;
    }
  }
}
