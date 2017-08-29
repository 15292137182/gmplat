/**
 * Created by jms on 2017/8/6.
 */

var keyValueSet=new Vue({
    el:"#kvsInfo",
    data: getData.dataObj({
        "rowObj":'',
        "divIndex":'',
        "editdivIndex":'',
        url:serverPath+'/keySet/queryPage',
        delUrl:serverPath+'/keySet/delete'

    }),
    methods: {
        searchPage(){
            pagingObj.Example(this.url,this.input, this.pageSize,this.pageNum,this,function(){});
        },
        search(){
             this.searchPage();
        },
        //点击某行
        currentChange(row, event, column){
            this.rowObj = row;
        },
        editEvent(){
            operate = 2;
            var htmlUrl = 'key-value-set-add.html';
            this.editdivIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑键值集合', '400px', '520px', function () {
                //code值
                topButtonObj.isEdit=true;
                keyValueSet.input='';
                keyValueSetAdd.rowObj=keyValueSet.rowObj;
                keyValueSetAdd.keyForm.keysetCodeInput = keyValueSet.rowObj.keysetCode;
                keyValueSetAdd.keyForm.numberInput=keyValueSet.rowObj.number;
                keyValueSetAdd.keyForm.keysetNameInput = keyValueSet.rowObj.keysetName;
                keyValueSetAdd.keyForm.confKeyInput = keyValueSet.rowObj.confKey;
                keyValueSetAdd.keyForm.confValueInput = keyValueSet.rowObj.confValue;
                keyValueSetAdd.keyForm.despInput = keyValueSet.rowObj.desp;
                keyValueSetAdd.keyForm.versionInput = keyValueSet.rowObj.version;
            });
        },
        deleteEvent(){
            deleteObj.del(function () {
                gmpAjax.showAjax(keyValueSet.delUrl,
                    {rowId:keyValueSet.rowObj.rowId},
                    function(res){
                    showMsg.MsgOk(keyValueSet,res);
                    queryData.getData(keyValueSet.url,keyValueSet.input,keyValueSet)
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
            pagingObj.headSort(queryPage,this.input,this.pageSize,this.pageNum,column,this);
        }
    },
    created(){
        this.searchPage();
    },
    updated() {
        this.FindFirstDate(this.tableData[0]);
    }
})


var topButtonObj = new Vue({
    el:'#myButton',
    data:{
        divIndex:'',
        isEdit:''
    },
    methods:{
        addEvent(){
            var htmlUrl = 'key-value-set-add.html';
            this.divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增键值集合', '400px', '520px',function(){
                topButtonObj.isEdit=false;
                keyValueSet.input='';
            });
        }
    }
})


