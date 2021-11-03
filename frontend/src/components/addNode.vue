<template>
    <div class="add-node-btn-box">
        <div class="add-node-btn">
            <el-popover placement="right-start" v-model="visible">
                <div class="add-node-popover-body">
                    <a class="add-node-popover-item approver" @click="addType(1)">
                        <div class="item-wrapper">
                            <!--                          <svg style="margin-top: 5px;" t="1601091008164" class="icon svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="686" width="64" height="64"><path d="M508.6 794H260.3c6.2-136.5 118.8-245.3 256.8-245.3 75 0 142.5 32.1 189.5 83.4 5.8 6.4 15.4 7.7 22.7 3.2 9.4-5.9 11.4-18.9 4-27.1-53.5-58.5-130.5-95.3-216.1-95.3 93.3 0 168.9-75.7 168.9-169s-75.7-169-169-169-169 75.7-169 169 75.6 169 168.9 169c-163 0-295 133.2-292.9 296.8v0.3c0 8.7 6.2 16 14.5 17.6 1.1 0.2 2.3 0.4 3.4 0.4h266.5c9.3 0 16.9-7.6 16.9-16.9 0.1-9.5-7.5-17.1-16.8-17.1zM382.1 343.8c0-74.6 60.4-135 135-135s135 60.4 135 135-60.4 135-135 135c-74.5 0-135-60.4-135-135z" fill="#5572FD" p-id="687"></path><path d="M785.4 740.6h-65.2v-65.2c0-7.9-6.5-14.4-14.4-14.4s-14.4 6.5-14.4 14.4v65.2h-65.2c-7.9 0-14.4 6.5-14.4 14.4s6.5 14.4 14.4 14.4h65.2v65.2c0 7.9 6.5 14.4 14.4 14.4s14.4-6.5 14.4-14.4v-65.2h65.2c7.9 0 14.4-6.5 14.4-14.4s-6.5-14.4-14.4-14.4z" fill="#5572FD" p-id="688"></path></svg>-->
                            <span class="iconfont"></span>
                        </div>
                        <p>审批人</p>
                    </a>
<!--                    <a class="add-node-popover-item condition" @click="addType(2)">-->
<!--                        <div class="item-wrapper">-->
<!--                            &lt;!&ndash;                          <svg style="margin-top: 5px;" t="1601090779268" class="icon svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2092" width="64" height="64"><path d="M516.266667 213.333333a64 64 0 1 1-64 64A64 64 0 0 1 516.266667 213.333333m0-85.333333a149.333333 149.333333 0 1 0 149.333333 149.333333A149.333333 149.333333 0 0 0 516.266667 128z" fill="#15bc83" p-id="2093"></path><path d="M516.266667 789.333333m-106.666667 0a106.666667 106.666667 0 1 0 213.333333 0 106.666667 106.666667 0 1 0-213.333333 0Z" fill="#15bc83" p-id="2094"></path><path d="M260.266667 746.666667m-106.666667 0a106.666667 106.666667 0 1 0 213.333333 0 106.666667 106.666667 0 1 0-213.333333 0Z" fill="#15bc83" p-id="2095"></path><path d="M772.266667 746.666667m-106.666667 0a106.666667 106.666667 0 1 0 213.333333 0 106.666667 106.666667 0 1 0-213.333333 0Z" fill="#15bc83" p-id="2096"></path><path d="M494.933333 341.333333h42.666667v384h-42.666667z" fill="#15bc83" p-id="2097"></path><path d="M499.242667 484.437333l30.165333-30.208 225.066667 225.066667-30.165334 30.208z" fill="#15bc83" p-id="2098"></path><path d="M312.32 713.130667l-30.165333-30.165334 228.693333-228.693333 30.165333 30.165333z" fill="#15bc83" p-id="2099"></path></svg>&ndash;&gt;-->
<!--                            <span class="iconfont"></span>-->
<!--                        </div>-->
<!--                        <p>抄送人</p>-->
<!--                    </a>-->
                    <a class="add-node-popover-item condition" @click="addType(4)">
                        <div class="item-wrapper">
                            <!--                          <svg style="margin-top: 5px;" t="1601090779268" class="icon svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2092" width="64" height="64"><path d="M516.266667 213.333333a64 64 0 1 1-64 64A64 64 0 0 1 516.266667 213.333333m0-85.333333a149.333333 149.333333 0 1 0 149.333333 149.333333A149.333333 149.333333 0 0 0 516.266667 128z" fill="#15bc83" p-id="2093"></path><path d="M516.266667 789.333333m-106.666667 0a106.666667 106.666667 0 1 0 213.333333 0 106.666667 106.666667 0 1 0-213.333333 0Z" fill="#15bc83" p-id="2094"></path><path d="M260.266667 746.666667m-106.666667 0a106.666667 106.666667 0 1 0 213.333333 0 106.666667 106.666667 0 1 0-213.333333 0Z" fill="#15bc83" p-id="2095"></path><path d="M772.266667 746.666667m-106.666667 0a106.666667 106.666667 0 1 0 213.333333 0 106.666667 106.666667 0 1 0-213.333333 0Z" fill="#15bc83" p-id="2096"></path><path d="M494.933333 341.333333h42.666667v384h-42.666667z" fill="#15bc83" p-id="2097"></path><path d="M499.242667 484.437333l30.165333-30.208 225.066667 225.066667-30.165334 30.208z" fill="#15bc83" p-id="2098"></path><path d="M312.32 713.130667l-30.165333-30.165334 228.693333-228.693333 30.165333 30.165333z" fill="#15bc83" p-id="2099"></path></svg>-->
                            <span class="iconfont"></span>
                        </div>
                        <p>条件分支</p>
                    </a>
                    <a class="add-node-popover-item condition" @click="addType(10)">
                        <div class="item-wrapper" style="transform: rotate(180deg);">
                            <!--                          <svg t="1601090602702" class="icon svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1365" data-spm-anchor-id="a313x.7781069.0.i7" width="64" height="64" style="-->
                            <!--    margin-top: 5px;-->
                            <!--"><path d="M533.333333 725.333333a64 64 0 1 1-64 64 64 64 0 0 1 64-64m0-85.333333a149.333333 149.333333 0 1 0 149.333334 149.333333 149.333333 149.333333 0 0 0-149.333334-149.333333z" fill="#f56f15" p-id="1366" data-spm-anchor-id="a313x.7781069.0.i0" class="selected"></path><path d="M533.333333 277.333333m-106.666666 0a106.666667 106.666667 0 1 0 213.333333 0 106.666667 106.666667 0 1 0-213.333333 0Z" fill="#f56f15" p-id="1367" data-spm-anchor-id="a313x.7781069.0.i3" class="selected"></path><path d="M277.333333 320m-106.666666 0a106.666667 106.666667 0 1 0 213.333333 0 106.666667 106.666667 0 1 0-213.333333 0Z" fill="#f56f15" p-id="1368" data-spm-anchor-id="a313x.7781069.0.i1" class="selected"></path><path d="M789.333333 320m-106.666666 0a106.666667 106.666667 0 1 0 213.333333 0 106.666667 106.666667 0 1 0-213.333333 0Z" fill="#f56f15" p-id="1369" data-spm-anchor-id="a313x.7781069.0.i5" class="selected"></path><path d="M512 341.333333h42.666667v384h-42.666667z" fill="#f56f15" p-id="1370" data-spm-anchor-id="a313x.7781069.0.i4" class="selected"></path><path d="M741.290667 357.504l30.165333 30.165333-225.066667 225.066667-30.165333-30.165333z" fill="#f56f15" p-id="1371" data-spm-anchor-id="a313x.7781069.0.i6" class="selected"></path><path d="M558.08 582.016l-30.165333 30.165333-228.693334-228.693333 30.165334-30.165333z" fill="#f56f15" p-id="1372" data-spm-anchor-id="a313x.7781069.0.i2" class="selected"></path></svg>-->
                            <span class="iconfont"></span>
                        </div>
                        <p>并行任务</p>
                    </a>
                </div>
                <button class="btn" type="button" slot="reference">
                    <span class="iconfont"></span>
                </button>
            </el-popover>
        </div>
    </div>
