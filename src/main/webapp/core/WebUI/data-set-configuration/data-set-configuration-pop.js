/**
 * Created by andim on 2017/8/9.
 */
//表格
var dataSetConfigPop = new Vue({
    el:'#dataSetConfigPop',
    data:{
        dataSetConfigTable:[],//table数据
        input:'',//搜索框输入
        Height:'',//表格高度
        selUrl:serverPath+'/dataSetConfig/query',//查询接口
    },
    methods:{
        searchResTable(){//查询
            this.$http.jsonp(this.selUrl,{
                str:this.input
            },{
                jsonp:'callback'
            }).then(function(res){
                if(res.data.data!=null){
                    this.dataSetConfigTable = res.data.data;
                }else{
                    this.dataSetConfigTable = [];
                }
            },function(){
                alert("error")
            })
        }
    },
    created(){
        this.searchResTable();
        $(document).ready(function(){
            dataSetConfig.Height=$(window).height()-150;
        });
        $(window).resize(function(){
            dataSetConfig.Height=$(window).height()-150;
        })
    }
})