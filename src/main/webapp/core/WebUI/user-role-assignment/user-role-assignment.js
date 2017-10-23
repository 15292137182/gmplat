/**
 * Created by admin on 2017/10/23.
 */


//查用户组信息
var searchGroup=serverPath + "/userGroup/queryById";
//查用户组的用户信息
var searchGroupUser=serverPath + "/userGroup/queryUserGroupUser";
//新增用户组
var addGroup=serverPath + "/userGroup/add";
//修改用户组
var modifyGroup=serverPath + "/userGroup/modify";
//删除用户组
var deleteGroup=serverPath + "/userGroup/logicDelete";
//删除用户组下的人员
var deleteGroupUser=serverPath + "/userGroup/deleteGroupUsers";
//添加组人员
//var addGroupUser=serverPath + "/userGroup/delete";

gmp_onload=function(){
    left=new Vue({
        el:'#left',
        data:getData.dataObj({
            radio:'1',//默认选中第一个单选框
        }),
        methods:{

        },
    })
    right=new Vue({
        "el": "#right",
        data: getData.dataObj({
            select: '',//查询类
            couldLook:false,//所属部门能否被看见
            srarchDepart: {//所属部门tree的参数
                clearable: false,  // 设置清空按钮
                checkbox: false, // 显示复选框
                expandedAll: true, // 展开所有节点
                checked: [],  // 默认选中项
                defaultProps: {
                    label: 'orgName', // 树节点显示文字
                    key: "orgId",  // 节点id
                    parent: "orgPid"  // 父节点信息
                },
                url: serverPath + "/baseOrg/queryPage", // 获取数据接口
            },
            activeName:'first',//tab页默认选中第一个
        }),
        methods: {
            //tab页点击交换
            handleClick(){
                //判断点击的是第一还是第二
                //console.log(left.activeName)
            },
            //搜索框查询
            searchUser(){
                //if(left.activeName=='first'){
                //    right.searchMore()
                //}else if(left.activeName=='second'){
                //    right.searchUserMore();
                //}
            },
            //复杂查询条件是否可见
            showMore(){
                this.couldLook=true;
            },
            //确认这个节点的时候
            getNodeId(id) {
                //确认点击的这个ID
                this.belongSector=id;
            },
            //清除框的时候
            clear(id, name) {
                //this.belongSector=id;
            },
            headSort(column){
                if(left.activeName=='first'){
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
                        var headDate={
                            search:this.input,
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
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
                        var headDate={
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
                    }
                }else if(left.activeName=='second'){
                    if(this.select==''){//需要search
                        var param={};
                        if(this.chooseState==''){//不需要状态
                            params='';
                        }else{//需要状态
                            param["status"] = this.chooseState;
                            var params=JSON.stringify(param)
                        }
                        var headDate={
                            rowId:leftBottom.userRowId,
                            search:this.input,
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
                    }else{  //不需要search
                        var param={};
                        param[this.select] = this.input;
                        if(this.chooseState==''){//不需要状态
                            var params=JSON.stringify(param)
                        }else{//需要状态
                            param["status"] = this.chooseState;
                            var params=JSON.stringify(param)
                        }
                        var headDate={
                            rowId:leftBottom.userRowId,
                            search:this.input,
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
                    }
                }

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
            handleSizeChange(val){
                //不跳回第一页
                this.pageSize=val;
                if(left.activeName=='first'){
                    right.searchMore()
                }else if(left.activeName=='second'){
                    right.searchUserMore();
                }
            },
            handleCurrentChange(val){
                this.pageNum=val;
                //不跳回第一页
                if(left.activeName=='first'){
                    right.searchMoreFirst()
                }else if(left.activeName=='second'){
                    right.searchUserMoreFirst();
                }
            },

            //查询组织机构下的用户  跳回第一页
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
                    var headDate={
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }

                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

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

                    var headDate={
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

                    })
                }
            },
            //查找角色下的用户  跳回第一页
            searchUserMore(){
                if(this.select==''){//需要search
                    var param={};
                    if(this.chooseState==''){//不需要状态
                        params='';
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }else{  //不需要search
                    var param={};
                    param[this.select] = this.input;
                    if(this.chooseState==''){//不需要状态
                        var params=JSON.stringify(param)
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }
            },
            //查询组织机构下的用户  不跳回第一页
            searchMoreFirst(){
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
                    var headDate={
                        search:this.input,
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }

                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

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

                    var headDate={
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

                    })
                }
            },
            //查找角色下的用户  不跳回第一页
            searchUserMoreFirst(){
                if(this.select==''){//需要search
                    var param={};
                    if(this.chooseState==''){//不需要状态
                        params='';
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }else{  //不需要search
                    var param={};
                    param[this.select] = this.input;
                    if(this.chooseState==''){//不需要状态
                        var params=JSON.stringify(param)
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
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
                        console.log(data)
                        right.userowId=data.rowId;//用户的ID号码
                        useAdd.id=data.id;//工号
                        useAdd.name=data.name;//姓名
                        useAdd.nickname=data.nickname;//昵称
                        useAdd.password=data.password;//初始密码有默认值
                        useAdd.config.checked=data.belongOrg;//所属部门
                        useAdd.belongOrg=data.belongOrg;//所属部门 提交的时候要的
                        useAdd.idCard=data.idCard;//身份证
                        useAdd.mobilePhone=data.mobilePhone;//移动电话
                        useAdd.officePhone=data.officePhone//办公电话
                        useAdd.email=data.email;//邮箱
                        useAdd.gender=data.gender;//性别
                        useAdd.job=data.job;//职务
                        useAdd.hiredate=data.hiredate;//入职日期
                        useAdd.description=data.description;//说明
                        useAdd.remarks=data.remarks;//备注
                        console.log( useAdd.config.checked)

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
                        if(left.activeName=='first'){
                            right.searchMore()
                        }else if(left.activeName=='second'){
                            right.searchUserMore();
                        }
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
                        if(left.activeName=='first'){
                            right.searchMore()
                        }else if(left.activeName=='second'){
                            right.searchUserMore();
                        }
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
                        if(left.activeName=='first'){
                            right.searchMore()
                        }else if(left.activeName=='second'){
                            right.searchUserMore();
                        }
                    })
                })
            }
        },
        created(){
            $(document).ready(function () {
                right.leftHeight = $(window).height() - 270;
            });
            $(window).resize(function () {
                right.leftHeight = $(window).height() - 270;
            });

        }
    })


    rightBlock=new Vue({
        el:'#rightBlock',
        template:'#tempBlock',
        data: getData.dataObj({
            select: '',//查询类
            couldLook:false,//所属部门能否被看见
            srarchDepart: {//所属部门tree的参数
                clearable: false,  // 设置清空按钮
                checkbox: false, // 显示复选框
                expandedAll: true, // 展开所有节点
                checked: [],  // 默认选中项
                defaultProps: {
                    label: 'orgName', // 树节点显示文字
                    key: "orgId",  // 节点id
                    parent: "orgPid"  // 父节点信息
                },
                url: serverPath + "/baseOrg/queryPage", // 获取数据接口
            },
        }),
        methods: {
            //搜索框查询
            searchUser(){
                //if(left.activeName=='first'){
                //    right.searchMore()
                //}else if(left.activeName=='second'){
                //    right.searchUserMore();
                //}
            },
            //复杂查询条件是否可见
            showMore(){
                this.couldLook=true;
            },
            //确认这个节点的时候
            getNodeId(id) {
                //确认点击的这个ID
                this.belongSector=id;
            },
            //清除框的时候
            clear(id, name) {
                //this.belongSector=id;
            },
            headSort(column){
                if(left.activeName=='first'){
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
                        var headDate={
                            search:this.input,
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
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
                        var headDate={
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
                    }
                }else if(left.activeName=='second'){
                    if(this.select==''){//需要search
                        var param={};
                        if(this.chooseState==''){//不需要状态
                            params='';
                        }else{//需要状态
                            param["status"] = this.chooseState;
                            var params=JSON.stringify(param)
                        }
                        var headDate={
                            rowId:leftBottom.userRowId,
                            search:this.input,
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
                    }else{  //不需要search
                        var param={};
                        param[this.select] = this.input;
                        if(this.chooseState==''){//不需要状态
                            var params=JSON.stringify(param)
                        }else{//需要状态
                            param["status"] = this.chooseState;
                            var params=JSON.stringify(param)
                        }
                        var headDate={
                            rowId:leftBottom.userRowId,
                            search:this.input,
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
                    }
                }

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
            handleSizeChange(val){
                //不跳回第一页
                this.pageSize=val;
                if(left.activeName=='first'){
                    right.searchMore()
                }else if(left.activeName=='second'){
                    right.searchUserMore();
                }
            },
            handleCurrentChange(val){
                this.pageNum=val;
                //不跳回第一页
                if(left.activeName=='first'){
                    right.searchMoreFirst()
                }else if(left.activeName=='second'){
                    right.searchUserMoreFirst();
                }
            },

            //查询组织机构下的用户  跳回第一页
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
                    var headDate={
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }

                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

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

                    var headDate={
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

                    })
                }
            },
            //查找角色下的用户  跳回第一页
            searchUserMore(){
                if(this.select==''){//需要search
                    var param={};
                    if(this.chooseState==''){//不需要状态
                        params='';
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }else{  //不需要search
                    var param={};
                    param[this.select] = this.input;
                    if(this.chooseState==''){//不需要状态
                        var params=JSON.stringify(param)
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }
            },
            //查询组织机构下的用户  不跳回第一页
            searchMoreFirst(){
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
                    var headDate={
                        search:this.input,
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }

                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

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

                    var headDate={
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

                    })
                }
            },
            //查找角色下的用户  不跳回第一页
            searchUserMoreFirst(){
                if(this.select==''){//需要search
                    var param={};
                    if(this.chooseState==''){//不需要状态
                        params='';
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }else{  //不需要search
                    var param={};
                    param[this.select] = this.input;
                    if(this.chooseState==''){//不需要状态
                        var params=JSON.stringify(param)
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
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
                        console.log(data)
                        right.userowId=data.rowId;//用户的ID号码
                        useAdd.id=data.id;//工号
                        useAdd.name=data.name;//姓名
                        useAdd.nickname=data.nickname;//昵称
                        useAdd.password=data.password;//初始密码有默认值
                        useAdd.config.checked=data.belongOrg;//所属部门
                        useAdd.belongOrg=data.belongOrg;//所属部门 提交的时候要的
                        useAdd.idCard=data.idCard;//身份证
                        useAdd.mobilePhone=data.mobilePhone;//移动电话
                        useAdd.officePhone=data.officePhone//办公电话
                        useAdd.email=data.email;//邮箱
                        useAdd.gender=data.gender;//性别
                        useAdd.job=data.job;//职务
                        useAdd.hiredate=data.hiredate;//入职日期
                        useAdd.description=data.description;//说明
                        useAdd.remarks=data.remarks;//备注
                        console.log( useAdd.config.checked)

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
                        if(left.activeName=='first'){
                            right.searchMore()
                        }else if(left.activeName=='second'){
                            right.searchUserMore();
                        }
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
                        if(left.activeName=='first'){
                            right.searchMore()
                        }else if(left.activeName=='second'){
                            right.searchUserMore();
                        }
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
                        if(left.activeName=='first'){
                            right.searchMore()
                        }else if(left.activeName=='second'){
                            right.searchUserMore();
                        }
                    })
                })
            }
        },
        created(){
            $(document).ready(function () {
                rightBlock.leftHeight = $(window).height() - 270;
            });
            $(window).resize(function () {
                rightBlock.leftHeight = $(window).height() - 270;
            });

        }
    })


    distributeUser=new Vue({
        "el": "#distributeUser",
        template:'#distribute',
        data: getData.dataObj({
            select: '',//查询类
            couldLook:false,//所属部门能否被看见
            srarchDepart: {//所属部门tree的参数
                clearable: false,  // 设置清空按钮
                checkbox: false, // 显示复选框
                expandedAll: true, // 展开所有节点
                checked: [],  // 默认选中项
                defaultProps: {
                    label: 'orgName', // 树节点显示文字
                    key: "orgId",  // 节点id
                    parent: "orgPid"  // 父节点信息
                },
                url: serverPath + "/baseOrg/queryPage", // 获取数据接口
            },
            activeName:'first',//tab页默认选中第一个
        }),
        methods: {
            //tab页点击交换
            handleClick(){
                //判断点击的是第一还是第二
                //console.log(left.activeName)
            },
            //搜索框查询
            searchUser(){
                //if(left.activeName=='first'){
                //    right.searchMore()
                //}else if(left.activeName=='second'){
                //    right.searchUserMore();
                //}
            },
            //复杂查询条件是否可见
            showMore(){
                this.couldLook=true;
            },
            //确认这个节点的时候
            getNodeId(id) {
                //确认点击的这个ID
                this.belongSector=id;
            },
            //清除框的时候
            clear(id, name) {
                //this.belongSector=id;
            },
            headSort(column){
                if(left.activeName=='first'){
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
                        var headDate={
                            search:this.input,
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
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
                        var headDate={
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
                    }
                }else if(left.activeName=='second'){
                    if(this.select==''){//需要search
                        var param={};
                        if(this.chooseState==''){//不需要状态
                            params='';
                        }else{//需要状态
                            param["status"] = this.chooseState;
                            var params=JSON.stringify(param)
                        }
                        var headDate={
                            rowId:leftBottom.userRowId,
                            search:this.input,
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
                    }else{  //不需要search
                        var param={};
                        param[this.select] = this.input;
                        if(this.chooseState==''){//不需要状态
                            var params=JSON.stringify(param)
                        }else{//需要状态
                            param["status"] = this.chooseState;
                            var params=JSON.stringify(param)
                        }
                        var headDate={
                            rowId:leftBottom.userRowId,
                            search:this.input,
                            param:params,
                            pageNum:this.pageNum,
                            pageSize:this.pageSize,
                            order:'',
                        }
                        querySearch.headSorts(searchMore,headDate,column,this);
                    }
                }

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
            handleSizeChange(val){
                //不跳回第一页
                this.pageSize=val;
                if(left.activeName=='first'){
                    right.searchMore()
                }else if(left.activeName=='second'){
                    right.searchUserMore();
                }
            },
            handleCurrentChange(val){
                this.pageNum=val;
                //不跳回第一页
                if(left.activeName=='first'){
                    right.searchMoreFirst()
                }else if(left.activeName=='second'){
                    right.searchUserMoreFirst();
                }
            },

            //查询组织机构下的用户  跳回第一页
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
                    var headDate={
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }

                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

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

                    var headDate={
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

                    })
                }
            },
            //查找角色下的用户  跳回第一页
            searchUserMore(){
                if(this.select==''){//需要search
                    var param={};
                    if(this.chooseState==''){//不需要状态
                        params='';
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }else{  //不需要search
                    var param={};
                    param[this.select] = this.input;
                    if(this.chooseState==''){//不需要状态
                        var params=JSON.stringify(param)
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }
            },
            //查询组织机构下的用户  不跳回第一页
            searchMoreFirst(){
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
                    var headDate={
                        search:this.input,
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }

                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

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

                    var headDate={
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchMore,headDate,this,function(res){

                    })
                }
            },
            //查找角色下的用户  不跳回第一页
            searchUserMoreFirst(){
                if(this.select==''){//需要search
                    var param={};
                    if(this.chooseState==''){//不需要状态
                        params='';
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
                        if(data!=null){
                            //默认选中行
                            //this.currentChange(this.tableData[0]);
                        }
                    })
                }else{  //不需要search
                    var param={};
                    param[this.select] = this.input;
                    if(this.chooseState==''){//不需要状态
                        var params=JSON.stringify(param)
                    }else{//需要状态
                        param["status"] = this.chooseState;
                        var params=JSON.stringify(param)
                    }
                    var headDate={
                        rowId:leftBottom.userRowId,
                        search:this.input,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRole,headDate,this,function(res){
                        console.log(res);
                        var data=res.resp.content.data;
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
                        console.log(data)
                        right.userowId=data.rowId;//用户的ID号码
                        useAdd.id=data.id;//工号
                        useAdd.name=data.name;//姓名
                        useAdd.nickname=data.nickname;//昵称
                        useAdd.password=data.password;//初始密码有默认值
                        useAdd.config.checked=data.belongOrg;//所属部门
                        useAdd.belongOrg=data.belongOrg;//所属部门 提交的时候要的
                        useAdd.idCard=data.idCard;//身份证
                        useAdd.mobilePhone=data.mobilePhone;//移动电话
                        useAdd.officePhone=data.officePhone//办公电话
                        useAdd.email=data.email;//邮箱
                        useAdd.gender=data.gender;//性别
                        useAdd.job=data.job;//职务
                        useAdd.hiredate=data.hiredate;//入职日期
                        useAdd.description=data.description;//说明
                        useAdd.remarks=data.remarks;//备注
                        console.log( useAdd.config.checked)

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
                        if(left.activeName=='first'){
                            right.searchMore()
                        }else if(left.activeName=='second'){
                            right.searchUserMore();
                        }
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
                        if(left.activeName=='first'){
                            right.searchMore()
                        }else if(left.activeName=='second'){
                            right.searchUserMore();
                        }
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
                        if(left.activeName=='first'){
                            right.searchMore()
                        }else if(left.activeName=='second'){
                            right.searchUserMore();
                        }
                    })
                })
            }
        },
        created(){
            $(document).ready(function () {
                right.leftHeight = $(window).height() - 270;
            });
            $(window).resize(function () {
                right.leftHeight = $(window).height() - 270;
            });

        }
    })

}