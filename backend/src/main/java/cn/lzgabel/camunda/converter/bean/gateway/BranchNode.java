package cn.lzgabel.camunda.converter.bean.gateway;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 〈功能简述〉<br>
 * 〈分支节点〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class BranchNode {

  private String nodeName;

  private String conditionExpression;

  private BaseDefinition nextNode;
}
