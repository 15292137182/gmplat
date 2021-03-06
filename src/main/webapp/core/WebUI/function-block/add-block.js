/**
 * Created by andim on 2017/8/7.
 */
var objPath=serverPath + "/businObj/query"
Vue.component('select-conobj', SelectOptions.setOpt('ObjSelect','connectObj','',objPath));
Vue.component('select-fbtype', SelectOptions.setOpt('','','functionBlockType',''));

var em=new Vue({
    el:'#addBlock',
    data: {
        labelPosition:'right',
        formTable:{
            codeInput:'',//funcCode功能代码
            nameInput:'',//funcName功能名称
            Module:'',//所属模块
            System:'',//所属系统
            desp:'',
        },
        dataId:'',//relateBusiObj关联对象ID
        isEdit:'',
        rowId:''
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
                    "funcType":em.$refs.fbtype.value,
                    "relateBusiObj":em.$refs.conObj.connectObj,
                    "belongModule":em.formTable.Module,
                    "belongSystem":em.formTable.System,
                    "desp":em.formTable.desp
                },
                "obj":em
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
                    "funcType":this.$refs.fbtype.value,
                    "relateBusiObj":this.$refs.conObj.connectObj,
                    "belongModule":this.formTable.Module,
                    "belongSystem":this.formTable.System,
                    "desp":this.formTable.desp
                },
                "obj":em
            }
            gmpAjax.showAjax(data,function(res){
                ibcpLayer.Close(functionBlock.divIndex);
                queryData.getData(functionBlock.Selurl,functionBlock.input,functionBlock);
            })
        },
        conformEvent(){
            var datas=[this.$refs.nameInput];
            for(var i=0;i<datas.length;i++){
                if(datas[i].value==''){
                    ibcpLayer.ShowMsg(datas[i].placeholder);
                    return;
                }
            }
            if(em.isEdit){//编辑
                editObj.editOk(function(){
                    em.editBlock();
                })
            }else{//新增
                addObj.addOk(function(){
                    em.addBlock();
                })
            }
        },
        cancel(){
            if(!em.isEdit){
                ibcpLayer.Close(topButtonObj.divIndex);
            }
            ibcpLayer.Close(functionBlock.divIndex);
        }
    }
})