package cn.lzgabel.camunda.converter.bean;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @since 1.0.0
 */
public enum DecisionRefBindingType {
  LATEST("latest"),

  VERSION("version"),

  VERSION_TAG("versionTag");

  private final String value;

  DecisionRefBindingType(final String value) {
    this.value = value;
  }

  public boolean isEquals(String value) {
    return this.value.equals(value);
  }

  public String value() {
    return this.value;
  }
}
