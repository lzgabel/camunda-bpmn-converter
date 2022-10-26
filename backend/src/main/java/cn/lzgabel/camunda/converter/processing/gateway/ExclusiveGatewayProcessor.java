package cn.lzgabel.camunda.converter.processing.gateway;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.gateway.BranchNode;
import cn.lzgabel.camunda.converter.bean.gateway.ExclusiveGatewayDefinition;
import com.google.common.collect.Lists;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ExclusiveGatewayBuilder;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;

/**
 * 〈功能简述〉<br>
 * 〈ExclusiveGateway节点类型详情设置〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public class ExclusiveGatewayProcessor
    extends AbstractGatewayProcessor<ExclusiveGatewayDefinition, AbstractFlowNodeBuilder> {

  @Override
  public String onComplete(
      AbstractFlowNodeBuilder flowNodeBuilder, ExclusiveGatewayDefinition flowNode)
      throws InvocationTargetException, IllegalAccessException {
    ExclusiveGatewayBuilder exclusiveGatewayBuilder =
        flowNodeBuilder.exclusiveGateway().name(flowNode.getNodeName());

    // create execution listener
    createExecutionListener(exclusiveGatewayBuilder, flowNode);
    List<BranchNode> branchNodes = flowNode.getBranchNodes();
    if (CollectionUtils.isEmpty(flowNode.getBranchNodes())
        && Objects.isNull(flowNode.getNextNode())) {
      return exclusiveGatewayBuilder.getElement().getId();
    }
    List<String> incoming = Lists.newArrayListWithCapacity(branchNodes.size());

    // 不存在任务节点的情况（即空分支: 见 branchNode-2）
    List<BranchNode> emptyNextNodeBranchNodes = Lists.newCopyOnWriteArrayList();
    for (BranchNode branchNode : branchNodes) {
      BaseDefinition nextNode = branchNode.getNextNode();

      String nodeName = branchNode.getNodeName();
      String expression = branchNode.getConditionExpression();

      // 记录分支条件中不存在任务节点的情况（即空分支: 见 branchNode-2）
      // ------------------------
      //
      //            --(branchNode-1)--> serviceTask(current) --> serviceTask  --
      // gateway -->                                                            --> gateway(merge)
      // --> serviceTask(nextNode)
      //            --(branchNode-2)-->  -----------------------------------  --
      //
      //
      if (Objects.isNull(nextNode)) {
        incoming.add(exclusiveGatewayBuilder.getElement().getId());
        BranchNode condition =
            BranchNode.builder().nodeName(nodeName).conditionExpression(expression).build();
        emptyNextNodeBranchNodes.add(condition);
        continue;
      }

      // 只生成一个任务，同时设置当前任务的条件
      nextNode.setIncoming(Collections.singletonList(exclusiveGatewayBuilder.getElement().getId()));
      String id =
          onCreate(
              moveToNode(exclusiveGatewayBuilder, exclusiveGatewayBuilder.getElement().getId()),
              nextNode);
      exclusiveGatewayBuilder.getElement().getOutgoing().stream()
          .forEach(
              sequenceFlow ->
                  conditionExpression(sequenceFlow, exclusiveGatewayBuilder, branchNode));
      if (Objects.nonNull(id)) {
        incoming.add(id);
      }
    }

    String id = exclusiveGatewayBuilder.getElement().getId();
    BaseDefinition nextNode = flowNode.getNextNode();
    if (Objects.nonNull(nextNode)) {
      nextNode.setIncoming(incoming);
      return merge(exclusiveGatewayBuilder, id, emptyNextNodeBranchNodes, nextNode);
    }
    return id;
  }

  protected void accept(
      AbstractFlowNodeBuilder flowNodeBuilder, String mergeId, List<BranchNode> conditions) {
    if (!(flowNodeBuilder instanceof ExclusiveGatewayBuilder)) {
      return;
    }
    //  针对分支条件中空分支场景 添加条件表达式
    ExclusiveGatewayBuilder exclusiveGatewayBuilder = (ExclusiveGatewayBuilder) flowNodeBuilder;
    if (CollectionUtils.isNotEmpty(conditions)) {
      List<SequenceFlow> sequenceFlows =
          moveToNode(exclusiveGatewayBuilder, mergeId).getElement().getIncoming().stream()
              // 获取从源 gateway 到目标节点 未设置条件表达式的节点
              .filter(
                  e ->
                      StringUtils.equals(
                          e.getSource().getId(), exclusiveGatewayBuilder.getElement().getId()))
              .collect(Collectors.toList());

      sequenceFlows.stream()
          .forEach(
              sequenceFlow -> {
                if (!conditions.isEmpty()) {
                  BranchNode condition = conditions.get(0);
                  conditionExpression(sequenceFlow, exclusiveGatewayBuilder, condition);
                  conditions.remove(0);
                }
              });
    }
  }

  private void conditionExpression(
      SequenceFlow sequenceFlow,
      ExclusiveGatewayBuilder exclusiveGatewayBuilder,
      BranchNode condition) {
    if (condition.isDefault()) {
      exclusiveGatewayBuilder.defaultFlow(sequenceFlow);
    }
    createConditionExpression(sequenceFlow, exclusiveGatewayBuilder, condition);
  }
}
