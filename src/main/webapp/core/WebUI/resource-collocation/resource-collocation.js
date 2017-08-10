/**
 * Created by admin on 2017/8/7.
 */

var resTop=new Vue({
    el:'#resTop',
    data:{

    },
    methods:{
        addResEvent() {
            operate = 1;
            var htmlUrl = 'resource-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增系统资源配置', '400px', '300px');
        },
        editResEvent() {
            operate = 2;
            var htmlUrl = 'resource-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑系统资源配置', '400px', '300px', function () {
                //code值
                resAdd.codeInput = resCol.currentValue.confKey;
                resAdd.nameInput = resCol.currentValue.confValue;
                resAdd.tableInput = resCol.currentValue.desp;
                resAdd.disabled = true
            });
        },
        deleteResEvent() {
            var deleteId = resCol.currentValue.rowId;
            console.log(deleteId);
            this.$http.jsonp(serverPath + "/sysConfig/delete", {
                delData: deleteId
            }, {
                jsonp: 'callback'
            }).then(function (ref) {
                resCol.searchResTable();
                ibcpLayer.ShowOK(ref.data.message);
            });
        }
    }
});

var resCol=new Vue({
    "el":"#resCol",
    data:{
        resInput: '',
        Height: '',
        myResData: []
    },
    methods:{
        searchResTable() {
            this.$http.jsonp(serverPath + "/sysConfig/query", {
                str: this.resInput
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                if(res.data.data!=null){
                    this.myResData = res.data.data;
                    console.log(this.myResData);
                    this.currentChange(this.myResData[0]);
                }else{
                    this.myResData=[];
                }
            });
        },
        currentChange(row, event, column) {
            //点击拿到这条数据的值
            console.log(row);
            this.currentValue=row;
            this.currentId = row.rowId;

        },
        FindFirstDate(row){
            this.$refs.myResData.setCurrentRow(row);
        }
    },
    created() {
        this.searchResTable();
        $(document).ready(function () {
            resCol.Height = $(window).height() - 150;
        });
        $(window).resize(function () {
            resCol.Height = $(window).height() - 150;
        })
    },
    updated() {
        this.FindFirstDate(this.myResData[0]);
    }
});