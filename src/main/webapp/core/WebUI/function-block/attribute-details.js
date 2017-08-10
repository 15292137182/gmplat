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
                "rowId":vm1.rowId
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                if(res.data.data!=null){
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