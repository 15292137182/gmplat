/**
 * Created by andim on 2017/8/2.
 */
//左边table
var vm = new Vue({
    el:"#app",
    data:{
        input:'',
        myData:[],
        leftHeight:'',
        url:serverPath+'/fronc/query',
        deleteId:'',
        editObj:''
    },
    methods:{
        get(){
            this.$http.jsonp(this.url,{
                str:this.input
            },{
                jsonp:'callback'
            }).then(function(res){
                if(res.data.data!==null){
                    this.myData=res.data.data;
                    vm1.FindData(vm.myData[0].rowId);
                }
            })
        },
        delete(){
            this.$http.jsonp(serverPath+"/fronc/delete",{
                rowId:this.deleteId
            },{
                jsonp:'callback'
            }).then(function(res){
                ibcpLayer.ShowOK(res.data.message);
                vm.get();
            })
        },
        click(row, event, column){
            vm1.FindData(row.rowId);
            this.deleteId = row.rowId;
            this.editObj = row;
        },
        FindOk(row){
            this.$refs.myTable.setCurrentRow(row);
        }
    },
    created(){
        this.get();
        $(document).ready(function(){
            vm.leftHeight=$(window).height()-90;
        });
        $(window).resize(function(){
            vm.leftHeight=$(window).height()-90;
        })
    },
    updated(){
        if(this.myData.length>0){
            this.FindOk(this.myData[0]);
        }
    }
});

//右边table
var vm1 = new Vue({
    el:'#right',
    data:{
        rightData:[],
        rightHeight:'',
        url:serverPath+'/fronFuncPro/query',//查询功能块
        rowId:'',//选中属性行ID
        funcId:'',//功能块ID
        divIndex:''
    },
    methods:{
        FindData(id){
            this.$http.jsonp(this.url,{
                "str":'',
                "rowId":id
            },{
                jsonp:'callback'
            }).then(function (res) {
                if(res.data.data!=null){
                    this.rightData=res.data.data;
                }else{
                    this.rightData=[];
                }
            },function(){
                alert("error")
            })
        },
        handleClick(){//查看按钮
            this.divIndex = ibcpLayer.ShowDiv('attribute-details.html','属性明细','400px', '400px',function(){

            });
        },
        clickRightTable(row, event, column){
            this.rowId = row.rowId;
            this.funcId = row.funcRowId;
        }
    },
    created(){
        $(document).ready(function(){
            vm1.rightHeight=$(window).height()-90;
        });
        $(window).resize(function(){
            vm1.rightHeight=$(window).height()-90;
        })
    },
});

//按钮
var mb = new Vue({
    el:'#myButton',
    data:{
        divIndex:'',
        rowObjId:'',//功能块ID
        objId:'',//关联业务对象ID
        isEdit:'',//是否编辑
        delUrl:serverPath+'/fronFuncPro/delete'//删除属性接口
    },
    methods: {
        //功能块
        addBlock(){
            this.divIndex = ibcpLayer.ShowDiv('add-block.html','新增功能块','400px', '400px',function(){
                em.isEdit = false;
            });
        },
        editBlock(){
            this.divIndex = ibcpLayer.ShowDiv('add-block.html','编辑功能块','400px', '400px',function(){
                em.isEdit = true;
                em.codeInput=vm.editObj.funcCode;
                em.nameInput=vm.editObj.funcName;
                em.typeInput=vm.editObj.funcType;
                em.dataId=vm.editObj.relateBusiObj;
                em.tableInput=vm.editObj.tables;
                em.desp=vm.editObj.desp;
                em.rowId=vm.editObj.rowId;
            });

        },
        del(){
            vm.delete();
        },
        //功能块属性
        addData(){
            mb.rowObjId = vm.editObj.rowId;
            mb.objId = vm.editObj.relateBusiObj;
            this.isEdit = false;
            mb.divIndex = ibcpLayer.ShowIframe('add-data.html','新增属性','500px', '550px')
        },
        editData(){
            mb.rowObjId = vm.editObj.rowId;
            mb.objId = vm.editObj.relateBusiObj;
            this.isEdit = true;
            mb.divIndex = ibcpLayer.ShowIframe('add-data.html','编辑属性','600px', '550px')
        },
        delData(){
            this.$http.jsonp(this.delUrl,{
                delData:vm1.rowId
            },{
                jsonp:'callback'
            }).then(function(res){
                vm1.FindData(vm1.funcId);
                ibcpLayer.ShowOK(res.data.message);
                console.log(res.data.data);
            },function(){
                alert("删除失败")
            })
        }
    }
})