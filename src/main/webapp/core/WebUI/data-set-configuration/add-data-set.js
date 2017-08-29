/**
 * Created by andim on 2017/8/9.
 */

/**
 * 修改下拉框 jms
 * @type {Vue}
 */
Vue.component('select-dsctype', SelectOptions.setOpt('','','dataSetConfigType',''));

var addDataSet = new Vue({
    el:'#addDataSet',
    data:{
        labelPosition:"right",
        formTable:{
            datasetCode:'',
            nameInput:'',
          //  typeInput:'',    修改下拉框 jms 2017/8/21
            content:'',
            desp:'',
            version:'',
            belongModule:'',//模块
            belongSystem:'',//系统
        },
        isEdit:'',//是否编辑
        addUrl:serverPath+'/dataSetConfig/add',//新增
        editUrl:serverPath+'/dataSetConfig/modify',//编辑
    },
    methods:{
        checkIsNull(){//非空验证
            var data = [
                this.$refs.nameInput,
                // this.$refs.typeInput
            ];
            for(var i=0;i<data.length;i++){
                if(data[i].value==''){
                    ibcpLayer.ShowMsg(data[i].placeholder);
                    return false;
                }
            }
            return true;
        },
        addSet(){//新增
            /**
             * tsj 07/8/28 替换ajax方法，修改赋值
             **/
            gmpAjax.showAjax(addDataSet.addUrl,{
                datasetName:this.formTable.nameInput,
                datasetType:this.$refs.dsctype.value,
                datasetContent:this.formTable.content,
                desp:this.formTable.desp,
                belongModule:this.formTable.belongModule,
                belongSystem:this.formTable.belongSystem
            },function(res){
                showMsg.MsgOk(dataSetConfig,res);
                ibcpLayer.Close(dataSetConfigButton.divIndex);
                queryData.getData(dataSetConfig.selUrl,dataSetConfig.input,dataSetConfig)
            })
        },
        editSet(){//编辑
            /**
             * tsj 07/8/28 替换ajax方法，修改赋值
             **/
            gmpAjax.showAjax(addDataSet.editUrl,{
                rowId:dataSetConfig.rowObjId,
                datasetName:this.formTable.nameInput,
                datasetType:this.$refs.dsctype.value,
                datasetContent:this.formTable.content,
                desp:this.formTable.desp,
                belongModule:this.formTable.belongModule,
                belongSystem:this.formTable.belongSystem
            },function(res){
                showMsg.MsgOk(dataSetConfig,res);
                ibcpLayer.Close(dataSetConfigButton.divIndex);
                queryData.getData(dataSetConfig.selUrl,dataSetConfig.input,dataSetConfig);
            })
        },
        conformEvent(){//确定
            if(!this.isEdit){//新增
                if(this.checkIsNull()){
                    addObj.addOk(function(){
                        addDataSet.addSet();
                    })
                }
            }else{//编辑
                if(this.checkIsNull()){
                    editObj.editOk(function(){
                        addDataSet.editSet();
                    })
                }
            }
        },
        cancel(){//取消
            ibcpLayer.Close(dataSetConfigButton.divIndex);
        }
    }
})