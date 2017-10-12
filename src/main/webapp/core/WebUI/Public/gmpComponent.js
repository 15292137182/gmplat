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
    // 设置参数
    props: ["initial"],
    data() {
        return {
            value: [],  // 下拉框选中绑定值
            isDisabled: false,  // 下拉框是否禁用
            options: [],    // 下拉框option
            url: "",    // 获取option接口
            params: {},  // 调用接口参数
            isMultiple: false
        };
    },
    methods: {
        changeSelect(val) {
            // console.log(val);
            // 保存this指针
            var _this = this;
            // 向父组件传递的对象
            var _obj = {
                value: ""
            };

            if(val != null) {
                if(val.length > 0 || val != "") {
                    if(this.isMultiple) {
                        _obj = val;
                        // 子组件向父组件传递的方法和参数
                        _this.$emit("change-data", _obj);
                    }else {
                        // 调用获取options方法 防止异步请求时间过长的问题
                        _this.getOptions(function(data) {
                            // console.log(data);
                            _obj = data.find(function(item) {
                                return item.value === val;
                            });
                            // console.log(_obj);
                            // 子组件向父组件传递的方法和参数
                            _this.$emit("change-data", _obj);
                        });
                    }
                }else {
                    _obj.value = val;
                    // 子组件向父组件传递的方法和参数
                    _this.$emit("change-data", _obj);
                }
            }else {
                _this.$emit("change-data", _obj);
            }
        },
        // 级联下拉框方法
        setValue(val) {
            // console.log(val);
            if(this.isMultiple) {
                // console.log(val);
                // console.log(typeof val);
                // var setVal = val.split(",");
                this.value = val;
            }else {
                this.value = val;
            }
        },
        // 级联改变url
        setUrl(_json) {
            // 获取更新url
            this.url = _json.url;
            // 获取更新key-value
            this.key = _json.key;
            // 更新options
            this.getOptions();
        },
        // 禁用
        setDisabled(bool) {
            this.isDisabled = bool;
        },
        // 级联改变键值集合查询参数
        setParam(param) {
            this.params = param;
        },
        // 获取options
        getOptions(callback) {
            // 保存this指针
            var that = this;
            // 获取配置value-label
            var key_set;
            that.key == "" ? key_set = "" : key_set = JSON.parse(that.key);
            // 调用接口获取options
            if(that.url != "" && that.key != "") {
                $.ajax({
                    url:this.url,
                    type:"get",
                    data: this.params,
                    dataType:"json",
                    success:function(res){
                        if(res.resp.respCode == "000"){
                            if(res.resp.content.state == "1"){
                                var _jsonObj = res.resp.content.data;
                                // 键值集合-有条件数据库表查询 数据结构判断
                                _jsonObj.data != undefined ? _jsonObj = _jsonObj.data : _jsonObj = _jsonObj;
                                // 无条件数据库表查询 数据结构判断
                                _jsonObj.result != undefined ? _jsonObj = _jsonObj.result : _jsonObj = _jsonObj;
                                // 循环配置value-label
                                if(key_set != "") {
                                    for(var i = 0;i < _jsonObj.length;i++) {
                                        _jsonObj[i].value = _jsonObj[i][key_set.value];
                                        _jsonObj[i].label = _jsonObj[i][key_set.label];
                                    }
                                }
                                // 赋值options
                                that.options = _jsonObj;
                                // console.log(that.options);
                                if(callback) {
                                    callback(_jsonObj);
                                }
                            }
                        }
                    },
                    error:function(){
                        alert("错误");
                    }
                });
            }
        }
    },
    mounted() {
        // 获取初始默认值
        if(this.initial.value == "" || this.initial.value == undefined) {
            this.value = []
        }else {
            this.value = this.initial.value;
        }

        // 获取禁用标记
        if(this.initial.disabled == "false" || this.initial.disabled == "" || this.initial.disabled == undefined) {
            this.isDisabled = false;
        }else {
            this.isDisabled = true;
        }

        // 获取多选配置
        if(this.initial.multiple == "false" || this.initial.multiple == "" || this.initial.multiple == undefined) {
            this.isMultiple = false;
        }else {
            this.isMultiple = true;
        }

        // 获取options的url
        if(this.initial.url) {
            this.url = this.initial.url;
            this.params = {
                search:"",
                pageSize:"",
                pageNum:""
            }
        }else if(this.initial.params) {
            // 键值集合
            this.url = serverPath + "/keySet/queryKeyCode";
            this.params = {
                keyCode: this.initial.params
            };
        }

        // 获取配置label-value
        this.initial.key == undefined ? this.key = '{"value": "confKey", "label": "confValue"}' : this.key = this.initial.key;

        // 获取下拉框options
        this.getOptions();
    },
    updated: function () {
        //
    },
    template: `<el-select @change="changeSelect" v-model="value" :multiple="isMultiple" :disabled="isDisabled" clearable="true" placeholder="请选择">
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
    // 设置参数
    props: ["initial"],
    data() {
        return {
            value: [],  // 下拉框选中绑定值
            isDisabled: false,  // 下拉框是否禁用
            options: [],    // 下拉框option
            url: "",    // 获取option接口
            params: {}  // 调用接口参数
        };
    },
    methods: {
        changeSelect() {
            // 子组件向父组件传递的方法和参数
            this.$emit("change-datas", this.value);
        },
        getOptions() {
            // 保存this指针
            var that = this;
            // 获取配置value-label
            var key_set;
            this.initial.key == undefined ? key_set = "" : key_set = JSON.parse(this.initial.key);
            // 调用接口获取options
            if(this.url != "") {
                $.ajax({
                    url:this.url,
                    type:"get",
                    data: this.params,
                    dataType:"json",
                    success:function(res){
                        if(res.resp.respCode == "000"){
                            if(res.resp.content.state == "1"){
                                var _jsonObj = res.resp.content.data;
                                // 键值集合-有条件数据库表查询 数据结构判断
                                _jsonObj.data != undefined ? _jsonObj = _jsonObj.data : _jsonObj = _jsonObj;
                                // 无条件数据库表查询 数据结构判断
                                _jsonObj.result != undefined ? _jsonObj = _jsonObj.result : _jsonObj = _jsonObj;
                                // 循环配置value-label
                                if(key_set != "") {
                                    for(var i = 0;i < _jsonObj.length;i++) {
                                        _jsonObj[i].value = _jsonObj[i][key_set.value];
                                        _jsonObj[i].label = _jsonObj[i][key_set.label];
                                    }
                                }
                                // 赋值options
                                that.options = _jsonObj;
                                // console.log(that.options);
                            }
                        }
                    },
                    error:function(){
                        alert("错误");
                    }
                });
            }
        }
    },
    mounted() {
        // 获取初始默认值
        if(this.initial.value == "" || this.initial.value == undefined) {
            this.value = []
        }else {
            this.value = this.initial.value;
        }

        // 获取禁用标记
        if(this.initial.disabled == "false" || this.initial.disabled == "" || this.initial.disabled == undefined) {
            this.isDisabled = false;
        }else {
            this.isDisabled = true;
        }

        // 获取options的url
        if(this.initial.url) {
            this.url = this.initial.url;
            this.params = {
                search:"",
                pageSize:"",
                pageNum:""
            }
        }else if(this.initial.params) {
            // 键值集合
            this.url = serverPath + "";
            this.params = this.initial.params;
        }

        // 调用接口获取options
        this.getOptions();
    },
    template: `<el-select @change="changeSelect" v-model="value" multiple :disabled="isDisabled" placeholder="请选择">
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
    // props: ["isFilter", "defaultExpandedKeys", "defaultCheckedKeys"],
    props: ["initial"],
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val);
        }
    },
    data() {
        return {
            // 是否显示复选框
            checkbox: false,
            // 是否可过滤
            isFilter: false,
            // 过滤文本
            filterText: "",
            // 默认展开的节点
            defaultExpandedKeys: [],
            // 默认选中的节点
            defaultCheckedKeys: [],
            // 获取树数据接口
            url: "",
            // 树数据
            treeNodes: [],
            // 配置选项
            defaultProps: {}
        }
    },
    beforeMount() {
        // 获取复选框显示配置
        if(this.initial.checkbox) {
            this.checkbox = true;
        }else {
            this.checkbox = false;
        }
        // 获取配置接口
        if(this.initial.url) {
            this.url = this.initial.url;
        }else {
            this.url = "";
        }
        // 获取配置过滤Boolean值
        if(this.initial.filter) {
            this.isFilter = true;
        }else {
            this.isFilter = false;
        }
        // 获取默认展开节点
        if(this.initial.expanded) {
            this.defaultExpandedKeys = this.initial.expanded;
        }else {
            this.defaultExpandedKeys = [];
        }
        // 获取配置选中节点
        if(this.initial.checked) {
            this.defaultCheckedKeys = this.initial.checked;
        }else {
            this.defaultCheckedKeys = [];
        }
        // 获取defaultProps
        if(this.initial.defaultProps) {
            this.defaultProps = this.initial.defaultProps;
        }else {
            this.defaultProps = {};
        }
    },
    mounted() {
        // 调用接口获取树节点
        this.getNode();
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
        },
        // 调接口获取数据
        getNode(callback) {
            // 保存this指针
            var that = this;
            // 调用接口获取树节点
            if(that.url != "") {
                $.ajax({
                    url:this.url,
                    type:"get",
                    data: "",
                    dataType:"json",
                    success:function(res){
                        if(res.resp.respCode == "000"){
                            if(res.resp.content.state == "1"){
                                var _jsonObj = res.resp.content.data.result;
                                // 赋值options
                                that.treeNodes = _jsonObj;
                                // console.log(_jsonObj);
                                // console.log(that.treeNodes);
                                // 回调
                                if(callback) {
                                    callback(_jsonObj);
                                }
                            }
                        }
                    },
                    error:function(){
                        gmpPopup.throwMsg("查询失败！");
                    }
                });
            }
        }
    },
    template: `<div>
                    <el-input placeholder="输入关键字进行过滤" v-model="filterText" v-show="isFilter" style="margin-bottom: 5px;"></el-input>
                    <el-tree :data="treeNodes" :show-checkbox="checkbox" @node-click="clickNode" @check-change="checkNode" :default-expanded-keys="defaultExpandedKeys" :default-checked-keys="defaultCheckedKeys" node-key="id" ref="tree" highlight-current :props="defaultProps" :filter-node-method="filterNode">
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