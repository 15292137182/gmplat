/**
 * Created by admin on 2017/8/30.
 */
    //下拉框修改 jms
Vue.component('select-tab', SelectOptions.setOpt('tableSelect','tableInput','',conTable));

var em = new Vue({
        el: '#addEvent',
        data: function () {
            return {
                labelPosition: 'right',
                addForm:{
                    codeInput: '',
                    nameInput: '',
                    versionInput: '',
                    disabled: true,
                    tabDisabled:false,
                    dataId:'',
                    flag:true,
                },
                reaOptTable:[],
                options:[],
                value5:'',
            }
        },
        methods: {
            conformEvent() {
                var nameInput= this.$refs.nameInput.value;
                var tableInput= em.$refs.selectTab.tableInput;
                if(nameInput==''){
                    ibcpLayer.ShowMsg(this.$refs.nameInput.placeholder);
                    return;
                }
                if(tableInput==''){
                    ibcpLayer.ShowMsg('请选择关联表');
                    return;
                }
                if (operate == 1) {
                    console.log(em.$refs.selectTab.tableInput)
                    addObj.addOk(function(){
                        em.$http.jsonp(addUrl, {
                            objectName: em.addForm.nameInput,
                            relateTableRowId: em.$refs.selectTab.tableInput,
                        }, {
                            jsonp: 'callback'
                        }).then(function (res) {
                            showMsg.MsgOk(basTop,res);
                            basLeft.leftInput='';
                            //分页跳回到第一页
                            basLeft.searchLeft();
                            ibcpLayer.Close(divIndex);
                        })
                    },function(){
                        showMsg.MsgError(basTop)
                    })
                }
                if (operate == 2) {
                    editObj.editOk(function(){
                        em.$http.jsonp(editUrl, {
                            //拿到这条数据的ID
                            rowId: basLeft.currentVal.rowId,
                            objectName: em.addForm.nameInput,
                            relateTableRowId: em.$refs.selectTab.tableInput,
                        }, {
                            jsonp: 'callback'
                        }).then(function (res) {
                            showMsg.MsgOk(basTop,res);
                            basLeft.leftInput='';
                            basLeft.searchLeft();
                            ibcpLayer.Close(divIndex);
                        });
                    },function(){
                        showMsg.MsgError(basTop)
                    })
                }
            },
            cancel() {
                ibcpLayer.Close(divIndex);
            }
        }
    })