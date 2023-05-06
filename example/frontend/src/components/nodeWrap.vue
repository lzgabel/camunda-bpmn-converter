<template>
    <div>
        <div class="node-wrap" v-if="processNode.type!=4 && processNode.type != 10 &&  processNode.type != 11 && processNode.type != -1">
<!--            <div class="node-wrap-box" :class="(processNode.type==0?'start-node ':'')+(isTried&&processNode.error?'active error':'')">-->
            <div class="node-wrap-box">
                <div>
                    <div class="title" :style="'background: rgb('+ ['87, 106, 149','255, 148, 62','50, 150, 250'][processNode.type] +');'">
                        <span class="iconfont" v-show="processNode.type==1"></span>
                        <span class="iconfont" v-show="processNode.type==2"></span>
                        <span v-if="processNode.type==0">{{processNode.nodeName}}</span>
                        <input type="text" class="ant-input editable-title-input" v-if="processNode.type!=0&&isInput"
                               @blur="blurEvent()" @focus="$event.currentTarget.select()" v-focus
                               v-model="processNode.nodeName" :placeholder="placeholderList[processNode.type]">
                        <span class="editable-title" @click="clickEvent()" v-if="processNode.type!=0&&!isInput">{{processNode.nodeName}}</span>
                        <i class="anticon anticon-close close" v-if="processNode.type!=0" @click="delNode()"></i>
                    </div>
<!--                    <div class="content" @click="setPerson">-->
<!--                        <div class="text" v-if="processNode.type==0">{{arrToStr(flowPermission)?arrToStr(flowPermission):'所有人'}}</div>-->
<!--                        <div class="text" v-if="processNode.type==1">-->
<!--                            <span class="placeholder" v-if="!setApproverStr(processNode)">请选择{{placeholderList[processNode.type]}}</span>-->
<!--                            {{setApproverStr(processNode)}}-->
<!--                        </div>-->
<!--                        <div class="text" v-if="processNode.type==2">-->
<!--                            <span class="placeholder" v-if="!copyerStr(processNode)">请选择{{placeholderList[processNode.type]}}</span>-->
<!--                            {{copyerStr(processNode)}}-->
<!--                        </div>-->
<!--                        <i class="anticon anticon-right arrow"></i>-->
<!--                    </div>-->
                    <div class="error_tip" v-if="isTried&&processNode.error">
                        <i class="anticon anticon-exclamation-circle" style="color: rgb(242, 86, 67);"></i>
                    </div>
                </div>
            </div>

            <addNode :nextNodeP.sync="processNode.nextNode"></addNode>
        </div>

        <div class="branch-wrap" v-if="processNode.type==4">
            <div class="branch-box-wrap">
                <div class="branch-box">
                    <button class="add-branch" @click="addTerm">条件分支</button>
                    <div class="col-box" v-for="(item,index) in processNode.branchNodes" :key="index">
                        <div class="condition-node">
                            <div class="condition-node-box">
                                <div class="auto-judge" :class="isTried&&item.error?'error active':''">
                                    <div class="sort-left" v-if="index!=0" @click="arrTransfer(index,-1)">&lt;</div>
                                    <div class="title-wrapper">
                                        <input type="text" class="ant-input editable-title-input" v-if="isInputList[index]"
                                               @blur="blurEvent(index)" @focus="$event.currentTarget.select()" v-focus v-model="item.nodeName">
                                        <span class="editable-title" @click="clickEvent(index)" v-if="!isInputList[index]">{{item.nodeName}}</span>
                                        <span class="priority-title" @click="setPerson(item.priorityLevel)">优先级{{item.priorityLevel}}</span>
                                        <i class="anticon anticon-close close" @click="delTerm(index)"></i>
                                    </div>
                                    <div class="sort-right" v-if="index!=processNode.branchNodes.length-1"
                                         @click="arrTransfer(index)">&gt;</div>
                                    <div class="content" @click="setPerson(item.priorityLevel)">{{$func.conditionStr(item,index)}}</div>
                                    <div class="error_tip" v-if="isTried&&item.error">
                                        <i class="anticon anticon-exclamation-circle" style="color: rgb(242, 86, 67);"></i>
                                    </div>
                                </div>
                                <addNode :nextNodeP.sync="item.nextNode" ></addNode>
                            </div>
                        </div>
                        <nodeWrap v-if="item.nextNode && item.nextNode && item.type !== -1" :processNode.sync="item.type != -1 ? item.nextNode : item.nextNode.nextNode" :tableId="tableId"
                                  :isTried.sync="isTried" ></nodeWrap>
                        <div class="top-left-cover-line" v-if="index==0"></div>
                        <div class="bottom-left-cover-line" v-if="index==0"></div>
                        <div class="top-right-cover-line" v-if="index==processNode.branchNodes.length-1"></div>
                        <div class="bottom-right-cover-line" v-if="index==processNode.branchNodes.length-1"></div>
                    </div>
                </div>
                <addNode :childNodeP.sync="processNode.nextNode.nextNode" :parentNode.sync="processNode.nextNode"></addNode>
            </div>
        </div>

        <div class="branch-wrap" v-if="processNode.type==10">
            <div class="branch-box-wrap">
                <div class="branch-box">
                    <button class="add-branch" @click="parall">并行任务</button>
                    <div class="col-box" v-for="(item,index) in processNode.branchNodes" :key="index">
                        <div class="condition-node">
                            <div class="condition-node-box">
                                <addNode :nextNodeP.sync="item.nextNode" :parentNode.sync="item"></addNode>
                            </div>
                        </div>
                        <nodeWrap v-if="item.nextNode && item.nextNode" :processNode.sync="item.nextNode" :tableId="tableId"
                                  :isTried.sync="isTried"></nodeWrap>
                        <div class="top-left-cover-line" v-if="index==0"></div>
                        <div class="bottom-left-cover-line" v-if="index==0"></div>
                        <div class="top-right-cover-line" v-if="index==processNode.branchNodes.length-1"></div>
                        <div class="bottom-right-cover-line" v-if="index==processNode.branchNodes.length-1"></div>
                    </div>
                </div>
                <addNode :nextNodeP.sync="processNode.nextNode.nextNode" :parentNode.sync="processNode.nextNode"></addNode>
            </div>
        </div>


        <nodeWrap v-if="processNode.nextNode && processNode.nextNode" :processNode.sync="processNode.nextNode" :tableId="tableId"
        :isTried.sync="isTried"></nodeWrap>
    </div>
