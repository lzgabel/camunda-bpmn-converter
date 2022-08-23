package cn.lzgabel.camunda.converter.bean.event.start;

import cn.lzgabel.camunda.converter.bean.BpmnElementType;
import cn.lzgabel.camunda.converter.bean.event.EventDefinition;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "eventType",
    visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = TimerStartEventDefinition.class, name = "timer"),
  @JsonSubTypes.Type(value = NoneStartEventDefinition.class, name = "none"),
  @JsonSubTypes.Type(value = MessageStartEventDefinition.class, name = "message")
})
public abstract class StartEventDefinition extends EventDefinition {

  @Override
  public String getNodeType() {
    return BpmnElementType.START_EVENT.getElementTypeName().get();
  }
}
