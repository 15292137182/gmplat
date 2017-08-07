/**
 * Created by jms on 2017/8/6.
 */

var keyValueSet=new Vue({
    el:"#kvsInfo",
    data:{
        keyValueSetdata:[],
        input:'',
    },
    methods: {
        search(){
            this.$http.jsonp(serverPath + "/core/queryKeySet", {
                "str": this.input
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                this.keyValueSetdata = res.data.content;
            });
        },
        handleCurrentChange(val){
            this.currentVal = val;
        },
        addEvent(){
            operate = 1;
            var htmlUrl = 'KeyValueSetAdd.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增键值集合', '400px', '450px');
        },
        editEvent(){
            operate = 2;
            var htmlUrl = 'KeyValueSetAdd.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑键值集合', '400px', '450px', function () {
                //code值
                keyValueSetAdd.keySetCodeInput = keyValueSet.currentVal.keySetCode;
                keyValueSetAdd.keySetNameInput = keyValueSet.currentVal.keySetName;
                keyValueSetAdd.confKeyInput = keyValueSet.currentVal.confKey;
                keyValueSetAdd.confValueInput = keyValueSet.currentVal.confValue;
                keyValueSetAdd.despInput = keyValueSet.currentVal.desp;
                keyValueSetAdd.versionInput = keyValueSet.currentVal.version;

                //不可点击
                keyValueSetAdd.disabled = true;
            });
        },
        deleteEvent(){
            this.$http.jsonp(serverPath + "/core/delKeySet", {
                rowId: keyValueSet.currentVal.rowId,
                keySetCode: keyValueSet.currentVal.keySetCode,
                keySetName: keyValueSet.currentVal.keySetName,
                confKey: keyValueSet.currentVal.confKey,
                confValue: keyValueSet.currentVal.confValue,
                desp: keyValueSet.currentVal.desp,
                version: keyValueSet.currentVal.version
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                ibcpLayer.ShowOK('删除成功');
                keyValueSet.search();
            });
        }
    },
    created(){
        this.search();
    },


})