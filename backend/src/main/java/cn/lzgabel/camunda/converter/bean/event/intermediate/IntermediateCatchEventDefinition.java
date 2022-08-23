package cn.lzgabel.camunda.converter.bean.event.intermediate;

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
  @JsonSubTypes.Type(value = TimerIntermediateCatchEventDefinition.class, name = "timer"),
  @JsonSubTypes.Type(value = MessageIntermediateCatchEventDefinition.class, name = "message")
})
public class IntermediateCatchEventDefinition extends EventDefinition {

  @Override
  public String getNodeType() {
    return "intermediateCatchEvent";
  }
}
