/**
 * Created by jms on 2017/8/7.
 */
var str=serverPath+"/sequenceRule";
const query=str+"/query";
const insert=str+"/add";
const modify=str+"/modify";
const del=str+"/delete";
const queryPage=str+"/queryPage";

var config=new Vue({
    el:'#srconfig',
    data:{
        input:'',
        seqRuleConfigdata:[],
        Height:'',
        loading:true,
        pageSize:10,//每页显示多少条
        pageNum:1,//第几页
        allDate:0//共多少条
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
        },
        addEvent(){
            operate = 1;
            var htmlUrl="sequence-rule-config-add.html";
            divIndex=ibcpLayer.ShowDiv(htmlUrl, '新增序列号规则配置', '400px', '450px');
        },
        editEvent(){
            operate=2;
            var htmlUrl = 'sequence-rule-config-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑键值集合', '400px', '450px', function () {
                //code值
                add.seqCodeInput = config.currentVal.seqCode;
                add.seqNameInput = config.currentVal.seqName;
                add.seqContentInput = config.currentVal.seqContent;
                add.despInput = config.currentVal.desp;
                add.versionInput = config.currentVal.version;
                add.disabled=true;
            });
        },
        deleteEvent(){
            var list=[];
            list.push(config.currentVal.rowId);
            deleteObj.del(function(){
                config.$http.jsonp(del,{
                    rowIds:list.join(" ")
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
    },
    created(){
        this.searchPage();
        $(document).ready(function(){
            config.Height=$(window).height()-190;
        });
        $(window).resize(function(){
            config.Height=$(window).height()-190;
        })
    }
})
