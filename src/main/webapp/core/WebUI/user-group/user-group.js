/**
 * Created by admin on 2017/10/12.
 */
var basTop;
var left;
var right;

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
//添加组人员
//var addGroupUser=serverPath + "/userGroup/delete";


gmp_onload=function(){
    basTop = new Vue({
        el: '#basTop',
        data:getData.dataObj({
            addOpe: false,
            addAttr: false,
            takeEffect: false,
        }),
        methods: {
            //新增用户组
            addEvent() {
                operate = 1;
                var htmlUrl = 'user-group-add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 添加用户组信息', '500px', '600px',function(){

                });
            },
            //删除用户组
            deleteEvent(){
                deleteObj.del(function(){
                    var data={
                        "url":deleteGroup,
                        "jsonData":{rowId:right.rowId},
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
            //添加组人员
            addUserEvent(){
                var htmlUrl = 'user-group-user.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 添加用户组下人员信息', '800px', '600px',function(){

                });
            },
        }
    });

    left=new Vue({
        el:'#left',
        data:getData.dataObj({
            config: {
                // 显示复选框
                checkbox: true,
                // 配置显示项
                defaultProps: {
                    // 树节点显示文字
                    label: 'groupName',
                    // 节点id
                    key: "rowId",
                },
                // 获取数据接口
                url: serverPath + "/userGroup/queryPage"
            },
            rowIds:[0],
        }),
        methods:{
            //点击左边的树得到数据
            getNodes(data) {
                this.rowId=data.rowId
                $.ajax({
                    url:searchGroup,
                    type:"get",
                    data:{
                        rowId:this.rowId
                    },
                    dataType:"json",
                    xhrFields: {withCredentials: true},
                    success:function(res){
                        var data=res.resp.content.data;
                        console.log(data);
                        right.groupName=data.groupName;
                        right.groupNumber=data.groupNumber;
                        right.rowId=data.rowId;
                        right.belongSector=data.belongSector;
                        right.belongSector=data.belongSector;//所属部门 为了提交
                        right.config1.checked=data.belongSector;
                        right.groupCategory=data.groupCategory;
                        right.desc=data.desc;
                        right.remarks=data.remarks;
                    },
                })
                rightBottom.searchMore();
            },
            //复选框选中得到得值
            getChecked(data) {
                //console.log(data);
                var arr = this.rowIds;
                for(var i=0;i<arr.length;i++){
                    if(data.rowId != arr[i]){
                        if(i==arr.length-1){
                            arr.push(data.rowId);
                        }
                    }
                }
                console.log(this.rowIds);


                //console.log(left.arr);
            }
        },
    })

    right=new Vue({
        "el": "#right",
        data: getData.dataObj({
            labelPosition:'right',
            groupNumber:'',
            groupName:'',
            belongSector:'',
            groupCategory:'',
            desc:'',
            remarks:'',
            config1: {
                // 设置清空按钮
                clearable: false,
                // 显示复选框
                checkbox: false,
                // 默认展开
//                  expanded: [324],
                // 展开所有节点
                expandedAll: true,
                // 默认选中项 当 checkbox 为 true 时  编辑时可以用
                checked: [],
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
        }),
        methods: {
            addClick(){
                var data={
                    "url":modifyGroup,
                    "jsonData":{
                        rowId:right.rowId,
                        groupName:right.groupName,//组名称
                        belongSector:right.belongSector,//所属部门
                        groupCategory:right.groupCategory,//组类别
                        desc:right.desc,//描述
                        remarks:right.remarks//描述
                    },
                    "obj":right,
                    "showMsg":true
                };
                gmpAjax.showAjax(data,function(res){
                    console.log(res);
                    //分页跳回到第一页
                    //basRight.searchRight();
                })
            },
            getNodes1(data, id, name) {
            },
            //确认这个节点的时候
            getNodeId1(id) {
                //确认点击的这个ID
                this.belongSector=id;
            },
            //复选框选中的时候
            getChecked1(data, id, name, flag) {

            },
            //清除框的时候
            clear1(id, name) {
                this.belongSector=id;
            },
        }
    })

    rightBottom=new Vue({
        "el": "#rightBottom",
        data: getData.dataObj({

        }),
        methods: {
            headSort(){

            },
            //点击这一行
            currentChange(row, event, column){

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
            //查询 调回第一页
            searchMore(){
                querySearch.searchResource(searchGroupUser,left.rowId,this,function(res){
                    console.log(res);
                    var data=res.resp.content.data;
                    if(data!=null){
                        //默认选中行
                        //this.currentChange(this.tableData[0]);
                    }

                })
            },
            //刷新按钮
            btnRefresh(){
                //this.PersonnelInformation(left.rowId);
            },
            //默认选中变颜色
            FindRFirstDate(row){
                // console.log(row)
                //this.$refs.tableData.setCurrentRow(row);
            },
        },
        created(){
            $(document).ready(function () {
                rightBottom.leftHeight = $(window).height() -440;
            });
            $(window).resize(function () {
                rightBottom.leftHeight = $(window).height() -440;
            });
        }
    })
}