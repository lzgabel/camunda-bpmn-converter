package cn.lzgabel.camunda.converter.bean.gateway;

import cn.lzgabel.camunda.converter.bean.BpmnElementType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 〈功能简述〉<br>
 * 〈并行网关〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ParallelGatewayDefinition extends GatewayDefinition {

  @Override
  public String getNodeType() {
    return BpmnElementType.PARALLEL_GATEWAY.getElementTypeName().get();
  }
}
