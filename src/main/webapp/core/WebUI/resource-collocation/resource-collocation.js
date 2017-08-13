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
        editCol:true,//编辑按钮不可用
        delCol:true//删除按钮不可用
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
                resAdd.codeInput = resCol.currentValue.confKey;  //编辑时候的键
                resAdd.nameInput = resCol.currentValue.confValue;//编辑时候的值
                resAdd.tableInput = resCol.currentValue.desp;//编辑时候的名称
                resAdd.disabled = true  //键的值不可以改变
            });
        },
        deleteResEvent() {
            var deleteId = resCol.currentValue.rowId;  //row的ID
            this.$http.jsonp(delUrl, {
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
            var colPage = new (Vue.extend(pagingObj.Example()))({
                propsData: {
                    url:qurUrl,//分页查询接口
                    pageSize :this.pageSize,//每页数据条数
                    pageNum:this.currentPage,//从第几页查
                    args:this.resInput,//input输入框
                }
            })
            colPage.pagingObjAjax(function(res){
                if(res.data.data.result.length!=0){ //有数据的时候
                    resCol.myResData = res.data.data.result;//标的内容
                    resCol.allDate=Number(res.data.data.total);  //分页条数
                    resCol.currentChange(resCol.myResData[0]);//默认选中第一行
                }else{
                    resCol.myResData=[];//没有数据的的时候表内容为空
                    resTop.editCol=true;//编辑按钮不可用
                    resTop.delCol=true;//删除按钮不可用
                }
            })//支持回调函数callback;
        },
        currentChange(row, event, column) {
            this.currentValue=row;//点击拿到这条数据的值
            this.currentId = row.rowId;//点击拿到这条数据的ID
            resTop.editCol=false;//编辑按钮可用
            resTop.delCol=false;//删除按钮可用
        },
        FindFirstDate(row){
            this.$refs.myResData.setCurrentRow(row); //将选中的行变颜色
        },
        handleSizeChange(val) {   //每页多少条数变化时
            this.pageSize=val;
            //console.log(`每页 ${val} 条`);
            this.searchResTable();
        },
        handleCurrentChange(val) {  //当前页是第几页
            this.currentPage=val;
            //console.log(`当前页: ${val}`);
            this.searchResTable();
        }
    },
    created() {
        this.searchResTable(); //页面一进入调查询
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

