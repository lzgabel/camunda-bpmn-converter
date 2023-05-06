package cn.lzgabel.camunda.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2021-08-21
 * @since 1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.lzgabel")
public class CamundaBpmnGenerateApplication {

  public static void main(String[] args) {
    SpringApplication.run(CamundaBpmnGenerateApplication.class, args);
  }
}
