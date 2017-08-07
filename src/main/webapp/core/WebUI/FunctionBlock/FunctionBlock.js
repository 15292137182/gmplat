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
                    vm1.FindData(vm.myData[0].rowId);
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
            console.log(row);
            vm1.FindData(row.rowId);
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
        url:serverPath+'/core/queryFronFuncPro'
    },
    methods:{
        FindData(id){
            this.$http.jsonp(this.url,{
                "str":'',
                "rowId":id
            },{
                jsonp:'callback'
            }).then(function (res) {
                if(res.data.content.data!=''){
                    this.rightData=res.data.content.data;
                    console.log(res.data.content.data);
                }
            },function(){
                alert("error")
            })
        },
        handleClick(){
            console.log("success");
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
        rowObjId:'',
        objId:''
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

            });

        },
        del(){
            vm.delete();
        },
        addData(){
            mb.rowObjId = vm.editObj.rowId;
            mb.objId = vm.editObj.relateBusiObj
            mb.divIndex = ibcpLayer.ShowIframe('AddData.html','新增属性','500px', '550px')
        }
    }
})