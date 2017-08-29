/**
 * Created by jms on 2017/8/29.
 */
var seqReset=new Vue({
    el:"#main",
    data:{

        resetform:{
            seqCode:'',
            seqName:'',
            seqContent:'',
            value:''
        },
        labelPosition: 'right',
        choseId:'',//选中的rowId
        queryUrl:serverPath+'/sequenceRule/queryById',
        resetUrl:serverPath+'/sequenceRule/reset',
    },
    methods:{
        //动态添加键值项
        dynamicRendering(){

            var elFormItem=$("<div></div>");
            elFormItem.addClass("el-form-item");
            elFormItem.appendTo("#main");

            var elFormItemLabel=$("<label></label>");
            elFormItemLabel.addClass("el-form-item__label");
            elFormItemLabel.css("width","90px");
            elFormItemLabel.text("c");
            elFormItemLabel.appendTo(elFormItem);

            var elFormItemContent=$("<div></div>");
            elFormItemContent.addClass("el-form-item__content");
            elFormItemContent.css("margin-left","90px");
            elFormItemContent.appendTo(elFormItem);

            var elInput=$("<div></div>");
            elInput.addClass("el-input");
            elInput.appendTo(elFormItemContent);

            var elInputInner=$("<input></input>");
            elInputInner.addClass("el-input__inner");
            elInputInner.attr("autocomplete","off");
            elInputInner.attr("type","text");
            elInputInner.attr("rows","2");
            elInputInner.attr("validateevent","true");
            elInputInner.appendTo(elInput);
        }
    },
    created(){

        var data=this.resetform.value;
        console.log("测试"+data);
        this.dynamicRendering();
    }
})
