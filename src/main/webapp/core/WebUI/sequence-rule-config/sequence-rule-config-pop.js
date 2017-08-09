/**
 * Created by jms on 2017/8/8.
 */
var pop=new Vue({
    el:'#srconfig',
    data:{
        input:'',
        seqRuleConfigdata:[]
    },
    methods: {
        search(){
            this.$http.jsonp(serverPath + "/sequenceRule/query", {
                "str": this.input
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                this.seqRuleConfigdata = res.data.data;
            });
        },
    },
    created(){
        this.search();
    }
})
