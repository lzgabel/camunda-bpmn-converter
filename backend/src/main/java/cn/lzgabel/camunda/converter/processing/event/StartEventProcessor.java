package cn.lzgabel.camunda.converter.processing.event;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.BpmnElementType;
import cn.lzgabel.camunda.converter.bean.event.start.*;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessor;
import cn.lzgabel.camunda.converter.processing.BpmnElementProcessors;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;

/**
 * 〈功能简述〉<br>
 * 〈StartEvent节点类型详情设置〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public class StartEventProcessor
    implements BpmnElementProcessor<StartEventDefinition, StartEventBuilder> {

  @Override
  public String onComplete(StartEventBuilder start, StartEventDefinition flowNode)
      throws InvocationTargetException, IllegalAccessException {
    // 事件类型 timer/message 默认：none
    String eventType = flowNode.getEventType();
    String nodeName = flowNode.getNodeName();
    start.name(nodeName);
    if (StringUtils.isNotBlank(eventType)) {
      if (EventType.TIMER.isEqual(eventType)) {
        TimerStartEventDefinition timer = (TimerStartEventDefinition) flowNode;
        // timer 定义类型： date/cycle/duration
        String timerDefinitionType = timer.getTimerDefinitionType();
        if (TimerDefinitionType.DATE.isEqual(timerDefinitionType)) {
          String timerDefinition = timer.getTimerDefinition();
          start.timerWithDate(timerDefinition);
        } else if (TimerDefinitionType.DURATION.isEqual(timerDefinitionType)) {
          String timerDefinition = timer.getTimerDefinition();
          start.timerWithDuration(timerDefinition);
        } else if (TimerDefinitionType.CYCLE.isEqual(timerDefinitionType)) {
          String timerDefinition = timer.getTimerDefinition();
          start.timerWithCycle(timerDefinition);
        }
      } else if (EventType.MESSAGE.isEqual(eventType)) {
        MessageStartEventDefinition message = (MessageStartEventDefinition) flowNode;
        String messageName = message.getMessageName();
        start.message(messageName);
      }
    }

    // create execution listener
    createExecutionListener(start, flowNode);

    String id = start.getElement().getId();
    BaseDefinition nextNode = flowNode.getNextNode();
    if (Objects.isNull(nextNode)) {
      return id;
    }

    BpmnElementType elementType = BpmnElementType.bpmnElementTypeFor(nextNode.getNodeType());
    return BpmnElementProcessors.getProcessor(elementType)
        .onComplete(moveToNode(start, id), nextNode);
  }
}
