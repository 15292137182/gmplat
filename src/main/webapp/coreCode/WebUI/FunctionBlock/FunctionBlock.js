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
        deleteId:''
    },
    methods:{
        get(){
            this.$http.jsonp(this.url,{
                str:this.input
            },{
                jsonp:'callback'
            }).then(function (res) {
                this.myData=res.data.content.data.content.data;
                console.log(res.data.content.data.content.data);
                //vm1.FindData(vm.myData[0].rowId);
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
            console.log(this.deleteId);
        },
        FindOk(row){
            this.$refs.myTable.setCurrentRow(row);
        }
    },
    created(){
        this.get();
        $(document).ready(function(){
            vm.leftHeight=$(window).height()-107;
        });
        $(window).resize(function(){
            vm.leftHeight=$(window).height()-107;
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
            vm1.rightHeight=$(window).height()-107;
        });
        $(window).resize(function(){
            vm1.rightHeight=$(window).height()-107;
        })
    },
});

//按钮
var mb = new Vue({
    el:'#myButton',
    data:{
        divIndex:''
    },
    methods: {
        addBlock(){
            this.divIndex = ibcpLayer.ShowDiv('AddBlock.html','新增功能块','400px', '500px')
        },
        del(){
            vm.delete();
        }
    }
})