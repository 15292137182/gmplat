/**
 * Created by andim on 2017/8/7.
 */
var em=new Vue({
    el:'#addBlock',
    data: {
        labelPosition:'right',
        formTable:{
            codeInput:'',//funcCode功能代码
            nameInput:'',//funcName功能名称
            typeInput:'',//funcType功能类型
            tableInput:'',//relateBusiObj关联对象val
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
                "funcType":this.formTable.typeInput,
                "relateBusiObj":this.dataId,
                "desp":this.formTable.desp
            },{
                jsonp:'callback'
            }).then(function(res){
                showMsg.MsgOk(functionBlock,res);
                ibcpLayer.Close(topButtonObj.divIndex);
                functionBlock.get();
            },function(){
                showMsg.MsgError(functionBlock);
            });
        },
        editBlock(){//编辑
            this.$http.jsonp(serverPath+"/fronc/modify",{
                "rowId":this.rowId,
                "funcCode":this.formTable.codeInput,
                "funcName":this.formTable.nameInput,
                "funcType":this.formTable.typeInput,
                "relateBusiObj":this.dataId,
                "desp":this.formTable.desp
            },{
                jsonp:'callback'
            }).then(function(res){
                showMsg.MsgOk(functionBlock,res);
                ibcpLayer.Close(functionBlock.divIndex);
                functionBlock.get();
            },function(){
                showMsg(functionBlock);
            });
        },
        conformEvent(){
            var datas=[this.$refs.nameInput,this.$refs.typeInput,this.$refs.tableInput];
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