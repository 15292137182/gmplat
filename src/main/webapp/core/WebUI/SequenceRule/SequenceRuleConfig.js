/**
 * Created by jms on 2017/8/7.
 */
var str="/code/sequenceRule";
const query=str+"/query";
const insert=str+"/add";
const modify=str+"modify";
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

        },
        addEvent(){
            operate = 1;
            var htmlUrl="SequenceRuleConfigAdd.html";
            divIndex=ibcpLayer.ShowDiv(htmlUrl, '新增序列号规则配置', '400px', '450px');
        }
    },
    created(){
        this.search();
    }
})
