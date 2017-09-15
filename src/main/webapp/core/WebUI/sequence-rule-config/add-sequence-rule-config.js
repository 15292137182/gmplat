/**
 * Created by jms on 2017/8/8.
 */

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
                constantValue:""
            }
        }
    }
    //变量
    if(type=='2'){
        return function(){
            return {
                keyInput:'',
                valueInput:'',
                checked:true,
                childObjoptions: window.parent.config.objoptions,
            }
        }
    }
    //日期
    if(type=='3'){
        return function(){
            return {
                dateValue:'',
                checked:true,
            }
        }
    }
    //序号
    if(type=='4'){
        return function(){
            return{
                numKey:'',
                numLen:'',
                dateCircle:'',
                relatedConstantKey:'',
                checked: false,
                dates: ''
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
        _template=`<el-form :prop="index" type="1">
                        <el-row>
                            <el-col :span="1">
                                <el-form-item>
                                    <el-button type="text" size="small" @click="remove" icon="delete"></el-button>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="常量" label-width="40px">
                                    <el-input @blur="getData" v-data="constantValue" v-model="constantValue" placeholder="请输入值"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>`;
    }
    if(type=='2'){
        _template=`<el-form :prop="index" type="2">
                        <el-row>
                            <el-col :span="1">
                                <el-form-item>
                                    <el-button @click="remove" type="text" size="small" icon="delete"></el-button>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="变量" label-width="40px">
                                    <el-cascader filterable placeholder="请选择对象属性" :options="childObjoptions" style="width: 160px"></el-cascader>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item label="或" label-width="20px">
                                    <el-input @blur="getData" v-data="keyInput" v-model="keyInput" placeholder="请输入键"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="默认值" label-width="80px">
                                    <el-input @blur="getData" v-data="valueInput" v-model="valueInput" placeholder="请输入默认值"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="是否显示">
                                    <el-checkbox @blur="getData" v-data="checked" v-model="checked"></el-checkbox>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>`;
    }
    if(type=='3'){
        _template=`<el-form :prop="index" type="3">
                        <el-row>
                                <el-col :span="1">
                                    <el-form-item>
                                        <el-button @click="remove" type="text" size="small" icon="delete"></el-button>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="5">
                                    <el-form-item label="日期" label-width="40px">
                                        <el-input placeholder="请输入日期格式" @blur="getData" v-data="dateValue" v-model="dateValue"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="4">
                                        <el-form-item > （如：yyyy-MM-dd）</el-form-item>
                                </el-col>
                    
                                <el-col :span="5">
                                    <el-form-item label="是否显示">
                                        <el-checkbox @change="getData" v-data="checked" v-model="checked"></el-checkbox>
                                    </el-form-item>
                                </el-col>
                        </el-row>
                    </el-form>`;
    }
    if(type=='4'){
        _template=`<el-form :prop="index" type="4">
                        <el-row>
                            <el-col :span="1">
                               <el-form-item>
                                   <el-button @click="remove" type="text" size="small" icon="delete"></el-button>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item label="序号" label-width="40px">
                                    <el-input placeholder="请输入键" @blur="getData" v-data="numKey" v-model="numKey"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                               <el-form-item label="长度" label-width="70px">
                                   <el-input placeholder="请输入" @blur="getData" v-data="numLen" v-model="numLen"></el-input>
                               </el-form-item>
                            </el-col>
                            <el-col :span="4">
                               <el-form-item label="循环" label-width="70px">
                                   <el-select placeholder="请选择" @change="getData" v-data="dateCircle" v-model="dateCircle" style="width: 100px">
                                       <el-option v-for="item in dates" :key="item.value" :label="item.label" :value="item.value" placeholder="请选择"></el-option>
                                   </el-select>
                               </el-form-item>
                            </el-col>
                            <el-col :span="5">
                               <el-form-item label="关联变量键" label-width="120px">
                                   <el-input placeholder="请输入" @blur="getData" v-data="relatedConstantKey" v-model="relatedConstantKey"></el-input>
                               </el-form-item>
                            </el-col>
                            <el-col :span="5">
                               <el-form-item label="是否显示">
                                   <el-checkbox @change="getData" v-data="checked" v-model="checked"></el-checkbox>
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
            //删除
            remove() {
                console.log(add.items);
                // var _index = this.$el.attributes.prop.value;
                // add.items.splice(_index, 1);
            },
            //失去焦点触发
            getData() {
                var $cnt = "";
                for(var i = 0;i < _vue.length;i++) {
                    var _data = JSON.parse(JSON.stringify(_vue[i].$data));
                    // 常量
                    if(_vue[i].$el.attributes.type.value == "1") {
                        for(var key in _data) {
                            $cnt += "@{" + _data[key] + "}&&";
                        }
                    }
                    // 变量
                    if(_vue[i].$el.attributes.type.value == "2") {
                        var _variable = "";
                        for(var key in _data) {
                            if(_data[key] === true) {
                                _data[key] = 1;
                            }else if(_data[key] === false) {
                                _data[key] = 0;
                            }else if(_data[key] == "") {
                                _data[key] = "null";
                            }
                            _variable += _data[key] + ";";
                        }
                        $cnt += "#{" + _variable + "}&&";
                    }
                    // 日期
                    if(_vue[i].$el.attributes.type.value == "3") {
                        var _date = "";
                        for(var key in _data) {
                            if(_data[key] === true) {
                                _data[key] = 1;
                            }else if(_data[key] === false) {
                                _data[key] = 0;
                            }else if(_data[key] == "") {
                                _data[key] = "null";
                            }
                            _date += _data[key]  + ";";
                        }
                        $cnt += "${" + _date + "}&&";
                    }
                    // 序号
                    if(_vue[i].$el.attributes.type.value == "4") {
                        var _number = "";
                        for(var key in _data) {
                            if(_data[key] === true) {
                                _data[key] = 1;
                            }else if(_data[key] === false) {
                                _data[key] = 0;
                            }else if(_data[key] == "") {
                                _data[key] = "null";
                            }
                            _number += _data[key] + ";";
                        }
                        $cnt += "*{" + _number + "}&&";
                    }
                }
                if($cnt.length>0){
                    $cnt=$cnt.substr(0,$cnt.length-2);
                }
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
                            belongModule:add.formTable.belongModuleInput,
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
                            belongModule:add.formTable.belongModuleInput,
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
                add.formTable.belongModuleInput=data[0].belongModule;
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