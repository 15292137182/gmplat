/**
 * Created by jms on 2017/8/6.
 */

var vm=new Vue({
    el:"#kvsInfo",
    data:{
        keyValueSetdata:[],
        input:'',
    },
    methods:{
        search(){
            this.$http.jsonp(serverPath+"/keySet/select",{
                "str":this.input
            },{
                jsonp:'callback'
            }).then(function(res){
                this.keyValueSetdata=res.data.content.data;
            });
        }
    },
    created(){
        this.search();
    },


})
