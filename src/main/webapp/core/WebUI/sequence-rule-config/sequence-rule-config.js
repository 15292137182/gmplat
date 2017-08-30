/**
 * Created by jms on 2017/8/7.
 */
var str=serverPath+"/sequenceRule";
const query=str+"/query";
const insert=str+"/add";
const modify=str+"/modify";
const del=str+"/delete";
const queryPage=str+"/queryPage";
const mock=str+"/mock";

var config=new Vue({
    el:'#srconfig',
    data:{
        input:'',
        tableData:[],
        Height:'',
        loading:true,
        pageSize:10,//每页显示多少条
        pageNum:1,//第几页
        allDate:0,//共多少条
        rowId:'',//重置选中的rowId
    },
    methods:{
        search(){
           this.searchPage();
        },
        searchPage(){
            pagingObj.Example(queryPage,this.input, this.pageSize,this.pageNum,this);
        },
        onClick(row, event, column){
            this.currentVal=row;
            config.rowId=config.currentVal.rowId;
            gmpAjax.showAjax(serverPath+'/sequenceRule/queryById',
                {rowId:config.currentVal.rowId},
                function(res){
                    var data=res.resp.content.data.result;
                    config.keyValueContent=data;
                })
        },
        addEvent(){
            operate = 1;
            var htmlUrl="sequence-rule-config-add.html";
            divIndex=ibcpLayer.ShowDiv(htmlUrl, '新增序列号规则配置', '400px', '420px');
        },
        editEvent(){
            operate=2;
            var htmlUrl = 'sequence-rule-config-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑键值集合', '400px', '420px', function () {
                //code值
                add.formTable.seqCodeInput = config.currentVal.seqCode;
                add.formTable.seqNameInput = config.currentVal.seqName;
                add.formTable.seqContentInput = config.currentVal.seqContent;
                add.formTable.despInput = config.currentVal.desp;
                add.formTable.versionInput = config.currentVal.version;
                add.disabled=true;
            });
        },
        deleteEvent(){
            deleteObj.del(function(){
                config.$http.jsonp(del,{
                    rowId:config.currentVal.rowId
                }, {
                    jsonp: 'callback'
                }).then(function (res) {
                    ibcpLayer.ShowOK(res.data.message);
                    config.searchPage();
                });

            })
        },
        handleSizeChange(val){//每页显示多少条
            this.pageSize=val;
            this.searchPage();
        },
        handleCurrentChange(val){//点击第几页
            this.pageNum=val;
            this.searchPage();
        },
        headSort(column){//列头排序
            pagingObj.headSorts1(queryPage,this.input,column,this);
        },
        //重置
        reset(){
            var htmlUrl='sequence-rule-config-reset.html';
            resetIndex = ibcpLayer.ShowDiv(htmlUrl, '序列重置', '400px', '420px',function(){
                gmpAjax.showAjax(serverPath+'/sequenceRule/queryById',
                    {rowId:config.currentVal.rowId},
                    function(res){
                        var data=res.resp.content.data.result[0];
                        seqReset.resetform.seqCode=data.seqCode;
                        seqReset.resetform.seqName=data.seqName;
                        seqReset.resetform.seqContent=data.seqContent;
                     })
            });

        }
    },
    created(){
        this.searchPage();
    }
})
