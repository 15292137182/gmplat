/**
 * Created by jms on 2017/8/6.
 */
var str="/keySet";
const query=str+"/query";
const insert=str+"/add";
const modify=str+"modify";
const del=str+"/delete";
const queryPage=str+"/queryPage"

var keyValueSet=new Vue({
    el:"#kvsInfo",
    data:{
        keyValueSetdata:[],
        input:'',
        total:3,
        pageNums:[1,2,3,4],
        pageSize:1
    },
    methods: {
        search(){
            this.$http.jsonp(serverPath + query, {
                "str": this.input
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                this.keyValueSetdata = res.data.data;
            });
        },
        handleCurrentChange(val){
            this.currentVal = val;
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
            this.$http.jsonp(serverPath + del, {
                rowId: keyValueSet.currentVal.rowId,
                keySetCode: keyValueSet.currentVal.keysetCode,
                keySetName: keyValueSet.currentVal.keysetName,
                confKey: keyValueSet.currentVal.confKey,
                confValue: keyValueSet.currentVal.confValue,
                desp: keyValueSet.currentVal.desp,
                version: keyValueSet.currentVal.version
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                ibcpLayer.ShowOK(res.data.message);
                keyValueSet.search();
            });
        },
        handleNumChange(val1){
            this.pageNum=val1;
            this.searchPage();

        },

        handleSizeChange(val){
            this.pageSize=val;
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

            });
        },
    },
    created(){
        this.pageNum=this.pageNums["0"];
        this.searchPage();
    },


})
