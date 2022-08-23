package cn.lzgabel.camunda.converter.processing.gateway;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @since 1.0.0
 */
import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.gateway.BranchNode;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ExclusiveGatewayBuilder;
import org.camunda.bpm.model.bpmn.instance.ConditionExpression;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

public abstract class AbstractGatewayProcessor<
        E extends BaseDefinition, T extends AbstractFlowNodeBuilder>
    implements BpmnElementProcessor<E, T> {

  String merge(
      AbstractFlowNodeBuilder flowNodeBuilder,
      String fromId,
      List<BranchNode> conditions,
      BaseDefinition flowNode)
      throws InvocationTargetException, IllegalAccessException {
    List<String> incoming = flowNode.getIncoming();

    // ------------------------
    //
    //            ---> serviceTask(current) --> serviceTask  --
    // gateway -->                                             --> gateway(merge) -->
    // serviceTask(nextNode)
    //            -->  serviceTask          --> serviceTask  --
    //
    //

    // 继续沿新分支，创建新的元素类型(参见 current 所在分支))
    if (incoming == null || incoming.size() == 0) {
      return onCreate(moveToNode(flowNodeBuilder, fromId), flowNode);
    } else {
      // 聚合节点所有入度边连接
      flowNode.setIncoming(incoming);

      // 1.0 先对聚合节点进行所有的边连接(参见 merge), 临时暂存聚合节点 nextNode 后续节点(参见 nextNode)
      BaseDefinition tempNextNode = flowNode.getNextNode();
      flowNode.setNextNode(null);

      // 创建聚合节点(参见 merge)
      String mergeId = onCreate(moveToNode(flowNodeBuilder, incoming.get(0)), flowNode);
      for (int i = 1; i < incoming.size(); i++) {
        moveToNode(flowNodeBuilder, incoming.get(i)).connectTo(mergeId);
      }

      accept(flowNodeBuilder, mergeId, conditions);

      // 1.1 边连接完成后，进行聚合节点 nextNode 后续节点创建
      if (Objects.nonNull(tempNextNode)) {
        return onCreate(moveToNode(flowNodeBuilder, mergeId), tempNextNode);
      } else {
        return mergeId;
      }
    }
  }

  /**
   * 排他网关: 针对分支条件中空分支场景 添加条件表达式
   *
   * @param flowNodeBuilder
   * @param mergeId
   * @param conditions
   */
  private void accept(
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
                  conditionExpress(sequenceFlow, exclusiveGatewayBuilder, condition);
                  conditions.remove(0);
                }
              });
    }
  }

  void conditionExpress(
      SequenceFlow sequenceFlow,
      ExclusiveGatewayBuilder exclusiveGatewayBuilder,
      BranchNode condition) {
    String nodeName = condition.getNodeName();
    String expression = condition.getConditionExpression();
    if (StringUtils.isBlank(sequenceFlow.getName()) && StringUtils.isNotBlank(nodeName)) {
      sequenceFlow.setName(nodeName);
    }
    // 设置条件表达式
    if (Objects.isNull(sequenceFlow.getConditionExpression())
        && StringUtils.isNotBlank(expression)) {
      ConditionExpression conditionExpression =
          createInstance(exclusiveGatewayBuilder, ConditionExpression.class);
      sequenceFlow.setConditionExpression(conditionExpression);
    }
  }

  private <T extends ModelElementInstance> T createInstance(
      AbstractFlowNodeBuilder<?, ?> abstractFlowNodeBuilder, Class<T> clazz) {
    return abstractFlowNodeBuilder.getElement().getModelInstance().newInstance(clazz);
  }
}
