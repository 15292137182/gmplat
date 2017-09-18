/**
 * Created by jms on 2017/8/8.
 */
var busObjUrl=serverPath+"/businObj/queryPage";
var busObjProUrl=serverPath+"/businObj/queryProPage";

// 页面动态加载vue实例集合
var _vue = [];
// 防止页面数更新 创建无效vue实例的标识
var flag = false;

//构造函数
function  new_vue(){};

//构造data返回值函数
new_vue.prototype.data=function (type) {
    //常量
    if(type=='1'){
        return function(){
            return {
                render: {
                    show: true,
                    constantValue:""
                }
            }
        }
    }
    //变量
    if(type=='2'){
        return function(){
            return {
                render: {
                    show: true,
                    keyInput:'',
                    valueInput:'',
                    checked:true,
                 //   childObjoptions: window.parent.config.objoptions,
                },
                busObj_1:{
                    url: busObjUrl,
                    key:'{"label":"objectName","value":"rowId"}',
                    value: "",
                    disabled: "false"
                },
                busObjPro_1:{
                    url:"",
                    key:"",
                    value: "",
                    disabled: "false"
                },
            }
        }
    }
    //日期
    if(type=='3'){
        return function(){
            return {
                render: {
                    show: true,
                    dateValue:'',
             //       checked:true
                }
            }
        }
    }
    //序号
    if(type=='4'){
        return function(){
            return{
                render: {
                    show: true,
                    numKey:'',
                    numLen:'',
                    relatedConstantKey:'',
                    checked: true,
                    dates: []
                },
                loop_1:{
                    params:"loop",
                    value: "",
                    disabled: "false"
                },
            }
        }
    }
};

