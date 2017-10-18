/**
 * Created by admin on 2017/10/10.
 */
var basTop;
var left;
var leftBottom;
var right;
//右侧查询接口
var searchMore=serverPath + "/user/queryPage";
//左侧查右侧查询接口
//var searchLeftMore=serverPath + "/user/queryByOrg";
//编辑查询
var editMore=serverPath + "/user/queryBySpecify";
//删除
var deleteMore=serverPath + "/user/delete";
//批量删除
var deleteALL=serverPath + "/user/deleteBatch";
//重置密码
var resetPass=serverPath + "/user/resetPasswordBatch";

//失效
var invalidUser=serverPath + "/user/outOfUseBatch";
//启用
var enableUser=serverPath + "/user/inUseBatch";
//解锁
var unlockUser=serverPath + "/user/unLock";
//锁定
var lockUser=serverPath + "/user/lock";

gmp_onload=function(){
    basTop = new Vue({
        el: '#basTop',
        data: {
            addOpe: false,
            addAttr: false,
            takeEffect: false,
            change: false,
        },
        methods: {
            //新增人员信息
            addEvent() {
                operate = 1;
                var htmlUrl = 'personnel_add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 添加人员信息', '50%', '98%',function(){

                });
            },
            //导出选择版本
            chooseVersion(){
                var htmlUrl = 'chooseVersion.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, '选择版本', '300px', '200px',function(){

                });

            },
            //导入
            leadRow(){
                var htmlUrl = 'import_user.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, '导入页面', '500px', '500px',function(){

                });
            },
            //导出
            exportRow(){
                var htmlUrl = 'personnel_export.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, '导出Excel', '800px', '400px', function () {


                });
            },
            //重置密码
            resetPass(){
                restartPassword.restartPassword(function(){
                    var data={
                        "url":resetPass,
                        "jsonData":{rowId:right.userId},
                        "obj":basTop,
                        "showMsg":true
                    };
                    gmpAjax.showAjax(data,function(res){
                        console.log(res);
                        //分页跳回到第一页
                        //basLeft.searchLeft();
                    })
                })
            },
            //批量删除
            deleteAll(){
                deleteObj.del(function(){
                    var data={
                        "url":deleteALL,
                        "jsonData":{rowId:right.userId},
                        "obj":basTop,
                        "showMsg":true
                    };
                    gmpAjax.showAjax(data,function(res){
                        //分页跳回到第一页
                        console.log(res);
                        //basLeft.searchLeft();
                    })
                })
            },
            //失效
            Invalid(){
                Invalid.Invalid(function(){
                    var data={
                        "url":invalidUser,
                        "jsonData":{rowId:right.userId},
                        "obj":basTop,
                        "showMsg":true
                    };
                    gmpAjax.showAjax(data,function(res){
                        console.log(res);
                        //basLeft.searchLeft();
                    })
                })
            },
            //启用
            Enable(){
                Enable.Enable(function(){
                    var data={
                        "url":enableUser,
                        "jsonData":{rowId:right.userId},
                        "obj":basTop,
                        "showMsg":true
                    };
                    gmpAjax.showAjax(data,function(res){
                        //basLeft.searchLeft();
                        console.log(res);
                    })
                })
            },
        }
    });

    left=new Vue({
        el:'#left',
        data:getData.dataObj({
            config: {
                // 显示复选框
                checkbox: false,
                // 默认展开  id
                expanded: [324],
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
                url: serverPath + "/baseOrg/queryPage"
            },
            activeName:'first',
        }),
        methods:{
            getNodes(data){
                //console.log(data);
                this.rowId=data.rowId;
                right.searchMore();
            },
            //复选框点击
            getChecked(){

            },

            //tab页点击交换
            handleClick(){

            }

        },
        create(){
            $(document).ready(function () {
                right.leftHeight = $(window).height() - 158;
            });
            $(window).resize(function () {
                right.leftHeight = $(window).height() - 158;
            });
        }
    })

    leftBottom=new Vue({
        el:'#basRightTop',
        template:'#tempBlock',
        data:getData.dataObj({
            data: [{
                label: '一级 11',
                children: [{
                    label: '二级 11-11',
                    children: [{
                        label: '三级 11-11-11'
                    }]
                }]
            }, {
                label: '一级 22',
                children: [{
                    label: '二级 22-11',
                    children: [{
                        label: '三级 22-11-11'
                    }]
                }, {
                    label: '二级 22-22',
                    children: [{
                        label: '三级 22-22-11'
                    }]
                }]
            }, {
                label: '一级 3',
                children: [{
                    label: '二级 3-1',
                    children: [{
                        label: '三级 3-1-1'
                    }]
                }, {
                    label: '二级 3-2',
                    children: [{
                        label: '三级 3-2-1'
                    }]
                }],
            }],
            defaultProps: {
                children: 'children',
                label: 'label'
            },
            activeName:'first',
        }),
        methods:{
            handleNodeClick(data){
                //console.log(data);
            },
            handleClick(){

            }

        }
    })

    right=new Vue({
        "el": "#right",
        data: getData.dataObj({
            select: '',//查询类
            chooseState: '',//状态
            couldLook:false,//状态能否被看见
        }),
        methods: {
            headSort(column){
                //列头排序
                // pagingObj.headSort(qurUrl,this.resInput,this.pageSize,this.pageNum,column,this);

            },
            //点击这一行
            currentChange(row, event, column){
                console.log(row);
                this.rightRowId=row.rowId
            },
            //选择复选框
            selectRow(selection, row){
                console.log(selection)
                right.userId=[];//多选的用户ID组
                $.each(selection,function(i,item){
                    right.userId.push(item.rowId)

                })
                console.log( right.userId)
            },
            //复选框全选
            selectAllRow(selection){
                right.userId=[];//多选的用户ID组
                $.each(selection,function(i,item){
                    right.userId.push(item.rowId)
                })
            },
            showMore(){
                this.couldLook=true;
            },
            handleSizeChange(val){
                this.pageSize=val;
            },
            handleCurrentChange(val){
                this.pageNum=val;
            },
            //查询
            searchMore(){
                if(this.select==''){//需要search
                    var param={
                        belongOrg:left.rowId
                    }
                    if(this.chooseState==''){//不需要状态
                        var params=JSON.stringify(param)
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    querySearch.needSearch(searchMore,this.input,params,this,function(res){
                        var data=res.resp.content.data;
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }else{  //不需要search
                    var param={
                        belongOrg:left.rowId
                    }
                    param[this.select] = this.input;
                    if(this.chooseState==''){//不需要状态
                        var params=JSON.stringify(param)
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    querySearch.uneedSearch(searchMore,params,this,function(res){
                        var data=res.resp.content.data;
                        console.log(data);
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }
            },
            //默认选中变颜色
            FindRFirstDate(row){
                // console.log(row)
                this.$refs.tableData.setCurrentRow(row);
            },
            //编辑
            editEvent(){
                operate = 2;
                var htmlUrl = 'personnel_add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 编辑人员信息', '600px', '660px',function(){
                    //调用接口
                    var data={
                        "url":editMore,
                        "jsonData":{rowId:right.rightRowId},
                        "obj":right,
                        "showMsg":true
                    };
                    gmpAjax.showAjax(data,function(res){
                        //编辑拿到的数据
                        var data=res.data[0];
                        console.log(res)
                        useAdd.id=data.id;//工号
                        useAdd.name=data.name;//姓名
                        useAdd.nickname=data.nickname;//昵称
                        useAdd.password=data.password;//初始密码有默认值
                        useAdd.belong.checked=data.belongOrg;//所属部门
                        useAdd.idCard=data.idCard;//身份证
                        useAdd.mobilePhone=data.mobilePhone;//移动电话
                        useAdd.officePhone=data.officePhone//办公电话
                        useAdd.email=data.email;//邮箱
                        useAdd.gender=data.gender;//性别
                        useAdd.job=data.job;//职务
                        useAdd.hiredate=data.hiredate;//入职日期
                        useAdd.description=data.description;//说明
                        useAdd.remarks=data.remarks;//备注
                    })
                });
            },
            //删除
            deleteEvent(){
                deleteObj.del(function(){
                    var data={
                        "url":deleteMore,
                        "jsonData":{rowId:right.rightRowId},
                        "obj":right,
                        "showMsg":true
                    };
                    gmpAjax.showAjax(data,function(res){
                        //分页跳回到第一页
                        //basLeft.searchLeft();
                    })
                })
            },
            //解锁
            unlockEvent(){
                unlockEvent.unlockEvent(function(){
                    var data={
                        "url":unlockUser,
                        "jsonData":{rowId:right.rightRowId},
                        "obj":basTop
                    };
                    gmpAjax.showAjax(data,function(res){
                        //basLeft.searchLeft();
                        console.log(res);
                    })
                })
            },
            //锁定
            lockEvent(){
                lockEvent.lockEvent(function(){
                    var data={
                        "url":lockUser,
                        "jsonData":{rowId:right.rightRowId},
                        "obj":basTop
                    };
                    gmpAjax.showAjax(data,function(res){
                        //basLeft.searchLeft();
                        console.log(res);
                    })
                })
            }
        },
        created(){
            $(document).ready(function () {
                right.leftHeight = $(window).height() - 206;
            });
            $(window).resize(function () {
                right.leftHeight = $(window).height() - 206;
            });

            $(document).ready(function () {
                var height = $(window).height()-50;
                $("#treeLeft").height(height);

            });
            $(window).resize(function () {
                var height1 = $(window).height()-50;
                $("#treeLeft").height(height1);
            });
        }
    })
}