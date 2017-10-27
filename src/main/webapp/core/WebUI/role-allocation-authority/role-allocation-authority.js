/**
 * Created by admin on 2017/10/12.
 */
var basTop;
var left;
var right;
var rightBottom;

//查角色详细信息
var Organization = serverPath + "/role/queryBySpecify";

//查角色下的权限信息
var queryPermission = serverPath + "/role/queryPermissions";

//角色权限删除接口
var deleteUrl = serverPath + "/role/deletePermission";

function GlobalParameter(){
    var args={"tableKeySet":{
        "Block":{permissionType:"privilegeType"}
    }
    };
    return args
}

gmp_onload=function(){
    basTop = new Vue({
        el: '#basTop',
        data: {
            addOpe: false,
            orgDeleteData: true,
            takeEffect: false,
            divIndex:"",
            disabled:true
        },
        methods: {
            //分配权限信息
            addClick(){
                var htmlUrl = "add-role-allocation-authority.html";
                this.divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 权限详细信息', '850px', '500px',function(){

                })
            }
        }
    });

    left=new Vue({
        el:'#left',
        data:getData.dataObj({
            treeData: {
                // 是否显示checkbook 默认为不显示
                checkbox: false,
                //展开所有节点
                expandedAll: true,
                // 获取树节点接口
                url: serverPath + "/role/queryPage",
                // 设置参数 -- 树节点上显示的文字
                defaultProps: {
                    // 树节点显示文字
                    label: 'roleName',
                    // 节点id
                    key: "roleId",
                }
            },
            rowIdArr:[0],
            rowId:'',
        }),
        methods:{
            //点击左边的树得到数据
            getNodes(data) {
                console.log(data);
                this.rowId=data.rowId;
                $.ajax({
                    url:Organization,
                    type:"get",
                    data:{
                        rowId:this.rowId
                    },
                    dataType:"json",
                    xhrFields: {withCredentials: true},
                    success:function(res){
                        if(res.resp.content.data.length>0){
                            var data=(res.resp.content.data)[0];
                            right.formTable.roleId=data.roleId;
                            right.formTable.roleName=data.roleName;
                            right.formTable.roleType=data.roleType;
                            right.formTable.desc=data.desc;
                            basTop.disabled = false;
                            //查询角色下的权限
                            rightBottom.rolePermission(data.rowId);
                        }
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

    right=new Vue({
        "el": "#right",
        data: getData.dataObj({
            labelPosition:'right',
            formTable:{
                roleId:"",
                roleName:"",
                roleType:"",
                fixedPhone:"",
                address:"",
                desc:"",
            },
            rowId:""
        }),
        methods: {

        }
    })

    rightBottom=new Vue({
        el:'#rightBottom',
        data:getData.dataObj({
            disabled:true,
            tableId:'Block',
        }),
        methods:{
            //删除
            del(row){
                console.log(row);
                deleteObj.del(function(){
                    var data = {
                        "url":deleteUrl,
                        "jsonData":{roleRowId:left.rowId,permissionRowIds:row.rowId},
                        "obj":rightBottom,
                        "showMsg":true
                    }
                    gmpAjax.showAjax(data,function(res){
                        rightBottom.rolePermission(left.rowId);
                    })
                })
            },
            //点击
            firstClick(){

            },
            //分页信息
            handleSizeChange(val){
                //alert("点击每页显示多少条");
                this.pageSize = val;
                rightBottom.rolePermission(left.rowId,1);
            },
            handleCurrentChange(val){
                //alert("当前第几页");
                this.pageNum = val;
                rightBottom.rolePermission(left.rowId,val);
            },
            //刷新按钮
            btnRefresh(){

            },
            //查询角色下的权限方法
            rolePermission(permissionRowId,numberPage,search,param){
                var page = 1;
                var data = null;
                if (numberPage) {
                    page = numberPage;
                }
                var rowId = permissionRowId;
                if (search != null) {
                    data = {
                        "rowId": rowId,
                        "search": search,
                    }
                } else {
                    data = {
                        "rowId": rowId,
                    }
                }
                if (param != null) {
                    var strParam = JSON.stringify(param);
                    data = {
                        "rowId": rowId,
                        "param": strParam,
                    }
                } else {
                    data = {
                        "rowId": rowId,
                        "search": search,
                    }
                }
                querySearch.getDataPage(queryPermission, data, rightBottom, page, function (res) {
                    console.log(res);
                })
            }
        },
        created(){
            $(document).ready(function () {
                rightBottom.leftHeight = $(window).height() - 390;
            });
            $(window).resize(function () {
                rightBottom.leftHeight = $(window).height() - 390;
            });
        }
    })
}