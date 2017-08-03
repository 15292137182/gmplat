/**
 * Created by andim on 2017/8/2.
 */
//左边table
var vm = new Vue({
    el:"#app",
    data:{
        input:'',
        myData:[],
        leftHeight:'',
        url:serverPath+'/fronc/select',
        deleteId:'',
        editObj:''
    },
    methods:{
        get(){
            this.$http.jsonp(this.url,{
                str:this.input
            },{
                jsonp:'callback'
            }).then(function(res){
                if(res.data.content.data!==''){
                    this.myData=res.data.content.data;
                    //vm1.FindData(vm.myData[0].rowId);
                }
            })
        },
        delete(){
            this.$http.jsonp(serverPath+"/fronc/delete",{
                rowId:this.deleteId
            },{
                jsonp:'callback'
            }).then(function(res){
                console.log(res);
                vm.get();
            })
        },
        click(row, event, column){
            //vm1.FindData(row.rowId);
            this.deleteId = row.rowId;
            this.editObj = row;
        },
        FindOk(row){
            this.$refs.myTable.setCurrentRow(row);
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

//右边table
var vm1 = new Vue({
    el:'#right',
    data:{
        rightData:[],
        rightHeight:'',
    },
    methods:{
        FindData(id){
            this.$http.jsonp('url',{
                "key":id
            },{
                jsonp:'callback'
            }).then(function (res) {
                this.rightData=res.data.content.data;
            })
        }
    },
    created(){
        $(document).ready(function(){
            vm1.rightHeight=$(window).height()-90;
        });
        $(window).resize(function(){
            vm1.rightHeight=$(window).height()-90;
        })
    },
});

//按钮
var mb = new Vue({
    el:'#myButton',
    data:{
        divIndex:'',
    },
    methods: {
        addBlock(){
            this.divIndex = ibcpLayer.ShowDiv('AddBlock.html','新增功能块','400px', '500px',function(){
                em.isEdit = false;
            });
        },
        editBlock(){
            this.divIndex = ibcpLayer.ShowDiv('AddBlock.html','编辑功能块','400px', '500px',function(){
                em.isEdit = true;
                var obj = vm.editObj;
                em.codeInput = obj.funcCode;
                em.nameInput = obj.funcName;
                em.typeInput = obj.funcType;
                em.tableInput = obj.tables;
                em.dataId = obj.relateBusiObj;
                em.desp = obj.desp;
                em.rowId = obj.rowId;
            });

        },
        del(){
            vm.delete();
        }
    }
})