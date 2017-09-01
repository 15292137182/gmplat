/**
 * Created by liyuanquan on 2017/9/1.
 */

/**
 * @description:自定义指令获取DOM节点
 * @author:liyuanquan
 */
Vue.directive("dom", {
    bind: function(el, binding) {
        var _obj = binding.value;
        if(_obj != null) {
            var key = Object.keys(modifiers.biding)[0] || "el";
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
    // type
    props: ["isDisabled", "url", "initialValue"],
    data() {
        return {
            // 为了将子组件和外部解耦 最好将受影响的数据写在子组件内部 这样子组件就形成一个相对封闭的区间
            // options是调用后端接口 并且只获取一次
            options: [{
                value: '选项1',
                label: '重庆火锅'
            }, {
                value: '选项2',
                label: '螺蛳粉'
            }, {
                value: '选项3',
                label: '臊子面'
            }],
            selectValue: {
                values: ""
            }
        };
    },
    methods: {
        changeSelect() {
            // 子组件向父组件传递的方法和参数
            this.$emit("change-data", this.selectValue.values);
        }
    },
    beforeMount() {
        this.selectValue.values = this.initialValue;
    },
    template: `<el-select @change="changeSelect" v-model="selectValue.values" :disabled="isDisabled" clearable="true" placeholder="请选择">
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
    // type
    props: ["isDisabled", "url", "initialValue"],
    data() {
        return {
            // 为了将子组件和外部解耦 最好将受影响的数据写在子组件内部 这样子组件就形成一个相对封闭的区间
            // options是调用后端接口 并且只获取一次
            options: [{
                value: '选项1',
                label: '重庆火锅'
            }, {
                value: '选项2',
                label: '螺蛳粉'
            }, {
                value: '选项3',
                label: '臊子面'
            }],
            selectValue: {
                values: []
            }
        };
    },
    methods: {
        changeSelect() {
            // 子组件向父组件传递的方法和参数
            this.$emit("change-datas", this.selectValue.values);
        }
    },
    beforeMount() {
        this.selectValue.values = this.initialValue;
    },
    template: `<el-select @change="changeSelect" v-model="selectValue.values" :disabled="isDisabled" multiple placeholder="请选择">
					<el-option
						v-for="item in options"
						:key="item.value"
						:label="item.label"
						:value="item.value">
					</el-option>
				</el-select>`
});