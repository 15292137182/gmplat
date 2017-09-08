/**
 * Created by liyuanquan on 2017/9/6.
 */

/**
 * @description:自定义指令获取DOM节点
 * @author:liyuanquan
 */
Vue.directive("dom", {
    bind: function(el, binding) {
        var _obj = binding.value;
        if(_obj != null) {
            var key = Object.keys(binding.modifiers)[0] || "el";
            Vue.set(_obj, key, el);
        }
    }
});

/**
 * @description:基础表格组件
 * @author:liyuanquan
 */
Vue.component("gmp-table", {
    props: ["tableData", "defaultHeight"],
    data() {
        return {
            defaultSort: {prop: 'date', order: 'descending'}
        }
    },
    methods: {
        setCurrent(row) {
            this.$refs.singleTable.setCurrentRow(row);
        },
        handleCurrentChange(val) {
            this.currentRow = val;
        },
        rowData(row, event, column) {
            this.$emit("get-row-data", row);
        }
    },
    template: `<el-table :data="tableData" :height="defaultHeight" border ref="singleTable" highlight-current-row  @current-change="handleCurrentChange" :default-sort="defaultSort" @row-click="rowData" style="width: 100%">
					<slot>
					</slot>
				</el-table>`
});

/**
 * @description:单选下拉框组件
 * @author:liyuanquan
 */
Vue.component("single-selection", {
    // 是否可用  获取options的接口  默认值
    props: ["isDisabled", "url", "initialValue"],
    data() {
        return {
            // 获取后端数据 并赋值给options
            options: [],
            // options: [{
            //     value: 'Beijing',
            //     label: '北京'
            // }, {
            //     value: 'Shanghai',
            //     label: '上海'
            // }, {
            //     value: 'Nanjing',
            //     label: '南京'
            // }, {
            //     value: 'Chengdu',
            //     label: '成都'
            // }, {
            //     value: 'Shenzhen',
            //     label: '深圳'
            // }, {
            //     value: 'Guangzhou',
            //     label: '广州'
            // }, {
            //     value: 'Hangzhou',
            //     label: '杭州'
            // }],
            selectValue: {
                values: [],
                label: ""
            }
        };
    },
    methods: {
        changeSelect(val) {
            var _obj = {};
            // 如果下拉框触发改变时 返回值不为空 则将option下与返回值相同的数据项传递给父组件
            if(val != "") {
                _obj = this.options.find(function(item) {
                    return item.value === val;
                });
            }
            // 子组件向父组件传递的方法和参数
            this.$emit("change-data", _obj);
        },
        // 级联下拉框方法
        cascaderEvent(val) {
            this.selectValue.values = val;
        },
        // 拼接option测试方法
        serverOptions(type) {
            if(type == "op_1") {
                return [{
                    deleteFlag: "0",
                    desp: "00",
                    value: "5243fc43-a9c6-42f0-a0c8-fd74e7fb",
                    label: "demo1",
                    tableEname: "demo2",
                    tableSchema: "demo"
                }, {
                    deleteFlag: "0",
                    desp: "11",
                    value: "6243gd43-a9c6-42f0-a0c8-fd74e7fb",
                    label: "demo11",
                    tableEname: "demo2",
                    tableSchema: "demo"
                }]
            }
            if(type == "op_2") {
                return [{
                    deleteFlag: "0",
                    desp: "22",
                    value: "7243fc43-a9c6-42f0-a0c8-fd74e7fb",
                    label: "demo22",
                    tableEname: "demo2",
                    tableSchema: "demo"
                }, {
                    deleteFlag: "0",
                    desp: "33",
                    value: "8243gd43-a9c6-42f0-a0c8-fd74e7fb",
                    label: "demo33",
                    tableEname: "demo2",
                    tableSchema: "demo"
                }]
            }
        }
    },
    mounted() {
        // 接收父组件传递的初始默认值 并将其赋值给子组件
        this.selectValue.values = this.initialValue;
        //ex:查询关联表
        // this.$http.jsonp(serverPath + "/maintTable/query",{
        //     search:''
        // },{
        //     jsonp: 'callback'
        // }).then(function (res) {
        //     var data = res.data.resp.content.data.result;
        //     // console.log(data);
        //     // 动态拼接options
        //     this.options = data;
        // });
        this.options = this.serverOptions(this.url)
    },
    updated: function () {
        //
    },
    template: `<el-select @change="changeSelect" v-dom="selectValue" v-model="selectValue.values" :disabled="isDisabled" clearable="true" placeholder="请选择">
					<el-option
						v-for="item in options"
						:key="item.value"
						:label="item.label"
						:value="item.value">
					</el-option>
				</el-select>`
});

