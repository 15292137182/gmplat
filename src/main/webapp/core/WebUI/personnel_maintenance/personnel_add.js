/**
 * Created by admin on 2017/10/10.
 */
var count=true;
var count1=true;
var count2=true;
var change=1;
var proEm = new Vue({
    el: '#addProEvent',
    data: function () {
        return {
            labelPosition: 'right',
            pickerOptions0: {
                disabledDate(time) {
                    return time.getTime() > Date.now();
                }
            },
            value1:'',
            addProForm: {
                useCode: '',//工号
                useName: '',//姓名
                useNickName:'',//昵称
                uniquePassword:'123456',//初始密码有默认值
                usePart:'',//所属部门
                useIdentify:'',//身份证
                usePhone:'',//移动电话
                useEmail: '',//邮箱
                useSex:'',//性别
                useDuty:'',//职务
                useDate:'',//入职日期
                useInstruction:'',//说明
                useRemark:'',//备注
            },
            apparent:true,//显示输入框还是下拉框
            rules:{
                nameProInput: [
                    { required: true, message: '请输入属性名称'},
                ],
                proType: [
                    { required: true,trigger: 'blur',message: '请选择属性类型'}
                ],
                fieldAliasInput: [
                    { required: true,message: '请输入字段别名'}
                ],
                typeInput: [
                    { required: true,trigger: 'blur',message: '请选择值类型'}
                ],

            }
        }
    },
    methods: {
        conformEvent(formName) {
            this.$refs.addProForm.validate((valid) => {
                if (valid) {
                    if(operateOPr==1){
                        addObj.addOk(function(){
                            //如果为输入框 值内容来源为输入框的值
                            if( proEm.valueTypeOrigin_1.value==''){
                                proEm.valueOriginContent_1.value=proEm.addProForm.comContent
                            }
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
                                    valueResourceContent:proEm.valueOriginContent_1.value,//值来源内容
                                    fieldAlias: proEm.addProForm.fieldAliasInput, //拓展英文名
                                    defaultValue: proEm.addProForm.defaultValue,//默认值
                                },
                                "obj":basTop,
                                "showMsg":true
                            };
                            gmpAjax.showAjax(data,function(res){
                                console.log(proEm.valueOriginContent_1.value)
                                //分页跳回到第一页
                                basRight.searchRight();
                                ibcpLayer.Close(divIndex);
                            })
                        })
                    }
                    else if(operateOPr==2){
                        editObj.editOk(function(){
                            if( proEm.valueTypeOrigin_1.value==''){
                                proEm.valueOriginContent_1.value=proEm.addProForm.comContent
                            }
                            var data={
                                "url":serverPath + "/businObjPro/modify",
                                "jsonData":{
                                    rowId: basRight.currentVal.rowId,//本生的ID
                                    objRowId: basLeft.currentId,//左边表的ID
                                    propertyName: proEm.addProForm.nameProInput,//业务对象属性名称
                                    wetherExpandPro: proEm.proType_1.value,//属性类型,
                                    relateTableColumn: proEm.tableField_1.value,//关联表字段
                                    valueType: proEm.valueType_1.value,//值类型
                                    valueResourceType: proEm.valueTypeOrigin_1.value,//值来源类型
                                    valueResourceContent: proEm.valueOriginContent_1.value,//值来源内容
                                    fieldAlias: proEm.addProForm.fieldAliasInput, //拓展英文名
                                    defaultValue: proEm.addProForm.defaultValue,//默认值

                                },
                                "obj":basTop,
                                "showMsg":true
                            };
                            gmpAjax.showAjax(data,function(res){
                                //console.log(res);
                                //分页跳回到第一页
                                basRight.searchRight();
                                ibcpLayer.Close(divIndex);
                            })
                        });
                    }
                } else {
                    $('.el-form-item__error').css("display", "block");
                    proEm.judgeMent();
                    return false;
                }
            })
            //if(this.proType_1.value=='extend'&&this.addProForm.fieldAliasInput==""){
            //    ibcpLayer.ShowMsg("扩展属性未指定字段别名");
            //    return false;
            //}
        },
        //属性类型
        getProType_1(datas){
            this.proType_1.value=datas.value;

            if(this.proType_1.value=='extend'){
                proEm.$refs.tableField_1.setDisabled(true);

            }else{
                proEm.$refs.tableField_1.setDisabled(false);
            }

            this.addProForm.proType = datas.value;

            var a=this.$refs.proType_1.$children[0].$children[0].$el;
            var b=$(a).children('input');
            if(this.addProForm.proType.length==0) {
                $(b).css('borderColor', '#ff4949');
            }else{
                $(b).css('borderColor','#bfcbd9');
            }

        },
        //关联表字段
        getTableField_1(datas){
            this.tableField_1.value=datas.value;

            //this.addProForm.reaTable = datas.value;
            //var a=this.$refs.tableField_1.$children[0].$children[0].$el;
            //var b=$(a).children('input');
            //if(this.addProForm.reaTable.length==0) {
            //    if(count1!=true){
            //        $(b).css('borderColor', '#ff4949');
            //    }
            //    count1=false;
            //}else{
            //    $(b).css('borderColor','#bfcbd9');
            //}
        },
        //值类型
        getValueType_1(datas){
            this.valueType_1.value=datas.value;

            this.addProForm.typeInput = datas.value;
            var a=this.$refs.valueType_1.$children[0].$children[0].$el;
            var b=$(a).children('input');
            if(this.addProForm.typeInput.length==0) {
                if(count2!=true){
                    $(b).css('borderColor', '#ff4949');
                }
                count2=false;
            }else{
                $(b).css('borderColor','#bfcbd9');
            }
        },
        //值类型来源
        getValueTypeOrigin_1(datas){
            this.valueTypeOrigin_1.value=datas.value;
            console.log(datas.value);
            //判断是输入框还是下拉框
            if( proEm.valueTypeOrigin_1.value==''){
                proEm.apparent=true;
            }else{
                proEm.apparent=false;
            }

            switch (datas.value){
                case "keySet":
                    this.$refs.valueOriginContent_1.setUrl({
                        url:keySetPageUrl,
                        key:'{"label":"keysetName","value":"rowId"}'
                    });
                    break;
                case "sequenceRule":
                    this.$refs.valueOriginContent_1.setUrl({
                        url:sequenceRuleConfigPageUrl,
                        key:'{"label":"seqName","value":"rowId"}'
                    });
                    break;
                case "dataSet":
                    this.$refs.valueOriginContent_1.setUrl({
                        url:datasetConfigPageUrl,
                        key:'{"label":"datasetName","value":"rowId"}'
                    });
                    break;
            }
        },
        //值来源内容
        getValueOriginContent_1(datas){
            this.valueOriginContent_1.value=datas.value;
        },
        cancel() {
            ibcpLayer.Close(divIndex);
        },
        judgeMent(){
            setTimeout(function(){
                $('.el-form-item__error').css("display", "none");
            },1500)
        },
    },

})