package cn.lzgabel.camunda.converter.bean.subprocess;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.BpmnElementType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * 〈功能简述〉<br>
 * 〈子流程定义〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class SubProcessDefinition extends BaseDefinition {

  @NonNull private BaseDefinition childNode;

  @Override
  public String getNodeType() {
    return BpmnElementType.SUB_PROCESS.getElementTypeName().get();
  }
}