/**
 * @description:多选下拉框组件
 * @author:liyuanquan
 */
Vue.component("multiple-selection", {
    // 是否可用  获取options的接口  默认值
    props: ["isDisabled", "url", "initialValue"],
    data() {
        return {
            // 获取后端数据 并赋值给options
            options: [{
                value: 'Beijing',
                label: '北京'
            }, {
                value: 'Shanghai',
                label: '上海'
            }, {
                value: 'Nanjing',
                label: '南京'
            }, {
                value: 'Chengdu',
                label: '成都'
            }, {
                value: 'Shenzhen',
                label: '深圳'
            }, {
                value: 'Guangzhou',
                label: '广州'
            }, {
                value: 'Hangzhou',
                label: '杭州'
            }],
            selectValue: {
                values: []
            }
        };
    },
    methods: {
        changeSelect() {
            // var _DOM = this.selectValue.el.children[1].lastElementChild;
            // 子组件向父组件传递的方法和参数
            this.$emit("change-datas", this.selectValue.values);
        }
    },
    mounted() {
        this.selectValue.values = this.initialValue;
    },
    template: `<el-select @change="changeSelect" v-dom="selectValue" v-model="selectValue.values" :disabled="isDisabled" multiple placeholder="请选择">
					<el-option
						v-for="item in options"
						:key="item.value"
						:label="item.label"
						:value="item.value">
					</el-option>
				</el-select>`
});

/**
 * @description:树形结构组件
 * @author:liyuanquan
 */
Vue.component("base-tree", {
    // 是否可过滤 默认展开的节点 默认选择的节点
    props: ["isFilter", "defaultExpandedKeys", "defaultCheckedKeys"],
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val);
        }
    },
    data() {
        return {
            filterText: "",
            datas: [{
                id: 1,
                label: '一级 1',
                children: [{
                    id: 4,
                    label: '二级 1-1',
                    children: [{
                        id: 9,
                        label: '三级 1-1-1'
                    }, {
                        id: 10,
                        label: '三级 1-1-2'
                    }]
                }]
            }, {
                id: 2,
                label: '一级 2',
                children: [{
                    id: 5,
                    label: '二级 2-1'
                }, {
                    id: 6,
                    label: '二级 2-2'
                }]
            }, {
                id: 3,
                label: '一级 3',
                children: [{
                    id: 7,
                    label: '二级 3-1'
                }, {
                    id:8,
                    label: '二级 3-2',
                    children: [{
                        id:11,
                        label: '三级 3-2-1'
                    }, {
                        id:12,
                        label: '三级 3-2-2'
                    }, {
                        id:13,
                        label: '三级 3-2-3'
                    }]
                }, {
                    id: 14,
                    label: '二级 3-3',
                    children: [{
                        id: 15,
                        label: '三级 3-3-1'
                    }, {
                        id: 16,
                        label: '三级 3-3-2'
                    }, {
                        id: 17,
                        label: '三级 3-3-3'
                    }]
                }]
            }],
            defaultProps: {
                children: 'children',
                label: 'label'
            }
        }
    },
    mounted() {
        //
    },
    methods: {
        // 点击节点 返回该节点对应的对象 对应的节点 节点本身
        clickNode(obj, node, row) {
            this.$emit("click-node", obj);
        },
        // 选择复选框 返回节点对应的对象 是否被选中 是否含有子节点
        checkNode(obj, checked, node) {
            this.$emit("checked-node", obj);
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        }
    },
    template: `<div>
                    <el-input placeholder="输入关键字进行过滤" v-model="filterText" v-show="isFilter" style="margin-bottom: 5px;"></el-input>
                    <el-tree :data="datas" show-checkbox @node-click="clickNode" @check-change="checkNode" :default-expanded-keys="defaultExpandedKeys" :default-checked-keys="defaultCheckedKeys" node-key="id" ref="tree" highlight-current :props="defaultProps" :filter-node-method="filterNode">
                    </el-tree>
                </div>`
});

