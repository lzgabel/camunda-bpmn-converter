package cn.lzgabel.camunda.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈功能简述〉<br>
 * 〈基础元素定义〉
 *
 * @author lizhi
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseDefinition extends JSONObject {

    private String nodeName;

    private String nodeType;

    private BaseDefinition nextNode;

}
