/**
 * Created by jms on 2017/8/6.
 */
var url=serverPath+'/keySet/queryPage';
var ProUrl=serverPath+'/keySetPro/queryPage';
var queryByIdUrl=serverPath+'/keySet/queryById';
var queryProUrl=serverPath+'/keySet/queryProPage';
var queryProByIdUrl=serverPath+'/keySetPro/queryById'
var delUrl=serverPath+'/keySet/delete';
var delProUrl=serverPath+'/keySetPro/delete'

function GlobalParameter(){
    var args={"tableKeySet":{"keyValue":{belongModule:"belongModule"}}};
    return args;
}

/*
 * 左边table
 */
var leftKeyValueSet;
var rightKeyValueSet;
var topButtonObj;
gmp_onload=function(){
    leftKeyValueSet=new Vue({
        el:"#left",
        data: getData.dataObj({
            tableId:'keyValue',
            divIndex:'',
            editdivIndex:'',
        }),
        methods: {
            //点击查询按钮
            leftSearch(){
                this.searchLeftPage();
            },
            //分页查询方法
            searchLeftPage(){
                pagingObj.Example(url,this.input, this.pageSize,this.pageNum,this,function(){
                    leftKeyValueSet.leftCurrentChange(leftKeyValueSet.tableData[0]);
                });
            },
            //点击某行
            leftCurrentChange(row, event, column){
                if(row){
                    this.rowId=row.rowId;
                    this.currentRowId=row.rowId;
                    rightKeyValueSet.getRightData(row.rowId);
                }
            },

            //左侧编辑
            editLeftEvent(res){
                var htmlUrl = 'key-value-set-add.html';
                this.editdivIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑主信息', '400px', '500px', function () {
                    var data={
                        "url":queryByIdUrl,
                        "jsonData":{
                            rowId:leftKeyValueSet.rowId,
                        },
                        "obj":leftKeyValueSet
                    }
                    gmpAjax.showAjax(data,function(res){
                        var data=res.data;
                        topButtonObj.isEdit=true;
                        keyValueSetAdd.keyForm.keysetCodeInput =data.keysetCode;
                        keyValueSetAdd.keyForm.keysetNameInput = data.keysetName;
                        keyValueSetAdd.$refs.belongModule_1.setValue(data.belongModule);
                        //       keyValueSetAdd.keyForm.belongSystemInput = data[0].belongSystem;
                        keyValueSetAdd.keyForm.despInput = data.desp;
                        keyValueSetAdd.keyForm.versionInput = data.version;
                    })
                });
            },
            //左侧删除
            deleteLeftEvent(){
                deleteObj.del(function () {
                    var data = {
                        "url": delUrl,
                        "jsonData": {
                            rowId: leftKeyValueSet.rowId
                        },
                        "obj": leftKeyValueSet
                    }
                    gmpAjax.showAjax(data, function (res) {
                        queryData.getData(url, leftKeyValueSet.input, leftKeyValueSet, function (res) {
                            leftKeyValueSet.leftCurrentChange(leftKeyValueSet.tableData[0]);
                            // rightKeyValueSet.getRightData(leftKeyValueSet.tableData[0].rowId);
                        })
                    })
                })
            },
            FindFirstDate(row){
                this.$refs.tableData.setCurrentRow(row); //将选中的行变颜色
            },
            handleSizeChange(val){//每页显示多少条
                this.pageSize=val;
                this.searchLeftPage();
            },
            handleCurrentChange(val){//点击第几页
                this.pageNum=val;
                this.searchLeftPage();
            },

            headSort(column){//列头排序
                pagingObj.headSort(url,this.input,this.pageSize,this.pageNum,column,this);
            }
        },
        created(){
            //初始化分页查询
            this.searchLeftPage();
            $(document).ready(function(){
                leftKeyValueSet.leftHeight=$(window).height()-190;
            });
            $(window).resize(function(){
                leftKeyValueSet.leftHeight=$(window).height()-190;
            })
        },
        updated() {
            //默认选中第一行
            this.FindFirstDate(this.tableData[0]);
        }
    })

    rightKeyValueSet=new Vue({
        el:"#right",
        data: getData.dataObj({
            rightHeight:'',
            //   rightInput:'',

            editProDivIndex:'',
            rowId:''
        }),
        methods: {
            searchPage(){
                pagingObj.Examples(queryProUrl,leftKeyValueSet.currentRowId,this.input, this.pageSize,this.pageNum,this,function(){
                    rightKeyValueSet.rightCurrentChange(rightKeyValueSet.tableData[0]);
                });
            },

            //右边table查询
            getRightData(id){
                pagingObj.Examples(queryProUrl,id,this.input,this.pageSize,this.pageNum,this,function(){
                    rightKeyValueSet.rightCurrentChange(rightKeyValueSet.tableData[0]);
                });
            },

            //点击某行
            rightCurrentChange(row, event, column){
                if(row){
                    this.rowId=row.rowId;
                }
            },
            //右边表格编辑
            editRightEvent(){
                var htmlUrl = 'key-value-set-pro-add.html';
                this.editProDivIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑明细信息', '400px', '400px', function () {
                    var data={
                        "url":queryProByIdUrl,
                        "jsonData":{
                            rowId:rightKeyValueSet.rowId,
                        },
                        "obj":rightKeyValueSet
                    }
                    gmpAjax.showAjax(data,function(res){
                        // showMsg.MsgOk(keyValueSet,res);
                        //code值
                        var data=res.data;
                        topButtonObj.isEdit=true;
                        keyValueSetProAdd.keyForm.confKeyInput = data.confKey;
                        keyValueSetProAdd.keyForm.confValueInput = data.confValue;
                        keyValueSetProAdd.keyForm.despInput = data.desp;
                    })
                });
            },
            deleteRigthEvent(){
                deleteObj.del(function () {
                    var data = {
                        "url": delProUrl,
                        "jsonData": {
                            rowId: rightKeyValueSet.rowId
                        },
                        "obj": rightKeyValueSet
                    }
                    gmpAjax.showAjax(data,function(res){
                        rightKeyValueSet.searchPage();
                        //  queryData.getDatas(queryProUrl,rightKeyValueSet.input,leftKeyValueSet.rowId,rightKeyValueSet,function(res){});
                    })
                })
            },
            FindFirstDate(row){
                this.$refs.tableData.setCurrentRow(row); //将选中的行变颜色
            },
            handleSizeChange(val){//每页显示多少条
                this.pageSize=val;
                this.searchPage();
            },
            handleCurrentChange(val){//点击第几页
                this.pageNum=val;
                this.searchPage();
            },

            headSort(column){//列头排序
                pagingObj.headSorts(queryProUrl,leftKeyValueSet.currentRowId,this.input,column,this);
            }
        },
        created(){
            $(document).ready(function(){
                rightKeyValueSet.rightHeight=$(window).height()-190;
            });
            $(window).resize(function(){
                rightKeyValueSet.rightHeight=$(window).height()-190;
            })
        },
        // updated() {
        //     this.FindFirstDate(this.tableData[0]);
        // }
    })


    /*
     * 按钮
     */
    topButtonObj = new Vue({
        el:'#myButton',
        data:{
            divIndex:'',
            isEdit:''
        },
        methods:{
            addEvent(){
                var htmlUrl = 'key-value-set-add.html';
                this.divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增主信息', '400px', '500px',function(){
                    topButtonObj.isEdit=false;
                    leftKeyValueSet.input='';
                });
            },
            addProEvent(){
                var htmlUrl = 'key-value-set-pro-add.html';
                this.divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增明细', '400px', '300px',function(){
                    topButtonObj.isEdit=false;
                    rightKeyValueSet.input='';
                });
            }
        }
    })


}
