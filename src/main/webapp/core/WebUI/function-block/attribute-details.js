/**
 * Created by andim on 2017/8/21.
 */
/**
 * Created by andim on 2017/8/8.
 */
Vue.component('select-align', SelectOptions.setOpt('','','functionBlockAlign',''));
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
            Twidth:'',//宽度
            exactQuery:'',//精确查询
            supportSort:'',//支持排序
            keywordOne:'',//关键字1
            keywordTwo:'',//关键字2
            keywordThree:'',//关键字3
        },
        input:'',
        url:serverPath+'/fronFuncPro/queryById',//查询功能块属性接口
        attributeData:[],//属性数组
    },
    methods:{
        getAttribute(){
            /**
             * tsj 07/8/29 ajax代码重构，增加字段
             **/
            var data = {
                "url":this.url,
                "jsonData":{
                    "rowId":properties.rowId,
                },
                "obj":this
            }
            gmpAjax.showAjax(data,function(res) {
                var data = res;
                attributeDetails.formTable.lengthSection = data[0].lengthInterval//长度区间
                attributeDetails.formTable.validateFunc = data[0].validateFunc//验证函数
                attributeDetails.formTable.displayFunc = data[0].displayFunc//显示函数
                attributeDetails.$refs.align.value = data[0].align//对齐方式
                attributeDetails.formTable.exactQuery = data[0].exactQuery//精确查询
                attributeDetails.formTable.supportSort = data[0].supportSort//支持排序
                attributeDetails.formTable.Twidth = data[0].widthSetting//宽度
                attributeDetails.formTable.keywordOne = data[0].keywordOne//关键字1
                attributeDetails.formTable.keywordTwo = data[0].keywordTwo//关键字2
                attributeDetails.formTable.keywordThree = data[0].keywordThree//关键字3
                if(data[0].wetherReadonly == 'true'){
                    attributeDetails.formTable.checkedReady = true//只读
                }
                if(data[0].wetherDisplay =='true'){
                    attributeDetails.formTable.checked = true//显示
                }
                if(data[0].allowEmpty =='true'){
                    attributeDetails.formTable.checkedNull = true//为空
                }
                if(data[0].exactQuery =='true'){
                    attributeDetails.formTable.exactQuery = true//精确查询
                }
                if(data[0].supportSort =='true'){
                    attributeDetails.formTable.supportSort = true//支持排序
                }

            })
        },
        conformEventClose(){//确定按钮
            ibcpLayer.Close(properties.divIndex);
        }
    },
    created(){
        this.getAttribute();
    }
})