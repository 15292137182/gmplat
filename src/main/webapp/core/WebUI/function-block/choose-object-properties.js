/**
 * Created by andim on 2017/8/11.
 */
var em = new Vue({
    el: '#objPropertiesTable',
    data: {
        objectPropertiesData: []
    },
    created() {
        this.$http.jsonp(serverPath + "/businObjPro/querySlave", {//查询指定业务对象ID下的所有属性
            objRowId:window.parent.em.relateBusiObjId,
        }, {
            jsonp: 'callback'
        }).then(function (res) {
            if(res.data.data!=null){
                this.objectPropertiesData = res.data.data;
            }
        });
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