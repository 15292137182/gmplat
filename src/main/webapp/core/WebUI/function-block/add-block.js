/**
 * Created by andim on 2017/8/7.
 */
//业务对象路径
var businObjPageUrl=serverPath + "/businObj/queryPage"

var em=new Vue({
    el:'#addBlock',
    data: {
        labelPosition:'right',
        formTable:{
            codeInput:'',//funcCode功能代码
            nameInput:'',//funcName功能名称
            Module:'',//所属模块
            System:'',//所属系统
            typeInput:'',//类型
            objData:'',//关联对象
            desp:'',
        },
        rules:{
            codeInput:[{ required: true, message: '请输入代码', trigger: 'blur' },
                {max: 32, message: '长度在 1 到 32 个字符', trigger: 'blur' }],
            nameInput:[{ required: true, message: '请输入名称', trigger: 'blur' },
                {max: 128, message: '长度在 1 到 64 个汉字', trigger: 'blur' }],
            typeInput:[{ required: true, message: '请选择类型', trigger: 'blur' }],
            objData:[{ required: true, message: '请选择关联对象', trigger: 'blur' }],
            Module:[{ required: true, message: '请选择所属模板', trigger: 'blur' }],
            desp:[{max: 1024, message: '长度在 1 到 512 个汉字', trigger: 'blur' }],
        },
        dataId:'',//relateBusiObj关联对象ID
        isEdit:'',
        rowId:'',

        //类型下拉框数据
        functionBlockType_1:{
            params:'functionBlockType',
            value:'',
            disabled:false
        },

        //关联对象下拉框数据
        obj_1:{
            url:businObjPageUrl,
            key:'{"label":"objectName","value":"rowId"}',
            value:'',
            disabled:false
        },

        //所属模块
        belongModule_1:{
            params:'belongModule',
            value:'',
            disabled:false
        }
    },
    methods:{
        searchConnectObj(){//查询所有关联对象
            var htmlUrl = 'show-add-block.html';
            littledivIndex = ibcpLayer.ShowIframe(htmlUrl, '关联对象数据', '400px', '420px',false);
        },
        addBlock(){//新增
            /**
             * tsj 07/8/29 新增功能块ajax代码重构,新增所属模块，系统字段
             **/
            var data = {
                "url":serverPath+"/fronc/add",
                "jsonData":{
                    "funcCode":em.formTable.codeInput,
                    "funcName":em.formTable.nameInput,
                    "funcType":em.functionBlockType_1.value,
                    "relateBusiObj":em.obj_1.value,
                    "belongModule":em.belongModule_1.value,
                  //  "belongSystem":em.formTable.System,
                    "desp":em.formTable.desp
                },
                "obj":em,
                "showMsg":true,
            }
            gmpAjax.showAjax(data,function(res){
                ibcpLayer.Close(topButtonObj.divIndex);
                    queryData.getData(functionBlock.Selurl,functionBlock.input,functionBlock,function(res){
                                 properties.getRight(functionBlock.tableData[0].rowId);
                             })
            })
        },
        editBlock(){//编辑
            /**
             * tsj 07/8/29 编辑功能块ajax代码重构,增加所属模块，系统字段
             **/
            var data = {
                "url":serverPath+"/fronc/modify",
                "jsonData":{
                    "rowId":this.rowId,
                    "funcCode":this.formTable.codeInput,
                    "funcName":this.formTable.nameInput,
                    "funcType":this.functionBlockType_1.value,
                    "relateBusiObj":this.obj_1.value,
                    "belongModule":this.belongModule_1.value,
                  //  "belongSystem":this.formTable.System,
                    "desp":this.formTable.desp
                },
                "obj":em,
                "showMsg":true,
            }
            gmpAjax.showAjax(data,function(res){
                ibcpLayer.Close(functionBlock.divIndex);
                queryData.getData(functionBlock.Selurl,functionBlock.input,functionBlock);
            })
        },
        conformEvent(formName){
            // var datas=[this.$refs.nameInput];
            // for(var i=0;i<datas.length;i++){
            //     if(datas[i].value==''){
            //         ibcpLayer.ShowMsg(datas[i].placeholder);
            //         return;
            //     }
            // }
            if(em.isEdit){//编辑
                // editObj.editOk(function(){
                //     em.editBlock();
                // })
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        editObj.editOk(function(){
                            em.editBlock();
                        })
                    } else {
                        return false;
                    }
                })
            }else{//新增
                // addObj.addOk(function(){
                //     em.addBlock();
                // })
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        addObj.addOk(function(){
                            em.addBlock();
                        })
                    } else {
                        return false;
                    }
                })
            }
        },
        cancel(){
            if(!em.isEdit){
                ibcpLayer.Close(topButtonObj.divIndex);
            }
            ibcpLayer.Close(functionBlock.divIndex);
        },

        //类型
        getFunctionBlockType_1(datas){
            this.functionBlockType_1.value = datas.value;
            this.formTable.typeInput = datas.value;
        },
        //关联对象下拉框数据
        getObj_1(datas){
            this.obj_1.value=datas.value;
            this.formTable.objData = datas.value;
        },

        //所属模块
        getBelongModule_1(datas){
            this.belongModule_1.value=datas.value;
            this.formTable.Module=datas.value;
        }
    }
})