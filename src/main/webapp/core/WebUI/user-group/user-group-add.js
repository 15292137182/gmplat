/**
 * Created by admin on 2017/10/16.
 */
var addDroupEvent=new  Vue({
    el:'#addDroupEvent',
    data:getData.dataObj({
        groupNumber:'',
        groupName:'',
        belongSector:'',
        groupCategory:'',
        desc:'',
        remarks:'',
        // 下拉框配置
        singleSelect: {
            params: "functionBlockAlign",
            value: "",
            multiple: "false"
        },
        config: {
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
    methods:{
        //点击他的时候
        getNodes(data, id, name) {

        },
        //确认这个节点的时候
        getNodeId(data) {
            //确认点击的这个ID
            this.belongSector=data.rowId;
        },
        //复选框选中的时候
        getChecked(data, id, name, flag) {
        },
        //清除框的时候
        clear(id, name) {
            this.belongSector=id;
        },
        conformEvent(){
            addObj.addOk(function(){
                var data={
                    "url":addGroup,
                    "jsonData":{
                        groupName:addDroupEvent.groupName,//组名称
                        belongSector:addDroupEvent.belongSector,//所属部门
                        groupCategory:addDroupEvent.groupCategory,//组类别
                        desc:addDroupEvent.desc,//描述
                        remarks:addDroupEvent.remarks//描述
                     },
                    "obj":addDroupEvent,
                    "showMsg":true
                };
                gmpAjax.showAjax(data,function(res){
                    //分页跳回到第一页
                    left.getNewDate();
                    ibcpLayer.Close(divIndex);
                })
            })
        },
        cancel(){
            ibcpLayer.Close(divIndex);
        }
    }
})
