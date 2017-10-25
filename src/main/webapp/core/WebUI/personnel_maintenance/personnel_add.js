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
            config: {
                // 设置清空按钮
                clearable: false,
                // 显示复选框
                checkbox: false,
                // 默认展开
//                  expanded: [324],
                // 展开所有节点
                expandedAll: true,
                // 默认选中项 当 checkbox 为 true 时  编辑时可以用
                // checked: [11],
                defaultProps: {
                    // 树节点显示文字
                    label: 'orgName',
                    // 节点id
                    key: "orgId",
                    // 父节点信息
                    parent: "orgPid"
                },
                // 获取数据接口
                url: serverPath + "/baseOrg/queryPage",
            },
            id:'',//工号
            name: '',//姓名
            nickname:'',//昵称
            password:'123456',//初始密码有默认值
            belongOrg:'',//所属部门
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
        //点击他的时候
        getNodes(data, id, name) {
            console.log(data);
            console.log("选中行id为：" + id);
            console.log("选中行名称为：" + name);
        },
        //确认这个节点的时候
        getNodeId(id) {
            //确认点击的这个ID
            this.belongOrg=id;
        },
        //复选框选中的时候
        getChecked(data, id, name, flag) {
            console.log(data);
            console.log("选中节点名称为：" + name);
            console.log("选中节点 id 为：" + id);
            console.log("选中标识为：" + flag);
        },
        //清除框的时候
        clear(id, name) {
            this.belongOrg=id;
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
                            belongOrg:useAdd.belongOrg,//所属部门
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
                        //刷新树节点
                        if(left.activeName=='first'){
                            left.$refs.organsizeTree.loadData();
                        }else if(left.activeName=='second'){
                            leftBottom.$refs.roleTree.loadData();
                        }
                        ibcpLayer.Close(divIndex);
                    })
                })
            }
            else if(operate==2){
                editObj.editOk(function(){
                    console.log(right.userowId)
                    var data={
                        "url":serverPath + "/user/modify",
                        "jsonData":{
                            rowId:right.userowId,
                            id:useAdd.id,//工号
                            name:useAdd.name,//姓名
                            nickname:useAdd.nickname,//昵称
                            password :useAdd.password,//初始密码有默认值
                            belongOrg:useAdd.belongOrg,//所属部门
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
                        //刷新这个节点 跳回第一页
                        if(left.activeName=='first'){
                            right.searchMore()
                        }else if(left.activeName=='second'){
                            right.searchUserMore();
                        }
                        ibcpLayer.Close(divIndex);
                    })
                });
            }
        },
        cancel() {
            ibcpLayer.Close(divIndex);
        },
    },
    updated(){

    }

})