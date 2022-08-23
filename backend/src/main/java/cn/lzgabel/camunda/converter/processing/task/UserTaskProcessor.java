package cn.lzgabel.camunda.converter.processing.task;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.task.UserTaskDefinition;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.UserTaskBuilder;
import org.camunda.bpm.model.bpmn.instance.UserTask;

/**
 * 〈功能简述〉<br>
 * 〈UserTask节点类型详情设置〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public class UserTaskProcessor
    implements BpmnElementProcessor<UserTaskDefinition, AbstractFlowNodeBuilder> {

  @Override
  public String onComplete(AbstractFlowNodeBuilder flowNodeBuilder, UserTaskDefinition flowNode)
      throws InvocationTargetException, IllegalAccessException {

    String nodeType = flowNode.getNodeType();
    String nodeName = flowNode.getNodeName();
    String assignee = flowNode.getAssignee();
    String candidateGroups = flowNode.getCandidateGroups();

    UserTask userTask = (UserTask) createInstance(flowNodeBuilder, nodeType);
    userTask.setName(nodeName);
    // set assignee and candidateGroups
    UserTaskBuilder userTaskBuilder = userTask.builder();
    if (StringUtils.isNotBlank(assignee)) {
      userTaskBuilder.camundaAssignee(assignee);
    }

    if (StringUtils.isNotBlank(candidateGroups)) {
      userTaskBuilder.camundaCandidateGroups(candidateGroups);
    }

    String id = userTask.getId();
    // 如果当前任务还有后续任务，则遍历创建后续任务
    BaseDefinition nextNode = flowNode.getNextNode();
    if (Objects.nonNull(nextNode)) {
      return onCreate(moveToNode(flowNodeBuilder, id), nextNode);
    } else {
      return id;
    }
  }
}
