/**
 * Created by admin on 2017/8/27.
 */

var addTemp = new Vue({
    "el": "#addEvent",
    data: function () {
            return {
                labelPosition: 'right',
                addTempObj: {
                    codeInput: '',
                    nameInput: '',
                    comContent: '',
                    modules:'',
                    system:'',
                }
            }
    },
    methods: {
        //保存事件
        saveTemp(){
            //新增
            if(operate==1){
                addObj.addOk(function(){
                    var data={
                        "url":addTempObj,
                        "jsonData":{templateName:addTemp.addTempObj.nameInput,desp:addTemp.addTempObj.comContent,belongModule:addTemp.addTempObj.modules,
                            belongSystem:addTemp.addTempObj.system},
                        "obj":basLeft
                    };
                    gmpAjax.showAjax(data,function(res){
                        //关闭弹层
                        ibcpLayer.Close(divIndex);
                        //分页查询
                        queryData.getData(queryTemp,basLeft.input,basLeft,function(res){
                            basLeft.currentChange(basLeft.tableData[0]);
                        })
                    })
                })
            }if(operate==2){
                editObj.editOk(function(){
                    var data={
                        "url":editTempObj,
                        "jsonData":{templateCode:addTemp.addTempObj.codeInput,rowId:basLeft.currentId,templateName:addTemp.addTempObj.nameInput,desp:addTemp.addTempObj.comContent,
                            belongModule:addTemp.addTempObj.modules, belongSystem:addTemp.addTempObj.system},
                        "obj":basLeft
                    };
                    gmpAjax.showAjax(data,function(res){
                        console.log(res)
                        ibcpLayer.Close(divIndex);
                        queryData.getData(queryTemp,basLeft.input,basLeft,function(res){
                            basLeft.currentChange(basLeft.tableData[0]);
                        })
                    })
                })
            }
        },
        //取消事件
        cancelTemp(){
            ibcpLayer.Close(divIndex);
        }

    }
});