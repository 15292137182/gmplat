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

/*
 * 左边table
 */
var leftKeyValueSet=new Vue({
    el:"#left",
    data: getData.dataObj({
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
                    var data=res;
                    topButtonObj.isEdit=true;
                    keyValueSetAdd.keyForm.keysetCodeInput =data[0].keysetCode;
                    keyValueSetAdd.keyForm.keysetNameInput = data[0].keysetName;
                    keyValueSetAdd.keyForm.belongModuleInput = data[0].belongModule;
             //       keyValueSetAdd.keyForm.belongSystemInput = data[0].belongSystem;
                    keyValueSetAdd.keyForm.despInput = data[0].desp;
                    keyValueSetAdd.keyForm.versionInput = data[0].version;
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
            this.searchPage();
        },
        handleCurrentChange(val){//点击第几页
            this.pageNum=val;
            this.searchPage();
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

var rightKeyValueSet=new Vue({
    el:"#right",
    data: getData.dataObj({
        rightHeight:'',
        rightInput:'',
        editProDivIndex:'',
        rowId:''
    }),
    methods: {
        searchPage(){
            pagingObj.Examples(ProUrl,leftKeyValueSet.rowId,this.rightInput, this.pageSize,this.pageNum,this,function(){
                rightKeyValueSet.rightCurrentChange(rightKeyValueSet.tableData[0]);
            });
        },
        rightSearch(){
            this.searchPage();
        },
        //点击某行
        rightCurrentChange(row, event, column){
            if(row){
                this.rowId=row.rowId;
            }
        },
        //右边table查询
        getRightData(id){
            pagingObj.Examples(queryProUrl,id,this.rightInput,this.pageSize,this.pageNum,this,function(){
                rightKeyValueSet.rightCurrentChange(rightKeyValueSet.tableData[0]);
            });
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
                    var data=res;
                    topButtonObj.isEdit=true;
                    keyValueSetProAdd.keyForm.confKeyInput = data[0].confKey;
                    keyValueSetProAdd.keyForm.confValueInput = data[0].confValue;
                    keyValueSetProAdd.keyForm.despInput = data[0].desp;
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
                gmpAjax.showAjax(data, function (res) {
                    queryData.getData(ProUrl, rightKeyValueSet.rightInput, leftKeyValueSet.rowId,this);
                    leftKeyValueSet.searchLeftPage();
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
            pagingObj.headSort(ProUrl,this.rightInput,this.pageSize,this.pageNum,column,this);
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
    updated() {
        this.FindFirstDate(this.tableData[0]);
    }
})


/*
 * 按钮
 */
var topButtonObj = new Vue({
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
                rightKeyValueSet.rightInput='';
            });
        }
    }
})

