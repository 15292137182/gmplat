/**
 * Created by jms on 2017/8/8.
 */
var add=new Vue({
    el:"#SequenceRuleConfigAdd",
    data:{
        name:'',
        names:[
            {name:1,label:'常量'},
            {name:2,label:'变量'},
            {name:3,label:'日期'},
            {name:4,label:'序号'}
        ],
        disabled:false,
        labelPosition:'right',
        formTable:{
            seqCodeInput:'',
            seqNameInput:'',
            seqContentInput:'',
            despInput:'',
            versionInput:'',
        },
        rowId:'',//选中的rowId

        rules: {
            seqCodeInput: [
                {required: true, message: '请输入代码', trigger: 'blur'},
            ],
            seqNameInput: [
                {required: true, message: '请输入名称', trigger: 'blur'}
            ],
        }
    },
    methods:{
        add(){
            switch(this.name){
                case 1:
                    this.addCon();
                    break;
                case 2:
                    this.addAlter();
                    break;
                case 3:
                    this.addDate();
                    break;
                case 4:
                    this.addNum();
                    break;
                default:
                    break;
            }
        },
        addCon(){
            //常量
            var constr='<div class="el-row conRow">' ;
            constr+='       <div class="el-col-1" style="margin-left: 2px">';
            constr+='           <button type="button" class="el-button el-button--text"><i class="el-icon-delete"></i></button>';
            constr+='       </div>';
            constr+='       <label class="el-form-item__label">常量：</label>';
            constr+='           <div class="el-col-4">';
            constr+='               <div class="el-form-item el-form-item__content" >';
            constr+='                   <input autocomplete="off" placeholder="请输入值" type="text" rows="2" validateevent="true" class="el-input__inner conValue">';
            constr+='               </div>';
            constr+='           </div>';
            constr+='   </div>';
            $("#content").append(constr);
        },
        addAlter(){
            //变量
            var alterstr='<div class="el-row alterRow">';
            alterstr+='   <div class="el-col-1" style="margin-left: 2px">';
            alterstr+='    <button type="button" class="el-button el-button--text"><i class="el-icon-delete"></i></button>';
            alterstr+='   </div>';
            alterstr+='    <label class="el-form-item__label">变量：</label>';
            alterstr+='<div class="el-col-4">';
            alterstr+='    <div class="el-input">';
            alterstr+='    <i class="el-input__icon el-icon-caret-bottom"></i>';
            alterstr+='    <input autocomplete="off" placeholder="请选择对象" readonly="readonly" icon="caret-bottom" type="text" rows="2" class="el-input__inner">';
            alterstr+='   </div>';
            alterstr+='    </div>';

            alterstr+='   <div class="el-col-4" style="margin-left: 15px">';
            alterstr+='   <div class="el-input">';
            alterstr+='   <i class="el-input__icon el-icon-caret-bottom"></i>';
            alterstr+='   <input autocomplete="off" placeholder="请选择属性" readonly="readonly" icon="caret-bottom" type="text" rows="2" class="el-input__inner">';
            alterstr+='    </div>';
            alterstr+='    </div>';
            alterstr+='    <label class="el-form-item__label" style="width: 50px">或</label>';
            alterstr+='    <div class="el-col-2">';
            alterstr+='    <div class="el-form-item el-form-item__content">';
            alterstr+='         <input autocomplete="off" type="text" rows="2" placeholder="请输入键" validateevent="true" class="el-input__inner alterKey"/>';
            alterstr+='    </div>';
            alterstr+='    </div>';
            alterstr+='    <label class="el-form-item__label" style="width: 80px">默认值</label>';
            alterstr+='   <div class="el-col-3">';
            alterstr+='   <div class="el-form-item el-form-item__content">';
            alterstr+='         <input autocomplete="off" type="text" rows="2" placeholder="请输入默认值" validateevent="true" class="el-input__inner alterValue"/>';
            alterstr+='    </div>';
            alterstr+='   </div>';
            alterstr+='    <label class="el-form-item__label" style="width: 80px">是否显示</label>';
            alterstr+='    <div style="margin-top: 10px" class="isShow">';
            alterstr+='     <label class="el-checkbox">';
            alterstr+='    <span class="el-checkbox__input is-checked">';
            alterstr+='    <span class="el-checkbox__inner"> </span>';
            alterstr+='    <input type="checkbox" class="el-checkbox__original" value=""></span>';
            alterstr+='     </label>';
            alterstr+='   </div>';
            alterstr+='    </div>';

            $("#content").append(alterstr);
        },

        addDate(){
            //日期
            var datestr='<div class="el-row dateRow">';
            datestr+='    <div class="el-col-1" style="margin-left: 2px">';
            datestr+='    <button type="button" class="el-button el-button--text"><i class="el-icon-delete"></i></button>';
            datestr+='    </div>';
            datestr+='    <label class="el-form-item__label">日期：</label>';
            datestr+='<div class="el-col-4">';
            datestr+='    <div class="el-form-item el-form-item__content">';
            datestr+='    <input autocomplete="off" type="text" rows="2" placeholder="请输入日期" validateevent="true" class="el-input__inner dateValue">';
            datestr+='    </div>';
            datestr+='    </div>';
            datestr+='    <label class="el-form-item__label">（如：yyyy-MM-dd）</label>';
            datestr+='    <label class="el-form-item__label" style="width: 80px">是否显示</label>';
            datestr+='    <div style="margin-top: 10px" class="isShow">';
            datestr+='     <label class="el-checkbox">';
            datestr+='    <span class="el-checkbox__input is-checked">';
            datestr+='    <span class="el-checkbox__inner"> </span>';
            datestr+='    <input type="checkbox" class="el-checkbox__original" value=""></span>';
            datestr+='     </label>';
            datestr+='    </div>';
            datestr+='   </div>';

            $("#content").append(datestr);
        },
        addNum(){
            //序号
            var numstr='<div class="el-row numRow">';
            numstr+='    <div class="el-col-1" style="margin-left: 2px">';
            numstr+='    <button type="button" class="el-button el-button--text"><i class="el-icon-delete"></i></button>';
            numstr+='   </div>';
            numstr+='    <label class="el-form-item__label">序号：</label>';
            numstr+='<div class="el-col-4">';
            numstr+='   <div class="el-form-item el-form-item__content">';
            numstr+='   <input autocomplete="off" type="text" placeholder="请输入键" rows="2" validateevent="true" class="el-input__inner numKey">';
            numstr+='    </div>';
            numstr+='   </div>';
            numstr+='   <label class="el-form-item__label" style="width: 50px;">长度</label>';
            numstr+='   <div class="el-col-2 len">';
            numstr+='   <div class="el-form-item el-form-item__content">';
            numstr+='    <input autocomplete="off" type="text" rows="2" validateevent="true" class="el-input__inner numLen">';
            numstr+='    </div>';
            numstr+='   </div>';
            numstr+='   <label class="el-form-item__label" style="width: 50px">循环</label>';
            numstr+='   <div class="el-col-3">';
            numstr+='   <div class="el-input  xunhuan" style="cursor:pointer" >';
            numstr+='  <i class="el-input__icon el-icon-caret-bottom "></i>';
            numstr+='  <input autocomplete="off" placeholder="请选择" readonly="readonly" icon="caret-bottom" type="text" rows="2" class="el-input__inner">';
            numstr+='   </div>';
            numstr+='  </div>';

            numstr+='   <label class="el-form-item__label" style="width: 100px;">关联变量键</label>';
            numstr+='   <div class="el-col-2 key">';
            numstr+='  <div class="el-form-item el-form-item__content">';
            numstr+='   <input autocomplete="off" type="text" rows="2" validateevent="true" class="el-input__inner conAlterKey">';
            numstr+='  </div>';
            numstr+='  </div>';
            numstr+='    <label class="el-form-item__label" style="width: 80px">是否显示</label>';
            numstr+='    <div style="margin-top: 10px" class="isShow">';
            numstr+='     <label class="el-checkbox">';
            numstr+='    <span class="el-checkbox__input is-checked">';
            numstr+='    <span class="el-checkbox__inner"></span>';
            numstr+='    <input type="checkbox" class="el-checkbox__original" value=""></span>';
            numstr+='     </label>';
            numstr+='  </div>';
            numstr+='  </div>';
            numstr+='  </div>';

            $("#content").append(numstr);
        },
        //确定按钮
        confirm(){
            //新增
            if (window.parent.config.operate == 1) {
                addObj.addOk(function(){
                    var data={
                        "url":window.parent.insertUrl,
                        "jsonData":{
                            seqCode:add.formTable.seqCodeInput,
                            seqName:add.formTable.seqNameInput,
                            seqContent:add.formTable.seqContentInput,
                            belongModule:add.formTable.belongModuleInput,
                            belongSystem:add.formTable.belongSystemInput,
                            desp:add.formTable.despInput
                        },
                        "obj":add
                    }
                    gmpAjax.showAjax(data,function(res){
                        queryData.getData(window.parent.queryPage,window.parent.config.input,window.parent.config);
                        parent.layer.close(window.parent.config.divIndex);
                    })
                })
            }
            //编辑
            if(window.parent.config.operate ==2){
                editObj.editOk(function(){
                    var data={
                        "url":window.parent.modifyUrl,
                        "jsonData":{
                            rowId:window.parent.config.rowId,
                            seqCode:add.formTable.seqCodeInput,
                            seqName:add.formTable.seqNameInput,
                            seqContent:add.formTable.seqContentInput,
                            belongModule:add.formTable.belongModuleInput,
                            belongSystem:add.formTable.belongSystemInput,
                            desp:add.formTable.despInput
                        },
                        "obj":add
                    }
                    gmpAjax.showAjax(data,function(res){
                        queryData.getData(window.parent.queryPage,window.parent.config.input,window.parent.config);
                        parent.layer.close(window.parent.config.divIndex);
                    })
                })
            }
        },
        cancel() {
            parent.layer.close(window.parent.config.divIndex);
        },
        menuClick(){
            content.dialogFormVisible=true;
        },
        show(){
            this.$http.jsonp(mock,{
                content:add.formTable.seqContentInput,
                args:{'a':''},
            },{
                jsonp:'callback'
            }).then(function (res) {
                alert(res.data.data);
            })
        },

        initContent(dataContent){
            //将内容放在对应的元素中
            var arr=dataContent.split('&&');
            for(var i=0;i<arr.length;i++){
                var first=arr[i].charAt(0);
                var alterArr=arr[i].slice(2,-1).split(';')
                //常量
                if(first=='@'){
                    add.addCon();
                    $(".conValue").val(alterArr[0]);
                }
                //变量
                if(first=='#'){
                    add.addAlter();
                    $('.alterKey').val(alterArr[0]);
                    $('.alterValue').val(alterArr[1]);
                }
                //日期
                if(first=='$'){
                    add.addDate();
                    $('.dateValue').val(alterArr[0]);
                }
                //序号
                if(first=='*'){
                    add.addNum();
                    $('.numKey').val(alterArr[0]);
                    var numArr=alterArr[1].split('-');
                    $('.numLen').val(numArr[0]);
                    if(numArr.length==2){
                        $('.conAlterKey').val(numArr[1]);
                    }
                    if(numArr.length==3){
                        $('.conAlterKey').val(numArr[2]);
                    }
                }
            }
        },
        //编辑时将数据绑定在控件中
        initValue(){
            this.rowId=window.parent.config.rowId;
            var data={
                "url":window.parent.queryById,
                "jsonData":{rowId:this.rowId},
                "obj":this
            }
            gmpAjax.showAjax(data,function(res){
                var data=res;
                add.formTable.seqCodeInput=data[0].seqCode;
                add.formTable.seqNameInput=data[0].seqName;
                add.formTable.seqContentInput=data[0].seqContent;
                add.formTable.belongModuleInput=data[0].belongModule;
                add.formTable.belongSystemInput=data[0].belongSystemInput;
                add.formTable.despInput=data[0].desp;
                add.formTable.versionInput=data[0].version;

                add.initContent(add.formTable.seqContentInput);
            })
        },
    },
    created(){
        if(window.parent.config.operate ==1){
            this.addCon();
            this.addAlter();
            this.addDate();
            this.addNum();
        }

        if(window.parent.config.operate == 2){
            this.initValue();
            this.initContent(this.formTable.seqContentInput);
        }
    }
})

