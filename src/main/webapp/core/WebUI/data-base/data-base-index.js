/**
 * Created by andim on 2017/8/3.
 */
/**
 * Created by andim on 2017/8/1.
 */
var dataBase = new Vue({
    el:"#app",
    data:getData.dataObj({
                    "rowObj":'',
                    "divIndex":'',
                    "editdivIndex":'',
                    "url":serverPath+'/maintTable/queryPage',
                    "delUrl":serverPath+'/maintTable/delete',
    }),
    methods:{
        get(){//分页查询
            pagingObj.Example(this.url,this.input,this.pageSize,this.pageNum,this,function(){

            });
        },
        click(row, event, column){//点击table
            this.rowObj = row;
            console.log(row);
        },
        FindOk(row){//选中table
            this.$refs.myData.setCurrentRow(row);
        },
        handleClick(){//查看明细
            this.divIndex = ibcpLayer.ShowDiv('data-base.html','表字段信息','700px', '500px',function(){
                DatabaseDetails.Robj = dataBase.rowObj;
                DatabaseDetails.selDatas();
            })
        },
        editTableBase(){//编辑
            this.editdivIndex = ibcpLayer.ShowDiv('add-tablebase.html','编辑数据库信息','400px', '440px',function(){
                topButtonObj.isEdit=true;
                dataBase.input = '';
                tableBase.rowObj = dataBase.rowObj;
                tableBase.formTable.name = dataBase.rowObj.tableSchema;
                tableBase.formTable.Ename = dataBase.rowObj.tableEname;
                tableBase.formTable.Cname = dataBase.rowObj.tableCname;
                tableBase.formTable.desp = dataBase.rowObj.desp;
            })
        },
        delTableBase(){
            deleteObj.del(function(){
                var data = {
                    "url":dataBase.delUrl,
                    "jsonData":{rowId:dataBase.rowObj.rowId},
                    "obj":dataBase
                }
                gmpAjax.showAjax(data,function(res){
                    queryData.getData(dataBase.url,dataBase.input,dataBase)
                })
            })
        },
        handleSizeChange(val){//每页显示多少条
            this.pageSize=val;
            this.get();
        },
        handleCurrentChange(val){//点击第几页
            this.pageNum=val;
            this.get();
        },
        headSort(column){//列头排序
            pagingObj.headSorts1(this.url,this.input,column,this);
        }
    },
    created(){
        this.get();
        // $(document).ready(function(){
        //     dataBase.leftHeight=$(window).height()-190;
        // });
        // $(window).resize(function(){
        //     dataBase.leftHeight=$(window).height()-190;
        // })
    },
    updated(){
        this.FindOk(this.tableData[0]);
    }
});
var topButtonObj = new Vue({
    el:'#myButton',
    data:{
        divIndex:'',
        isEdit:''
    },
    methods:{
        addTableBase(){
            this.divIndex = ibcpLayer.ShowDiv('add-tablebase.html','新增数据库信息','400px', '440px',function(){
                topButtonObj.isEdit=false;
                dataBase.input = '';
            })
        }
    }
})