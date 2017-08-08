/**
 * Created by jms on 2017/8/8.
 */
var add=new Vue({
    el:"#SequenceRuleConfigAdd",
    data:function(){
        return{
            seqCodeInput:'',
            seqNameInput:'',
            seqContentInput:'',
            despInput:'',
            versionInput:'',
        }
    },
    methods:{
        confirm(){
            var datas = [
                this.$refs.seqNameInput,
                this.$refs.seqContentInput,
                this.$refs.seqContentInput,
                this.$refs.despInput
            ];
            for (var i = 0; i < datas.length; i++) {
                if (datas[i].value == '') {
                    ibcpLayer.ShowMsg(datas[i].placeholder);
                    return;
                }
            }
            if (operate == 1) {
                this.$http.jsonp(serverPath + insert, {
                    seqCode: add.seqCodeInput,
                    seqName: add.seqNameInput,
                    seqContent: add.confKeyInput,
                    desp: add.despInput,
                    version: add.version
                }, {
                    jsonp: 'callback'
                }).then(function (res) {
                    ibcpLayer.ShowOK(res.data.message);
                    config.search();
                    ibcpLayer.Close(divIndex);
                });
            }
        }
    }
})
