/**
 * Created by admin on 2017/8/27.
 */

Vue.component('select-value', SelectOptions.setOpt('','value','valueType',''));//templateId,modelName,keysetCode,path


var addTempProp = new Vue({
    "el": "#addEventProp",
    data: {
        function () {
            return {
                labelPosition: 'right',
                addTempProp: {
                    codeInput: '',
                    engNameInput: '',
                    chnNameInput: '',
                    default:'',
                    comContent:''
                }
            }
        }
    },
    methods: {
        //保存事件
        saveTempProp(){

        },
        //取消事件
        cancelTempProp(){

        }

    }
});