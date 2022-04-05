package cn.lzgabel.camunda.bean;

import lombok.Data;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @since 1.0.0
 */

@Data
public class Process {

    /**
     * 流程id
     */
    private String processId;

    /**
     * 流程名称
     */
    private String name;
}
