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
                    success:function(res) {
                        if(res.resp.respCode == "000") {
                            if(res.resp.content.state == "1") {
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
                    error:function() {
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
            // 父子节点级联
            strictly: true,
            // 是否可过滤
            isFilter: false,
            // 过滤文本
            filterText: "",
            // 默认展开的节点
            defaultExpandedKeys: [],
            // 默认选中的节点
            defaultCheckedKeys: [],
            // 展开所有节点
            expandedAll: false,
            // 获取树数据接口
            url: "",
            // 树数据
            treeNodes: [],
            // 配置选项
            defaultProps: {
                children: 'children',
                disabled: function(data, node) {
                    // console.log(data);
                    // console.log(node);
                    if(data.orgId == "ROOT" && data.orgPid == "") {
                        // return node.disabled = true;
                    }
                }
            },
            // 树节点 key
            id: "",
            // 当前选中节点
            currentCheckedNodes: [],
            // 加载
            loading: true
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
        // 获取配置树节点 key
        if(this.initial.defaultProps.id) {
            this.id = this.initial.defaultProps.id;
        }else {
            this.id = "";
        }
        // 获取defaultProps
        if(this.initial.defaultProps && this.initial.defaultProps.label) {
            this.defaultProps.label = this.initial.defaultProps.label;
        }else {
            this.defaultProps = {
                children: 'children'
            };
        }
        // 下拉框展开 获取是否展开所有节点配置
        if(this.initial.expandedAll) {
            this.expandedAll = this.initial.expandedAll;
        }else {
            this.expandedAll = false;
        }
        // 父子节点严格级联
        if(this.initial.strictly) {
            this.strictly = this.initial.strictly;
        }else {
            this.strictly = true;
        }
    },
    mounted() {
        var self = this;
        // 调用接口获取树节点
        this.getNode(function(data) {
            var checkedKey = self.initial.checked;
            // 选中节点数据
            var checkedData = self.currentCheckedNodes;
            // 若无指定选中项 默认选中根节点
            if(checkedKey &&checkedKey.length == 0) {
                for(var i = 0;i < data.length;i++) {
                    if(data[i].orgId == "ROOT" && data[i].orgPid == "") {
                        self.setCheckedKeys([data[i].rowId]);
                        // 获取当前选中节点
                        checkedData.push(data[i]);
                    }
                }
            }else if(checkedKey && checkedKey.length > 0) {
                // 否则选中指定项
                self.setCheckedKeys(checkedKey);
                for(var i = 0;i < data.length;i++) {
                    for(var j = 0;j < checkedKey.length;j++) {
                        if(data[i].rowId == checkedKey[j]) {
                            // 获取当前选中节点
                            checkedData.push(data[i]);
                        }
                    }
                }
            }
            var _expanded = self.initial.expanded;
            var _checked = self.initial.checked;
            // 获取默认展开节点
            if(_expanded && _expanded.length > 0) {
                self.defaultExpandedKeys = JSON.parse(JSON.stringify(self.initial.expanded));
            }else {
                self.defaultExpandedKeys = [];
            }
            // 获取配置选中节点
            if(_checked && _checked.length > 0) {
                // self.defaultCheckedKeys = self.initial.checked;
                self.setCheckedKeys(self.initial.checked);
            }else {
                self.defaultCheckedKeys = [];
            }
        });

        this.loadData();
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
        // 过滤数据
        filterNode(value, data) {
            var label = this.initial.defaultProps.label;
            if (!value) return true;
            return data[label].indexOf(value) !== -1;
        },
        // 组织层级关系数据
        hierarchicalData(jsonArr) {
            var data = [];
            var nodes = [];
            // 获取配置信息
            var parent = this.initial.defaultProps.parentId;
            var self = this.initial.defaultProps.selfId;
            // 若指定父节点信息 遍历原数组
            if(parent && self) {
                for(var i = 0; i < jsonArr.length; i++) {
                    // 向每一个json数组对象下添加 children 属性值
                    jsonArr[i].children = [];
                    // 若当前json对象的节点信息为ROOT 说明其为根节点
                    if(jsonArr[i][self] == "ROOT" && jsonArr[i][parent] == "") {
                        // 将当前节点 push 到数据 data 中
                        data.push(jsonArr[i]);
                    }
                }

                for(var i = 0; i < jsonArr.length; i++) {
                    // 向每一个json数组对象下添加 children 属性值
                    jsonArr[i].children = [];
                    // 若当前json对象下有父节点信息 且为二级根节点
                    if(jsonArr[i][self] != "ROOT" && jsonArr[i][parent] != "") {
                        // 遍历查找json数组里符合该父节点信息的所有对象 并将其添加到父节点的 children 树形下
                        for(var j = 0;j < jsonArr.length;j++) {
                            if(jsonArr[j][parent] == jsonArr[i][self]) {
                                jsonArr[i].children.push(jsonArr[j]);
                            }
                        }
                    }
                    // 当前节点为一级根节点
                    if(jsonArr[i][parent] == "ROOT") {
                        // console.log(jsonArr[i]);
                        // console.log(data[0]);
                        nodes.push(jsonArr[i]);
                    }
                }

                // 将一级根节点插入根节点下
                data[0].children = nodes;
            }else {
                // 如果没指定父节点信息 当前节点信息 说明当前树结构是一级树
                data = jsonArr;
            }

            // console.log(data);
            return data;
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
                    success:function(res) {
                        if(res.resp.respCode == "000"){
                            if(res.resp.content.state == "1") {
                                var _jsonObj = res.resp.content.data.result;
                                // 节点数据赋值
                                that.treeNodes = that.hierarchicalData(_jsonObj);
                                // loading消失
                                that.loading = false;
                                // console.log(_jsonObj);
                                // console.log(that.treeNodes);
                                // 回调
                                if(callback) {
                                    callback(_jsonObj);
                                }
                            }
                        }
                    },
                    error:function() {
                        // loading消失
                        that.loading = false;
                        // 弹出错误信息
                        gmpPopup.throwMsg("查询失败！");
                    }
                });
            }
        },
        // 设置选择项
        setCheckedKeys(keys) {
            this.$refs.tree.setCheckedKeys(keys);
        },
        // 获取选中节点
        loadData(callback) {
            // 回掉函数
            if(callback) {
                callback(this.currentCheckedNodes);
            }
            // console.log(this.currentCheckedNodes);
        }
    },
    template: `<div>
                    <el-input placeholder="输入关键字进行过滤" v-model="filterText" v-show="isFilter" style="margin-bottom: 5px;"></el-input>
                    <el-tree v-loading.body="loading" element-loading-text="拼命加载中" :check-strictly="strictly" :data="treeNodes" :show-checkbox="checkbox" @node-click="clickNode" @check-change="checkNode" :default-expanded-keys="defaultExpandedKeys" :default-expand-all="expandedAll" :node-key="id" ref="tree" highlight-current :props="defaultProps" :filter-node-method="filterNode">
                    </el-tree>
                </div>`
});

