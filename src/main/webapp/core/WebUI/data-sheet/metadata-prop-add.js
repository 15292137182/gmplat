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

                //属性类型
                proType_1:{
                    params: "proType",
                    value: "",
                    disabled: "false"
                },

                //关联表字段
                tableField_1:{
                    url: conChildTable+"?rowId="+basLeft.relateTableRowId,
                    key:'{"label":"columnEname","value":"rowId"}',
                    value: "",
                    disabled: "false"
                },

                //值类型
                valueType_1:{
                    params:"valueType",
                    value:"",
                    disabled:"false"
                },

                //值类型来源
                valueTypeOrigin_1:{
                    params:"valueTypeOrigin",
                    value:"",
                    disabled:"false"
                },
                //值来源内容
                valueOriginContent_1:{
                    url:keySetPageUrl,
                    key:'',
                    value:"",
                    disabled:"false"
                },

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
                                        wetherExpandPro: proEm.proType_1.value,//属性类型
                                        //wetherExpandPro: proEm.value_4,//扩展属性名
                                        relateTableColumn: proEm.tableField_1.value,//关联表字段
                                        valueType: proEm.valueType_1.value,//值类型
                                        valueResourceType: proEm.valueTypeOrigin_1.value,//值来源类型
                                        extendsEname: proEm.valueOriginContent_1.value,//值来源内容
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
            //属性类型
            getProType_1(datas){
                this.proType_1.value=datas.value;
            },

            //关联表字段
            getTableField_1(datas){
                this.tableField_1.value=datas.value;
            },

            //值类型
            getValueType_1(datas){
                this.valueType_1.value=datas.value;
            },

            //值类型来源
            getValueTypeOrigin_1(datas){
                this.valueTypeOrigin_1.value=datas.value;

                switch (datas.value){
                    case "keySet":
                        this.$refs.valueOriginContent_1.cascaderUrl(keySetPageUrl,'{"label":"keysetName","value":"rowId"}');
                         break;
                    case "sequenceRule":
                        this.$refs.valueOriginContent_1.cascaderUrl(sequenceRuleConfigPageUrl,'{"label":"seqName","value":"rowId"}');
                        break;
                    case "dataSet":
                        this.$refs.valueOriginContent_1.cascaderUrl(datasetConfigPageUrl,'{"label":"datasetName","value":"rowId"}');
                         break;
                }
            },
            //值来源内容
            getValueOriginContent_1(datas){
                 this.valueOriginContent_1.value=datas.value;
            },

            // getChildData_1(datas){
            //     this.value_1=datas.value;
            // },
            // getChildData_2(datas){
            //     this.value_2=datas.value;
            //     //console.log(this.value_2)
            // },
            // getChildData_3(datas){
            //     this.value_3 = datas.value;
            //     //设置默认值
            //     if (datas != "" || datas != undefined) {
            //         this.value_3 = datas.value;
            //         this.label_3 = datas.label;
            //         this.$refs.childMethod.cascaderEvent("Shanghai");
            //     }
            //     if(JSON.stringify(datas) === "{}") {
            //         this.$refs.childMethod.cascaderEvent("");
            //     }
            // },
            // getChildData_4(datas){
            //   this.value_4=datas.value;
            //    //console.log(this.value_4);
            //
            // },
            // getChildData_5(datas){
            //     this.value_5=datas.value;
            //     //console.log(this.value_5);
            //
            // },
            cancel() {
                ibcpLayer.Close(divIndex);
            }
        },
        created(){
            if(operateOPr==1) {
                this.proType_1.value="";
                this.tableField_1.value="";
                this.valueType_1.value="";
                this.valueTypeOrigin_1.value="";
            }
            if(operateOPr==2){
                this.proType_1.value="";
                this.tableField_1.value="";
                this.valueType_1.value="";
                this.valueTypeOrigin_1.value="";
            }
        },
        updated() {
            //基本属性
            // if(this.value_4=='1'){
            //     proEm.reaTable=true;
            //     proEm.extendPro=false;
            //
            // }//扩展属性
            // else if(this.value_4=='2'){
            //     proEm.reaTable=false;
            //     proEm.extendPro=true;
            // }
            // //值类型来源(键值集合 序列规则)
            // if(this.value_3=='5'){
            //     //console.log(111)
            //     this.value_5='3'
            // }else if(this.value_3=='6'){
            //     //console.log(222)
            //     this.value_5='4'
            // }
        }
    })