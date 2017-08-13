/**
 * Created by andim on 2017/8/3.
 */
/**
 * Created by andim on 2017/8/1.
 */
var dataBase = new Vue({
    el:"#app",
    data:{
        loading:true,
        input:'',
        tableData:[],
        leftHeight:'',
        rowObj:'',
        divIndex:'',
        url:serverPath+'/maintTable/queryPage',
        pageSize:10,//每页显示多少条
        pageNum:1,//第几页
        allDate:0//共多少条
    },
    methods:{
        get(){
            pagingObj.Example(this.url,this.input, this.pageSize,this.pageNum,this);
        },
        click(row, event, column){
            this.rowObj = row;
            console.log(row);
        },
        FindOk(row){
            this.$refs.myData.setCurrentRow(row);
        },
        handleClick(){
            this.divIndex = ibcpLayer.ShowDiv('data-base.html','表字段信息','600px', '400px',function(){
                DatabaseDetails.Robj = dataBase.rowObj;
                DatabaseDetails.FindData(DatabaseDetails.Robj.rowId);
            })
        },
        handleSizeChange(val){//每页显示多少条
            this.pageSize=val;
            this.get();
        },
        handleCurrentChange(val){//点击第几页
            this.pageNum=val;
            this.get();
        }
    },
    created(){
        this.get();
        $(document).ready(function(){
            dataBase.leftHeight=$(window).height()-190;
        });
        $(window).resize(function(){
            dataBase.leftHeight=$(window).height()-190;
        })
    },
    updated(){
        this.FindOk(this.tableData[0]);
    }
});
var topButtonObj = new Vue({
    el:'#myButton'
})