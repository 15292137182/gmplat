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
                    gmpAjax.showAjax(addObjTemp,{templateObjRowId:basLeft.currentId,
                                                   ename:addTempProp.addTempPropObj.engNameInput,
                                                   cname:addTempProp.addTempPropObj.chnNameInput,
                                                   valueType:addTempProp.$refs.vtype.value,
                                                   defaultValue:addTempProp.addTempPropObj.default,
                                                   desp:addTempProp.addTempPropObj.comContent
                    },function(res){
                        console.log(res);
                        //显示消息
                        showMsg.MsgOk(basLeft,res);
                        //关闭弹层
                        ibcpLayer.Close(divIndex);
                        //分页查询
                        queryData.getData(queryTemp,basLeft.input,basLeft,function(res){})
                    })
                })
            }else if(operateOPr==2){

            }
        },
        //取消事件
        cancelTempProp(){
            ibcpLayer.Close(divIndex);
        }

    }
});