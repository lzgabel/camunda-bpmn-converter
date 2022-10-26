package cn.lzgabel.camunda.converter.processing.container;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.subprocess.CallActivityDefinition;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.CallActivityBuilder;

/**
 * 〈功能简述〉<br>
 * 〈CallActivity节点类型详情设置〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public class CallActivityProcessor
    implements BpmnElementProcessor<CallActivityDefinition, AbstractFlowNodeBuilder> {

  @Override
  public String onComplete(AbstractFlowNodeBuilder flowNodeBuilder, CallActivityDefinition flowNode)
      throws InvocationTargetException, IllegalAccessException {
    CallActivityBuilder callActivityBuilder = flowNodeBuilder.callActivity();

    // create execution listener
    createExecutionListener(callActivityBuilder, flowNode);

    callActivityBuilder.getElement().setName(flowNode.getNodeName());
    callActivityBuilder.calledElement(flowNode.getCalledElement());
    String id = callActivityBuilder.getElement().getId();
    BaseDefinition childNode = flowNode.getNextNode();
    if (Objects.nonNull(childNode)) {
      return onCreate(moveToNode(callActivityBuilder, id), childNode);
    }
    return id;
  }
}
