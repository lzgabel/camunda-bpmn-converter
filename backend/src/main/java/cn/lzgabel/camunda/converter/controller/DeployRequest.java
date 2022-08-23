package cn.lzgabel.camunda.converter.controller;

import cn.lzgabel.camunda.converter.bean.BaseDefinition;
import cn.lzgabel.camunda.converter.bean.Process;
import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 〈功能简述〉<br>
 * 〈process流程定义〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@Data
public class DeployRequest {

  private Process process;

  private BaseDefinition processNode;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
