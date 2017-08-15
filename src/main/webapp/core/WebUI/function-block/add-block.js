/**
 * Created by andim on 2017/8/7.
 */
var em=new Vue({
    el:'#addBlock',
    data: {
        codeInput :'',//funcCode功能代码
        nameInput:'',//funcName功能名称
        typeInput:'',//funcType功能类型
        tableInput:'',//relateBusiObj关联对象val
        dataId:'',//relateBusiObj关联对象ID
        desp:'',
        isEdit:'',
        rowId:''
    },
    methods:{
        searchConnectObj(){
            var htmlUrl = 'show-add-block.html';
            littledivIndex = ibcpLayer.ShowIframe(htmlUrl, '关联对象数据', '400px', '420px',false);
        },
        conformEvent(){
            var datas=[this.$refs.codeInput,this.$refs.nameInput,this.$refs.typeInput,this.$refs.tableInput];
            for(var i=0;i<datas.length;i++){
                if(datas[i].value==''){
                    ibcpLayer.ShowMsg(datas[i].placeholder);
                    return;
                }
            }
            if(em.isEdit){//编辑
                this.$http.jsonp(serverPath+"/fronc/modify",{
                    "rowId":this.rowId,
                    "funcCode":this.codeInput,
                    "funcName":this.nameInput,
                    "funcType":this.typeInput,
                    "relateBusiObj":this.dataId,
                    "desp":this.desp
                },{
                    jsonp:'callback'
                }).then(function(res){
                    ibcpLayer.ShowOK(res.data.message);
                    functionBlock.get();
                },function(){
                    alert("error")
                });
            }else{//新增
                this.$http.jsonp(serverPath+"/fronc/add",{
                    "funcCode":this.codeInput,
                    "funcName":this.nameInput,
                    "funcType":this.typeInput,
                    "relateBusiObj":this.dataId,
                    "desp":this.desp
                },{
                    jsonp:'callback'
                }).then(function(res){
                    ibcpLayer.ShowOK(res.data.message);
                    ibcpLayer.Close(topButtonObj.divIndex);
                    functionBlock.get();
                },function(){
                    alert("error")
                });
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