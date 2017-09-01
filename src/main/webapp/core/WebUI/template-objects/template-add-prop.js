/**
 * Created by admin on 2017/8/27.
 */

Vue.component('select-vtype', SelectOptions.setOpt('','value','valueType',''));


var addTempProp = new Vue({
    "el": "#addEventProp",
    data: function () {
            return {
                labelPosition: 'right',
                addTempPropObj: {
                    codeInput: '',
                    engNameInput: '',
                    chnNameInput: '',
                    default:'',
                    comContent:''
                }
            }
        },
    methods: {
        //保存事件
        saveTempProp(){
            if(operateOPr==1){
                addObj.addOk(function(){
                    var data={
                        "url":addObjTemp,
                        "jsonData":{templateObjRowId:basLeft.currentId,
                            ename:addTempProp.addTempPropObj.engNameInput,
                            cname:addTempProp.addTempPropObj.chnNameInput,
                            valueType:addTempProp.$refs.vtype.value,
                            defaultValue:addTempProp.addTempPropObj.default,
                            desp:addTempProp.addTempPropObj.comContent
                        },
                        "obj":basRight
                    };
                    gmpAjax.showAjax(data,function(res){
                        //关闭弹层
                        ibcpLayer.Close(divIndex);
                        //分页查询
                        queryData.getDatas(queryObjTemp,basRight.input,basLeft.currentId,basRight,function(res){})
                    })
                })
            }else if(operateOPr==2){
                editObj.editOk(function(){
                    var data={
                        "url":editObjTemp,
                        "jsonData":{templateObjRowId:basLeft.currentId,
                            rowId:basRight.currentId,
                            ename:addTempProp.addTempPropObj.engNameInput,
                            cname:addTempProp.addTempPropObj.chnNameInput,
                            valueType:addTempProp.$refs.vtype.value,
                            defaultValue:addTempProp.addTempPropObj.default,
                            desp:addTempProp.addTempPropObj.comContent
                        },
                        "obj":basRight
                    };
                    gmpAjax.showAjax(data,function(res){
                        //关闭弹层
                        ibcpLayer.Close(divIndex);
                        //分页查询
                        queryData.getDatas(queryObjTemp,basRight.input,basLeft.currentId,basRight,function(res){
                        })
                    })
                })
            }
        },
        //取消事件
        cancelTempProp(){
            ibcpLayer.Close(divIndex);
        }

    }
});