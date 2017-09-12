/**
 * Created by admin on 2017/8/30.
 */
var proEm = new Vue({
        el: '#addProEvent',
        data: function () {
            return {
                labelPosition: 'right',
                addProForm: {
                    codeProInput: '',//代码
                    nameProInput: '',//名称
                    comContent: '',
                    iconEvent: false,
                    checkEvent: false,
                    reaTabEvent:false,
                    defaultValue:'',//默认值
                    extendName:'' //扩展属性名
                },
                value_1:'',//关联表字段
                value_2:'',//值类型
                value_3:'',//值来源类型
                value_4:'',//类型
                value_5:'',//值来源内容
                reaTable:true,//关联表字段是否显示
                extendPro:false,//关扩展属性名是否显示
            }
        },
        methods: {
            conformEvent() {
                        if(operateOPr==1){
                            addObj.addOk(function(){
                                var data={
                                    "url":serverPath + "/businObjPro/add",
                                    "jsonData":{
                                        objRowId: basLeft.currentId,//左边表的ID
                                        propertyName: proEm.addProForm.nameProInput,//业务对象属性名称
                                        wetherExpandPro: proEm.value_4,//属性类型
                                        //wetherExpandPro: proEm.value_4,//扩展属性名
                                        relateTableColumn: proEm.value_1,//关联表字段
                                        valueType: proEm.value_2,//值类型
                                        valueResourceType: proEm.value_3,//值来源类型
                                        extendsEname: proEm.value_4,//值来源内容
                                        valueResourceContent: proEm.addProForm.defaultValue,//默认值
                                    },
                                    "obj":basTop
                                };
                                gmpAjax.showAjax(data,function(res){
                                    //分页跳回到第一页
                                    basRight.searchRight();
                                    ibcpLayer.Close(divIndex);
                                })
                            })
                        }
                        else if(operateOPr==2){
                            editObj.editOk(function(){
                                var data={
                                    "url":serverPath + "/businObjPro/modify",
                                    "jsonData":{
                                        rowId: basRight.currentVal.rowId,//本生的ID
                                        objRowId: basLeft.currentId,//左边表的ID
                                        propertyName: proEm.addProForm.nameProInput,//业务对象属性名称
                                        wetherExpandPro: proEm.value_4,//是否为扩展属性
                                        relateTableColumn: proEm.value_1,//关联表字段
                                        valueType: proEm.value_2,//值类型
                                        valueResourceType: proEm.value_3,//值来源类型
                                        valueResourceContent: proEm.value_4,//值来源内容
                                        //valueResourceContent: proEm.value_4,//默认值
                                    },
                                    "obj":basTop
                                };
                                gmpAjax.showAjax(data,function(res){
                                    console.log(res);
                                    //分页跳回到第一页
                                    basRight.searchRight();
                                    ibcpLayer.Close(divIndex);
                                })
                            });
                        }
            },
            getChildData_1(datas){
                this.value_1=datas.value;
            },
            getChildData_2(datas){
                this.value_2=datas.value;
                //console.log(this.value_2)
            },
            getChildData_3(datas){
                this.value_3 = datas.value;
                //设置默认值
                if (datas != "" || datas != undefined) {
                    this.value_3 = datas.value;
                    this.label_3 = datas.label;
                    this.$refs.childMethod.cascaderEvent("Shanghai");
                }
                if(JSON.stringify(datas) === "{}") {
                    this.$refs.childMethod.cascaderEvent("");
                }


            },
            getChildData_4(datas){
              this.value_4=datas.value;
               //console.log(this.value_4);

            },
            getChildData_5(datas){
                this.value_5=datas.value;
                //console.log(this.value_5);

            },
            cancel() {
                ibcpLayer.Close(divIndex);
            }
        },
        updated() {
            //基本属性
            if(this.value_4=='1'){
                proEm.reaTable=true;
                proEm.extendPro=false;

            }//扩展属性
            else if(this.value_4=='2'){
                proEm.reaTable=false;
                proEm.extendPro=true;
            }
            //值类型来源(键值集合 序列规则)
            if(this.value_3=='5'){
                //console.log(111)
                this.value_5='3'
            }else if(this.value_3=='6'){
                //console.log(222)
                this.value_5='4'
            }
        }
    })