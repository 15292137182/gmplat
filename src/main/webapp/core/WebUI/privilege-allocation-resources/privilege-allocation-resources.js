/**
 * Created by andim on 2017/10/19.
 */
var left;
var right;
var rightBottom;
var basRightTop;
var basRight;
var rightTable;

//权限类型信息查询接口
var roleInformation = serverPath + "/keySet/queryKeySet";

//权限指定字段查询接口
var selUrl = serverPath + "/permission/queryTypePermission";

//查询指定rowId的详细信息
var selRowId = serverPath + "/permission/queryById"

//查看权限类型下的权限信息
var permissionsInformation = serverPath + "/permission/queryTypePermission";

//查看权限类型下的角色信息
var personnelInformationInterface = serverPath + "/permission/queryRole";

//权限编辑接口
var modify = serverPath + "/permission/modify";

//权限删除接口
var deleteUrl = serverPath + "/permission/delete";

//接口资源查询
var funcOperat = serverPath + '/funcOperat/queryPage';

function GlobalParameter(){
    var args={"tableKeySet":{
        "Block":{permissionType:"privilegeType"}
    }
    };
    return args
}

gmp_onload=function(){

    //左侧树
    left=new Vue({
        el:'#left',
        data:getData.dataObj({
            treeData: {
                // 是否显示checkbook 默认为不显示
                checkbox: false,
                expandedAll: true,
                // 获取树节点接口
                url: roleInformation,
                //传递参数
                params:"privilegeType",
                // 设置参数 -- 树节点上显示的文字
                defaultProps: {
                    // 树节点显示文字
                    label: 'confValue',
                    // 节点id
                    key: "rowId",
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
                rightBottom.permissionType = data.confKey;
                basRightTop.PersonnelInformation(data.confKey);
                basRightTop.roleInformation(data.rowId);
            },
            //复选框选中得到得值
            getChecked(data) {
                console.log(data);
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
            }
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

    //右边详细信息
    right=new Vue({
        "el": "#right",
        data: getData.dataObj({
            searchInput:"",
            select:"",
            chooseData:""
        }),
        methods: {
            firstClick(){},
            searchClick(){},
        }
    })

    //中间table信息
    rightBottom=new Vue({
        el:'#rightBottom',
        data:getData.dataObj({
            activeName:'first',
            disabled:true,
            searchInput:"",
            select:"",
            permissionType:"",
            tableId:'Block',
            selRowId:"",
            deleteIds:[],
            divIndex:"",
        }),
        methods:{
            //查询框点击
            searchClick(){
                if(this.select != ""){
                    var param = {};
                    param[this.select] = this.searchInput;
                    basRightTop.PersonnelInformation(this.permissionType,1,null,param);
                }else{
                    basRightTop.PersonnelInformation(this.permissionType,1,this.select,null);
                }
            },
            //tab页点击交换
            handleClick(tablePageName){

            },
            //点击
            firstClick(row){
                if(row.permissionType == "接口资源"){
                    this.permissionType = "interfaceResources";
                }else if(row.permissionType == "页面按钮"){
                    this.permissionType = "pageButoon";
                }else if(row.permissionType == "页面资源"){
                    this.permissionType = "pageResources";
                }else if(row.permissionType == "菜单资源"){
                    this.permissionType = "menuResource";
                }
                this.selRowId = row.rowId;
                this.deleteIds.push(this.selRowId);
            },
            //表点击
            twoClick(row){

            },
            //分页信息
            handleSizeChange(val){
                //alert("点击每页显示多少条");
                this.pageSize = val;
            },
            handleCurrentChange(val){
                //alert("当前第几页");
                var strArr = '["'+right.rowId+'"]';
                console.log(strArr);
                querySearch.jumpPage(PersonnelInformationUrl,strArr,this,val,function(res){
                    console.log(res);
                })
            },
            //分配
            distribution(){
                var htmlUrl = 'choose-page-resources.html';
                rightBottom.divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 资源信息', '800px', '600px',function(){
                    var data = {search:"",};
                    querySearch.getDataPage(funcOperat,data,pageResources,1,function(res){
                        console.log(res);
                    })
                });
            },
            //删除
            del(){
                var data = {
                    "url":deleteUrl,
                    "jsonData":{
                        rowIds:rightBottom.deleteIds
                    },
                    "obj":rightBottom,
                    "showMsg":true,
                }
                deleteObj.del(function(){
                    gmpAjax.showAjax(data,function(res){
                        basRightTop.PersonnelInformation(rightBottom.permissionType);
                    })
                })
            },
        },
        created(){
            $(document).ready(function () {
                rightBottom.leftHeight = $(window).height() - 235;
            });
            $(window).resize(function () {
                rightBottom.leftHeight = $(window).height() - 235;
            });
        }
    })

    //中间table信息
    basRightTop = new Vue({
        el:"#basRightTop",
        template:'#tempBlock',
        data:getData.dataObj({
            searchInput:"",
            select:"",
        }),
        methods:{
            //点击行
            twoClick(){

            },
            //角色查看
            roleView(rowId){
                var strArr = '["'+rowId+'"]';
                console.log(strArr);
                $.ajax({
                    url:roleViewUrl,
                    type:"get",
                    data:{
                        param:strArr
                    },
                    dataType:"json",
                    xhrFields: {withCredentials: true},
                    success:function(res){
                        console.log(res.resp.content.data)
                        rightBottom.loading=false;
                        rightBottom.tableDataTwo = res.resp.content.data;//数据源
                    },
                })
            },
            //分页信息
            handleSizeChange(val){
                this.pageSize = val;
                this.PersonnelInformation(right.rowId);
            },
            handleCurrentChange(val){
                this.pageNum = val;
                this.PersonnelInformation(right.rowId,val);
            },
            //权限信息查询
            PersonnelInformation(permissionType,numberPage,search,param){
                var page = 1;
                var data = null;
                if(numberPage){
                    page = numberPage;
                }
                var type = permissionType;
                if(search != null){
                    data = {
                        "permissionType":type,
                        "search":search,
                    }
                }else{
                    data = {
                        "permissionType":type
                    }
                }
                if(param != null){
                    var strParam = JSON.stringify(param);
                    data = {
                        "permissionType":type,
                        "param":strParam,
                    }
                }else{
                    data = {
                        "permissionType":type
                    }
                }
                querySearch.getDataPage(permissionsInformation,data,rightBottom,page,function(res){

                })
            },
            //角色信息查询
            roleInformation(rowId,numberPage,search,param){
                var page = 1;
                var data = {};
                if(numberPage){
                    page = numberPage;
                }
                if(search != null){
                    data = {
                        "rowId":rowId,
                        "search":search
                    }
                }else{
                    data = {
                        "rowId":rowId
                    }
                }
                if(param != null){
                    var strParam = JSON.stringify(param);
                    data = {
                        "rowId":rowId,
                        "param":strParam
                    }
                }else{
                    data = {
                        "rowId":rowId
                    }
                }
                querySearch.getDataPage(personnelInformationInterface,data,basRightTop,page,function(res){

                })
            },
            //角色信息查询按钮
            searchClick(){
                if(this.select != ""){
                    var param = {};
                    param[this.select] = this.searchInput;
                    basRightTop.roleInformation(left.rowId,1,null,param);
                }else{
                    basRightTop.roleInformation(left.rowId,1,this.select,null);
                }
            }
        },
        created(){
            $(document).ready(function () {
                basRightTop.leftHeight = $(window).height() - 235;
            });
            $(window).resize(function () {
                basRightTop.leftHeight = $(window).height() - 235;
            });
        }
    })

}