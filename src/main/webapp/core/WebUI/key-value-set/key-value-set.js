/**
 * Created by jms on 2017/8/6.
 */
var str=serverPath+"/keySet";
const query=str+"/query";
const insert=str+"/add";
const modify=str+"/modify";
const del=str+"/delete";
const queryPage=str+"/queryPage"

var keyValueSet=new Vue({
    el:"#kvsInfo",
    data:{
        tableData:[],
        input:'',
        height:'',
        loading:true,
        pageSize:10,//每页显示多少条
        pageNum:1,//第几页
        allDate:0//共多少条
    },
    methods: {
        searchPage(){
            pagingObj.Example(queryPage,this.input, this.pageSize,this.pageNum,this);
        },
        search(){
             this.searchPage();
        },
        addEvent(){
            operate = 1;
            var htmlUrl = 'key-value-set-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增键值集合', '400px', '450px');
        },
        editEvent(){
            operate = 2;
            var htmlUrl = 'key-value-set-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑键值集合', '400px', '450px', function () {
                //code值
                keyValueSetAdd.keysetCodeInput = keyValueSet.currentVal.keysetCode;
                keyValueSetAdd.keysetNameInput = keyValueSet.currentVal.keysetName;
                keyValueSetAdd.confKeyInput = keyValueSet.currentVal.confKey;
                keyValueSetAdd.confValueInput = keyValueSet.currentVal.confValue;
                keyValueSetAdd.despInput = keyValueSet.currentVal.desp;
                keyValueSetAdd.versionInput = keyValueSet.currentVal.version;

                //不可点击
                keyValueSetAdd.disabled = true;
            });
        },
        deleteEvent(){
            deleteObj.del(function () {
                keyValueSet.$http.jsonp(del, {
                    rowId: keyValueSet.currentVal.rowId  //row的ID
                }, {
                    jsonp: 'callback'
                }).then(function (res) {
                    ibcpLayer.ShowOK(res.data.message);
                    keyValueSet.searchPage();
                });
            })

        },
        handleSizeChange(val){//每页显示多少条
            this.pageSize=val;
            this.searchPage();
        },
        handleCurrentChange(val){//点击第几页
            this.pageNum=val;
            this.searchPage();
        },
        //点击
        onClick(row, event, column){
            if(row){
                this.currentVal=row;
            }
        },
    },
    created(){
        this.searchPage();
        $(document).ready(function(){
            keyValueSet.height=$(window).height()-200;
        });
        $(window).resize(function(){
            keyValueSet.height=$(window).height()-200;
        })
    }
})


