/**
 * Created by admin on 2017/10/12.
 */
var basTop;
var left;
var right;

//查用户组信息
// var searchGroup=serverPath + "/userGroup/queryById";

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
                var htmlUrl = 'personnel_add.html';
                divIndex = ibcpLayer.ShowDiv(htmlUrl, ' 添加人员信息', '600px', '660px',function(){

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
            treeData: {
                // 是否显示checkbook 默认为不显示
                checkbox: false,
                // 获取树节点接口
                url: serverPath + "/userGroup/queryPage",
                // 设置参数 -- 树节点上显示的文字
                defaultProps: {
                    children: 'children',
                    label: 'groupName'
                }
            }
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
            groupName:'',
            belongSector:'',
            groupCategory:'',
            desc:'',
            remarks:'',
        }),
        methods: {

        }
    })

    rightBottom=new Vue({
        el:'#rightBottom',
        data:getData.dataObj({
            data: [{
                label: '一级 1',
                children: [{
                    label: '二级 1-1',
                    children: [{
                        label: '三级 1-1-1'
                    }]
                }]
            }, {
                label: '一级 2',
                children: [{
                    label: '二级 2-1',
                    children: [{
                        label: '三级 2-1-1'
                    }]
                }, {
                    label: '二级 2-2',
                    children: [{
                        label: '三级 2-2-1'
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
                label: 'label',
            },
            activeName:'first',
        }),
        methods:{
            //tab页点击交换
            handleClick(){

            },
            //点击
            firstClick(){
                alert("1");
            },
            //表点击
            twoClick(){
                alert("2");
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
}