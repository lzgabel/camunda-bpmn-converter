package cn.lzgabel.camunda.converter.bean.event.start;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public enum EventType {

  /** none event */
  NONE("none"),

  /** timer event */
  TIMER("timer"),

  /** message event */
  MESSAGE("message");

  private String value;

  EventType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  public boolean isEqual(String value) {
    return this.value.equals(value);
  }
}
