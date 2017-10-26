/**
 * Created by admin on 2017/10/23.
 */

//用户查询角色
var searchUserRole=serverPath+"/roleDistribute/queryUserRole";
//用户组查询角色
var searchUserGroupRole=serverPath+"/roleDistribute/queryUserGroupRole";

//角色查看用户
var searchRoleUser=serverPath+"/roleDistribute/queryRoleUserInfo";
//角色组查看用户
var searchRoleUserGroup=serverPath+"/roleDistribute/queryRoleUserGroup";

//查看所有角色
var allRole=serverPath+"/role/queryPage";

//查看所有用户
var allUser=serverPath+"/user/queryPage";

//查看所有用户组
var allUserGroup=serverPath+"/userGroup/queryPage";

//用户分配角色
userDeverRole=serverPath+"/roleDistribute/addUserRole";

//用户组分配角色
userGroupDeverRole=serverPath+"/roleDistribute/addUserGroupRole";

//角色分配用户
roleDeverUser=serverPath+"/roleDistribute/addRoleUser";

//角色分配用户组
roleDeverUserGroup=serverPath+"/roleDistribute/addRoleUserGroup";


gmp_onload=function(){
    //左边单选框
    left=new Vue({
        el:'#left',
        data:getData.dataObj({
            radio:'1',//默认选中第一个单选框
        }),
        updated(){
            //当选择用户分配时
            if(this.radio=='1'){
                right.useLook=true;
                distributeUser.roleCould=false;
            }
            //当选择角色分配时
            else if(this.radio=='2'){
                right.useLook=false;
                distributeUser.roleCould=true;
            }
        },
    })
    //用户分配角色
    right=new Vue({
        "el": "#right",
        data: getData.dataObj({
            select: '',//查询类
            useLook:true,//用户用户组分配能否看见
            searchDepart: {//所属部门tree的参数
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
                //console.log(right.activeName)
            },
            //搜索框查询
            searchUser(){
                //用户下的角色
                right.searchUserRoleFirst()
            },
            //确认这个节点的时候
            getNodeId(data) {
                console.log(data);
                //确认点击的这个ID
                this.belongOrg=data.rowId;
            },
            //清除框的时候
            clear(id, name) {
                this.belongOrg=id;
            },
            headSort(column){
                if(this.select==''){//需要search
                    if(right.belongOrg==undefined){
                        right.belongOrg='';
                    }
                    var headDate={
                        search:this.input,
                        belongOrg:right.belongOrg,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        order:'',
                    }
                    querySearch.headSorts(searchUserRole,headDate,column,this,function(res){})
                }else{  //不需要search
                    if(right.belongOrg==undefined){
                        right.belongOrg='';
                    }
                    var param={};
                    param[right.select] = right.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        belongOrg:right.belongOrg,
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        order:'',
                    }
                    querySearch.headSorts(searchUserRole,headDate,column,this,function(res){})
                }
            },
            //点击这一行
            currentChange(row, event, column){
                console.log(row);
                //这一行的ID数据
                this.userRowId=row.rowIds
               //获得所属角色的key
                this.rightRowId=row.roleRowIds
            },
            handleSizeChange(val){
                //不跳回第一页
                this.pageSize=val;
                right.searchUserRole()
            },
            handleCurrentChange(val){
                this.pageNum=val;
                //不跳回第一页
                right.searchUserRole()
            },
            //查询用户分配下的角色  跳回第一页
            searchUserRoleFirst(){
                if(this.select==''){//需要search
                    if(right.belongOrg==undefined){
                        right.belongOrg='';
                    }
                    var headDate={
                        search:this.input,
                        belongOrg:right.belongOrg,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchUserRole,headDate,this,function(res){
                    })
                }else{  //不需要search
                    if(right.belongOrg==undefined){
                        right.belongOrg='';
                    }
                    var param={};
                    param[right.select] = right.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        belongOrg:right.belongOrg,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchUserRole,headDate,this,function(res){

                    })
                }
            },
            //查询用户分配下的角色  不跳回第一页
            searchUserRole(){
                if(this.select==''){//需要search
                    if(right.belongOrg==undefined){
                        right.belongOrg='';
                    }
                    var headDate={
                        search:this.input,
                        belongOrg:right.belongOrg,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchUserRole,headDate,this,function(res){
                    })
                }else{  //不需要search
                    if(right.belongOrg==undefined){
                        right.belongOrg='';
                    }
                    var param={};
                    param[right.select] = right.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        belongOrg:right.belongOrg,
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchUserRole,headDate,this,function(res){

                    })
                }
            },
            //查看
            searchEvent(){
                operate = 1;
                var htmlUrl = 'assigment-add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, '查看权限', '480px', '480px',function(){

                });
            },
        },
        created(){
            $(document).ready(function () {
                right.leftHeight = $(window).height() - 270;
            });
            $(window).resize(function () {
                right.leftHeight = $(window).height() - 270;
            });
            this.searchUserRoleFirst();
            //查询所有角色
            querySearch.easySearch(allRole,function(res){
                right.allRoleData=res.resp.content.data.result;
            })
        }
    })
    //用户组分配角色
    rightBlock=new Vue({
        el:'#rightBlock',
        template:'#tempBlock',
        data: getData.dataObj({
            select: '',//查询类
            searchDepart: {//所属部门tree的参数
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
            //搜索框查询
            searchUser(){
                //用户组下的下的角色
                rightBlock.searchUserGroupRoleFirst();
            },
            //确认这个节点的时候
            getNodeId(data) {
                console.log(data);
                //确认点击的这个ID
                this.belongOrg=data.rowId;
            },
            //清除框的时候
            clear(id, name) {
                this.belongOrg=id;
            },
            headSort(column){
                if(this.select==''){//需要search
                    if(rightBlock.belongOrg==undefined){
                        rightBlock.belongOrg='';
                    }
                    var headDate={
                        search:this.input,
                        belongOrg:rightBlock.belongOrg,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        order:'',
                    }
                    querySearch.headSorts(searchUserGroupRole,headDate,column,this,function(res){})
                }else{  //不需要search
                    if(rightBlock.belongOrg==undefined){
                        rightBlock.belongOrg='';
                    }
                    var param={};
                    param[rightBlock.select] = rightBlock.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        belongOrg:right.belongOrg,
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        order:'',
                    }
                    querySearch.headSorts(searchUserGroupRole,headDate,column,this,function(res){})
                }
            },
            //点击这一行
            currentChange(row, event, column){
                console.log(row);
                //这一行的ID数据
                this.userRowId=row.rowIds
                //获得所属角色的key
                this.rightRowId=row.roleRowIds
            },
            handleSizeChange(val){
                //不跳回第一页
                this.pageSize=val;
                rightBlock.searchUserGroupRole();

            },
            handleCurrentChange(val){
                this.pageNum=val;
                //不跳回第一页
                rightBlock.searchUserGroupRole();

            },
            //查找用户组分配下的角色  跳回第一页
            searchUserGroupRoleFirst(){
                if(this.select==''){//需要search
                    var headDate={
                        search:rightBlock.input,
                        belongOrg:rightBlock.belongOrg,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchUserGroupRole,headDate,this,function(res){
                    })
                }else{  //不需要search
                    var param={};
                    param[rightBlock.select] = rightBlock.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        belongOrg:rightBlock.belongOrg,
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchUserGroupRole,headDate,this,function(res){

                    })
                }
            },
            //查找用户组分配下的角色  不跳回第一页
            searchUserGroupRole(){
                if(this.select==''){//需要search
                    if(right.belongOrg==undefined){
                        right.belongOrg='';
                    }
                    var headDate={
                        search:this.input,
                        belongOrg:right.belongOrg,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchUserRole,headDate,this,function(res){})
                }else{  //不需要search
                    if(right.belongOrg==undefined){
                        right.belongOrg='';
                    }
                    var param={};
                    param[right.select] = right.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        belongOrg:right.belongOrg,
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchUserRole,headDate,this,function(res){})
                }
            },
            //查看
            searchEvent(){
                operate = 2;
                var htmlUrl = 'assigment-add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, '查看权限', '480px', '480px',function(){

                });
            },
        },
        created(){
            $(document).ready(function () {
                rightBlock.leftHeight = $(window).height() - 270;
            });
            $(window).resize(function () {
                rightBlock.leftHeight = $(window).height() - 270;
            });
            this.searchUserGroupRoleFirst();
            //一加载就查询所有角色
            querySearch.easySearch(allRole,function(res){
                rightBlock.allRoleData=res.resp.content.data.result;
            })
        }
    })
    //角色分配用户
    distributeUser=new Vue({
        "el": "#distributeUser",
        template:'#distribute',
        data: getData.dataObj({
            select: '',//查询类
            roleCould:false,//角色分配用户能否看见
            activeName:'first',//tab页默认选中第一个
        }),
        methods: {
            //tab页点击交换
            handleClick(){
            },
            //搜索框查询
            searchRole(){
                //角色下的用户
                distributeUser.searchRoleUser();
            },
            headSort(column){
                if(this.select==''){//需要search
                    var headDate={
                        search:this.input,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        order:'',
                    }
                    querySearch.headSorts(searchRoleUser,headDate,column,this,function(res){})
                }else{  //不需要search
                    var param={};
                    param[rightBlock.select] = rightBlock.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        order:'',
                    }
                    querySearch.headSorts(searchRoleUser,headDate,column,this,function(res){})
                }
            },
            //点击这一行
            currentChange(row, event, column){
                console.log(row);
                this.userRowId=row.rowIds
                //获得所属角色的key
                this.rightRowId=row.userRowIds
            },
            handleSizeChange(val){
                //不跳回第一页
                this.pageSize=val;
                distributeUser.searchRoleUser();
            },
            handleCurrentChange(val){
                this.pageNum=val;
                //不跳回第一页
                distributeUser.searchRoleUser();

            },
            //查找角色下的用户  跳回第一页
            searchRoleUserFirst(){
                if(this.select==''){//需要search
                    var headDate={
                        search:distributeUser.input,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRoleUser,headDate,this,function(res){})
                }else{  //不需要search
                    var param={};
                    param[rightBlock.select] = rightBlock.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRoleUser,headDate,this,function(res){})
                }
            },
            //查找角色下的用户  不跳回第一页
            searchRoleUser(){
                if(this.select==''){//需要search
                    var headDate={
                        search:this.input,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRoleUser,headDate,this,function(res){

                    })
                }else{  //不需要search
                    var param={};
                    param[distributeUser.select] = distributeUser.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRoleUser,headDate,this,function(res){})
                }
            },
            //查看
            searchEvent(){
                operate = 3;
                var htmlUrl = 'assigment-add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, '查看权限', '480px', '480px',function(){

                });
            },
        },
        created(){
            $(document).ready(function () {
                distributeUser.leftHeight = $(window).height() - 270;
            });
            $(window).resize(function () {
                distributeUser.leftHeight = $(window).height() - 270;
            });
            this.searchRoleUserFirst();
            querySearch.easySearch(allUser,function(res){
                console.log(res);
                distributeUser.allRoleData=res.resp.content.data.result;
            })
        }
    })
    //角色分配用户组
    rightUserBlockRole=new Vue({
        el:'#rightBlockRole',
        template:'#rightUserBlockRole',
        data: getData.dataObj({
            select: '',//查询类
            activeName:'first',//tab页默认选中第一个
        }),
        methods: {
            //tab页点击交换
            handleClick(){
            },
            searchDepart(){
                //用户下的角色
                rightUserBlockRole.searchRoleUserGroupFirst()
            },
            //搜索框查询
            searchRole(){
                //角色下的用户
                rightUserBlockRole.searchRoleUserGroup();
            },
            headSort(column){
                if(this.select==''){//需要search
                    var headDate={
                        search:this.input,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        order:'',
                    }
                    querySearch.headSorts(searchRoleUserGroup,headDate,column,this,function(res){})
                }else{  //不需要search
                    var param={};
                    param[rightUserBlockRole.select] = rightUserBlockRole.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        order:'',
                    }
                    querySearch.headSorts(searchRoleUserGroup,headDate,column,this,function(res){})
                }
            },
            //点击这一行
            currentChange(row, event, column){
                console.log(row);
                this.userRowId=row.rowIds
                //获得所属角色的key
                this.rightRowId=row.userGroupRowIds
            },
            handleSizeChange(val){
                //不跳回第一页
                this.pageSize=val;
                rightUserBlockRole.searchRoleUserGroup();
            },
            handleCurrentChange(val){
                this.pageNum=val;
                //不跳回第一页
                rightUserBlockRole.searchRoleUserGroup();
            },
            //查找角色下的用户组  跳回第一页
            searchRoleUserGroupFirst(){
                if(this.select==''){//需要search
                    var headDate={
                        search:distributeUser.input,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRoleUserGroup,headDate,this,function(res){})
                }else{  //不需要search
                    var param={};
                    param[rightUserBlockRole.select] = rightUserBlockRole.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        param:params,
                        pageNum:1,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRoleUserGroup,headDate,this,function(res){})
                }
            },
            //查找角色下的用户组  不跳回第一页
            searchRoleUserGroup(){
                if(this.select==''){//需要search
                    var headDate={
                        search:this.input,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRoleUserGroup,headDate,this,function(res){})
                }else{  //不需要search
                    var param={};
                    param[distributeUser.select] = distributeUser.input;
                    var params=JSON.stringify(param);
                    var headDate={
                        param:params,
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                    }
                    querySearch.searchResourceFirst(searchRoleUserGroup,headDate,this,function(res){})
                }
            },  //查看
            searchEvent(){
                operate = 4;
                var htmlUrl = 'assigment-add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, '查看权限', '480px', '480px',function(){});
            },
        },
        created(){
            $(document).ready(function () {
                rightUserBlockRole.leftHeight = $(window).height() - 270;
            });
            $(window).resize(function () {
                rightUserBlockRole.leftHeight = $(window).height() - 270;
            });
            this.searchRoleUserGroupFirst();
            querySearch.easySearch(allUserGroup,function(res){
                console.log(res);
                rightUserBlockRole.allRoleData=res.resp.content.data.result;
            })
        }
    })

}