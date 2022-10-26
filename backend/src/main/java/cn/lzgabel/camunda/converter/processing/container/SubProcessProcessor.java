package cn.lzgabel.camunda.converter.processing.container;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.subprocess.SubProcessDefinition;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EmbeddedSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;

/**
 * 〈功能简述〉<br>
 * 〈SubProcess节点类型详情设置〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public class SubProcessProcessor
    implements BpmnElementProcessor<SubProcessDefinition, AbstractFlowNodeBuilder> {

  @Override
  public String onComplete(AbstractFlowNodeBuilder flowNodeBuilder, SubProcessDefinition flowNode)
      throws InvocationTargetException, IllegalAccessException {
    SubProcessBuilder subProcessBuilder = flowNodeBuilder.subProcess();
    EmbeddedSubProcessBuilder embeddedSubProcessBuilder = subProcessBuilder.embeddedSubProcess();

    // create execution listener
    createExecutionListener(subProcessBuilder, flowNode);

    // 子流程内部创建开始
    StartEventBuilder startEventBuilder = embeddedSubProcessBuilder.startEvent();
    subProcessBuilder.getElement().setName(flowNode.getNodeName());
    String lastNode = startEventBuilder.getElement().getId();
    // 创建子流程节点
    BaseDefinition childNode = flowNode.getChildNode();
    if (Objects.nonNull(childNode)) {
      lastNode =
          onCreate(
              moveToNode(subProcessBuilder, startEventBuilder.getElement().getId()), childNode);
    }
    // 子流程内部创建结束
    moveToNode(startEventBuilder, lastNode).endEvent();

    // 如果当前任务还有后续任务，则遍历创建后续任务
    String id = subProcessBuilder.getElement().getId();
    BaseDefinition nextNode = flowNode.getNextNode();
    if (Objects.nonNull(nextNode)) {
      return onCreate(moveToNode(subProcessBuilder, id), nextNode);
    }
    return subProcessBuilder.getElement().getId();
  }
}
