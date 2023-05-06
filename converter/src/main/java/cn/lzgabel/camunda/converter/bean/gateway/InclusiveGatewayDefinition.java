package cn.lzgabel.camunda.converter.bean.gateway;

import cn.lzgabel.camunda.converter.bean.BpmnElementType;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 〈功能简述〉<br>
 * 〈包容网关〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@SuperBuilder
@NoArgsConstructor
public class InclusiveGatewayDefinition extends GatewayDefinition {

  @Override
  public String getNodeType() {
    return BpmnElementType.INCLUSIVE_GATEWAY.getElementTypeName().get();
  }
}
