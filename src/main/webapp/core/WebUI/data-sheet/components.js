// 表格组件
Vue.component("my-app", {
	props: ["tableData"],
	data() {
		return {
			defaultSort: {prop: 'date', order: 'descending'}
		}
	},
	template: '\
				<el-table :data="tableData" height="350" border ref="singleTable" highlight-current-row  @current-change="handleCurrentChange" :default-sort="defaultSort" @row-click="sendMsgToParent" style="width: 100%">\
					<el-table-column type="index"></el-table-column>\
					<el-table-column label="日期" sortable width="180" prop="date">\
						<template scope="scope">\
							<el-icon name="time"></el-icon>\
							<span style="margin-left: 10px">{{ scope.row.date }}</span>\
						</template>\
					</el-table-column>\
					<el-table-column label="姓名" width="180">\
						<template scope="scope">\
							<el-popover trigger="hover" placement="top">\
								<p>姓名: {{ scope.row.name }}</p>\
								<p>住址: {{ scope.row.address }}</p>\
								<div slot="reference" class="name-wrapper">\
									<el-tag>{{ scope.row.name }}</el-tag>\
								</div>\
							</el-popover>\
						</template>\
					</el-table-column>\
					<slot>\
					</slot>\
					<el-table-column label="操作">\
						<template scope="scope">\
							<el-button size="small" @click="handleEdit(scope.$index, scope.row)" :disabled=scope.row.flag>编辑</el-button>\
							<el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)" :disabled=scope.row.flag>删除</el-button>\
						</template>\
					</el-table-column>\
				</el-table>\
				',
	methods: {
		handleEdit(index, row) {
			this.$emit("childHandleEdit");
			console.log(index, row);
		},
		handleDelete(index, row) {
			this.$emit("childHandleDelete");
			console.log(index, row);
		},
		setCurrent(row) {
			this.$refs.singleTable.setCurrentRow(row);
		},
		handleCurrentChange(val) {
			this.currentRow = val;
		},
		sendMsgToParent(row, event, column) {
			this.$emit("listenToChildeEvent");
			if(row.date == "2016-05-02") {
				$.each(this.tableData, function(index, item) {
					if(item.date == "2016-05-02") {
						item.flag = true;
						return;
					}
				});
			}
		}
    }
});

// 表单
Vue.component("my-form", {
	props: ["comRules"],
	data() {
		return {
			ruleForm: {
				name: '',
				region: '',
				date1: '',
				date2: '',
				delivery: false,
				type: [],
				resource: '',
				desc: ''
			}
		}
	},
	template: '\
				<el-form :model="ruleForm" :rules="comRules" ref="ruleForm" label-width="100px" class="demo-ruleForm">\
			<el-form-item label="活动名称" prop="name">\
			<el-input v-model="ruleForm.name"></el-input>\
			</el-form-item>\
			<el-form-item label="活动区域" prop="region">\
			<el-select v-model="ruleForm.region" placeholder="请选择活动区域">\
			<el-option label="上海" value="shanghai"></el-option>\
			<el-option label="北京" value="beijing"></el-option>\
			</el-select>\
			</el-form-item>\
			<el-form-item label="活动时间" required>\
			<el-col :span="11">\
			<el-form-item prop="date1">\
			<el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date1" style="width: 100%;"></el-date-picker>\
			</el-form-item>\
			</el-col>\
			<el-col class="line" :span="2" style="text-align: center;">-</el-col>\
			<el-col :span="11">\
			<el-form-item prop="date2">\
			<el-time-picker type="fixed-time" placeholder="选择时间" v-model="ruleForm.date2" style="width: 100%;"></el-time-picker>\
			</el-form-item>\
			</el-col>\
			</el-form-item>\
			<el-form-item label="即时配送" prop="delivery">\
			<el-switch on-text="" off-text="" v-model="ruleForm.delivery"></el-switch>\
			</el-form-item>\
			<el-form-item label="活动性质" prop="type">\
			<el-checkbox-group v-model="ruleForm.type">\
			<el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>\
			<el-checkbox label="节假日促销活动" name="type"></el-checkbox>\
			<el-checkbox label="线下主题活动" name="type"></el-checkbox>\
			<el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>\
			</el-checkbox-group>\
			</el-form-item>\
			<el-form-item label="特殊资源" prop="resource">\
			<el-radio-group v-model="ruleForm.resource">\
			<el-radio label="线上品牌商赞助"></el-radio>\
			<el-radio label="线下场地免费"></el-radio>\
			</el-radio-group>\
			</el-form-item>\
			<el-form-item label="活动形式" prop="desc">\
			<el-input type="textarea" v-model="ruleForm.desc"></el-input>\
			</el-form-item>\
			<el-form-item style="text-align: center;">\
			<el-button type="primary" @click="submitForm()">立即创建</el-button>\
			<el-button @click="resetForm()">重置</el-button>\
			</el-form-item>\
			</el-form>\
			',
	methods: {
		submitForm() {
			this.$refs.ruleForm.validate((valid) => {
				if (valid) {
					this.$message({
						message: "创建成功",
						showClose: true,
						duration: 5000,
						type: "success"
					})
				} else {
					this.$message({
						message: "请输入正确项目",
						showClose: true,
						duration: 5000,
						type: "warning"
					})
					return false;
				}
			});
		},
		resetForm() {
			this.$refs.ruleForm.resetFields();
		}
	}
});

// 拆分的表格组件
Vue.component("ibcpTable", {
	props: ["defaultHeight", "tableRef", "tableData"],
	data() {
		return {
			defaultHeight: "300",
			tableRef: "singleTable",
			defaultSort: {prop: 'date', order: 'descending'}
		}
	},
	template: '\
				<el-table :data="tableData" :height="defaultHeight" border :ref="tableRef" highlight-current-row  @current-change="handleCurrentChange" :default-sort="defaultSort" @row-click="sendMsgToParent" style="width: 100%">\
				<slot>\
				</slot>\
				</el-table>\
				',
	methods: {
		setCurrent(row) {
			this.$refs.singleTable.setCurrentRow(row);
		},
		handleCurrentChange(val) {
			this.currentRow = val;
		},
		sendMsgToParent(row, event, column) {
			this.$emit("listenToChildeEvent");
			if(row.date == "2016-05-02") {
				$.each(this.tableData, function(index, item) {
					if(item.date == "2016-05-02") {
						item.flag = true;
						return;
					}
				});
			}
		}
    }
});

// 可多选下拉框组件
Vue.component("ibcpSelection", {
	props: ["multipleData", "multipleOption", "isDisabled", "isMultiple", "isFilterable"],
	data() {
		return {
			multipleData: []
		}
	},
	template: '\
				<el-select @change="sendToParent" v-model="multipleData" :disabled="isDisabled" :multiple="isMultiple" :clearable="!isMultiple" :filterable="isFilterable" placeholder="请选择">\
				<el-option\
				v-for="item in multipleOption"\
				:key="item.value"\
				:label="item.label"\
				:value="item.value">\
				</el-option>\
				</el-select>\
				',
	methods: {
		sendToParent() {
			this.$emit("clear-event", this.multipleData);
		}
	}
});