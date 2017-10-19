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
//修改用户组
//var searchGroupUser=serverPath + "/userGroup/modify

gmp_onload=function(){
    basTop = new Vue({
        el: '#basTop',
        data: {
            addOpe: false,
            addAttr: false,
            takeEffect: false,
        },
        methods: {
            //新增人员信息
            addEvent() {
                operate = 1;
                var htmlUrl = 'user-group-add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 添加用户组信息', '600px', '660px',function(){

                });
            },
            //新增业务对象属性
            addProp(){
                operateOPr=1;
                var htmlUrl = 'metadata-prop-add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增对象属性', '800px', '400px', function () {
                    proEm.$refs.proType_1.setValue("base");  //属性类型
                    proEm.proType_1.value='base';//不点击的时候直接把属性传过去
                    proEm.addProForm.proType='base';//只是为了验证的时候判断是否为空

                });
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
        }
    });

    left=new Vue({
        el:'#left',
        data:getData.dataObj({
            config: {
                // 显示复选框
                checkbox: false,
                // 默认展开  id
                //expanded: [1],
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
                        right.groupName=data.groupName;
                        right.belongSector=data.belongSector;
                        right.groupCategory=data.groupCategory;
                        right.desc=data.desc;
                        right.remarks=data.remarks;
                    },
                })
                rightBottom.searchMore();
            },
            //复选框选中得到得值
            getChecked(data) {
                console.log(data);
            }
        },
    })

    right=new Vue({
        "el": "#right",
        data: getData.dataObj({
            labelPosition:'right',
            groupName:'',
            belongSector:'',
            groupCategory:'',
            desc:'',
            remarks:'',
        }),
        methods: {
            addClick(){

            }
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
                    var data=res.resp.content.data.result;
                    if(data!=null){
                        //默认选中行
                        //this.currentChange(this.tableData[0]);
                    }

                })
            },
            //默认选中变颜色
            FindRFirstDate(row){
                // console.log(row)
                //this.$refs.tableData.setCurrentRow(row);
            },
        },
        created(){
            $(document).ready(function () {
                rightBottom.leftHeight = $(window).height() -380;
            });
            $(window).resize(function () {
                rightBottom.leftHeight = $(window).height() -380;
            });
        }
    })
}