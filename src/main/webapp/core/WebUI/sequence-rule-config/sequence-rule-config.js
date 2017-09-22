/**
 * Created by jms on 2017/8/7.
 */
var str=serverPath+"/sequenceRule";
var query=str+"/query";
var queryById=str+"/queryById"
var insertUrl=str+"/add";
var modifyUrl=str+"/modify";
var del=str+"/delete";
var queryPage=str+"/queryPage";
var mock=str+"/mock";
var resetUrl=str+"/reset";
var queryObj=serverPath+"/businObj/queryPage";
var queryPro=serverPath+"/businObj/queryProPage";

function GlobalParameter(){
    var args={"tableKeySet":{"ruleConfig":{belongModule:"belongModule"}}};
    return args;
}

var config;
gmp_onload = function () {
    config=new Vue({
        el:'#srconfig',
        data:getData.dataObj({
            tableId:'ruleConfig',
            rowId:'',//重置选中的rowId
            divIndex:'',
            operate:'',

        }),
        methods:{
            search(){
                this.searchPage();
            },
            searchPage(){
                pagingObj.Example(queryPage,this.input, this.pageSize,this.pageNum,this,function(){
                    config.onClick(config.tableData[0]);
                });
            },
            searchRes(){
                pagingObj.Example(queryPage,this.input, this.pageSize,this.pageNum,this);
            },
            onClick(row, event, column){
                this.currentVal=row;
                config.rowId=config.currentVal.rowId;
                var data={
                    "url":queryById,
                    "jsonData":{rowId:config.currentVal.rowId},
                    "obj":config
                }
                gmpAjax.showAjax(data,function(res){
                    var data=res.data;
                    config.keyValueContent=data;
                })
            },
            addEvent(){
                // var data={
                //     "url":queryObj,
                //     "jsonData":{search:""},
                //     "obj":config
                // }
                // gmpAjax.showAjax(data, function(res){
                //     var data=res.data.result;
                //     var len = data.length;
                //     var jsonStr = [];
                //     for (var i = 0; i < len; i++) {
                //         var json = {};
                //         var objName = data[i].objectName;
                //         var rowId = data[i].rowId;
                //         json['label'] = objName;
                //         json['value'] = rowId;
                //
                //         var data1={
                //             "url":queryPro,
                //             "jsonData":{rowId: rowId, search: ""},
                //             "obj":config
                //         }
                //
                //         gmpAjax.showAjax(data1, function(res1){
                //             console.log(res1);
                //             var data1=res1.data.result;
                //             if (data1 == null) {
                //                 return false;
                //             } else {
                //                 var child = [];
                //                 for (var j = 0; j < data1.length; j++) {
                //                     var _child = {};
                //                     var _rowid = data1[j].rowId;
                //                     var propertyName = data1[j].propertyName;
                //                     _child['label'] = propertyName;
                //                     _child['value'] = _rowid;
                //                     child[j] = _child;
                //                 }
                //                 json['children'] = child;
                //             }
                //         })
                //         jsonStr[i] = json;
                //     }
                //     config.objoptions=jsonStr;
                // })
                this.operate = 1;
                var htmlUrl="add-sequence-rule-config.html";
                this.divIndex=ibcpLayer.ShowIframe(htmlUrl, '新增序列号规则配置', '1000px', "550px",true);
            },
            editEvent(){
                this.operate=2;
                var htmlUrl = 'add-sequence-rule-config.html';
                this.divIndex = ibcpLayer.ShowIframe(htmlUrl, '编辑序列号规则配置', '1000px', '550px');
            },
            deleteEvent(){
                deleteObj.del(function () {
                    var data = {
                        "url": del,
                        "jsonData": {
                            rowId: config.currentVal.rowId
                        },
                        "obj": config
                    }
                    gmpAjax.showAjax(data, function (res) {
                        queryData.getData(queryPage, config.input, config, function (res) {
                            config.onClick(config.tableData[0]);
                        })
                    })
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
                    var data={
                        "url":queryById,
                        "jsonData":{rowId:config.currentVal.rowId},
                        "obj":config
                    }
                    gmpAjax.showAjax(data, function(res){
                        var data=res.data;
                        seqReset.resetform.seqCode=data[0].seqCode;
                        seqReset.resetform.seqName=data[0].seqName;
                        seqReset.resetform.seqContent=data[0].seqContent;
                    })
                });

            }
        },
        created(){

            this.searchRes();

        }
    })
}




