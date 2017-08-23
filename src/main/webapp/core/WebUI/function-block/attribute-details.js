/**
 * Created by andim on 2017/8/21.
 */
/**
 * Created by andim on 2017/8/8.
 */
var attributeDetails = new Vue({
    el:'#attributeDetails',
    data:{
        labelPosition:'right',
        formTable:{
            checked:'',//显示
            checkedNull:'',//为空
            checkedReady:'',//只读
            lengthSection:'',//长度区间
            validateFunc:'',//验证函数
            displayFunc:'',//显示函数
        },
        input:'',
        url:serverPath+'/fronFuncPro/queryById',//查询功能块属性接口
        attributeData:[],//属性数组
    },
    methods:{
        getAttribute(){
            this.$http.jsonp(this.url, {
                "rowId":properties.rowId,
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                var data = res.data.data;
                this.formTable.lengthSection =data[0].lengthInterval//长度区间
                this.formTable.validateFunc =data[0].validateFunc//验证函数
                this.formTable.displayFunc =data[0].displayFunc//显示函数
                if(res.data.data!=null){
                    if(data[0].wetherReadonly == 'true'){
                        this.formTable.checkedReady = true//只读
                    }
                    if(data[0].wetherDisplay =='true'){
                        this.formTable.checked = true//显示
                    }
                    if(data[0].allowEmpty =='true'){
                        this.formTable.checkedNull = true//为空
                    }

                }
            });
        },
        conformEventClose(){//确定按钮
            ibcpLayer.Close(properties.divIndex);
        }
    },
    created(){
        this.getAttribute();
    }
})