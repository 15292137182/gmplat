/**
 * Created by andim on 2017/8/1.
 */
    var vm = new Vue({
        el:"#app",
        data:{
            input:'',
            myData:[],
            rightData:[],
            leftHeight:'',
            rightHeight:'',
        },
        methods:{
            get(){
                this.$http.jsonp('http://192.168.100.193/GMPlat/maint/select',{
                    "str":this.input
                },{
                    jsonp:'callback'
                }).then(function (res) {
                    this.myData=res.data.content.data;
                })
            },
            click(row, event, column){
                this.FindData(row.rowId);
                this.FindOk(row);
            },
            FindData(id){
                this.$http.jsonp('http://192.168.100.193/GMPlat/maint/selectById',{
                    "rowId":id
                },{
                    jsonp:'callback'
                }).then(function (res) {
                    this.rightData=res.data.content.data;
                })
            },
            FindOk(row){
                    this.$refs.myTable.setCurrentRow(row);
            }
        },
        created(){
            this.get();
            $(document).ready(function(){
                vm.leftHeight=$(window).height()-107;
                vm.rightHeight=$(window).height()-107;
            });
            $(window).resize(function(){
                vm.leftHeight=$(window).height()-107;
                vm.rightHeight=$(window).height()-107;
            })
        },
        updated(){

        }
    });