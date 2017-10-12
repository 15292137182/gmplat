/**
 * Created by admin on 2017/10/12.
 */
var basTop;
var left;
var right;
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
        }),
        methods:{
            handleNodeClick(data){
                //console.log(data);
            },
        },
    })

    right=new Vue({
        "el": "#right",
        data: getData.dataObj({
            input3:'',
        }),
        methods: {

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
                console.log(row)
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
            //查询
            searchMore(){

            },
            //默认选中变颜色
            FindRFirstDate(row){
                // console.log(row)
                //this.$refs.tableData.setCurrentRow(row);
            },
        },
        created(){
            $(document).ready(function () {
                rightBottom.leftHeight = $(window).height() -335;
            });
            $(window).resize(function () {
                rightBottom.leftHeight = $(window).height() -335;
            });
        }
    })
}