/**
 * Created by andim on 2017/8/9.
 */
//表格
var dataSetConfig = new Vue({
    el:'#dataSetConfig',
    data:getData.dataObj({
        /**
         * tsj 07/8/28 替换vue data
         **/
        tableId:'dataSetConfig',
        rowObjId:'',//行内容ID
        rowObj:'',//行内容
        selUrl:serverPath+'/dataSetConfig/queryPage',//分页查询接口
    }),
    methods:{
        searchResTable(){//分页查询
            pagingObj.Example(this.selUrl,this.input, this.pageSize,this.pageNum,this);
        },
        clickTable(row){//表格点击事件
            console.log(row)
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
        },
        headSort(column){
            pagingObj.headSort(this.selUrl,this.input,this.pageSize,this.pageNum,column,this);
        }
    },
    created(){
        var args={"dataSetConfig":{datasetType:"dataSetConfigType"}};
        TableKeyValueSet.init(args);
        this.searchResTable();
        // $(document).ready(function(){
        //     dataSetConfig.Height=$(window).height()-190;
        // });
        // $(window).resize(function(){
        //     dataSetConfig.Height=$(window).height()-190;
        // })
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
            this.divIndex = ibcpLayer.ShowDiv('add-data-set.html','新增数据集配置','400px', '500px',function(){
                addDataSet.isEdit = false;
            });
        },
        editDataSetConfig(){//编辑
            /**
             * tsj 07/8/28 替换ajax方法，修改赋值
             **/
            this.divIndex = ibcpLayer.ShowDiv('add-data-set.html','编辑数据集配置','400px', '500px',function(){
                gmpAjax.showAjax(serverPath+'/dataSetConfig/queryById',{
                    rowId: dataSetConfig.rowObjId
                },function(res){
                    var data =res.resp.content.data[0];
                    addDataSet.isEdit = true;
                    addDataSet.formTable.datasetCode = data.datasetCode;
                    addDataSet.formTable.nameInput =data.datasetName;
                        //类型下拉框赋值修改  jms  2017/8/21
                    addDataSet.$refs.dsctype.value = data.datasetType;

                    addDataSet.formTable.content = data.datasetContent;
                    addDataSet.formTable.desp = data.desp;
                    addDataSet.formTable.version =data.version;
                })
            });
        },
        deleteDataSetConfig(){//删除
            deleteObj.del(function(){
                /**
                 * tsj 07/8/28 替换ajax方法
                * */
                gmpAjax.showAjax(dataSetConfigButton.delUrl,{
                    rowId:dataSetConfig.rowObjId
                },function(res){
                    showMsg.MsgOk(dataSetConfig,res);
                    queryData.getData(dataSetConfig.selUrl,dataSetConfig.input,dataSetConfig);
                })
            })
        }
    }
})