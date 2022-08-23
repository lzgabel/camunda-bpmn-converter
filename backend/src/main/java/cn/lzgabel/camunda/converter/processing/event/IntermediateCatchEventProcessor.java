package cn.lzgabel.camunda.converter.processing.event;

import cn.lzgabel.camunda.converter.bean.event.intermediate.IntermediateCatchEventDefinition;
import cn.lzgabel.camunda.converter.bean.event.intermediate.MessageIntermediateCatchEventDefinition;
import cn.lzgabel.camunda.converter.bean.event.intermediate.TimerIntermediateCatchEventDefinition;
import cn.lzgabel.camunda.converter.bean.event.start.EventType;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;

/**
 * 〈功能简述〉<br>
 * 〈IntermediateCatchEvent节点类型详情设置〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public class IntermediateCatchEventProcessor
    implements BpmnElementProcessor<IntermediateCatchEventDefinition, AbstractFlowNodeBuilder> {

  @Override
  public String onComplete(
      AbstractFlowNodeBuilder flowNodeBuilder, IntermediateCatchEventDefinition flowNode) {
    String nodeName = flowNode.getNodeName();
    String eventType = flowNode.getEventType();
    if (EventType.TIMER.isEqual(eventType)) {
      TimerIntermediateCatchEventDefinition timer =
          (TimerIntermediateCatchEventDefinition) flowNode;
      String timerDefinition = timer.getTimerDefinition();
      return flowNodeBuilder
          .intermediateCatchEvent()
          .timerWithDuration(timerDefinition)
          .getElement()
          .getId();
    } else if (EventType.MESSAGE.isEqual(eventType)) {
      MessageIntermediateCatchEventDefinition message =
          (MessageIntermediateCatchEventDefinition) flowNode;
      String messageName = message.getMessageName();
      return flowNodeBuilder
          .intermediateCatchEvent()
          .name(nodeName)
          .message(messageName)
          .getElement()
          .getId();
    }
    return null;
  }
}
