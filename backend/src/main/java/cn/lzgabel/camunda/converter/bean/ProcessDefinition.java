package cn.lzgabel.camunda.converter.bean;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;

/**
 * 〈功能简述〉<br>
 * 〈process流程定义〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ProcessDefinition {

  private Process process;

  private BaseDefinition processNode;

  public abstract static class ProcessDefinitionBuilder<
      C extends ProcessDefinition, B extends ProcessDefinition.ProcessDefinitionBuilder> {

    public ProcessDefinitionBuilder() {
      process = new Process();
    }

    public B name(String name) {
      process.setName(name);
      return self();
    }

    public B processId(@NonNull String processId) {
      process.setProcessId(processId);
      return self();
    }

    public B processNode(@NonNull BaseDefinition processNode) {
      this.processNode = processNode;
      return self();
    }
  }

  public static ProcessDefinition of(String json) {
    ObjectMapper mapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    ProcessDefinition result;
    try {
      result = mapper.readValue(json, ProcessDefinition.class);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
    return result;
  }

  @SneakyThrows
  @Override
  public String toString() {
    return new ObjectMapper().writeValueAsString(this);
  }
}