//构造函数原型链
new_vue.prototype.creat = function(_id, type) {
    //定义模板
    var _template;

    //常量模板
    if(type=='1'){
        _template=`<el-form type="1" v-show="render.show">
                        <el-row>
                            <el-col :span="1">
                                <el-form-item>
                                    <el-button type="text" size="small" @click="remove" icon="delete"></el-button>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="常量" label-width="40px">
                                    <el-input @blur="getData" v-model="render.constantValue" placeholder="请输入值"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>`;
    }
    if(type=='2'){
        _template=`<el-form type="2" v-show="render.show">
                        <el-row>
                            <el-col :span="1">
                                <el-form-item>
                                    <el-button @click="remove" type="text" size="small" icon="delete"></el-button>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="变量" label-width="40px">
                                    <!--<el-cascader filterable placeholder="请选择对象属性" :options="childObjoptions" style="width: 160px"></el-cascader>-->
                                    <single-selection @change-data="getBusObj_1"  :initial="busObj_1" ref="busObj_1" style="width: 120px" placeholder="请选择业务对象"></single-selection>
                                    <single-selection @change-data="getBusObjPro_1"  :initial="busObjPro_1" ref="busObjPro_1" style="width: 120px" placeholder="请选择业务对象属性"></single-selection>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="或" label-width="20px">
                                    <el-input @blur="getData" v-model="render.keyInput" placeholder="请输入键"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="默认值" label-width="80px">
                                    <el-input @blur="getData" v-model="render.valueInput" placeholder="请输入默认值"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="是否显示" label-width="100px">
                                    <el-checkbox @blur="getData" v-model="render.checked"></el-checkbox>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>`;
    }
    if(type=='3'){
        _template=`<el-form type="3" v-show="render.show">
                        <el-row>
                                <el-col :span="1">
                                    <el-form-item>
                                        <el-button @click="remove" type="text" size="small" icon="delete"></el-button>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="5">
                                    <el-form-item label="日期" label-width="40px">
                                        <el-input placeholder="请输入日期格式" @blur="getData" v-model="render.dateValue"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="4">
                                        <el-form-item > （如：yyyy-MM-dd）</el-form-item>
                                </el-col>
                    
                                <!--<el-col :span="5">-->
                                    <!--<el-form-item label="是否显示">-->
                                        <!--<el-checkbox @change="getData" v-model="render.checked"></el-checkbox>-->
                                    <!--</el-form-item>-->
                                <!--</el-col>-->
                        </el-row>
                    </el-form>`;
    }
    if(type=='4'){
        _template=`<el-form type="4" v-show="render.show">
                        <el-row>
                            <el-col :span="1">
                               <el-form-item>
                                   <el-button @click="remove" type="text" size="small" icon="delete"></el-button>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="序号" label-width="40px">
                                    <el-input placeholder="请输入键" @blur="getData" v-model="render.numKey"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                               <el-form-item label="长度" label-width="70px">
                                   <el-input placeholder="请输入" @blur="getData" v-model="render.numLen"></el-input>
                               </el-form-item>
                            </el-col>
                            <el-col :span="4">
                               <el-form-item label="循环" label-width="70px">
                                      <single-selection @change-data="getLoop_1"  :initial="loop_1" ref="loop_1" style="width: 100px"></single-selection>

                                   <!--<el-select placeholder="请选择" @change="getData" v-model="render.dateCircle" style="width: 100px">-->
                                       <!--<el-option v-for="item in render.dates" :key="item.value" :label="item.label" :value="item.value" placeholder="请选择"></el-option>-->
                                   <!--</el-select>-->
                               </el-form-item>
                            </el-col>
                            <el-col :span="5">
                               <el-form-item label="关联变量键" label-width="120px">
                                   <el-input placeholder="请输入" @blur="getData" v-model="render.relatedConstantKey"></el-input>
                               </el-form-item>
                            </el-col>
                            <el-col :span="5">
                               <el-form-item label="是否显示" label-width="100px">
                                   <el-checkbox @change="getData" v-model="render.checked"></el-checkbox>
                               </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>`;
    }

    //实例化对象，绑定接受组件参数
    var _obj_=new new_vue();

    //拼接vue实例
    new Vue({
        el:_id,
        template:_template,
        data:_obj_.data(type),
        mounted() {
            // 将当前实例添加到全局变量数组
            _vue.push(this);
        },
        methods: {
            getBusObj_1(datas){
                this.$data.busObj_1.value=datas.value;
                var rowId=datas.value;
                if(typeof(rowId) == "undefined"){
                    rowId="";
                }
                this.$refs.busObjPro_1.setUrl({url:busObjProUrl+"?rowId="+rowId,key:'{"label":"propertyName","value":"rowId"}'});
            },
            getBusObjPro_1(datas){
                this.$data.busObjPro_1.value=datas.value;
            },
            getLoop_1(datas){
                this.$data.loop_1.value=datas.value;
                this.getData();
            },

            //删除
            remove() {
                // 将当前区块和挂载实例隐藏
                this.render.show = false;
                // 删除实例属性值
                var _data = this.$data.render;
                for(var key in _data) {
                    if(key != "show") {
                        Vue.delete(this.$data.render, key);
                    }
                }
                // 将当前挂载实例类型转为0 类型判断就回过滤调当前实例
                this.$el.attributes.type.value = 0;
                // 更新内容模块绑定数据
                this.getData();
            },
            //失去焦点触发
            getData() {
                var $cnt = "";
                for(var i = 0;i < _vue.length;i++) {
                    var _data = JSON.parse(JSON.stringify(_vue[i].$data.render));
                    // 常量
                    if(_vue[i].$el.attributes.type.value == "1") {
                        // 循环遍历取出实例中data
                        for(var key in _data) {
                            // 过滤data中的show属性
                            if(key != "show") {
                                if(_data[key] == "") {
                                    _data[key] = null;
                                }
                                $cnt += "@{" + _data[key] + "}&&";
                            }
                        }
                    }
                    // 变量
                    if(_vue[i].$el.attributes.type.value == "2") {
                        var _variable = "";
                        // 循环遍历取出实例中data
                        for(var key in _data) {
                            // 过滤data中的show属性
                            if(key != "show") {
                                // 将Boolean值转化为二进制数值
                                if(_data[key] === true) {
                                    _data[key] = 1;
                                }else if(_data[key] === false) {
                                    _data[key] = 0;
                                }else if(_data[key] == "") {
                                    _data[key] = "null";
                                }
                                _variable += _data[key] + ";";
                            }
                        }
                        $cnt += "#{" + _variable + "}&&";
                    }
                    // 日期
                    if(_vue[i].$el.attributes.type.value == "3") {
                        //var _date = "";
                        // 循环遍历取出实例中data
                        // for(var key in _data) {
                        //     // 过滤data中的show属性
                        //     if(key != "show") {
                        //         // 将Boolean值转化为二进制数值
                        //         if(_data[key] === true) {
                        //             _data[key] = 1;
                        //         }else if(_data[key] === false) {
                        //             _data[key] = 0;
                        //         }else if(_data[key] == "") {
                        //             _data[key] = "null";
                        //         }
                        //         _date += _data[key]  + ";";
                        //     }
                        // }
                        // $cnt += "${" + _date + "}&&";

                        //去除是否显示
                        for(var key in _data) {
                            // 过滤data中的show属性
                            if(key != "show") {
                                if(_data[key] == "") {
                                    _data[key] = null;
                                }
                                $cnt += "${" + _data[key] + "}&&";
                            }
                        }
                    }
                    // 序号
                    if(_vue[i].$el.attributes.type.value == "4") {
                        var _number = "";
                        var loopValue=_vue[i].$data.loop_1.value;
                        if(typeof (loopValue) =="undefined")
                            loopValue="";

                        // 循环遍历取出实例中data
                        for(var key in _data) {
                            // 过滤data中的show属性 dates属性
                            if(key != "show" && key != "dates" && key !="relatedConstantKey") {
                                // 将Boolean值转化为二进制数值
                                if(_data[key] === true) {
                                    _data[key] = 1;
                                }else if(_data[key] === false) {
                                    _data[key] = 0;
                                }else if(_data[key] == "") {
                                    _data[key] = "null";
                                }
                                if(key =="numLen"){
                                    _data[key]= _data[key]+"-"+loopValue+"-"+_data["relatedConstantKey"]
                                }
                                _number += _data[key] + ";";
                            }
                        }
                        $cnt += "*{" + _number + "}&&";
                    }
                }
                if($cnt.length>0){
                    $cnt=$cnt.substr(0,$cnt.length-2);
                }
                // 将拼接字符串赋值给内容输入框
                add.formTable.seqContentInput = $cnt;
            },
        }
    });

    // 实例生成后将flag置为false 防止生成无效实例
    flag = false;
};