</template>
<script>
function uuid() {

    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {

        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);

        return v.toString(16);

    });

}
export default {
    props: ["nextNodeP"],
    data() {
        return {
            visible: false
        }
    },
    methods: {
        addType(type) {
            var id = uuid()
            this.visible = false;
            console.log("type: " + type)
            if (type != 4) {
                var data;
                if (type == 1) {
                    data = {
                        "nodeName": "审核人",
                        "jobType": "user-"+id,
                        "type": 1,
                        "nodeType": "serviceTask",
                        "nextNode": this.nextNodeP
                    }
                } else if (type == 2) {
                    data = {
                        "id": 'id-2',
                        "nodeName": "抄送人",
                        "type": 2,
                        "nextNode": this.nextNodeP
                    }
                } else {
                    data ={
                        "nodeName": "",
                        "type": 10,
                        "id": "parallel-"+id,
                        "nodeType": "parallelGateway",
                        "branchNodes": [{
                            "nodeName":"条件1",
                            "type": 7,
                            "nextNode": this.nextNodeP,
                        }, {
                            "nodeName": "分支2",
                            "type": 7,
                            "nextNode": null
                        }],
                        "nextNode": {
                            "nodeName": "",
                            "type": -1,
                            "nodeType": "parallelGateway",
                            "nextNode": this.nextNodeP
                        },
                    }
                }
                this.$emit("update:nextNodeP", data)
            } else {
                this.$emit("update:nextNodeP", {
                    "nodeName": "",
                    "type": 4,
                    "nodeType": "exclusiveGateway",
                    "nextNode": {
                        "nodeName": "",
                        "type": -1,
                        "nodeType": "exclusiveGateway",
                        "nextNode": this.nextNodeP
                    },
                    "branchNodes": [{
                        "nodeName": "条件1",
                        "type": 7,
                        "conditionExpression":"=id>1", // 条件表达式
                        "nextNode": null,
                    }, {
                        "nodeName": "条件2",
                        "type": 7,
                        "conditionExpression":"=id<1",
                        "nextNode": null
                    }]
                })
            }
        }
    }
}
</script>
