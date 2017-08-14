/**
 * Created by jms on 2017/8/6.
 */
var str="/keySet";
const query=str+"/query";
const insert=str+"/add";
const modify=str+"/modify";
const del=str+"/delete";
const queryPage=str+"/queryPage"

var keyValueSet=new Vue({
    el:"#kvsInfo",
    data:{
        keyValueSetdata:[],
        input:'',
        height:'',
        total:0,
        pageSizes:[10,20,30,40],
        pageNum:1,
        pageSize:10
    },
    methods: {
        handleSizeChange(val){
            this.pageSize=val;
            this.searchPage();
        },

        handleNumChange(val){
            this.pageNum=val;
            this.searchPage();
        },
        searchPage(){
            this.$http.jsonp(serverPath + queryPage, {
                "args":this.input,
                "pageSize": this.pageSize,
                "pageNum":this.pageNum,
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                this.keyValueSetdata = res.data.data.result;
                this.total=Number(res.data.data.total);
            });
        },
        search(){
             this.searchPage();
        },
        handleCurrentChange(row, event, column){
            this.currentVal = row;
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
        deleteEvent(index,row){
             this.$http.jsonp(serverPath + del, {
                rowId: row.rowId  //row的ID
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                ibcpLayer.ShowOK(res.data.message);
                keyValueSet.search();
            });
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


