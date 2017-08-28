/**
 * Created by admin on 2017/8/27.
 */

//模板对象查询接口
//模板对象新增接口
//模板对象编辑接口
//模板对象删除接口




var basTop = new Vue({
    el: '#basTop',
    data: {
        addTempAttr:false,//新增属性开始禁用
    },
    methods: {
        //新增模板对象
        addTemp(){
            operate = 1;
            var htmlUrl = 'template-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增模板对象', '400px', '340px',function(){
                //关联表字段
                this.$http.jsonp(conTable, {
                    str:''
                }, {
                    jsonp: 'callback'
                }).then(function (res) {
                    console.log(res)
                });
            });
        },
        //新增模板对象属性
        addTempProp(){
            operateOPr=1;
            //var htmlUrl = 'metadata-prop-add.html';
            //divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增模板对象属性', '400px', '540px', function () {
            //    basTop.getSelectValue();
            //
            //});
        }

    }
});


var basLeft = new Vue({
    "el": "#basLeft",
    data: {
        leftInput:'',//左边搜索框
        loading:true,
        tableData:[],//左边表格
        leftHeight: '',//左边表格高度
        pageNum:1,//当前为第一页
        pageSize:10,//每页显示条数
        allDate:0,//总共有多少条

    },
    methods: {
        //不分页查询
        searchLeft(){

        },
        //分页查询
        searchLeftTable(){

        },
        //编辑事件
        editEvent(){
            operate = 2;
            var htmlUrl = 'template-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑模板对象', '400px', '340px',function(){

            });
        },
        //删除事件
        deleteEvent(){
            deleteObj.del(function(){
                //var deleteId = basLeft.currentVal.rowId;  //左侧表的row的ID
                //basLeft.$http.jsonp(deleteUrl, {
                //    rowId: deleteId
                //}, {
                //    jsonp: 'callback'
                //}).then(function (ref) {
                //    showMsg.MsgOk(basTop,ref)
                //    //分页跳回到第一页
                //    basLeft.searchLeft();
                //},function(){
                //    showMsg.MsgError(basTop)
                //});
            })
        },
        //表格点击事件
        currentChange(){
            //左边这一行的数据
            //this.currentId = row.rowId;
            //查找右边表的数据

        },
        //将选中行变颜色
        FindLFirstDate(row){
            this.$refs.tableData.setCurrentRow(row);
        },
        //排序事件
        headSort(){

        },
        //选择每页多少条数据
        handleSizeChange(val) {
            this.pageSize=val;
            //不分页查询
            this.searchLeft();
        },
        //选择当前页第几页
        handleCurrentChange(val) {
            this.pageNum=val;
            //不分页查询
            this.searchLeft();
        },
    },
    //表格高度
    created(){
        this.searchLeft();
        $(document).ready(function () {
            basLeft.leftHeight = $(window).height() - 194;
        });
        $(window).resize(function () {
            basLeft.leftHeight = $(window).height() - 194;
        })
    },
    //页面一进入第一行高亮显示
    updated() {
        this.FindLFirstDate(this.tableData[0]);
    }
});



var basRight = new Vue({
    "el": "#basRight",
    data: {
        rightInput:'',//右边输入框
        loading:true,
        tableData:[],//右边表格
        rightHeight: '',//右边表格高度
        pageNum:1,//当前为第一页
        pageSize:10,//每页显示条数
        allDate:0,//总共有多少条
    },
    methods: {
        //不分页查询
        searchRight(){

        },
        //分页查询
        searchRightTable(){

        },
        //右边点击事件
        currentRChange(){

        },
        //编辑事件
        editProp(){

        },
        //删除事件
        editProp(){

        },
        //将选中行变颜色
        FindLFirstDate(row){
            this.$refs.tableData.setCurrentRow(row);
        },
        //排序事件
        headSort(){

        },
        //选择每页多少条数据
        handleSizeChange(val) {
            this.pageSize=val;
            //不分页查询
            this.searchRight();
        },
        //选择当前页第几页
        handleCurrentChange(val) {
            this.pageNum=val;
            //不分页查询
            this.searchRight();
        },
    },
    //表格高度
    created(){
        this.searchRight();
        $(document).ready(function () {
            basRight.rightHeight = $(window).height() - 194;
        });
        $(window).resize(function () {
            basRight.rightHeight = $(window).height() - 194;
        });
    },
    //页面一进入第一行高亮显示
    updated() {
        this.FindLFirstDate(this.tableData[0]);
    }
});