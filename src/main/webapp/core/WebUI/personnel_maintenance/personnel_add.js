/**
 * Created by admin on 2017/10/10.
 */
var useAdd = new Vue({
    el: '#addProEvent',
    data: function () {
        return {
            pickerOptions0: {
                disabledDate(time) {
                    return time.getTime() > Date.now();
                }
            },
            belongOrg: {
                // 配置显示项
                defaultProps: {
                    // 树节点显示文字
                    label: 'orgName',
                    // 节点id
                    id: "rowId",
                    // 父节点信息
                    parentId: "orgPid",
                    // 当前节点信息
                    selfId: "orgId"
                },
                // 获取数据接口
                url: serverPath + "/baseOrg/queryPage",
            },
            id:'',//工号
            name: '',//姓名
            nickname:'',//昵称
            password:'123456',//初始密码有默认值
            //belongOrg:'',//所属部门
            idCard:'',//身份证
            mobilePhone:'',//移动电话
            officePhone:'',//办公电话
            email: '',//邮箱
            gender:'',//性别
            job:'',//职务
            hiredate:'',//入职日期
            description:'',//说明
            remarks:'',//备注

        }
    },
    methods: {
        getTime(date){
            this.hiredate = date;
        },
        getNodes(data) {
            console.log(data);
        },
        getChecked(data) {
            console.log(data);
        },
        conformEvent() {
            if(operate==1){
                addObj.addOk(function(){
                    var data={
                        "url":serverPath + "/user/add",
                        "jsonData":{
                            id:useAdd.id,//工号
                            name:useAdd.name,//姓名
                            nickname:useAdd.nickname,//昵称
                            password :useAdd.password,//初始密码有默认值
                            //belongOrg:useAdd.belongOrg,//所属部门
                            idCard:useAdd.idCard,//身份证
                            mobilePhone:useAdd.mobilePhone,//移动电话
                            officePhone:useAdd.officePhone,//办公电话
                            email:useAdd.email,//邮箱
                            gender:useAdd.gender,//性别
                            job :useAdd.job,//职务
                            hiredate:useAdd.hiredate,//入职日期
                            description:useAdd.description,//说明
                            remarks:useAdd.remarks,//备注
                        },
                        "obj":useAdd,
                        "showMsg":true
                    };
                    gmpAjax.showAjax(data,function(res){
                        console.log(res);
                        //分页跳回到第一页
                        //basRight.searchRight();
                        ibcpLayer.Close(divIndex);
                    })
                })
            }
            else if(operate==2){
                editObj.editOk(function(){
                    var data={
                        "url":serverPath + "/user/modify",
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
                        "obj":useAdd,
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
        },
        cancel() {
            ibcpLayer.Close(divIndex);
        },
    },

})