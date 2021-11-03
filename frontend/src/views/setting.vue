<template>
	<div>
		<div class="fd-nav">
			<div class="fd-nav-left">
				<div class="fd-nav-back" @click="toReturn"><i class="anticon anticon-left"></i></div>
				<div class="fd-nav-title">{{process.name}}</div>
			</div>
			<!-- <div class="fd-nav-center">
                <div class="fd-nav-container">
                    <div class="ghost-bar" style="transform: translateX(300px);"></div>
                    <div class="fd-nav-item"><span class="order-num">1</span>基础设置</div>
                    <div class="fd-nav-item"><span class="order-num">2</span>表单设计</div>
                    <div class="fd-nav-item active"><span class="order-num">3</span>流程设计</div>
                    <div class="fd-nav-item"><span class="order-num">4</span>高级设置</div>
                </div>
            </div> -->
			<div class="fd-nav-right">
				<!-- <button type="button" class="ant-btn button-preview"><span>预 览</span></button> -->
				<button type="button" class="ant-btn button-publish" @click="saveSet"><span>发 布</span></button>
			</div>
		</div>
		<div class="fd-nav-content">
			<section class="dingflow-design">
				<div class="zoom">
					<div :class="'zoom-out'+ (nowVal==50?' disabled':'')" @click="zoomSize(1)"></div>
					<span>{{nowVal}}%</span>
					<div :class="'zoom-in'+ (nowVal==300?' disabled':'')" @click="zoomSize(2)"></div>
				</div>
				<div class="box-scale" id="box-scale" :style="'transform: scale('+nowVal/100+'); transform-origin: 50% 0px 0px;'">
					<nodeWrap :processNode.sync="processNode" :flowPermission.sync="flowPermission"
						:isTried.sync="isTried" :tableId="tableId"></nodeWrap>
					<div class="end-node">
						<div class="end-node-circle"></div>
						<div class="end-node-text">流程结束</div>
					</div>
				</div>
			</section>
		</div>
		<errorDialog
			:visible.sync="tipVisible"
			:list="tipList"
		/>
		<promoterDrawer />
		<approverDrawer  :directorMaxLevel="directorMaxLevel"/>
		<copyerDrawer />
		<conditionDrawer />
	</div>
</template>
<script>
import errorDialog from '@/components/dialog/errorDialog'
import promoterDrawer from '@/components/drawer/promoterDrawer'
import approverDrawer from '@/components/drawer/approverDrawer'
import copyerDrawer from '@/components/drawer/copyerDrawer'
import conditionDrawer from '@/components/drawer/conditionDrawer'
import ElementUI from 'element-ui';

function replace(node) {
	var map = {
		0: 'task',
		1: 'task',
		7: 'task',
		4: 'exclusive',
		10: 'parallel'
	}
	var type = node.type
	if (map[type] == undefined) {
		return null;
	}
	node.type = map[type]
	if (node.branchNodes != null) {
		for (var i in node.branchNodes) {
			replace(node.branchNodes[i])
		}
	}
	if (node.nextNode != undefined && node.nextNode != null) {
		replace(node.nextNode)
	}
	return node;
}

export default {
	components:{
		errorDialog,
		promoterDrawer,
		approverDrawer,
		copyerDrawer,
		conditionDrawer
	},
	data() {
		return {
			isTried: false,
			tipList: [],
			tipVisible: false,
			nowVal: 100,
			processConfig: {},
			processNode: {},
			process: {},
			flowPermission: [],
			directorMaxLevel: 0,
			tableId: "",
		};
	},
	created() {
		this.$axios.get(`${process.env.BASE_URL}data.json`, {
			processId: this.$route.params.processId
		}).then(({data}) => {
			this.processConfig = data;
			let {processNode,flowPermission,directorMaxLevel,process,tableId} = data
			this.processNode = processNode;
			this.flowPermission = flowPermission;
			this.directorMaxLevel = directorMaxLevel;
			this.process = process
			this.tableId = tableId
		})
	},
	methods: {

		toReturn() {
			//window.location.href = ""
		},
		reErr({branchNodes}) {
			if (branchNodes) {
				let {type,error,nodeName,branchNodes} = branchNodes
				if (type == 1 || type == 2) {
					if (error) {
						this.tipList.push({ name: nodeName, type: ["","审核人","抄送人"][type] })
					}
					this.reErr(branchNodes)
				} else if (type == 3) {
					this.reErr(branchNodes)
				} else if (type == 4) {
					this.reErr(branchNodes)
					for (var i = 0; i < branchNodes.length; i++) {
						if (branchNodes[i].error) {
							this.tipList.push({ name: branchNodes[i].nodeName, type: "条件" })
						}
						this.reErr(branchNodes[i])
					}
				}
			} else {
				branchNodes = null
			}
		},
		saveSet() {
			// this.isTried = true;
			// this.tipList = [];
			// this.reErr(this.processNode);
			// if (this.tipList.length != 0) {
			// 	this.tipVisible = true;
			// 	return;
			// }
			// this.processConfig.flowPermission = this.flowPermission

			var workflow = JSON.parse(JSON.stringify(this.processConfig));

			workflow.processNode = replace(workflow.processNode.nextNode);
			console.log(JSON.stringify(this.processConfig))
			var data = {"json": JSON.stringify(workflow)};
			this.$axios.post("http://localhost:8080/deploy", data).then(function(res){
				ElementUI.Message({
					message: '部署成功',
					type: 'success'
				});
				const content = res
				const blob = new Blob([content])
				const elink = document.createElement('a')
				elink.download = workflow.process.name + ".bpmn"
				elink.style.display = 'none'
				elink.href = URL.createObjectURL(blob)
				document.body.appendChild(elink)
				elink.click()
				URL.revokeObjectURL(elink.href) // Release the URL object
				document.body.removeChild(elink)
			}, function(){
				//console.log('请求失败处理');
			});
			// this.$axios.post("", this.processConfig).then(res => {
			//     if (res.code == 200) {
			//         this.$message.success("设置成功");
			//         setTimeout(function () {
			//             window.location.href = ""
			//         }, 200)
			//     }
			// })
		},
		zoomSize(type) {
			if (type == 1) {
				if (this.nowVal == 50) {
					return;
				}
				this.nowVal -= 10;
			} else {
				if (this.nowVal == 300) {
					return;
				}
				this.nowVal += 10;
			}
		}
	}
};
</script>
<style>
@import "../css/workflow.css";
.error-modal-list {
	width: 455px;
}
</style>
