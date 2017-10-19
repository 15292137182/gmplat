/**
 * Created by andim on 2017/8/21.
 */

var addInlayerData = new Vue({
    el:"#addInlayerData",
    data:{
        labelPosition:'right',
        Inlayer:{
            Cname:'',
            Ename:'',
            primaryKey:"",
            desp:'',
            primaryKeyData:""
        },
        rules:{
            Cname:[{ required: true, message: '请输入表中文名', trigger: 'blur' },
                { min: 1, max: 128, message: '长度在 1 到 64 个汉字', trigger: 'blur' }],
            Ename:[{ required: true, message: '请输入表英文名', trigger: 'blur' },
                { min: 1, max: 32, message: '长度在 1 到 32个字符', trigger: 'blur' }],
            desp:[{max: 1024, message: '长度在 1 到 512个汉字', trigger: 'blur' }]
        },
        addUrl:serverPath+'/dbTableColumn/add',//新增表字段
        editUrl:serverPath+'/dbTableColumn/modify',//编辑表字段
    },
    methods:{
        addTable(){//新增
            if(addInlayerData.Inlayer.primaryKey == true){
                addInlayerData.Inlayer.primaryKeyData = 1;
            }else{
                addInlayerData.Inlayer.primaryKeyData = 0;
            }
            var data = {
                "url":addInlayerData.addUrl,
                "jsonData":{
                    relateTableRowId:DatabaseDetails.Robj.rowId,
                    columnCname:addInlayerData.Inlayer.Cname,
                    columnEname:addInlayerData.Inlayer.Ename,
                    isPk:addInlayerData.Inlayer.primaryKeyData,
                    desp:addInlayerData.Inlayer.desp,
                },
                "obj":myInlayerButton,
                "showMsg":true
            }
            gmpAjax.showAjax(data,function(res){
                queryData.getDatas(DatabaseDetails.selUrl,DatabaseDetails.input,DatabaseDetails.Robj.rowId,DatabaseDetails);
                addInlayerData.cancel();
            })
        },
        editTable(){//编辑
            if(addInlayerData.Inlayer.primaryKey == true){
                addInlayerData.Inlayer.primaryKeyData = 1;
            }else{
                addInlayerData.Inlayer.primaryKeyData = 0;
            }
            var data = {
                "url":addInlayerData.editUrl,
                "jsonData":{
                    relateTableRowId:DatabaseDetails.Robj.rowId,
                    rowId:DatabaseDetails.rowObj.rowId,
                    columnCname:this.Inlayer.Cname,
                    columnEname:this.Inlayer.Ename,
                    isPk:addInlayerData.Inlayer.primaryKeyData,
                    desp:this.Inlayer.desp,
                },
                "obj":myInlayerButton,
                "showMsg":true
            }
            gmpAjax.showAjax(data,function(res){
                queryData.getDatas(DatabaseDetails.selUrl,DatabaseDetails.input,DatabaseDetails.Robj.rowId,DatabaseDetails);
                addInlayerData.cancel();
            })
        },
        isNull(){//非空验证
            var data = [
                this.$refs.inlayerCname,
                this.$refs.inlayerEname
            ]
            console.log(data);
            for(var i=0;i<data.length;i++){
                if(data[i].value==''){
                    ibcpLayer.ShowMsg(data[i].placeholder);
                    return false
                }
            }
            return true;
        },
        conformEvent(formName){//确定按钮
            if(myInlayerButton.isEdit == true){//编辑
                // if(this.isNull()){
                //     editObj.editOk(function(){
                //         addInlayerData.editTable();
                //     })
                // }
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        editObj.editOk(function(){
                            addInlayerData.editTable();
                        })
                    } else {
                        return false;
                    }
                });
            }else{//新增
                // if(this.isNull()){
                //     addObj.addOk(function(){
                //         addInlayerData.addTable();
                //     })
                // }
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log(addInlayerData.Inlayer.primaryKey);
                        addObj.addOk(function(){
                            addInlayerData.addTable();
                        })
                    } else {
                        return false;
                    }
                });
            }
        },
        cancel(){//取消按钮
            ibcpLayer.Close(myInlayerButton.divIndex);
        }
    }
})