var _html=Vue.extend({
    props:["childNum", "index"],
    data() {
        return {
            name: ""
        }
    },
    mounted() {
        // 拼接新增HTML的id
        this.name = "cnt-" + this.childNum;
    },
    template: "<div :id='this.name'></div>"
});


var add = new Vue({
    el:"#SequenceRuleConfigAdd",
    data: function() {
        return {
            num:0,
            name:"",
            falg: false,
            names:[
                {name:1,label:'常量'},
                {name:2,label:'变量'},
                {name:3,label:'日期'},
                {name:4,label:'序号'}
            ],
            items: [],
            disabled:false,
            labelPosition:'right',
            formTable:{
                seqCodeInput:'',
                seqNameInput:'',
                seqContentInput:'',
                despInput:'',
                versionInput:'',
            },
            rowId:'',//选中的rowId
            contentShow: '',
            rules: {
                seqCodeInput: [
                    {required: true, message: '请输入代码', trigger: 'blur'},
                ],
                seqNameInput: [
                    {required: true, message: '请输入名称', trigger: 'blur'}
                ],
            },
            //所属模块下拉框
            belongModule_1:{
                params:'belongModule',
                value:'',
                disabled:'false',
            }
        }
    },
    components: {
        '$html': _html
    },
    methods:{
        //点击预览按钮
        show(){
            var data={
                "url":window.parent.mock,
                "jsonData":{content: add.formTable.seqContentInput},
                "obj":this
            }
            gmpAjax.showAjax(data,function(res){
                var data=res.data;
                var reg=/[,，]/g;

                var str=data.toString().replace(reg,"\r\n");
                add.contentShow=str;
            })
        },
        //新增按钮
        add() {
            //新增一次标识
            this.num +=1;
            //将模板push到对象数组里
            this.items.push({
                "html":"$html"
            });
            // 点击添加按钮 将flag置为true 否则无法生成实例
            flag = true;
        },
        //初始化时将组件加载到页面中
        init(){

        },
        //确定按钮
        confirm(){
            //新增
            if (window.parent.config.operate == 1) {
                addObj.addOk(function(){
                    var data={
                        "url":window.parent.insertUrl,
                        "jsonData":{
                            seqCode:add.formTable.seqCodeInput,
                            seqName:add.formTable.seqNameInput,
                            seqContent:add.formTable.seqContentInput,
                            belongModule:add.belongModule_1.value,
                        //    belongSystem:add.formTable.belongSystemInput,
                            desp:add.formTable.despInput
                        },
                        "obj":add
                    }
                    gmpAjax.showAjax(data,function(res){
                        queryData.getData(window.parent.queryPage,window.parent.config.input,window.parent.config);
                        parent.layer.close(window.parent.config.divIndex);
                    })
                })
            }
            //编辑
            if(window.parent.config.operate ==2){
                editObj.editOk(function(){
                    var data={
                        "url":window.parent.modifyUrl,
                        "jsonData":{
                            rowId:window.parent.config.rowId,
                            seqCode:add.formTable.seqCodeInput,
                            seqName:add.formTable.seqNameInput,
                            seqContent:add.formTable.seqContentInput,
                            belongModule:add.belongModule_1.value,
                            belongSystem:add.formTable.belongSystemInput,
                            desp:add.formTable.despInput
                        },
                        "obj":add
                    }
                    gmpAjax.showAjax(data,function(res){
                        queryData.getData(window.parent.queryPage,window.parent.config.input,window.parent.config);
                        parent.layer.close(window.parent.config.divIndex);
                    })
                })
            }
        },
        //取消按钮
        cancel() {
            parent.layer.close(window.parent.config.divIndex);
        },
        //编辑时将数据绑定在控件中
        bindValue(){
            this.rowId=window.parent.config.rowId;
            var data={
                "url":window.parent.queryById,
                "jsonData":{rowId:this.rowId},
                "obj":this
            }
            gmpAjax.showAjax(data,function(res){
                var data=res.data;
                add.formTable.seqCodeInput=data[0].seqCode;
                add.formTable.seqNameInput=data[0].seqName;
                add.formTable.seqContentInput=data[0].seqContent;
                add.$refs.belongModule_1.setValue(data[0].belongModule);
                add.formTable.belongSystemInput=data[0].belongSystem;
                add.formTable.despInput=data[0].desp;
                add.formTable.versionInput=data[0].version;

                // add.loadTemplate(data[0].seqContent);
            })
        },
        loadTemplate(data) {
            var _arr = data.split("&&");
            // 编辑状态回填动态实例
            $.each(_arr, function(index, item) {
                var _first = item.substr(0, 1);
                if(_first == "@") {
                    add.name = "1";
                    add.add();
                }
                if(_first == "$") {
                    add.name = "2";
                    add.add();
                }
                if(_first == "#") {
                    add.name = "3";
                    add.add();
                }
                if(_first == "*") {
                    add.name = "4";
                    add.add();
                }
            });
        },
        //模块下拉框
        getBelongModule_1(datas){
            this.belongModule_1.value=datas.value;
        }
    },
    created(){
        // if(window.parent.config.operate ==1){
        //     this.init();
        // }

        if(window.parent.config.operate == 2){
            this.bindValue();
        }
    },
    updated() {
        //新增创建vue实例
        if(flag) {
            var _obj=new new_vue();
            _obj.creat("#cnt-"+this.num, this.name);
        }
    }
})