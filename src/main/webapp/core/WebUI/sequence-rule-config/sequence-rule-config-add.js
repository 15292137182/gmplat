/**
 * Created by jms on 2017/8/8.
 */
var add=new Vue({
    el:"#SequenceRuleConfigAdd",
    data:{
        labelPosition:'right',
        formTable:{
            seqCodeInput:'',
            seqNameInput:'',
            seqContentInput:'',
            despInput:'',
            versionInput:'',
        },
        disabled:false
    },
    methods:{
        confirm(){
            var datas = [
                this.$refs.seqCodeInput,
                this.$refs.seqNameInput,
                this.$refs.seqContentInput,
            ];
            for (var i = 0; i < datas.length; i++) {
                if (datas[i].value == '') {
                    ibcpLayer.ShowMsg(datas[i].placeholder);
                    return;
                }
            }
            if (operate == 1) {
                addObj.addOk(function(){
                    add.$http.jsonp(insert,{
                        seqCode: add.formTable.seqCodeInput,
                        seqName: add.formTable.seqNameInput,
                        seqContent: add.formTable.seqContentInput,
                        desp: add.formTable.despInput,
                        version: add.formTable.versionInput
                    }, {
                        jsonp: 'callback'
                    }).then(function (res) {
                        ibcpLayer.ShowOK(res.data.message);
                        config.search();
                        ibcpLayer.Close(divIndex);
                    });
                })
            }
            if(operate==2){
                editObj.editOk(function(){
                    add.$http.jsonp(modify,{
                        rowId:config.currentVal.rowId,
                        seqCode: add.formTable.seqCodeInput,
                        seqName: add.formTable.seqNameInput,
                        seqContent: add.formTable.seqContentInput,
                        desp: add.formTable.despInput,
                        version: add.formTable.versionInput
                    },{
                        jsonp:'callback'
                    }).then(function(res){
                        ibcpLayer.ShowOK(res.data.message);
                        config.search();
                        ibcpLayer.Close(divIndex);
                    })
                })
            }
        },
        cancel() {
            ibcpLayer.Close(divIndex);
        },
        menuClick(){
            content.dialogFormVisible=true;
        },
        show(){
            this.$http.jsonp(mock,{
                content:add.seqContentInput,
                args:{'YM':''},
            },{
                jsonp:'callback'
            }).then(function (res) {
                alert(res.data.data);
            })
        }
    }
})

var content=new Vue({
    el:"#content",
    data:{
        domains:[{
            name:'',option:'',key:'',value:''
        }],
        options:[
            {value:0,label:'否'},
            {value:1,label:'是'}],
        names:[
            {name:1,label:'常量'},
            {name:2,label:'变量'},
            {name:3,label:'日期'},
            {name:4,label:'序号'}
        ],
        dialogFormVisible:false,
        closeOnClickModal:false
    },
    methods:{
        cancel(){
            this.dialogFormVisible=false;
        },
        addDomain(){
            this.domains.push({
                name:'',option:'',key:'',value:'',
                dokey:Date.now()
            });
        },removeDomain(item){
            var index = this.domains.indexOf(item)
            if (index !== -1) {
                this.domains.splice(index, 1)
            }
        },confirm(formName){
            //判断是否有重复的key
            var num=this.$refs["domain.key"];
            var len=num.length;
            for(var m=0;m<len;m++){
                for(var n=0;n<m;n++){
                    if(num[m].currentValue==num[n].currentValue){
                        ibcpLayer.ShowMsg("键重复!");
                        return false;
                    }
                }
            }
            var mains = this.$refs[formName].$root.domains;
            var count = mains.length;
            var str = '';
            for (var i = 0; i < count; i++) {
                var name1 = mains[i].name;
                var key1 = mains[i].key;
                var value1=mains[i].value;
                var isShow1 = mains[i].option;
                var contentInput1='';

                if(name1==''||key1=='' ||value1==''){
                    ibcpLayer.ShowMsg("不能为空!");
                    return false;
                }
                if (name1 == '1') {
                    contentInput1 = '@{' + value1 + '}';
                }
                if (name1 == '2') {
                    contentInput1 = '#{'+key1+';' + value1 + ';'+isShow1+'}';
                }
                if (name1 == '3') {
                    contentInput1 = '${'+key1+';' + value1 + ';'+isShow1+'}';
                }
                if (name1 == '4') {
                    contentInput1 = '*{'+key1+';' + value1 + ';'+isShow1+'}';
                }
                str +=contentInput1+"&&";
            }
            if(str.length>0){
                str=str.substr(0,str.length-2);
            }
            this.dialogFormVisible=false;
            add.formTable.seqContentInput=str;
        }
    }
})
