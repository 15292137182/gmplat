/**
 * Created by admin on 2017/8/27.
 */


var addTemp = new Vue({
    "el": "#addEvent",
    data: function () {
            return {
                labelPosition: 'right',
                addTemp: {
                    codeInput: '',
                    nameInput: '',
                    comContent: '',
                }
            }
    },
    methods: {
        //保存事件
        saveTemp(){
            //新增
            if(operate==1){
                addObj.addOk(function(){
                    this.$http.jsonp(addTemp, {
                        templateName:this.nameInput,
                        templatedesp:this.comContent
                    }, {
                        jsonp: 'callback'
                    }).then(function (ref) {
                        showMsg.MsgOk(addTemp,ref);
                        //分页调回第一页
                    });
                })
            }
        },
        //取消事件
        cancelTemp(){
            ibcpLayer.Close(divIndex);
        }

    }
});