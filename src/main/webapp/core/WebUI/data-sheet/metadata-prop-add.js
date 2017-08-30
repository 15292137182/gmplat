/**
 * Created by admin on 2017/8/30.
 */

Vue.component('select-contablefield',SelectOptions.ConSetOpt('conTableField','conTableFieldInput',basLeft.relateTableRowId,conChildTable));
Vue.component('select-vtype', SelectOptions.setOpt('','value','valueType',''));
Vue.component('select-vorigin', SelectOptions.setOpt('','value','valueTypeOrigin',''));

var proEm = new Vue({
        el: '#addProEvent',
        data: function () {
            return {
                labelPosition: 'right',
                addProForm: {
                    codeProInput: '',
                    nameProInput: '',
                    checked: '',
                    checked1: '',
                    comContent: '',
                    disabled: true,
                    iconEvent: false,
                    checkEvent: false,
                    reaTabEvent:false,
                    dataId:'',
                    flag:true,
                },
                optionValue:[],
                optionLeft: [],
                optionRight: [],
            }
        },
        methods: {
            searchProTable(){
                this.checked = false  //点击选择表 按钮不选中
                var htmlUrl = 'connect-child.html';
                littledivIndex = ibcpLayer.ShowIframe(htmlUrl, '关联表字段', '600px', '560px', false)
            },
            conformEvent(addProForm) {
                console.log(proEm.$refs.contablefield.conTableFieldInput)
                if(proEm.addProForm.checked==true&proEm.$refs.contablefield.conTableFieldInput!='') {
                    ibcpLayer.ShowMsg('扩展属性与关联表字段只能选其一')
                    return;
                }
                proEm.$refs[addProForm].validate(function (valid) {
                    if (valid) {
                        if(operateOPr==1){
                            addObj.addOk(function(){
                                proEm.$http.jsonp(serverPath + "/businObjPro/add", {
                                    objRowId: basLeft.currentId,//左边表的ID
                                    propertyName: proEm.addProForm.nameProInput,//业务对象属性名称
                                    wetherExpandPro: proEm.addProForm.checked,//是否为扩展属性
                                    relateTableColumn: proEm.$refs.contablefield.conTableFieldInput,//关联表字段
                                    valueType: proEm.$refs.vtype.value,//值类型
                                    valueResourceType: proEm.$refs.vorigin.value,//值来源类型
                                    valueResourceContent: proEm.addProForm.comContent,//值来源内容
                                }, {
                                    jsonp: 'callback'
                                }).then(function (res) {
                                    showMsg.MsgOk(basTop,res);
                                    basRightBottom.rightInput='';
                                    //分页跳回到第一页
                                    basRightBottom.searchRight();
                                    ibcpLayer.Close(divIndex);
                                });
                            },function(){
                                showMsg.MsgError(basTop)
                            })
                        }
                        else if(operateOPr==2){
                            console.log(basRightBottom.currentVal.rowId)
                            editObj.editOk(function(){
                                proEm.$http.jsonp(serverPath + "/businObjPro/modify", {
                                    rowId: basRightBottom.currentVal.rowId,//本生的ID
                                    objRowId: basLeft.currentId,//左边表的ID
                                    propertyCode: proEm.addProForm.codeProInput,//业务对象代码
                                    propertyName: proEm.addProForm.nameProInput,//业务对象属性名称
                                    wetherExpandPro: proEm.addProForm.checked,//是否为扩展属性
                                    relateTableColumn:  proEm.$refs.contablefield.conTableFieldInput,//关联表字段
                                    valueType: proEm.$refs.vtype.value,//值类型
                                    valueResourceType: proEm.$refs.vorigin.value,//值来源类型
                                    valueResourceContent: proEm.addProForm.comContent,//值来源内容
                                }, {
                                    jsonp: 'callback'
                                }).then(function (res) {
                                    showMsg.MsgOk(basTop,res);
                                    basRightBottom.rightInput='';
                                    basRightBottom.searchRight();
                                    ibcpLayer.Close(divIndex);
                                });
                            },function(){
                                showMsg.MsgError(basTop)
                            });
                        }
                    }else {
                        return false;
                    }
                })

            },
            cancel() {
                ibcpLayer.Close(divIndex);
            }
        },
        updated() {
//            if(proEm.$refs.contablefield.conTableFieldInput!=''){
//                proEm.addProForm.checked =true
//            }
            //console.log(proEm.$refs.contablefield.conTableFieldInput)
        }
    })
