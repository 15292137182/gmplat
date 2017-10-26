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

//权限类型查询接口
var selUrl = serverPath + "/permission/queryTypePermission";

//查看权限类型下的权限信息
var permissionsInformation = serverPath + "/permission/queryTypePermission";

//资源删除接口
var deleteUrl = serverPath + "/permissionResource/deleteResource";

//接口资源查询
var funcOperat = serverPath + '/funcOperat/queryPage';

//页面资源查询
var page =  serverPath + '/page/queryPage';

//按钮资源查询
var selButton =  serverPath + '/button/queryPage';

//菜单资源查询
var menu = serverPath + '/menu/queryPage';

//权限查询资源
var queryResource = serverPath + "/permissionResource/queryResource";

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
            keyName:"",
        }),
        methods:{
            //点击左边的树得到数据
            getNodes(data) {
                console.log(data);
                this.rowId=data.rowId;
                rightBottom.permissionType = data.confKey;
                basRightTop.PersonnelInformation(data.confKey);
                this.keyName = data.confKey;
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
            chooseData:"",
            divIndex:""
        }),
        methods: {
            firstClick(row){

            },
            searchClick(){},
            searchDetails(row){
                //接口资源
                if(left.keyName == 'interfaceResources'){
                    console.log(row);
                   var htmlUrl = "see-funcOperat-resources.html";
                    right.divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 接口详细信息', '400px', '260px',function(){
                        funcOperat.$refs.avail.setValue(row.avail);
                        funcOperat.formTable.interceptUrl = row.interceptUrl;
                    })
                }
                //页面资源
                if(left.keyName == 'pageResources'){
                    console.log(row);
                    var htmlUrl = "see-page-resources.html";
                    right.divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 页面详细信息', '400px', '300px',function(){
                        page.$refs.grantAuth.setValue(row.grantAuth);
                        page.$refs.belongModule.setValue(row.belongModule);
                        page.formTable.pageUrl = row.pageUrl;
                    })
                }
                //菜单资源
                if(left.keyName == 'menuResources'){
                    console.log(row);
                    var htmlUrl = "see-menu-resources.html";
                    right.divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 菜单详细信息', '400px', '540px',function(){
                        menuRes.$refs.grantAuth.setValue(row.grantAuth);
                        menuRes.$refs.avail.setValue(row.avail);
                        menuRes.$refs.category.setValue(row.category);
                        menuRes.formTable.url = row.url;
                        menuRes.formTable.parentNumber = row.parentNumber;
                        menuRes.formTable.sort = row.sort;
                        menuRes.formTable.icon = row.icon;
                    })
                }
                //按钮资源
                if(left.keyName == 'pageButton'){
                    console.log(row);
                    var htmlUrl = "see-button-resources.html";
                    right.divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 菜单详细信息', '400px', '420px',function(){
                        button.$refs.grantAuth.setValue(row.grantAuth);
                        button.$refs.avail.setValue(row.avail);
                        button.$refs.pageIdent.setValue(row.pageIdent);
                        button.formTable.customStyle = row.customStyle;
                        button.formTable.sort = row.sort;
                    })
                }
            },
            del(row){
                var rowIdArr = [];
                rowIdArr.push(row.rowId);
                deleteObj.del(function(){
                    var data = {
                        "url":deleteUrl,
                        "jsonData":{
                            permissionRowId:rightBottom.selRightRowId.rowId,
                            sourceRowIds:rowIdArr
                                    },
                        "obj":right,
                        "showMsg":true
                    }
                    gmpAjax.showAjax(data,function(res){
                        right.refreshTable(rightBottom.selRightRowId.rowId);
                    })
                })
            },
            refreshTable(permissionRowId,numberPage,search,param){
                var page = 1;
                var data = null;
                if(numberPage){
                    page = numberPage;
                }
                var rowId = permissionRowId;
                if(search != null){
                    data = {
                        "permissionRowId":rowId,
                        "search":search,
                    }
                }else{
                    data = {
                        "permissionRowId":rowId,
                    }
                }
                if(param != null){
                    var strParam = JSON.stringify(param);
                    data = {
                        "permissionRowId":rowId,
                        "param":strParam,
                    }
                }else{
                    data = {
                        "permissionRowId":rowId,
                    }
                }
                querySearch.getDataPage(queryResource,data,right,page,function(res){

                })
            },
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
            divIndex:"",
            htmlUrl:"",//弹出页面地址
            url:"",//查询接口地址
            rowId:"",//权限Id
            selRightRowId:"",
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
                console.log(row.rowId);
                if(row.permissionType == "接口资源"){
                    this.permissionType = "interfaceResources";
                }else if(row.permissionType == "页面按钮"){
                    this.permissionType = "pageButton";
                }else if(row.permissionType == "页面资源"){
                    this.permissionType = "pageResources";
                }else if(row.permissionType == "菜单资源"){
                    this.permissionType = "menuResources";
                }
                this.selRowId = row.rowId;
                rightBottom.selRightRowId = row;
                right.refreshTable(rightBottom.selRightRowId.rowId);
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
            //分配资源按钮
            distribution(row){
                this.rowId = row.rowId;
                rightBottom.selRightRowId = row;
                if(row.permissionType == "接口资源"){
                    this.htmlUrl = "choose-funcOperat-resources.html";
                    this.url = funcOperat;
                }else if(row.permissionType == "页面按钮"){
                    this.htmlUrl = "choose-pageButton-resources.html";
                    this.url = selButton;
                }else if(row.permissionType == "页面资源"){
                    this.htmlUrl = "choose-page-resources.html";
                    this.url = page;
                }else if(row.permissionType == "菜单资源"){
                    this.htmlUrl = "choose-menu-resources.html";
                    this.url = menu;
                }
                var htmlUrl = this.htmlUrl;
                var selUrl = this.url;
                rightBottom.divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 资源信息', '800px', '600px',function(){
                    var data = {search:"",};
                    querySearch.getDataPage(selUrl,data,pageResources,1,function(res){
                        console.log(res);
                    })
                });
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
                    rightBottom.selRightRowId = rightBottom.tableData[0];
                    right.refreshTable(rightBottom.selRightRowId.rowId);
                    console.log(rightBottom.tableData[0].rowId);
                })
            },
            //权限信息查询按钮
            searchClick(){
                if(this.select != ""){
                    var param = {};
                    param[this.select] = this.searchInput;
                    this.PersonnelInformation(this.permissionType,1,null,param);
                }else{
                    this.PersonnelInformation(this.permissionType,1,this.select,null);
                }
            },
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