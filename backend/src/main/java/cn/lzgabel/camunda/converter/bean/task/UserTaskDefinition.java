package cn.lzgabel.camunda.converter.bean.task;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.BpmnElementType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 〈功能简述〉<br>
 * 〈用户任务定义〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class UserTaskDefinition extends BaseDefinition {

  private String assignee;

  private String candidateGroups;

  @Override
  public String getNodeType() {
    return BpmnElementType.USER_TASK.getElementTypeName().get();
  }
}
