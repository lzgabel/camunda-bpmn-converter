package cn.lzgabel.camunda.converter.bean.task;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.BpmnElementType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 〈功能简述〉<br>
 * 〈接收任务定义〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ReceiveTaskDefinition extends BaseDefinition {

  private String messageName;

  @Override
  public String getNodeType() {
    return BpmnElementType.RECEIVE_TASK.getElementTypeName().get();
  }
}
