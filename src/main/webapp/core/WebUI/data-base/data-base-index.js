/**
 * Created by andim on 2017/8/3.
 */
/**
 * Created by andim on 2017/8/1.
 */
var dataBase = new Vue({
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
            this.divIndex = ibcpLayer.ShowDiv('data-base.html','表字段信息','600px', '400px',function(){
                DatabaseDetails.Robj = dataBase.rowObj;
                DatabaseDetails.FindData(DatabaseDetails.Robj.rowId);
            })
        }
    },
    created(){
        this.get();
        $(document).ready(function(){
            dataBase.leftHeight=$(window).height()-150;
        });
        $(window).resize(function(){
            dataBase.leftHeight=$(window).height()-150;
        })
    },
    updated(){
        this.FindOk(this.myData[0]);
    }
});
var topButtonObj = new Vue({
    el:'#myButton'
})