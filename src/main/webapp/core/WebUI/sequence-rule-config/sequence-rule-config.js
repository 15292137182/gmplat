/**
 * Created by jms on 2017/8/7.
 */
var str="/sequenceRule";
const query=str+"/query";
const insert=str+"/add";
const modify=str+"/modify";
const del=str+"/delete";

var config=new Vue({
    el:'#srconfig',
    data:{
        input:'',
        seqRuleConfigdata:[]
    },
    methods:{
        search(){
            this.$http.jsonp(serverPath + query, {
                "str": this.input
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                this.seqRuleConfigdata = res.data.data;
            });
        },
        handleCurrentChange(val){
            this.currentVal=val;
        },
        addEvent(){
            operate = 1;
            var htmlUrl="sequence-rule-config-add.html";
            divIndex=ibcpLayer.ShowDiv(htmlUrl, '新增序列号规则配置', '400px', '450px');
        },
        editEvent(){
            operate=2;
            var htmlUrl = 'sequence-rule-config-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑键值集合', '400px', '450px', function () {
                //code值
                add.seqCodeInput = config.currentVal.seqCode;
                add.seqNameInput = config.currentVal.seqName;
                add.seqContentInput = config.currentVal.seqContent;
                add.despInput = config.currentVal.desp;
                add.versionInput = config.currentVal.version;
            });
        },
        deleteEvent(){
            var list=[];
            list.push(config.currentVal.rowId);
            this.$http.jsonp(serverPath+del,{
                rowIds:list.join(" ")
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                ibcpLayer.ShowOK(res.data.message);
                this.search();
            });
        }
    },
    created(){
        this.search();
    }
})