/**
 * 循环下拉框
 */
$(document).on("click",".xunhuan",function(){
    if($(this).children("i").hasClass("is-reverse")){
        $(this).children("i").removeClass("is-reverse");
        $(".circle").css("display","none");
    }else{
        $(this).children("i").addClass("is-reverse");
        $(".circle").css("top","100px");
        $(".circle").css("display","block");
    }
});

/*
 * 单击单选框
 */
$(document).on("click", ".el-checkbox__original", function() {
    if($(this).parent().hasClass("is-checked"))
        $(this).parent().removeClass("is-checked");
    else
        $(this).parent().addClass("is-checked");
});

/*
 *删除图标
 */
$(document).on("click", ":button", function() {
     $(this).parent().parent().remove(".el-row");
});

/**
 * 失去焦点事件
 */
$(document).on("blur",":input",function(){
    var len=$("#content").children().length;
    var str='';
    $("#content").children().each(function(i,n){
        var conValue='';
        var cls=$(n);
        if(cls.hasClass("conRow")){
            conValue='@{'+$(n).children("div .el-col-4").children().children().val()+'}';
        }
        if(cls.hasClass("alterRow")){
            var alterKey=$(n).children("div .el-col-2").children().children().val();
            var alterValue=$(n).children("div .el-col-3").children().children().val();

            //判断是否显示，显示1，隐藏0
            var isAlterShow=$(n).children("div .isShow").children().children();
            if(isAlterShow.hasClass("is-checked"))
                isAlterShow='1';
            else
                isAlterShow='0';
            conValue='#{'+alterKey+";"+alterValue+";"+isAlterShow+'}';
        }
        if(cls.hasClass("dateRow")){
            var dateValue=$(n).children("div .el-col-4").children().children().val();
            var isDateShow='';
            //判断是否显示，显示1，隐藏0
            var isDateShow=$(n).children("div .isShow").children().children();
            if(isDateShow.hasClass("is-checked"))
                isDateShow='1';
            else
                isDateShow='0';
            conValue = '${'+dateValue+';'+isDateShow+'}';
        } if(cls.hasClass("numRow")){
            var numKey=$(n).children("div .el-col-4").children().children().val();
            var len=$(n).children("div .len").children().children().val();
            var conKey=$(n).children("div .key").children().children().val();

            //判断是否显示，显示1，隐藏0
            var isNumShow=$(n).children("div .isShow").children().children();
            if(isNumShow.hasClass("is-checked"))
                isNumShow='1';
            else
                isNumShow='0';
            conValue='*{'+numKey+';' + len +'-'+conKey+ ';'+isNumShow+'}';
        }
        str+=conValue+"&&";
    })
    if(str.length>0){
        str=str.substr(0,str.length-2);
    }
    add.formTable.seqContentInput=str;
})
