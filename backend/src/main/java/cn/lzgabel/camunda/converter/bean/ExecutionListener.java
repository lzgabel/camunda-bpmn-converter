package cn.lzgabel.camunda.converter.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2022/10/26
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class ExecutionListener {

  private String eventType = "start";

  private String expression;

  private String delegateExpression;

  private String javaClass;

  public boolean isClass() {
    return StringUtils.isNotBlank(javaClass);
  }

  public boolean isDelegateExpression() {
    return StringUtils.isNotBlank(delegateExpression);
  }

  public boolean isExpression() {
    return StringUtils.isNotBlank(expression);
  }
}
