/**
 * Created by andim on 2017/8/11.
 */
var DatabaseDetails = new Vue({
    el:'#right',
    data:{
        rightData:[],
        //rightHeight:'',
        Robj:'',
        url:serverPath+'/maintTable/queryById'
    },
    methods:{
        FindData(id){
            this.$http.jsonp(DatabaseDetails.url,{
                "rowId":id
            },{
                jsonp:'callback'
            }).then(function (res) {
                if(res.data.data!=null){
                    this.rightData=res.data.data;
                }
            })
        },
        conformEvent(){
            ibcpLayer.Close(dataBase.divIndex);
        }
    }
});