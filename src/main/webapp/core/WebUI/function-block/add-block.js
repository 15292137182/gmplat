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
            // typeInput:'',//funcType功能类型
            // tableInput:'',//relateBusiObj关联对象val
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
            this.$http.jsonp(serverPath+"/fronc/add",{
                "funcCode":this.formTable.codeInput,
                "funcName":this.formTable.nameInput,
                "funcType":this.$refs.fbtype.value,
                "relateBusiObj":this.$refs.conObj.connectObj,
                "desp":this.formTable.desp
            },{
                jsonp:'callback'
            }).then(function(res){
                showMsg.MsgOk(functionBlock,res);
                ibcpLayer.Close(topButtonObj.divIndex);
                queryData.getData(functionBlock.Selurl,functionBlock.input,functionBlock,function(res){
                    properties.getRight(functionBlock.tableData[0].rowId);
                })
            },function(){
                showMsg.MsgError(functionBlock);
            });
        },
        editBlock(){//编辑
            this.$http.jsonp(serverPath+"/fronc/modify",{
                "rowId":this.rowId,
                "funcCode":this.formTable.codeInput,
                "funcName":this.formTable.nameInput,
                "funcType":this.$refs.fbtype.value,
                "relateBusiObj":this.$refs.conObj.connectObj,
                "desp":this.formTable.desp
            },{
                jsonp:'callback'
            }).then(function(res){
                showMsg.MsgOk(functionBlock,res);
                ibcpLayer.Close(functionBlock.divIndex);
                queryData.getData(functionBlock.Selurl,functionBlock.input,functionBlock)
            },function(){
                showMsg(functionBlock);
            });
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