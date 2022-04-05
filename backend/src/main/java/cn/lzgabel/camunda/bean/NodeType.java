package cn.lzgabel.camunda.bean;

import com.google.common.collect.Maps;
import org.camunda.bpm.model.bpmn.instance.*;

import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @since 1.0.0
 */

public enum NodeType {
    /**
     * 开始事件
     */
    START_EVENT("startEvent", StartEvent.class),

    /**
     * 并行事件
     */
    PARALLEL_GATEWAY("parallelGateway", ParallelGateway.class),

    /**
     * 排他事件
     */
    EXCLUSIVE_GATEWAY("exclusiveGateway", ExclusiveGateway.class),

    /**
     * serviceTask
     */
    SERVICE_TASK("serviceTask", ServiceTask.class),

    /**
     * userTask
     */
    USER_TASK("userTask", UserTask.class),

    /**
     * 子任务类型
     */
    SUB_PROCESS("subProcess", SubProcess.class);


    private String typeName;

    private Class<?> typeClass;

    NodeType(String typeName, Class<?> typeClass) {
        this.typeName = typeName;
        this.typeClass = typeClass;
    }

    public final static Map<String, Class<?>> TYPE_CLASS_MAP = Maps.newHashMap();

    static {
        for (NodeType element : NodeType.values()) {
            TYPE_CLASS_MAP.put(element.typeName, element.typeClass);
        }
    }

    public boolean isEqual(String typeName) {
        return this.typeName.equals(typeName);
    }
}