/**
 * @description:时间选择器组件
 * @author:liyuanquan
 */
Vue.component("time-picker", {
    // 默认显示的时间  是否是时间段  是否可用  是否只读  时间格式化  是否可输入
    props: ["initialTime", "isRange", "isDisabled", "readOnly", "formatter", "editAble"],
    data() {
        return {
            value: []
        }
    },
    methods: {
        // 选择器值发生改变时 返回当前值
        currentVal(date) {
            // 如果change事件返回值为undefined 强制转为空
            date == undefined ? date = "" : date = date;
            // 将子组件返回值传递给父组件
            this.$emit("selected-time", date);
        }
    },
    template: `<el-time-picker v-model="value" @change="currentVal" :format="formatter" :is-range="isRange" :disabled="isDisabled" :readonly="readOnly" placeholder="选择时间" :editable="editAble">
                </el-time-picker>`
});

/**
 * @description:日期时间选择器组件
 * @author:liyuanquan
 */
Vue.component("date-time-picker", {
    // 控件类型(必选)  默认显示日期  快捷键选择值  输出格式化  是否只读  是否可用  是否禁用  是否可输入
    props: ["pickerType", "initialDate", "pickerOptions", "formatter", "readOnly", "isDisabled", "editAble"],
    data() {
        return {
            dateTimeVal: []
        }
    },
    methods: {
        // 选择器值发生改变时 返回当前值
        datetimeVal(datetime) {
            // 如果change事件返回值为undefined 强制转为空
            datetime == undefined ? datetime = "" : datetime = datetime;
            // 将子组件返回值传递给父组件
            this.$emit("selected-datetime", datetime);
        }
    },
    template: `<el-date-picker @change="datetimeVal" v-model="dateTimeVal" :type="pickerType" :format="formatter" placeholder="选择日期时间范围" :picker-options="pickerOptions" :readonly="readOnly" :disabled="isDisabled" :editable="editAble">
                </el-date-picker>`
});

/**
 * @description:级联选择组件
 * @author:liyuanquan
 */
Vue.component("cascader", {
    // 触发方式(点击或者滑过)  设置默认显示的值
    props: ["triggerType", "initialVal"],
    data() {
        return {
            options: [{
                value: "beijing",
                label: "北京市",
                children: [{
                    value: "chaoyang",
                    label: "朝阳区",
                    children: [{
                        value: "huixinxijie",
                        label: "惠新西街"
                    }]
                }, {
                    value: "haidian",
                    label: "海淀区"
                }, {
                    value: "dongcheng",
                    label: "东城区"
                }, {
                    value: "xicheng",
                    label: "西城区"
                }]
            }, {
                value: "shanghai",
                label: "上海市",
                children: [{
                    value: "baoshan",
                    label: "宝山区",
                    children: [{
                        value: "youyilu",
                        label: "友谊路"
                    }]
                }, {
                    value: "pudong",
                    label: "浦东新区"
                }, {
                    value: "jingan",
                    label: "静安区"
                }, {
                    value: "putuo",
                    label: "普陀区"
                }]
            }, {
                value: "liaoning",
                label: "辽宁省",
                children: [{
                    value: "shenyang",
                    label: "沈阳市"
                }, {
                    value: "dalian",
                    label: "大连市",
                    children: [{
                        value: "jinzhou",
                        label: "金州区"
                    }]
                }]
            }, {
                value: "guangxi",
                label: "广西壮族自治区",
                children: [{
                    value: "nanning",
                    label: "南宁市"
                }, {
                    value: "liuzhou",
                    label: "柳州市",
                    children: [{
                        value: "yufeng",
                        label: "鱼峰区"
                    }]
                }]
            }],
            selectedOptions: []
        }
    },
    methods: {
        handleChange(val) {
            // console.log(val);
            this.$emit("get-select", val);
        }
    },
    mounted() {
        // 接收默认值
        this.selectedOptions = this.initialVal;
    },
    template: `<el-cascader :expand-trigger="triggerType" :options="options" v-model="selectedOptions" @change="handleChange"></el-cascader>`
});