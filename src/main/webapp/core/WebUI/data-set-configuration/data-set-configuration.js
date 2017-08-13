/**
 * Created by andim on 2017/8/9.
 */
//表格
var dataSetConfig = new Vue({
    el:'#dataSetConfig',
    data:{
        loading:true,
        tableData:[],//table数据
        input:'',//搜索框输入
        Height:'',//表格高度
        rowObjId:'',//行内容ID
        rowObj:'',//行内容
        selUrl:serverPath+'/dataSetConfig/queryPage',//分页查询接口
        allDate:0,//共多少条数据
        pageSize:10,//每页显示多少条数据
        pageNum:1//当前第几页
    },
    methods:{
        searchResTable(){//分页查询
            pagingObj.Example(this.selUrl,this.input, this.pageSize,this.pageNum,this);
        },
        clickTable(row){//表格点击事件
            this.rowObjId = row.rowId;
            this.rowObj = row;
            dataSetConfigButton.dis = false;
        },
        FindOk(row){
            this.$refs.dataSetConfigTable.setCurrentRow(row);
        },
        editDataSetConfig(){
            dataSetConfigButton.editDataSetConfig();
        },
        deleteDataSetConfig(){
            dataSetConfigButton.deleteDataSetConfig();
        },
        handleSizeChange(val){
            this.pageSize=val;
            this.searchResTable();
        },
        handleCurrentChange(val){
            this.pageNum=val;
            this.searchResTable();
        }
    },
    created(){
        this.searchResTable();
        $(document).ready(function(){
            dataSetConfig.Height=$(window).height()-190;
        });
        $(window).resize(function(){
            dataSetConfig.Height=$(window).height()-190;
        })
    },
    updated(){
        if(this.tableData.length>0){
            this.FindOk(this.tableData[0]);
        }
    }
})

//按钮
var dataSetConfigButton = new Vue({
    el:'#dataSetConfigButton',
    data:{
        divIndex:'',
        dis:true,//是否禁用
        delUrl:serverPath+'/dataSetConfig/delete'//删除
    },
    methods:{
        addDataSetConfig(){//新增
            this.divIndex = ibcpLayer.ShowDiv('add-data-set.html','新增数据集配置','400px', '450px',function(){
                addDataSet.isEdit = false;
            });
        },
        editDataSetConfig(){//编辑
            this.divIndex = ibcpLayer.ShowDiv('add-data-set.html','编辑数据集配置','400px', '450px',function(){
                addDataSet.isEdit = true;
                addDataSet.datasetCode = dataSetConfig.rowObj.datasetCode;
                addDataSet.nameInput = dataSetConfig.rowObj.datasetName;
                addDataSet.typeInput = dataSetConfig.rowObj.datasetType;
                addDataSet.content = dataSetConfig.rowObj.datasetContent;
                addDataSet.desp = dataSetConfig.rowObj.desp;
                addDataSet.version = dataSetConfig.rowObj.version;
            });
        },
        deleteDataSetConfig(){//删除
            this.$http.jsonp(this.delUrl,{
                rowId:dataSetConfig.rowObjId
            },{
                jsonp:'callback'
            }).then(function(res){
                ibcpLayer.ShowOK(res.data.message);
                if(res.data.state==1){
                    dataSetConfig.searchResTable();
                }
            },function(){
                alert("删除失败")
            })
        }
    }
})