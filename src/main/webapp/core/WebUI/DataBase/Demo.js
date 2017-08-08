/**
 * Created by andim on 2017/8/3.
 */
/**
 * Created by andim on 2017/8/1.
 */
var vm = new Vue({
    el:"#app",
    data:{
        input:'',
        myData:[],
        leftHeight:'',
        rowObj:'',
        divIndex:'',
        url:serverPath+'/maintTable/query'
    },
    methods:{
        get(){
            this.$http.jsonp(this.url,{
                "str":this.input
            },{
                jsonp:'callback'
            }).then(function (res) {
                if(res.data.data!=null){
                    this.myData=res.data.data;
                }
            })
        },
        click(row, event, column){
            this.rowObj = row;
        },
        FindOk(row){
            this.$refs.myTable.setCurrentRow(row);
        },
        handleClick(){
            this.divIndex = ibcpLayer.ShowDiv('dataDemo.html','表字段信息','600px', '400px',function(){
                vm1.Robj = vm.rowObj;
                vm1.FindData(vm1.Robj.rowId);
                console.log(vm1.Robj);
            })
        }
    },
    created(){
        this.get();
        $(document).ready(function(){
            vm.leftHeight=$(window).height()-90;
        });
        $(window).resize(function(){
            vm.leftHeight=$(window).height()-90;
        })
    },
    updated(){
        this.FindOk(this.myData[0]);
    }
});
var mb = new Vue({
    el:'#myButton'
})