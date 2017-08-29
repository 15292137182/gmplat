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
        },
        labelPosition: 'right',
        queryUrl:serverPath+'/sequenceRule/queryById',
        resetUrl:serverPath+'/sequenceRule/reset',
    },
    methods:{
        //动态添加键值项
        dynamicRendering(key,value){
            var elFormItem=$("<div></div>");
            elFormItem.addClass("el-form-item");
            elFormItem.appendTo("#main");

            var elFormItemLabel=$("<label></label>");
            elFormItemLabel.addClass("el-form-item__label");
            elFormItemLabel.css("width","100px");
            elFormItemLabel.text(key);
            elFormItemLabel.appendTo(elFormItem);

            var elFormItemContent=$("<div></div>");
            elFormItemContent.addClass("el-form-item__content");
            elFormItemContent.css("margin-left","90px");
            elFormItemContent.appendTo(elFormItem);

            var elInput=$("<div></div>");
            elInput.addClass("el-input");
            elFormItemContent.css("padding","0px 20px 0px 10px");
            elInput.appendTo(elFormItemContent);

            var elInputInner=$("<input></input>");
            elInputInner.addClass("el-input__inner");
            elInputInner.attr("autocomplete","off");
            elInputInner.attr("type","text");
            elInputInner.attr("rows","2");
            elInputInner.attr("value",value);
            elInputInner.attr("validateevent","true");
            elInputInner.appendTo(elInput);
        },

        /*
         *  动态添加按钮
         */
        dynamicButton(){
            var elRow=$("<div></div>");
            elRow.addClass("el-row");
            elRow.appendTo("#main");

            var elCol11=$("<div></div>");
            elCol11.addClass("el-col el-col-11");
            elCol11.css("text-align","right");
            elCol11.appendTo(elRow);

            var save=$("<button></button>");
            save.attr("type","button");
            save.attr("onclick","save()");
            save.addClass("el-button el-button--primary");
            save.appendTo(elCol11);

            var spanSave=$("<span></span>");
            spanSave.text("保存");
            spanSave.appendTo(save);

            var elColOffset2=$("<div></div>");
            elColOffset2.addClass("el-col el-col-11 el-col-offset-2");
            elColOffset2.css("text-align","left");
            elColOffset2.appendTo(elRow);

            var cancel=$("<button></button>");
            cancel.attr("type","button");
            cancel.attr("onclick","cancel()");
            cancel.addClass("el-button el-button--primary");
            cancel.appendTo(elColOffset2);

            var spanCancel=$("<span></span>");
            spanCancel.text("取消");
            spanCancel.appendTo(cancel);
        },
    },
    created(){
        //获得此序列号规则的流水号的键值，动态添加键值行
        var data=config.keyValueContent;
        var len=data.length;
        for(var i=0;i<len;i++){
            var key=data[i].variableKey;
            var value=data[i].currenValue;
            this.dynamicRendering(key,value);
        }
        this.dynamicButton();
    }
})

//保存按钮
save=function(){
    // $.ajax({
    //     url:serverPath+'/sequenceRule/reset',
    //     type:"get",
    //     data:
    //         (rowId:"7a9327b8-c0d1-434d-a053-18e9fc52",
    //         serialId: "X",
    //         currentValue: "150"),
    //
    //     dataType:"jsonp",
    //     success:function(res){
    //         if(typeof callback == "function"){
    //             callback(res);
    //         }
    //     },
    //     error:function(){
    //         alert("请求失败")
    //     }
    // })
    gmpAjax.showAjax(serverPath+'/sequenceRule/reset',{
        rowId:"7a9327b8-c0d1-434d-a053-18e9fc52",
        serialId: "X",
        currentValue: "150"
    },function(res){
        ibcpLayer.Close(resetIndex);
    })
},
cancel=function() {
    ibcpLayer.Close(resetIndex);
}