</template>
<script>
export default {
    props: ["processNode", "flowPermission", "isTried", "tableId"],
    data() {
        return {
            placeholderList: ["发起人", "审核人", "抄送人"],
            isInputList: [],
            isInput: false,
        }
    },
    mounted() {
        // if (this.processNode.type == 1) {
        //     this.processNode.error = !this.$func.setApproverStr(this.processNode)
        // } else if (this.processNode.type == 2) {
        //     this.processNode.error = !this.$func.copyerStr(this.processNode)
        // } else if (this.processNode.type == 4) {
        //     // for (var i = 0; i < this.processNode.branchNodes.length; i++) {
        //     //     this.processNode.branchNodes[i].error = this.$func.conditionStr(this.processNode, i) == "请设置条件" && i != this.processNode.branchNodes.length - 1
        //     // }
        // }
    },
    computed: {
        flowPermission1() {
            return this.$store.state.flowPermission
        },
        approverConfig1() {
            return this.$store.state.approverConfig
        },
        copyerConfig1() {
            return this.$store.state.copyerConfig
        },
        conditionsConfig1() {
            return this.$store.state.conditionsConfig
        },
    },
    watch: {
        flowPermission1(data) {
            if (data.flag&&data.id === this._uid) {
                this.$emit('update:flowPermission',data.value)
            }
        },
        approverConfig1(data) {
            if (data.flag&&data.id === this._uid) {
                this.$emit('update:processNode',data.value)
            }
        },
        copyerConfig1(data) {
            if (data.flag&&data.id === this._uid) {
                this.$emit('update:processNode',data.value)
            }
        },
        conditionsConfig1(data) {
            console.log(data)
            if (data.flag&&data.id === this._uid) {
                this.$emit('update:processNode',data.value)
            }
        },
    },
    methods: {
        clickEvent(index) {
            if (index || index === 0) {
                this.$set(this.isInputList, index, true)
            } else {
                this.isInput = true;
            }
        },
        blurEvent(index) {
            if (index || index === 0) {
                this.$set(this.isInputList, index, false)
                this.processNode.branchNodes[index].nodeName = this.processNode.branchNodes[index].nodeName ? this.processNode.branchNodes[index].nodeName : "条件"
            } else {
                this.isInput = false;
                this.processNode.nodeName = this.processNode.nodeName ? this.processNode.nodeName : this.placeholderList[this.processNode.type]
            }
        },
        delNode() {
            this.$emit("update:processNode", this.processNode.nextNode);
        },
        parall() {
            let len = this.processNode.branchNodes.length + 1
            this.processNode.branchNodes.push({
                //"nodeName": "条件" + len,
                "type": 4,
                "nodeType": "parallel",
                "nextNode": null
            });
            // for (var i = 0; i < this.processNode.branchNodes.length; i++) {
            //     this.processNode.branchNodes[i].error = this.conditionStr(this.processNode.branchNodes[i], i) == "请设置条件" && i != this.processNode.branchNodes.length - 1
            // }
            this.$emit("update:processNode", this.processNode);
        },
        addTerm() {
            let len = this.processNode.branchNodes.length + 1
            this.processNode.branchNodes.push({
                "nodeName": "条件" + len,
                "type": 3,
                "nextNode": null
            });
            // for (var i = 0; i < this.processNode.branchNodes.length; i++) {
            //     this.processNode.branchNodes[i].error = this.$func.conditionStr(this.processNode, i) == "请设置条件" && i != this.processNode.branchNodes.length - 1
            // }
            this.$emit("update:processNode", this.processNode);
        },
        delTerm(index) {
            this.processNode.branchNodes.splice(index, 1)
            this.processNode.branchNodes.map((item, index) => {
                item.priorityLevel = index + 1
                item.nodeName = `条件${index + 1}`
            });
            // for (var i = 0; i < this.processNode.branchNodes.length; i++) {
            //     this.processNode.branchNodes[i].error = this.$func.conditionStr(this.processNode, i) == "请设置条件" && i != this.processNode.branchNodes.length - 1
            // }
            this.$emit("update:processNode", this.processNode);
            if (this.processNode.branchNodes.length == 1) {
                if (this.processNode.nextNode) {
                    if (this.processNode.branchNodes[0].nextNode) {
                        this.reData(this.processNode.branchNodes[0].nextNode, this.processNode.nextNode)
                    } else {
                        this.processNode.branchNodes[0].nextNode = this.processNode.nextNode
                    }
                }
                this.$emit("update:processNode", this.processNode.branchNodes[0].nextNode);
            }
        },
        reData(data, addData) {
            if (!data.nextNode) {
                data.nextNode = addData
            } else {
                this.reData(data.nextNode, addData)
            }
        },
        setPerson(priorityLevel) {
            var { type } = this.processNode;
            if (type == 0) {
                this.$store.commit('updatePromoter',true)
                this.$store.commit('updateFlowPermission',{
                    value:this.flowPermission,
                    flag:false,
                    id:this._uid
                })
            } else if (type == 1) {
                this.$store.commit('updateApprover',true)
                this.$store.commit('updateApproverConfig',{
                    value: {...JSON.parse(JSON.stringify(this.processNode)),
                    ...{settype:this.processNode.settype ? this.processNode.settype : 1}},
                    flag:false,
                    id:this._uid
                })
            } else if (type == 2) {
                this.$store.commit('updateCopyer',true)
                this.$store.commit('updateCopyerConfig',{
                    value:JSON.parse(JSON.stringify(this.processNode)),
                    flag:false,
                    id:this._uid
                })
            } else {
                this.$store.commit('updateCondition',true)
                this.$store.commit('updateConditionsConfig',{
                    value:JSON.parse(JSON.stringify(this.processNode)),
                    priorityLevel,
                    flag:false,
                    id:this._uid
                })
            }
        },
        arrTransfer(index, type = 1) {//向左-1,向右1
            this.processNode.branchNodes[index] = this.processNode.branchNodes.splice(index + type, 1, this.processNode.branchNodes[index])[0];
            this.processNode.branchNodes.map((item, index) => {
                item.priorityLevel = index + 1
            })
            // for (var i = 0; i < this.processNode.branchNodes.length; i++) {
            //     this.processNode.branchNodes[i].error = this.$func.conditionStr(this.processNode, i) == "请设置条件" && i != this.processNode.branchNodes.length - 1
            // }
            this.$emit("update:processNode", this.processNode);
        }
    }
}
</script>
<style>
.error_tip {
    position: absolute;
    top: 0px;
    right: 0px;
    transform: translate(150%, 0px);
    font-size: 24px;
}
.promoter_person .el-dialog__body {
    padding: 10px 20px 14px 20px;
}
.selected_list {
    margin-bottom: 20px;
    line-height: 30px;
}
.selected_list span {
    margin-right: 10px;
    padding: 3px 6px 3px 9px;
    line-height: 12px;
    white-space: nowrap;
    border-radius: 2px;
    border: 1px solid rgba(220, 220, 220, 1);
}
.selected_list img {
    margin-left: 5px;
    width: 7px;
    height: 7px;
    cursor: pointer;
}
</style>
