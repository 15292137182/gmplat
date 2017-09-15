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
            // nameInput:'',//显示控件
            lengthSection:'',//长度区间
            testFunction:'',//验证函数
            displayFunction:'',//显示函数
            sortNumber:'',//排序
            Twidth:'',//宽度
            Keyword1:'',//关键字1
            Keyword2:'',//关键字2
            Keyword3:'',//关键字3
        },
        funcRowId:'',//功能块ID
        dataId:'',//关联对象属性ID
        attrSource:'',//关联对象属性所属模块
        isEdit:'',//是否编辑
        rowId:'',//行ID
        checked:true,//是否显示
        checkedNull:'',//允许为空
        checkedReady:'',//只读
        checkType:true,//判断显示控件禁用或者启用
        relateBusiObjId:'',//业务对象ID
        sortArr:[],//排序数组
        url:serverPath+'/fronFuncPro/add',//新增接口
        queryUrl:serverPath+'/fronFuncPro/queryById',//查询指定ID功能块属性接口
        queryDataUrl:serverPath+'/businObjPro/queryById',//查询指定业务对象属性ID业务对象属性接口
        editUrl:serverPath+'/fronFuncPro/modify',//编辑功能块属性
        isDisabled:false,//是否禁用
        rightRowId:'',//右边表行ID
        SupportSorting:'',//支持排序
        ExactSearch:'',//是否精确查询
        funcRowIds:'',

        objPro_1:{
            url:serverPath + "/businObjPro/queryBusinPro?objRowId="+window.parent.topButtonObj.objId+"&frontRowId="+window.parent.topButtonObj.rowObjId,
            value:"",
            disabled:"false"
        },
        //显示控件下拉框
        showControl_1:{
            params:"showControl",
            value:"",
            disabled:"false",
        },

        //对齐方式下拉框
        align_1:{
            params:"functionBlockAlign",
            value:"",
            disabled:"false",
        }
    },
    methods:{
        searchConnectObj(){//查询所有对象属性，弹出对象属性表
            var htmlUrl = 'choose-object-properties.html';
            // this.funcRowIds = window.parent.topButtonObj.rowObjId;
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
                // this.$refs.lengthSection,
                // this.$refs.testFunction,
                // this.$refs.displayFunction,
                // this.$refs.sortNumber
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
            console.log(em.attrSource);
            /**
             * tsj 07/8/29 新增数据ajax代码重构，增加字段
             **/
            var data = {
                "url":em.url,
                "jsonData":{
                    funcRowId:em.funcRowId,//功能块ID
                    relateBusiPro:em.dataId,//业务对象属性ID
                    attrSource:em.attrSource,//业务对象属性所属模块
                    displayTitle:em.formTable.nameTitle,//显示标题
                    wetherDisplay:em.checked,//是否显示
                    displayWidget:em.$refs.show.value,//显示控件
                    wetherReadonly:em.checkedReady,//只读
                    allowEmpty:em.checkedNull,//允许为空
                    lengthInterval:em.formTable.lengthSection,//长度区间
                    validateFunc:em.formTable.testFunction,//验证函数
                    displayFunc:em.formTable.displayFunction,//显示函数
                    sort:em.formTable.sortNumber,//排序
                    widthSetting:em.formTable.Twidth,//宽度
                    align:em.$refs.align.value,//对齐方式
                    exactQuery:em.ExactSearch,//是否精确查询
                    supportSort:em.SupportSorting,//支持排序
                    keywordOne:em.formTable.Keyword1,//关键字1
                    keywordTwo:em.formTable.Keyword2,//关键字2
                    keywordThree:em.formTable.Keyword3,//关键字3
                },
                "obj":window.parent.functionBlock
            }
            gmpAjax.showAjax(data,function(res){
                    //showMsg.MsgOk(window.parent.functionBlock,res);
                    queryData.getDatas(window.parent.properties.findRightDataUrl,window.parent.properties.rightInput,this.funcRowId,window.parent.properties);
                    window.parent.functionBlock.get();
                    parent.layer.close(window.parent.topButtonObj.divIndex);
            })
        },
        editAttribute(){//编辑属性
            /**
             * tsj 07/8/29 编辑数据ajax代码重构，增加字段
             **/
            var data = {
                "url":em.editUrl,
                "jsonData":{
                    rowId:em.rowId,//新增属性的ID
                    funcRowId:em.funcRowId,//功能块ID
                    relateBusiPro:em.dataId,//业务对象属性ID
                    attrSource:em.attrSource,//业务对象属性所属模块
                    displayTitle:em.nameTitle,//显示标题
                    wetherDisplay:em.checked,//是否显示
                    displayWidget:em.$refs.show.value,//显示控件
                    wetherReadonly:em.checkedReady,//只读
                    allowEmpty:em.checkedNull,//允许为空
                    lengthInterval:em.lengthSection,//长度区间
                    validateFunc:em.testFunction,//验证函数
                    displayFunc:em.displayFunction,//显示函数
                    sort:em.sortNumber,//排序
                    widthSetting:em.formTable.Twidth,//宽度
                    align:em.$refs.align.value,//对齐方式
                    exactQuery:em.ExactSearch,//是否精确查询
                    supportSort:em.SupportSorting,//支持排序
                    keywordOne:em.formTable.Keyword1,//关键字1
                    keywordTwo:em.formTable.Keyword2,//关键字2
                    keywordThree:em.formTable.Keyword3,//关键字3
                },
                "obj":window.parent.functionBlock
            }
            gmpAjax.showAjax(data,function(res){
                    //showMsg.MsgOk(window.parent.functionBlock,res);
                    queryData.getDatas(window.parent.properties.findRightDataUrl,window.parent.properties.rightInput,this.funcRowId,window.parent.properties);
                    window.parent.functionBlock.get();
                    parent.layer.close(window.parent.topButtonObj.divIndex);
                })
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
                addObj.addOk(function(){
                    em.newAttribute();
                })
            }else{//编辑
                editObj.editOk(function(){
                    em.editAttribute();
                })
            }
        },
        loadComplete(){
            this.funcRowId = window.parent.topButtonObj.rowObjId;
            this.QueryProperties();
        },
        QueryProperties(){
            /**
             * tsj 07/8/29 编辑数据ajax代码重构，增加字段
             **/
            var data = {
                "url":this.queryUrl,
                "jsonData":{
                    "rowId":this.rightRowId
                },
                "obj":this
            }
            gmpAjax.showAjax(data,function(res){
                console.log(res);
                var data = res;
                em.rowId=data[0].rowId;//新增成功后返回的ID
                em.funcRowId=data[0].funcRowId;//功能块ID
                em.dataId=data[0].relateBusiPro;//业务对象属性ID
                em.formTable.nameTitle=data[0].displayTitle;//显示标题
                if(data[0].wetherDisplay =="true"){
                    em.checked=true;//是否显示
                }else{
                    em.checked=false;//是否显示
                }
                if(data[0].wetherReadonly =="true"){
                    em.checkedReady=true;//只读
                }
                if(data[0].allowEmpty =="true"){
                    em.checkedNull=true;//允许为空
                }
                if(data[0].exactQuery =="true"){
                    em.ExactSearch=true;//精确查询
                }
                if(data[0].supportSort =="true"){
                    em.SupportSorting=true;//支持排序
                }
                em.$refs.show.value=data[0].displayWidget;
                em.formTable.nameInput=data[0].displayWidget;//显示控件
                em.formTable.lengthSection=data[0].lengthInterval;//长度区间
                em.formTable.testFunction=data[0].validateFunc;//验证函数
                em.formTable.displayFunction=data[0].displayFunc;//显示函数
                em.formTable.sortNumber=data[0].sort;//排序
                em.$refs.align.value=data[0].align//下拉框
                em.formTable.Twidth=data[0].widthSetting//宽度
                em.formTable.Keyword1=data[0].keywordOne//关键字1
                em.formTable.Keyword2=data[0].keywordTwo//关键字2
                em.formTable.Keyword3=data[0].keywordThree//关键字3
                em.isDisabled=true;
                em.queryObjectProperties();
            })
        },
        queryObjectProperties(){
            var data = {
                "url":em.queryDataUrl,
                "jsonData":{
                    "rowId":em.dataId
                },
                "obj":em
            }
            gmpAjax.showAjax(data,function(res){
                var data = res;
                em.formTable.tableInput = data[0].propertyCode+"("+data[0].propertyName+")"//业务对象属性中文名
            })
        },

        //点击对象属性
        getObjPro_1(){

        },
        //点击显示控件下拉框
        getShowControl_1(){

        },
        //点击对齐方式下拉框
        getAlign_1(){

        }
    },
    created(){
        this.relateBusiObjId = window.parent.topButtonObj.objId;//业务对象Id
        this.rightRowId = window.parent.properties.rowId;
        this.funcRowIds = window.parent.topButtonObj.rowObjId;

        if(window.parent.topButtonObj.isEdit){//编辑
            var args={"Block":{funcType:"functionBlockType"},"blockAttribute":{displayWidget:"showControl"}};
            TableKeyValueSet.init(args);
            this.loadComplete();
        }
    }
})