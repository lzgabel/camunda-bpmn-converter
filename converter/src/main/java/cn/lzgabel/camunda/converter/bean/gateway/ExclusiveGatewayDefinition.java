package cn.lzgabel.camunda.converter.bean.gateway;

import cn.lzgabel.camunda.converter.bean.BpmnElementType;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 〈功能简述〉<br>
 * 〈排他网关定义〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@SuperBuilder
@NoArgsConstructor
public class ExclusiveGatewayDefinition extends GatewayDefinition {

  @Override
  public String getNodeType() {
    return BpmnElementType.EXCLUSIVE_GATEWAY.getElementTypeName().get();
  }
}
