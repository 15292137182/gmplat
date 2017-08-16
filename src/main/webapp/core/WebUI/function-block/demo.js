/**
 * Created by andim on 2017/8/15.
 */
/**
 * Created by andim on 2017/8/11.
 */
var em=new Vue({
    el:'#addData',
    data: {
        labelPosition:'right',
        formTable:{
            tableInput:'',//关联对象属性
            nameTitle:'',//显示标题
            nameInput:'',//显示控件
            lengthSection:'',//长度区间
            testFunction:'',//验证函数
            displayFunction:'',//显示函数
            sortNumber:'',//排序
        },
        funcRowId:'',//功能块ID
        dataId:'',//关联对象属性ID
        isEdit:'',//是否编辑
        rowId:'',//行ID
        checked:'',//是否显示
        checkedNull:'',//允许为空
        checkedReady:'',//只读
        checkType:true,//判断显示控件禁用或者启用
        relateBusiObjId:'',//业务对象ID
        sortArr:[],//排序数组
        url:serverPath+'/fronFuncPro/add',//新增接口
        queryUrl:serverPath+'/fronFuncPro/queryProRowId',//查询指定ID功能块属性接口
        queryDataUrl:serverPath+'/businObjPro/queryPro',//查询指定ID业务对象属性接口
        editUrl:serverPath+'/fronFuncPro/modify',//编辑功能块属性
        isDisabled:false,//是否禁用
        rightRowId:'',//右边表行ID
    },
    methods:{
        searchConnectObj(){//查询所有对象属性，弹出对象属性表
            var htmlUrl = 'choose-object-properties.html';
            littledivIndex = ibcpLayer.ShowIframe(htmlUrl, '关联对象属性', '450px', '500px',false);
        },
        isChecked(){//判断是否显示，选中
            if(this.checked){
                this.checkType=false;
            }else{
                this.checkType=true;
            }
        },
        conformEvent(){
            var data = [
                this.$refs.tableInput,
                this.$refs.nameTitle,
                this.$refs.lengthSection,
                this.$refs.testFunction,
                this.$refs.displayFunction,
                this.$refs.sortNumber
            ];
            for(var i=0;i<data.length;i++){
                if(data[i].value==''){
                    ibcpLayer.ShowMsg(data[i].placeholder);
                    return;
                }
            }
            if(!this.checkType && this.$refs.nameInput==''){
                ibcpLayer.ShowMsg(this.$refs.nameInput.placeholder);
                return;
            }
            //this.sortArrFun(this.sortNumber);//排序
            this.funcRowId = window.parent.topButtonObj.rowObjId;
            this.addObjectProperties();//新增
        },
        newAttribute(){//新增属性
            this.$http.jsonp(this.url, {
                funcRowId:this.funcRowId,//功能块ID
                relateBusiPro:this.dataId,//业务对象属性ID
                displayTitle:this.formTable.nameTitle,//显示标题
                wetherDisplay:this.checked,//是否显示
                displayWidget:this.formTable.nameInput,//显示控件
                wetherReadonly:this.checkedReady,//只读
                allowEmpty:this.checkedNull,//允许为空
                lengthInterval:this.formTable.lengthSection,//长度区间
                validateFunc:this.formTable.testFunction,//验证函数
                displayFunc:this.formTable.displayFunction,//显示函数
                sort:this.formTable.sortNumber,//排序
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                window.parent.ibcpLayer.ShowOK(res.data.message);
                window.parent.properties.getRight(this.funcRowId);
                parent.layer.close(window.parent.topButtonObj.divIndex);
            },function(){
                alert("新增属性失败！")
            });
        },
        editAttribute(){//编辑属性
            this.$http.jsonp(this.editUrl, {
                rowId:this.rowId,//新增属性的ID
                funcRowId:this.funcRowId,//功能块ID
                relateBusiPro:this.dataId,//业务对象属性ID
                displayTitle:this.nameTitle,//显示标题
                wetherDisplay:this.checked,//是否显示
                displayWidget:this.nameInput,//显示控件
                wetherReadonly:this.checkedReady,//只读
                allowEmpty:this.checkedNull,//允许为空
                lengthInterval:this.lengthSection,//长度区间
                validateFunc:this.testFunction,//验证函数
                displayFunc:this.displayFunction,//显示函数
                sort:this.sortNumber,//排序
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                window.parent.ibcpLayer.ShowOK(res.data.message);
                window.parent.properties.getRight(this.funcRowId);
                parent.layer.close(window.parent.topButtonObj.divIndex);
            });
        },
        cancel(){
            parent.layer.close(window.parent.topButtonObj.divIndex);
        },
        sortArrFun(str){
            var arr=str.split(',');
            for(var i=0;i<arr.length;i++){
                this.sortArr.push(arr[i]);
            }
        },
        addObjectProperties(){
            if(!window.parent.topButtonObj.isEdit){//新增
                this.newAttribute();
            }else{//编辑
                this.editAttribute();
            }
        },
        loadComplete(){
            this.funcRowId = window.parent.topButtonObj.rowObjId;
            this.QueryProperties();
        },
        QueryProperties(){
            this.$http.jsonp(this.queryUrl,{
                "queryProRowId":this.rightRowId,
            },{
                jsonp:'callback'
            }).then(function (res) {
                this.rowId=res.data.data[0].rowId;//新增成功后返回的ID
                this.funcRowId=res.data.data[0].funcRowId;//功能块ID
                this.dataId=res.data.data[0].relateBusiPro;//业务对象属性ID
                this.formTable.nameTitle=res.data.data[0].displayTitle;//显示标题
                if(res.data.data[0].wetherDisplay){
                    this.checked=true;//是否显示
                }
                if(res.data.data[0].wetherReadonly){
                    this.checkedReady=true;//只读
                }
                if(res.data.data[0].allowEmpty){
                    this.checkedNull=true;//允许为空
                }
                this.formTable.nameInput=res.data.data[0].displayWidget;//显示控件
                this.formTable.lengthSection=res.data.data[0].lengthInterval;//长度区间
                this.formTable.testFunction=res.data.data[0].validateFunc;//验证函数
                this.formTable.displayFunction=res.data.data[0].displayFunc;//显示函数
                this.formTable.sortNumber=res.data.data[0].sort;//排序
                this.isDisabled=true;
                this.queryObjectProperties();
            })
        },
        queryObjectProperties(){
            this.$http.jsonp(this.queryDataUrl,{
                "businProrowId":this.dataId
            },{
                jsonp:'callback'
            }).then(function(res){
                this.formTable.tableInput = res.data.data[0].propertyName//业务对象属性中文名
            },function(){
                alert("1111111错误!")
            })
        }
    },
    created(){
        this.relateBusiObjId = window.parent.topButtonObj.objId;//业务对象Id
        this.rightRowId = window.parent.properties.rowId;
        if(window.parent.topButtonObj.isEdit){//编辑
            this.loadComplete();
        }
    }
})