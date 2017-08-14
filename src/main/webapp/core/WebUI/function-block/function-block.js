/**
 * Created by andim on 2017/8/2.
 */
//左边table
var functionBlock = new Vue({
    el:"#app",
    data:{
        loading:false,
        input:'',
        myData:[],
        leftHeight:'',
        url:serverPath+'/fronc/query',//查询所有功能块信息
        findRightDataUrl:serverPath+'/fronFuncPro/queryPro',//查询指定ID功能块的属性信息
        deleteId:'',
        editObj:'',
        divIndex:'',
        pageNum:1,//当前页号
        pageSize:10,//每页显示数据条数
        allDate:0//共多少条数据
    },
    methods:{
        //查询
        get(){
            this.$http.jsonp(this.url,{
                str:this.input
            },{
                jsonp:'callback'
            }).then(function(res){
                if(res.data.data!==null){
                    this.myData=res.data.data;
                    this.click(this.myData[0]);
                    this.deleteId = this.myData[0].rowId;
                    //properties.FindData(vm.myData[0].rowId);
                    this.getRight(this.myData[0].rowId);
                }else{
                    this.myData=[];
                }
            })
        },
        //编辑
        editBlock(){
            this.divIndex = ibcpLayer.ShowDiv('add-block.html','编辑功能块','400px', '400px',function(){
                em.isEdit = true;
                em.codeInput=functionBlock.editObj.funcCode;
                em.nameInput=functionBlock.editObj.funcName;
                em.typeInput=functionBlock.editObj.funcType;
                em.dataId=functionBlock.editObj.relateBusiObj;
                em.tableInput=functionBlock.editObj.objectName;
                em.desp=functionBlock.editObj.desp;
                em.rowId=functionBlock.editObj.rowId;
            });

        },
        //删除
        del(){
            this.$http.jsonp(serverPath+"/fronc/delete",{
                rowId:this.deleteId
            },{
                jsonp:'callback'
            }).then(function(res){
                ibcpLayer.ShowOK(res.data.message);
                functionBlock.get();
            })
        },
        //点击
        click(row, event, column){
            //properties.FindData(row.rowId);
            if(row){
                this.getRight(row.rowId);
                this.deleteId = row.rowId;
                this.editObj = row;
            }
        },
        //选中
        FindOk(row){
            this.$refs.myTable.setCurrentRow(row);
        },
        //查询右边table
        getRight(id){
            this.$http.jsonp(this.findRightDataUrl,{
                str:'',
                fronByProRowId:id,
                // tables:'',
                // proRowId:''
            },{
                jsonp:'callback'
            }).then(function(res){
                if(res.data.data!=null){
                    properties.rightData = res.data.data
                    properties.clickRightTable(properties.rightData[0]);
                }else{
                    properties.rightData = [];
                }
            })
        },
        //分页显示数据条数变化
        handleSizeChange(){},
        //分页页数变化
        handleCurrentChange(){}
    },
    created(){
        this.get();
        $(document).ready(function(){
            functionBlock.leftHeight=$(window).height()-150;
        });
        $(window).resize(function(){
            functionBlock.leftHeight=$(window).height()-150;
        })
    },
    updated(){
        if(this.myData.length>0){
            this.FindOk(this.myData[0]);
        }
    }
});

//右边table
var properties = new Vue({
    el:'#right',
    data:{
        rightData:[],
        rightHeight:'',
        rightInput:'',//右边表输入框
        rowId:'',//选中属性行ID
        funcId:'',//功能块ID
        divIndex:'',
    },
    methods:{
        handleClick(){//查看按钮
            this.divIndex = ibcpLayer.ShowDiv('attribute-details.html','属性明细','400px', '400px',function(){

            });
        },
        //点击右边table
        clickRightTable(row, event, column){
            if(row){
                this.rowId = row.rowId;
                this.funcId = row.funcRowId;
            }
        },
        //选中
        FindOk(row){
            this.$refs.myTable.setCurrentRow(row);
        },
        //编辑属性
        editData(){
            topButtonObj.rowObjId = functionBlock.editObj.rowId;
            topButtonObj.objId = functionBlock.editObj.relateBusiObj;
            topButtonObj.isEdit = true;
            topButtonObj.divIndex = ibcpLayer.ShowIframe('add-data.html','编辑属性','600px', '550px')
        },
        //删除属性
        delData(){
            this.$http.jsonp(topButtonObj.delUrl,{
                rowId:properties.rowId
            },{
                jsonp:'callback'
            }).then(function(res){
                functionBlock.getRight(properties.funcId);
                ibcpLayer.ShowOK(res.data.message);
            },function(){
                alert("删除失败")
            })
        }
    },
    created(){
        $(document).ready(function(){
            properties.rightHeight=$(window).height()-150;
        });
        $(window).resize(function(){
            properties.rightHeight=$(window).height()-150;
        })
    },
    updated(){
        if(this.rightData.length>0){
            this.FindOk(this.rightData[0]);
        }
    }
});

//按钮
var topButtonObj = new Vue({
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
                em.codeInput=functionBlock.editObj.funcCode;
                em.nameInput=functionBlock.editObj.funcName;
                em.typeInput=functionBlock.editObj.funcType;
                em.dataId=functionBlock.editObj.relateBusiObj;
                em.tableInput=functionBlock.editObj.objectName;
                em.desp=functionBlock.editObj.desp;
                em.rowId=functionBlock.editObj.rowId;
            });

        },
        del(){
            functionBlock.del();
        },
        //功能块属性
        addData(){
            topButtonObj.rowObjId = functionBlock.editObj.rowId;
            topButtonObj.objId = functionBlock.editObj.relateBusiObj;
            this.isEdit = false;
            topButtonObj.divIndex = ibcpLayer.ShowIframe('add-data.html','新增属性','500px', '550px')
        },
        editData(){
            topButtonObj.rowObjId = functionBlock.editObj.rowId;
            topButtonObj.objId = functionBlock.editObj.relateBusiObj;
            this.isEdit = true;
            topButtonObj.divIndex = ibcpLayer.ShowIframe('add-data.html','编辑属性','600px', '550px')
        },
        delData(){
            this.$http.jsonp(this.delUrl,{
                rowId:properties.rowId
            },{
                jsonp:'callback'
            }).then(function(res){
                functionBlock.getRight(properties.funcId);
                ibcpLayer.ShowOK(res.data.message);
            },function(){
                alert("删除失败")
            })
        }
    }
})