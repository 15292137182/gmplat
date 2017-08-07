/**
 * Created by jms on 2017/8/7.
 */
var keyValueSetAdd = new Vue({
    el: '#keyValueSetAdd',
    data: function () {
        return {
            keySetCodeInput: '',
            keySetNameInput: '',
            confKeyInput: '',
            confValueInput: '',
            despInput: '',
            versionInput: false,
            disabled:''
        }
    },
    methods: {
        confirm() {
            var datas = [
                this.$refs.keySetNameInput,
                this.$refs.confKeyInput,
                this.$refs.confValueInput,
                this.$refs.despInput
            ];
            for (var i = 0; i < datas.length; i++) {
                if (datas[i].value == '') {
                    ibcpLayer.ShowMsg(datas[i].placeholder);
                    return;
                }
            }
            if (operate == 1) {
                this.$http.jsonp(serverPath + "/core/addKeySet", {
                    keySetCode: keyValueSetAdd.keySetCodeInput,
                    keySetName: keyValueSetAdd.keySetNameInput,
                    confKey: keyValueSetAdd.confKeyInput,
                    confValue: keyValueSetAdd.confValueInput,
                    desp: keyValueSetAdd.despInput,
                    version: keyValueSetAdd.version
                }, {
                    jsonp: 'callback'
                }).then(function (res) {
                    ibcpLayer.ShowOK(res.data.message);
                    keyValueSet.search();
                    ibcpLayer.Close(divIndex);
                });
            }
            if (operate == 2) {
                this.$http.jsonp(serverPath + "/core/modifyKeySet", {
                    //拿到这条数据的ID
                    rowId: keyValueSet.currentVal.rowId,
                    keySetCode: keyValueSetAdd.keySetCodeInput,
                    keySetName: keyValueSetAdd.keySetNameInput,
                    confKey: keyValueSetAdd.confKeyInput,
                    confValue: keyValueSetAdd.confValueInput,
                    desp: keyValueSetAdd.despInput,
                    version: keyValueSetAdd.version
                 }, {
                    jsonp: 'callback'
                }).then(function () {
                    ibcpLayer.ShowOK('编辑成功');
                    keyValueSet.search();
                    ibcpLayer.Close(divIndex);
                });
            }
        },
        cancel() {
            ibcpLayer.Close(divIndex);
        }
    }
})
