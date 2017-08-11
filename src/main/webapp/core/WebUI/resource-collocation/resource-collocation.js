/**
 * Created by admin on 2017/8/7.
 */

var resTop=new Vue({
    el:'#resTop',
    data:{
        editCol:true,
        delCol:true
    },
    methods:{
        addResEvent() {
            operate = 1;
            var htmlUrl = 'resource-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增系统资源配置', '400px', '300px');
        },
        editResEvent() {
            operate = 2;
            var htmlUrl = 'resource-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑系统资源配置', '400px', '300px', function () {
                //code值
                resAdd.codeInput = resCol.currentValue.confKey;
                resAdd.nameInput = resCol.currentValue.confValue;
                resAdd.tableInput = resCol.currentValue.desp;
                resAdd.disabled = true
            });
        },
        deleteResEvent() {
            var deleteId = resCol.currentValue.rowId;
            console.log(deleteId);
            this.$http.jsonp(serverPath + "/sysConfig/delete", {
                rowId: deleteId
            }, {
                jsonp: 'callback'
            }).then(function (ref) {
                resCol.searchResTable();
                ibcpLayer.ShowOK(ref.data.message);
            });
        }
    }
});

var resCol=new Vue({
    "el":"#resCol",
    data:{
        resInput: '',
        Height: '',
        myResData: [],
        currentPage:1,//当前为第一页
        pageSize:10,//每页显示条数
        //开始不能为空 否则会报错
        allDate:0  //总共有多少条
    },
    methods:{
        searchResTable() {
            this.$http.jsonp(serverPath + "/sysConfig/queryPage", {
                "args":this.resInput,
                "pageSize": this.pageSize,
                "pageNum":this.currentPage,
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                console.log(res);
                if(res.data.data.result.length!=0){
                    this.myResData = res.data.data.result;
                    //分页条数
                    resCol.allDate=Number(res.data.data.total);
                    //console.log(this.myResData);
                    this.currentChange(this.myResData[0]);
                }else{
                    this.myResData=[];
                    resTop.editCol=true;
                    resTop.delCol=true;
                }
            });
        },
        currentChange(row, event, column) {
            //点击拿到这条数据的值
            console.log(row);
            this.currentValue=row;
            this.currentId = row.rowId;
            resTop.editCol=false;
            resTop.delCol=false;
        },
        FindFirstDate(row){
            this.$refs.myResData.setCurrentRow(row);
        },
        handleSizeChange(val) {
            //每页多少条数变化时
            this.pageSize=val;
            console.log(`每页 ${val} 条`);
            this.searchResTable();
        },
        handleCurrentChange(val) {
            this.currentPage=val;
            console.log(`当前页: ${val}`);
            this.searchResTable();
        }
    },
    created() {
        this.searchResTable();
        $(document).ready(function () {
            resCol.Height = $(window).height() - 195;
        });
        $(window).resize(function () {
            resCol.Height = $(window).height() - 195;
        })
    },
    updated() {
        this.FindFirstDate(this.myResData[0]);
    }
});