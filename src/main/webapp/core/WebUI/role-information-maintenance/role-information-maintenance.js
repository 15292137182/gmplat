/**
 * Created by andim on 2017/10/19.
 */
/**
 * Created by admin on 2017/10/12.
 */
var basTop;
var left;
var right;
var rightBottom;
var basRightTop;
var basRight;

//查角色详细信息查询接口
var roleInformation = serverPath + "/role/queryBySpecify";

//查看角色下的权限信息
var permissionsInformation = serverPath + "/role/queryPermissions";

//查看角色下的人员信息
var personnelInformationInterface = serverPath + "/role/queryUsers";

//查看角色下的组织机构信息
var selOrganization = serverPath + "/role/queryOrgs";


//角色编辑接口
var modify = serverPath + "/role/modify";

//角色删除接口
var deleteUrl = serverPath + "/role/delete";

gmp_onload=function(){
    //顶部按钮
    basTop = new Vue({
        el: '#basTop',
        data: {
            addOpe: false,
            takeEffect: false,
            divIndex:"",
            disabled:true
        },
        methods: {
            //新增人员信息
            addEvent() {
                var htmlUrl = 'add-role-information-maintenance.html';
                this.divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 新增角色信息', '400px', '460px',function(){

                });
            },
            //删除角色信息
            orgDelete(){
                deleteObj.del(function(){
                    var data = {
                        "url":deleteUrl,
                        "jsonData":{rowId:left.rowId},
                        "obj":basTop,
                        "showMsg":true
                    }
                    gmpAjax.showAjax(data,function(res){
                        // queryData.getData(dataBase.url,dataBase.input,dataBase)
                        left.$refs.org.getNode();
                        basTop.disabled = true;
                    })
                })
            },
            //生效
            affectProp(){
                //var data={
                //    "url":affectPropUrl,
                //    "jsonData":{rowId:basLeft.currentId},
                //    "obj":basTop,
                //};
                //gmpAjax.showAjax(data,function(res){
                //    basLeft.searchLeft();
                //})
            },
            //保存
            addClick(){
                right.addClick();
            }
        }
    });

    //左侧树
    left=new Vue({
        el:'#left',
        data:getData.dataObj({
            treeData: {
                // 是否显示checkbook 默认为不显示
                checkbox: true,
                expandedAll: true,
                // 获取树节点接口
                url: serverPath + "/role/queryPage",
                // 设置参数 -- 树节点上显示的文字
                defaultProps: {
                    // 树节点显示文字
                    label: 'roleName',
                    // 节点id
                    key: "roleId",
                    // 父节点信息
                    // parent: "orgPid",
                    // // 当前节点信息
                    // selfId: "orgId",
                }
            },
            rowIdArr:[0],
        }),
        methods:{
            //点击左边的树得到数据
            getNodes(data) {
                console.log(data);
                this.rowId=data.rowId;
                basTop.disabled = false;
                rightBottom.disabled = false;
                // basTop.orgDeleteData = false;
                this.selRole(this.rowId);
                basRightTop.selQueryPermissions(this.rowId);
                basRight.selOrganization(this.rowId);
            },
            //复选框选中得到得值
            getChecked(data) {
                console.log(data);
                basTop.disabled = false;
                rightBottom.disabled = false;
                var arr = this.rowIdArr;
                for(var i=0;i<arr.length;i++){
                    if(data.rowId != arr[i]){
                        if(i==arr.length-1){
                            arr.push(data.rowId);
                            console.log(this.rowIdArr);
                        }
                    }
                }
            },

            //查角色下的详细信息及人员表格赋值
            selRole(rowId){
                $.ajax({
                    url:roleInformation,
                    type:"get",
                    data:{
                        rowId:rowId
                    },
                    dataType:"json",
                    xhrFields: {withCredentials: true},
                    success:function(res){
                        var data=(res.resp.content.data)[0];
                        console.log(data);
                        right.formTable.roleId=data.roleId;
                        right.formTable.roleName=data.roleName;
                        right.formTable.roleType=data.roleType;
                        right.formTable.desc=data.desc;
                        right.formTable.remarks=data.remarks;
                        right.rowId = data.rowId;
                        basRightTop.PersonnelInformation(right.rowId);
                    },
                })
            },
        },
        created(){
            $(document).ready(function () {
                var height = $(window).height()-70;
                $("#allBorderHeight").height(height);
            });
            $(window).resize(function () {
                var height1 = $(window).height()-70;
                $("#allBorderHeight").height(height1);
            });
        }
    })

    //详细信息
    right=new Vue({
        "el": "#right",
        data: getData.dataObj({
            labelPosition:'right',
            formTable:{
                roleId:"",
                roleName:"",
                roleType:"",
                desc:"",
                remarks:"",
            },
            rowId:""
        }),
        methods: {
            editTbleBase(rowId){
                var data = {"url":modify,"jsonData":{
                    rowId:rowId,
                    roleName:this.formTable.roleName,
                    roleId:this.formTable.roleId,
                    roleType:this.formTable.roleType,
                    desc:this.formTable.desc,
                    remarks:this.formTable.remarks,
                },
                    "obj":right,
                    "showMsg":true
                }
                gmpAjax.showAjax(data,function(res){
                    console.log(res);
                    left.$refs.org.getNode();
                })
            },
            addClick(){
                editObj.editOk(function(){
                    right.editTbleBase(right.rowId);
                })
            }
        }
    })

    //关联其他信息及权限信息
    rightBottom=new Vue({
        el:'#rightBottom',
        data:getData.dataObj({
            activeName:'first',
            disabled:true
        }),
        methods:{
            //tab页点击交换
            handleClick(tablePageName){

            },
            //点击
            firstClick(){

            },
            //表点击
            twoClick(){

            },
            //分页信息
            handleSizeChange(val){
                this.pageSize = val;
                basRightTop.selQueryPermissions(left.rowId);
            },
            handleCurrentChange(val){
                this.pageNum = val;
                basRightTop.selQueryPermissions(left.rowId,val);
            },
            //刷新按钮
            btnRefresh(){
                basRightTop.PersonnelInformation(left.rowId);
            }
        },
        created(){
            $(document).ready(function () {
                rightBottom.leftHeight = $(window).height() - 450;
            });
            $(window).resize(function () {
                rightBottom.leftHeight = $(window).height() - 450;
            });
        }
    })

    //人员信息
    basRightTop = new Vue({
        el:"#basRightTop",
        template:'#tempBlock',
        data:getData.dataObj({}),
        methods:{
            //点击行
            twoClick(){
                alert("1");
            },
            //分页信息
            handleSizeChange(val){
                this.pageSize = val;
                this.PersonnelInformation(left.rowId);
            },
            handleCurrentChange(val){
                this.pageNum = val;
                this.PersonnelInformation(left.rowId,val);
            },
            //人员信息查询
            PersonnelInformation(rowId,numberPage){
                var page = 1;
                if(numberPage){
                    page = numberPage;
                }
                var rowId = rowId;
                console.log(rowId);
                var data = {
                    "rowId":rowId
                }
                querySearch.getDataPage(personnelInformationInterface,data,basRightTop,page,function(res){
                    console.log(res);
                })
            },

            //查角色下的权限信息
            selQueryPermissions(rowId,numberPage){
                var page = 1;
                if(numberPage){
                    page = numberPage;
                }
                var rowId = rowId;
                console.log(rowId);
                var data = {
                    "rowId":rowId
                }
                querySearch.getDataPage(permissionsInformation,data,rightBottom,page,function(res){
                    console.log(res);
                });
            },
        },
        created(){
            $(document).ready(function () {
                basRightTop.leftHeight = $(window).height() - 450;
            });
            $(window).resize(function () {
                basRightTop.leftHeight = $(window).height() - 450;
            });
        }
    })

    //机构信息
    basRight = new Vue({
        el:"#basRight",
        template:'#tempBlockThree',
        data:getData.dataObj({

        }),
        methods:{
            //点击行
            twoClick(){
                alert("1");
            },
            //分页信息
            handleSizeChange(val){
                //一页显示多少条
                this.pageSize = val;
                this.selOrganization(left.rowId);
            },
            handleCurrentChange(val){
                //当前第几页
                this.pageNum = val;
                this.selOrganization(left.rowId,val);
            },
            //组织机构
            selOrganization(rowId,numberPage){
                var page = 1;
                if(numberPage){
                    page = numberPage;
                }
                var rowId = rowId;
                console.log(rowId);
                var data = {
                    "rowId":rowId
                }
                querySearch.getDataPage(selOrganization,data,basRight,page,function(res){
                    console.log(res);
                });
            },
        },
        created(){
            $(document).ready(function () {
                basRight.leftHeight = $(window).height() - 450;
            });
            $(window).resize(function () {
                basRight.leftHeight = $(window).height() - 450;
            });
        }
    })
}