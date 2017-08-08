/**
 * Created by andim on 2017/8/8.
 */
var attributeDetails = new Vue({
    el:'#attributeDetails',
    data:{
        input:'',
        url:serverPath+'/fronFuncPro/query',//查询功能块属性接口
        attributeData:[]//属性数组
    },
    methods:{
        getAttribute(){
            this.$http.jsonp(this.url, {
                "rowIds":vm1.rowId
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                if(res.data.data!=null){
                    ibcpLayer.ShowOK(res.data.message);
                    this.attributeData=res.data.data;
                }
            });
        },
        conformEventClose(){//确定按钮
            ibcpLayer.Close(vm1.divIndex);
        }
    },
    created(){
        this.getAttribute();
    }
})