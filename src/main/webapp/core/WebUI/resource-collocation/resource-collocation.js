/**
 * Created by zym on 2017/8/7.
 */

//调用路径
var addUrl=serverPath + "/sysConfig/add";
var editUrl=serverPath + "/sysConfig/modify";
var delUrl=serverPath + "/sysConfig/delete";
var qurUrl=serverPath + "/sysConfig/queryPage";

var resTop=new Vue({
    el:'#resTop',
    data:{

    },
    methods:{
        addResEvent() {
            operate = 1;
            var htmlUrl = 'resource-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增系统资源配置', '400px', '340px');
        },
    }
});

var resCol=new Vue({
    "el":"#resCol",
    data:{
        resInput: '',
        tableData: [],
        loading:true,
        currentPage:1,//当前为第一页
        pageSize:10,//每页显示条数
        //开始不能为空 否则会报错
        allDate:0  //总共有多少条
    },
    methods:{
        searchResTable() {
            pagingObj.Example(qurUrl,this.resInput,this.pageSize,this.currentPage,this);
        },
        editResEvent() {
            operate = 2;
            var htmlUrl = 'resource-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑系统资源配置', '400px', '300px', function () {
                //code值
                resAdd.colForm.codeInput = resCol.currentValue.confKey;  //编辑时候的键
                resAdd.colForm.nameInput = resCol.currentValue.confValue;//编辑时候的值
                resAdd.colForm.tableInput = resCol.currentValue.desp;//编辑时候的名称
                resAdd.colForm.disabled = true  //键的值不可以改变
            });
        },
        deleteResEvent() {
            deleteObj.del(function(){
                var deleteId = resCol.currentValue.rowId;  //row的ID
                resCol.$http.jsonp(delUrl, {
                    rowId: deleteId
                }, {
                    jsonp: 'callback'
                }).then(function (ref) {
                    resCol.searchResTable();
                    ibcpLayer.ShowOK(ref.data.message);
                });
            })
        },
        currentChange(row, event, column) {
            this.currentValue=row;//点击拿到这条数据的值
            this.currentId = row.rowId;//点击拿到这条数据的ID
            resTop.editCol=false;//编辑按钮可用
            resTop.delCol=false;//删除按钮可用
        },
        FindFirstDate(row){
            this.$refs.tableData.setCurrentRow(row); //将选中的行变颜色
        },
        handleSizeChange(val) {   //每页多少条数变化时
            this.pageSize=val;
            this.searchResTable();
        },
        handleCurrentChange(val) {  //当前页是第几页
            this.currentPage=val;
            this.searchResTable();
        },
        headSort(column){//列头排序
            pagingObj.headSort(qurUrl,this.resInput,this.pageSize,this.currentPage,column,this);
        }
    },
    created() {
        this.searchResTable(); //页面一进入调查询
    //    $(document).ready(function () {
    //        resCol.Height = $(window).height() - 195;
    //    });
    //    $(window).resize(function () {
    //        resCol.Height = $(window).height() - 195;
    //    })
    },
    updated() {
        this.FindFirstDate(this.tableData[0]);
    }
});

