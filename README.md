<div align="center">
<img src="./static/img/img.jpeg" width="400px"/>

<h1>Camunda BPMN Converter </h1>
<p>
Camunda BPMN Converter is a tool to convert JSON file format to BPMN file format
</p>
</div>


### 1. 节点类型（nodeType)
- serviceTask
    - service 任务节点
- parallel
    - 并行节点
- exclusive
    - 排他节点

### 2. 数据结构

- serviceTask
```json
{
    "nodeName":"审核人1",
    "nodeType":"serviceTask",
    "nextNode": null
}
```

> 对于 parallel/exclusive 类型，目前建议设置 nextNode 的 nodeType 类型一一对应

- exclusive
```json
{
    "nodeName":"排他",
    "nodeType":"exclusive",
    "nextNode":{
        "nodeName":"",
        "nodeType":"exclusive",
        "nextNode":null
    },
    "branchNodes":[
        {
            "nodeName":"条件1",
            "conditionExpression":"=id>1",
            "nextNode":{
                "nodeName":"审核人2.1",
                "nodeType":"serviceTask",
                "nextNode": null
            }
        },
        {
            "nodeName":"条件2",
            "conditionExpression":"=id<1",
            "nextNode":{
                "nodeName":"审核人2.2",
                "nodeType":"serviceTask",
                "nextNode":null
            }
        },
        {...}
    ]
}
```

- parallel
```json
{
    "nodeName":"并行任务",
    "nodeType":"parallel",
    "nextNode":{
        "nodeName":"",
        "nodeType":"parallel",
        "nextNode":null
    },
    "branchNodes":[
        {
            "nextNode":{
                "nodeName":"审核人2.1",
                "nodeType":"serviceTask",
                "nextNode": null
            }
        },
        {
            "nextNode":{
                "nodeName":"审核人2.2",
                "nodeType":"serviceTask",
                "nextNode":null
            }
        },
        {
            "nextNode":{
                "nodeName":"审核人2.3",
                "nodeType":"serviceTask",
                "nextNode":null
            }
        },
        {...}
    ]
}
```

- 完整数据结构
```json
{
    "process":{
        "processId":"work-flow-id",
        "name":"合同审批"
    },
    "processNode":{
        "nodeName":"审核人1",
        "nodeType":"serviceTask",
        "taskHeaders":{
            "a":"b",
            "e":"d"
        },
        "nextNode":{
            "nodeName":"排他",
            "nodeType":"exclusive",
            "nextNode":{
                "nodeName":"排他网关",
                "nodeType":"exclusive",
                "nextNode":null
            },
            "branchNodes":[
                {
                    "nodeName":"条件1",
                    "conditionExpression":"=id>1",
                    "nextNode":{
                        "nodeName":"审核人2.1",
                        "nodeType":"serviceTask",
                        "nextNode":null
                    }
                },
                {
                    "nodeName":"条件2",
                    "conditionExpression":"=id<1",
                    "nextNode":{
                        "nodeName":"审核人2.2",
                        "nodeType":"serviceTask",
                        "nextNode":null
                    }
                }
            ]
        }
    }
}
```
### 3. 示例
* 启动服务
    * ``mvn spring-boot:run``
    * http://localhost:8080
