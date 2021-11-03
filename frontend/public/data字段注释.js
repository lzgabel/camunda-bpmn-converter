export default {
    "code": "200",
    "msg": "success",
    "data": {
        "process":{
            "processId":"process-id",
            "name":"自动生成"
        },
        "processNode": {
            "id": "start",  // demo 中仅仅作为【发起人】初始节点展示， 最终给后端时使用的是 processNode.nextNode 给到后端
            "nodeType": "start",
            "nodeName": "发起人",
            "type": 0,
            "nextNode": null,
            "branchNodes": []
        }
    }
}

