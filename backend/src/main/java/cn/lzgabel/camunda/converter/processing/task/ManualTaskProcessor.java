package cn.lzgabel.camunda.converter.processing.task;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.task.ManualTaskDefinition;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.instance.ManualTask;

/**
 * 〈功能简述〉<br>
 * 〈ManualTask节点类型详情设置〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public class ManualTaskProcessor
    implements BpmnElementProcessor<ManualTaskDefinition, AbstractFlowNodeBuilder> {

  @Override
  public String onComplete(AbstractFlowNodeBuilder flowNodeBuilder, ManualTaskDefinition flowNode)
      throws InvocationTargetException, IllegalAccessException {

    String nodeType = flowNode.getNodeType();
    String nodeName = flowNode.getNodeName();
    ManualTask manualTask = (ManualTask) createInstance(flowNodeBuilder, nodeType);
    String id = manualTask.getId();
    manualTask.setName(nodeName);

    // 如果当前任务还有后续任务，则遍历创建后续任务
    BaseDefinition nextNode = flowNode.getNextNode();
    if (Objects.nonNull(nextNode)) {
      return onCreate(moveToNode(flowNodeBuilder, id), nextNode);
    } else {
      return id;
    }
  }
}
