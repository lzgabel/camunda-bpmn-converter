package cn.lzgabel.camunda.converter.bean.task;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.BpmnElementType;
import cn.lzgabel.camunda.converter.bean.DecisionRefBindingType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 〈功能简述〉<br>
 * 〈业务规则定义〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class BusinessRuleTaskDefinition extends BaseDefinition {

  private String decisionRef;

  private String decisionRefBinding = DecisionRefBindingType.LATEST.value();

  private String decisionRefVersion;

  private String decisionRefVersionTag;

  private String decisionRefTenantId;

  private String resultVariable;

  @Override
  public String getNodeType() {
    return BpmnElementType.BUSINESS_RULE_TASK.getElementTypeName().get();
  }
}