/**
 * @description:树形下拉框
 * @author:liyuanquan
 */
Vue.component("select-tree", {
    // 用户配置信息
    props: ["initial"],
    data() {
        return {
            // 获取树节点接口
            url: "",
            // 树节点数据
            treeNodes: [],
            // 节点 key
            id: "",
            // 选中的节点名称
            select_node: "",
            // 选中节点 id
            select_id: "",
            // 是否显示复选框
            checkbox: "",
            // 中间参数
            middle: "",
            // 默认展开节点
            defaultExpandedKeys: [],
            // 展开所有节点
            expandedAll: false,
            // 默认选中节点 -- 仅当 checkbox 为true时
            defaultCheckedKeys: [],
            // 默认配置信息
            defaultProps: {
                children: 'children',
                disabled: function(data, node) {
                    // console.log(data);
                    // console.log(node);
                    if(data.orgId == "ROOT" && data.orgPid == "") {
                        // return node.disabled = true;
                    }
                }
            },
            // 是否显示清空图标
            clearable: true,
            // 加载
            loading: true,
            // 配置信息错误 获取数据失败
            error: true,
            // 下拉框宽度
            dropWidth: ""
        }
    },
    methods: {
        // 点击节点 返回该节点对应的对象 对应的节点 节点本身
        clickNode(obj, node, row) {
            var label = this.defaultProps.label;
            var id = this.initial.defaultProps.id;
            // console.log(obj);
            // console.log(node);
            // console.log(row);

            // 若为根节点 确认按钮不可用
            if(obj.orgId == "ROOT" && obj.orgPid == "") {
                // 确认按钮不可用
                this.error = true;
            }else {
                // 选中的节点名称
                this.middle = obj[label];
                // 选中节点 id
                this.select_id = obj[id];
                // 确认按钮可用
                this.error = false;
                // 向父组件传递方法和数据
                this.$emit("click-node", obj);
            }
        },
        // 选择复选框 返回节点对应的对象 是否被选中 节点的子树中是否有被选中的节点
        checkNode(obj, checked, node) {
            var label = this.defaultProps.label;
            var _id = this.initial.defaultProps.id;
            // 选中的节点名称
            this.middle = obj[label];
            // 选中节点 id
            this.select_id = obj[_id];
            // 返回数据增加节点选择标识
            obj.selected = checked;
            // 确认按钮状态
            // if(this.$refs.selectTree.getCheckedNodes()) {
            //     this.error = false;
            // }else {}
            this.error = !checked;
            // 向父组件传递方法和数据
            this.$emit("checked-node", obj);
        },
        // 选中节点事件
        checked() {
            // console.log(this.$refs.selectTree.getCheckedNodes());
            // 选中节点传递给中间变量
            this.select_node = this.middle;
            this.$emit("select-node", this.select_id);
        },
        // 鼠标移入事件
        enter() {
            if(this.select_node != "" && this.clearable) {
                // addClass
                $("#gmpDrop .el-input").find('.el-input__icon').addClass('el-icon-circle-close');
            }
        },
        // 鼠标移出事件
        out() {
            if(this.select_node != "" && this.clearable) {
                // removeClass
                $("#gmpDrop .el-input").find('.el-input__icon').removeClass('el-icon-circle-close');
            }
        },
        // 清除选择内容事件
        clear(ev) {
            var isClose = $("#gmpDrop .el-input").find('.el-input__icon').hasClass("el-icon-circle-close");
            // 若当前图标为关闭图标
            if(isClose && this.clearable) {
                // 阻止事件冒泡
                ev.stopPropagation();
                // 清空数据
                this.select_node = "";
                // 清除选择项 仅当复选框配置的情况下
                if(this.checkbox) {
                    this.setCheckedKeys([]);
                }
                // 移除class
                $("#gmpDrop .el-input").find('.el-input__icon').removeClass('el-icon-circle-close');
            }
        },
        // 下拉框展开事件
        expanded(bool) {
            // console.log(this);
            if(bool && this.clearable) {
                var _expanded = this.initial.expanded;
                var _checked = this.initial.checked;
                // 用Vue的方法写一个查找DOM和addClass的方法
                $("#gmpDrop .el-input").find('.el-input__icon').addClass('is-reverse');
                // 下拉框展开 树结构展开配置节点
                if(_expanded && _expanded.length > 0) {
                    this.defaultExpandedKeys = this.initial.expanded;
                }else {
                    this.defaultExpandedKeys = [];
                }
                // 下拉框展开 树结构选中配置节点
                if(_checked && _checked.length > 0 && this.select_node != "") {
                    // this.defaultCheckedKeys = this.initial.checked;
                    this.setCheckedKeys(this.initial.checked);
                    // 确认按钮可用
                    this.error = false;
                }else {
                    this.defaultCheckedKeys = [];
                    // 确认按钮不可用
                    this.error = true;
                }

            }else if(!bool  && this.clearable) {
                $("#gmpDrop .el-input").find('.el-input__icon').removeClass('is-reverse');
            }else if(!this.clearable) {
                var _expanded = this.initial.expanded;
                var _checked = this.initial.checked;
                // 下拉框展开 树结构展开配置节点
                if(_expanded && _expanded.length > 0) {
                    this.defaultExpandedKeys = this.initial.expanded;
                }else {
                    this.defaultExpandedKeys = [];
                }
                // 下拉框展开 树结构选中配置节点
                if(_checked && _checked.length > 0) {
                    // this.defaultCheckedKeys = this.initial.checked;
                    this.setCheckedKeys(JSON.parse(JSON.stringify(this.initial.checked)))
                    // 确认按钮可用
                    this.error = false;
                }else {
                    this.defaultCheckedKeys = [];
                    // 确认按钮不可用
                    this.error = true;
                }
            }
        },
        // 组织层级关系数据
        hierarchicalData(jsonArr) {
            var data = [];
            var nodes = [];
            // 获取配置信息
            var parent = this.initial.defaultProps.parentId;
            var self = this.initial.defaultProps.selfId;
            // console.log(parent);
            // console.log(self);
            // 遍历原数组
            for(var i = 0; i < jsonArr.length; i++) {
                // 向每一个json数组对象下添加 children 属性值
                jsonArr[i].children = [];
                // 若当前json对象的节点信息为ROOT 说明其为根节点
                if(jsonArr[i][self] == "ROOT") {
                    // 将当前节点 push 到数据 data 中
                    data.push(jsonArr[i]);
                }
            }

            for(var i = 0; i < jsonArr.length; i++) {
                // 向每一个json数组对象下添加 children 属性值
                jsonArr[i].children = [];
                // 若当前json对象下有父节点信息 且为二级根节点
                if(jsonArr[i][self] != "ROOT") {
                    // 遍历查找json数组里符合该父节点信息的所有对象 并将其添加到父节点的 children 树形下
                    for(var j = 0;j < jsonArr.length;j++) {
                        if(jsonArr[j][parent] == jsonArr[i][self]) {
                            jsonArr[i].children.push(jsonArr[j]);
                        }
                    }
                }
                // 当前节点为一级根节点
                if(jsonArr[i][parent] == "ROOT") {
                    // console.log(jsonArr[i]);
                    // console.log(data[0]);
                    nodes.push(jsonArr[i]);
                }
            }

            // 将一级根节点插入根节点下
            data[0].children = nodes;

            // console.log(data);
            return data;
        },
        // 调接口获取树形下拉框节点数据
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
                    success:function(res) {
                        if(res.resp.respCode == "000"){
                            if(res.resp.content.state == "1") {
                                var _jsonObj = res.resp.content.data.result;
                                // 节点数据赋值
                                that.treeNodes = that.hierarchicalData(_jsonObj);
                                // loading消失
                                that.loading = false;
                                // console.log(_jsonObj);
                                // console.log(that.treeNodes);
                                // 回调
                                if(callback) {
                                    callback(_jsonObj);
                                }
                            }
                        }
                    },
                    error:function() {
                        // loading消失
                        that.loading = false;
                        // 弹出错误信息
                        gmpPopup.throwMsg("查询失败！");
                    }
                });
            }
        },
        // 设置默认选中节点
        setCheckedKeys(keys) {
            this.$refs.selectTree.setCheckedKeys(keys);
        },
        // 设置下拉框宽度
        widthChange() {
            this.dropWidth = this.$refs.treeInput.$el.clientWidth - 2;
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
        // 树节点 key
        if(this.initial.defaultProps.id) {
            this.id = this.initial.defaultProps.id;
        }else {
            this.id = "";
        }
        // 获取defaultProps
        if(this.initial.defaultProps && this.initial.defaultProps.label) {
            this.defaultProps.label = this.initial.defaultProps.label;
        }else {
            this.defaultProps = {
                children: 'children'
            };
            // 确认按钮可用
            this.error = false;
        }
        // 获取配置是否显示清空内容图标
        if(this.initial.clearable) {
            this.clearable = this.initial.clearable;
        }else {
            this.clearable = true;
        }
        // 下拉框展开 获取是否展开所有节点配置
        if(this.initial.expandedAll) {
            this.expandedAll = this.initial.expandedAll;
        }else {
            this.expandedAll = false;
        }
    },
    mounted() {
        var self = this;
        // 获取树节点数据
        this.getNode(function(data) {
            var _checked = self.initial.checked;
            if(_checked && _checked.length > 0) {
                for(var i = 0;i < data.length;i++) {
                    if(data[i].rowId == _checked) {
                        self.select_node = data[i].orgName;
                    }
                }
            }
        });
        // 实例创建完成 设置下拉框宽度;
        this.widthChange();
        // 监听窗口事件 下拉框宽度自适应
        window.addEventListener("resize", this.widthChange);
    },
    template: `<el-dropdown id="gmpDrop" trigger="click" @visible-change="expanded">
                    <el-input v-model="select_node" :readonly="true" ref="treeInput" icon="caret-top" @mouseover.native="enter" @mouseout.native="out" :on-icon-click="clear" placeholder="请选择"></el-input>
                    <el-dropdown-menu slot="dropdown" id="select_tree" :style="{width: dropWidth + 'px'}">
                        <el-dropdown-item>
                            <div style="max-height: 320px;width: 100%;overflow-y: scroll;">
                                <el-tree :data="treeNodes" :show-checkbox="checkbox" :check-strictly="true" @node-click="clickNode" @check-change="checkNode" :default-expanded-keys="defaultExpandedKeys" :default-expand-all="expandedAll" :node-key="id" ref="selectTree" highlight-current :props="defaultProps"></el-tree>
                            </div>
                        </el-dropdown-item>
                        <el-dropdown-item>
                            <el-button size="mini" type="primary" icon="check" @click="checked" :disabled="error"></el-button>
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>`
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