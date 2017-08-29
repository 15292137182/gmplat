/**
 * Created by andim on 2017/8/11.
 */
var em = new Vue({
    el: '#objPropertiesTable',
    data: {
        objectPropertiesData: []
    },
    created() {
        /**
         * tsj 07/8/29 ajax代码重构
         **/
        gmpAjax.showAjax(serverPath + "/businObjPro/queryBusinPro",{
            objRowId:window.parent.em.relateBusiObjId
        },function(res){
            if(res.resp.content.data!=null){
                em.objectPropertiesData = res.resp.content.data;
            }
        })
    },
    methods: {
        handleCurrentChange(val) {
            //选中行的数据
            this.currentRow = val.propertyCode + "(" + val.propertyName + ")";
            //选中行的ID
            this.currentId = val.rowId;
        },
        conformEvent() {
            //将数据渲染到外面的input里面
            window.parent.em.formTable.tableInput = this.currentRow;
            window.parent.em.dataId = this.currentId;
            //执行关闭
            parent.layer.close(window.parent.littledivIndex);
        },
        cancel() {
            parent.layer.close(window.parent.littledivIndex)
        }
    }
})