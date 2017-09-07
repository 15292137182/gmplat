/**
 * Created by jms on 2017/8/8.
 */

//常量组件
var constantComponent = Vue.extend({
    props: [],
    template: '<el-row>'+
                '<el-col :span="1">'+
                    '<el-form-item>'+
                        '<el-button type="text" size="small" @click="remove" icon="delete"></el-button>'+
                    '</el-form-item>'+
                '</el-col>'+
                '<el-col :span="5">'+
                    '<el-form-item label="常量"  label-width="40px">'+
                        '<el-input placeholder="请输入值" ></el-input>'+
                    '</el-form-item>'+
                '</el-col>'+
                '</el-row>',
    methods: {
        //删除按钮
        remove(){
            // 获取当前新增组件的index
            var _index = this.$el.attributes[1].value;
            if(_index != null) {
                add.items.splice(_index, 1);
            }
        }
    }
});

//变量组件
var alterComponent = Vue.extend({
    props: [],
    data(){
        return {
            checked:true,
            obj: '',
            objs: [
                {value:1,label:"变量1"},
                {value:2,label:"变量2"}],
        }
    },
    template: '<el-row>'+
                '<el-col :span="1">'+
                    '<el-form-item>'+
                        '<el-button @click="remove" type="text" size="small" icon="delete"></el-button>'+
                    '</el-form-item>'+
                '</el-col>'+
                '<el-col :span="6">'+
                    '<el-form-item label="变量" label-width="40px">'+
                        '<el-select placeholder="请选择" v-model="obj" style="width: 160px">'+
                            '<el-option v-for="item in objs" :key="item.value" :label="item.label" :value="item.value" placeholder="请选择"></el-option>'+
                        '</el-select>'+
                    '</el-form-item>'+
                '</el-col>'+
                '<el-col :span="4">'+
                    '<el-form-item label="或" label-width="20px">'+
                        '<el-input placeholder="请输入键"/>'+
                    '</el-form-item>'+
                '</el-col>'+
                '<el-col :span="5">'+
                    '<el-form-item label="默认值">'+
                        '<el-input placeholder="请输入默认值"/>'+
                    '</el-form-item>'+
                '</el-col>'+
                '<el-col :span="5">'+
                '<el-form-item label="是否显示">'+
                '<el-checkbox v-model="checked"></el-checkbox>'+
                '</el-form-item>'+
                '</el-col>'+
            '</el-row>',
    methods: {
        //删除按钮
        remove(){
            // 获取当前新增组件的index
            var _index = this.$el.attributes[1].value;
            if(_index != null) {
                add.items.splice(_index, 1);
            }
        }
    }
});

//日期组件
var dateComponent = Vue.extend({
    props: [],
    data(){
        return{
            checked:true
        }
    },
    template: '<el-row>'+
                '<el-col :span="1">'+
                    '<el-form-item>'+
                        '<el-button type="text" size="small" icon="delete" @click="remove"></el-button>'+
                    '</el-form-item>'+
                '</el-col>'+
                '<el-col :span="5">'+
                '<el-form-item label="日期" label-width="40px">'+
                '<el-input placeholder="请输入键" ></el-input>'+
                '</el-form-item>'+
                '</el-col>'+
                '<el-col :span="4">'+
                    '<el-form-item >（如：yyyy-MM-dd）</el-form-item>'+
                '</el-col>'+

                '<el-col :span="5">'+
                    '<el-form-item label="是否显示">'+
                        '<el-checkbox v-model="checked"></el-checkbox>'+
                    '</el-form-item>'+
                '</el-col>'+
                '</el-row>',
    methods: {
        //删除按钮
        remove(){
            // 获取当前新增组件的index
            var _index = this.$el.attributes[1].value;
            if(_index != null) {
                add.items.splice(_index, 1);
            }
        }
    }
});

//序号组件
var numComponent = Vue.extend({
    props:[],
    data(){
        return {
            checked:true,
            date: '',
            dates: [
                {value:'yyyy',label:'yyyy'},
                {value:'MM',label:'MM'},
                {value:'dd',label:'dd'}],
        }
    },
    template:'<el-row>'+
                '<el-col :span="1">'+
                '   <el-form-item>'+
                        '<el-button type="text" size="small" icon="delete" @click="remove"></el-button>'+
                    '</el-form-item>'+
                '</el-col>'+
                '<el-col :span="5">'+
                    '<el-form-item label="序号" label-width="40px">'+
                        '<el-input placeholder="请输入键" ></el-input>'+
                    '</el-form-item>'+
                '</el-col>'+

                '<el-col :span="4">'+
                '   <el-form-item label="长度" label-width="70px">'+
                '       <el-input placeholder="请输入" ></el-input>'+
                '   </el-form-item>'+
                '</el-col>'+

                '<el-col :span="4">'+
                '   <el-form-item label="循环" label-width="70px">'+
                '       <el-select placeholder="请选择" v-model="date" style="width: 100px">'+
                '           <el-option v-for="item in dates" :key="item.value" :label="item.label" :value="item.value" placeholder="请选择"></el-option>'+
                '       </el-select>'+
                '   </el-form-item>'+
                '</el-col>'+

                '<el-col :span="5">'+
                '   <el-form-item label="关联变量键" label-width="120px">'+
                '       <el-input placeholder="请输入" ></el-input>'+
                '   </el-form-item>'+
                '</el-col>'+

                '<el-col :span="5">'+
                '   <el-form-item label="是否显示">'+
                '       <el-checkbox v-model="checked"></el-checkbox>'+
                '   </el-form-item>'+
                '</el-col>'+
                '</el-row>',
    methods: {
        //删除按钮
        remove(){
            // 获取当前新增组件的index
            var _index = this.$el.attributes[1].value;
            if(_index != null) {
                add.items.splice(_index, 1);
            }
        }
    }
})

var add=new Vue({
    el:"#SequenceRuleConfigAdd",
    data:{
        name:'',
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

        rules: {
            seqCodeInput: [
                {required: true, message: '请输入代码', trigger: 'blur'},
            ],
            seqNameInput: [
                {required: true, message: '请输入名称', trigger: 'blur'}
            ],
        }
    },
    components: {
        'constant-component': constantComponent,
        'alter-component':alterComponent,
        'date-component':dateComponent,
        'num-component':numComponent
    },
    methods:{

        //新增按钮
        add() {
            switch(this.name){
                case 1:
                    this.items.push({
                        'component': 'constant-component'
                    });
                    break;
                case 2:
                    this.items.push({
                        'component':'alter-component'
                    })
                    break;
                case 3:
                    this.items.push({
                        'component':'date-component'
                    })
                    break;
                case 4:
                    this.items.push({
                        'component':'num-component'
                    })
                    break;
            }
        },
        //初始化时将组件加载到页面中
        init(){
            this.items.push({
                'component': 'constant-component'
            });
            this.items.push({
                'component':'alter-component'
            });
            this.items.push({
                'component':'date-component'
            });
            this.items.push({
                'component':'num-component'
            });
        }
    },
    created(){
        this.init();
    }
})
