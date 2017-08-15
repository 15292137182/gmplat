/**
 * Created by andim on 2017/8/11.
 */
var em = new Vue({
    el: '#connectTable',
    data: {
        myLeftData: []
    },
    created() {
        this.$http.jsonp(serverPath + "/businObj/query", {
            str: ''
        }, {
            jsonp: 'callback'
        }).then(function (res) {
            if(res.data.data!=null){
                this.myLeftData = res.data.data;
            }
        });
    },
    methods: {
        handleCurrentChange(val) {
            //选中行的数据
            this.currentRow = val.objectCode + "(" + val.objectName + ")";
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