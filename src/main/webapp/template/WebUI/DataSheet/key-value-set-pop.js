/**
 * Created by jms on 2017/8/2.
 */
window.onload=function () {
    var vm=new Vue({
        el:'#app',
        data:{
            input:'',
            gridData:[],
        },
        methods:{
            search(){
                this.$http.jsonp(serverPath+'/plat/dbTableColumn/select',{
                    "str":this.input
                },{
                    jsonp:'callback'
                }).then(function (res) {
                    this.gridData=res.data.content.data;
                })
            }
        }
    })
}
