/**
 * Created by admin on 2017/10/12.
 */
var basTop;
var left;
var right;
var rightBottom;

//查用户组信息
var Organization = serverPath + "/baseOrg/queryById";

//查看组织机构下的人员信息
var PersonnelInformationUrl = serverPath + "/user/queryByOrg"

//查看组织机构下的角色信息
// var roleViewUrl = serverPath + "role/queryBySpecify"

//组织机构编辑接口
var modify = serverPath + "/baseOrg/modify";

//组织机构删除接口
var deleteUrl = serverPath + "/baseOrg/delete";

gmp_onload=function(){
    basTop = new Vue({
        el: '#basTop',
        data: {
            addOpe: false,
            orgDeleteData: true,
            takeEffect: false,
        },
        methods: {
            //新增人员信息
            addEvent() {
                operate = 1;
                var htmlUrl = 'personnel_add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 添加人员信息', '600px', '660px',function(){

                });
            },
            //删除组织机构
            orgDelete(){
                deleteObj.del(function(){
                    left.rowIdArr.splice(0,1);
                    console.log(left.rowIdArr);
                    var data = {
                        "url":deleteUrl,
                        "jsonData":{rowIds:left.rowIdArr},
                        "obj":basTop,
                        "showMsg":true
                    }
                    gmpAjax.showAjax(data,function(res){
                        // queryData.getData(dataBase.url,dataBase.input,dataBase)
                        left.$refs.org.getNode();
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

    left=new Vue({
        el:'#left',
        data:getData.dataObj({
            treeData: {
                // 是否显示checkbook 默认为不显示
                checkbox: true,
                expandedAll: true,
                // 获取树节点接口
                url: serverPath + "/baseOrg/queryPage",
                // 设置参数 -- 树节点上显示的文字
                defaultProps: {
                    // 树节点显示文字
                    label: 'orgName',
                    // 节点id
                    id: "rowId",
                    // 父节点信息
                    parentId: "orgPid",
                    // 当前节点信息
                    selfId: "orgId",
                }
            },
            rowIdArr:[0],
        }),
        methods:{
            //点击左边的树得到数据
            getNodes(data) {
                this.rowId=data.rowId
                basTop.orgDeleteData = false;
                $.ajax({
                    url:Organization,
                    type:"get",
                    data:{
                        rowId:this.rowId
                    },
                    dataType:"json",
                    xhrFields: {withCredentials: true},
                    success:function(res){
                        var data=res.resp.content.data;
                        console.log(data);
                        right.formTable.orgPid=data.orgPid;
                        right.formTable.orgId=data.orgId;
                        right.formTable.orgName=data.orgName;
                        right.formTable.orgSort=data.orgSort;
                        right.formTable.fixedPhone=data.fixedPhone;
                        right.formTable.address=data.address;
                        right.formTable.desp=data.desp;
                        right.rowId = data.rowId;
                        rightBottom.PersonnelInformation(right.rowId);
                        // basRightTop.roleView(data.rowId);
                    },
                })
            },
            //复选框选中得到得值
            getChecked(data) {
                console.log(data);
                basTop.orgDeleteData = false;
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

    right=new Vue({
        "el": "#right",
        data: getData.dataObj({
            labelPosition:'right',
            formTable:{
                orgPid:"",
                orgId:"",
                orgName:"",
                orgSort:"",
                fixedPhone:"",
                address:"",
                desp:"",
            },
            rowId:""
        }),
        methods: {
            editTbleBase(rowId){
                var data = {"url":modify,"jsonData":{
                    rowId:rowId,
                    orgPid:this.formTable.orgPid,
                    orgId:this.formTable.orgId,
                    orgName:this.formTable.orgName,
                    orgSort:this.formTable.orgSort,
                    fixedPhone:this.formTable.fixedPhone,
                    address:this.formTable.address,
                    desp:this.formTable.desp,
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

    rightBottom=new Vue({
        el:'#rightBottom',
        data:getData.dataObj({
            activeName:'first',
        }),
        methods:{
            //tab页点击交换
            handleClick(tablePageName){
                if(tablePageName.index==0){
                    console.log("人员查看");
                }else{
                    console.log("角色查看");
                }
            },
            //人员信息查询
            PersonnelInformation(rowId){
                var strArr = '["'+rowId+'"]';
                console.log(strArr);
                querySearch.uneedSearch(PersonnelInformationUrl,strArr,this,function(res){
                    console.log(res);
                })
                // $.ajax({
                //     url:PersonnelInformationUrl,
                //     type:"get",
                //     data:{
                //         param:strArr
                //     },
                //     dataType:"json",
                //     xhrFields: {withCredentials: true},
                //     success:function(res){
                //         console.log(res.resp.content)
                //         rightBottom.loading=false;
                //         rightBottom.tableData = res.resp.content.data;//数据源
                //     },
                // })
            },
            //点击
            firstClick(){
                alert("1");
            },
            //表点击
            twoClick(){
                alert("2");
            },
            //分页信息
            handleSizeChange(val){
                alert(val);
            },
            handleCurrentChange(val){
                alert(val);
            }
        },
        created(){
            $(document).ready(function () {
                rightBottom.leftHeight = $(window).height() - 510;
            });
            $(window).resize(function () {
                rightBottom.leftHeight = $(window).height() - 510;
            });
        }
    })
    basRightTop = new Vue({
        el:"#basRightTop",
        template:'#tempBlock',
        data:getData.dataObj({}),
        methods:{
            //点击行
            twoClick(){
                alert("1");
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
            handleSizeChange(){

            },
            handleCurrentChange(){

            },
        },
        created(){
            $(document).ready(function () {
                basRightTop.leftHeight = $(window).height() - 510;
            });
            $(window).resize(function () {
                basRightTop.leftHeight = $(window).height() - 510;
            });
        }
    })
}