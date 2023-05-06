package cn.lzgabel.camunda.converter.bean.listener;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2023/3/2
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class TaskListener {

  private String eventType = "create";

  private String expression;

  private String delegateExpression;

  private String javaClass;

  public boolean isClass() {
    return javaClass != null;
  }

  public boolean isDelegateExpression() {
    return delegateExpression != null;
  }

  public boolean isExpression() {
    return expression != null;
  }
}
