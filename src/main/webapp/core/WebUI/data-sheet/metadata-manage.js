/**
 * Created by admin on 2017/8/1.
 */
var basTop=new Vue({
  el:'#basTop',
  data:{
    addOpe:false,
    editOpe:true,
    deleteOpe:true,
    addAttr:false,
    deleteAttr:false,
    takeEffect:false,
    change:false,
  },
  methods:{
    addEvent() {
      operate = 1;
      var htmlUrl = 'metadata-add.html';
      divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增业务对象', '400px', '340px');
    },
    editEvent() {
      operate = 2;
      var htmlUrl = 'metadata-add.html';
      divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑业务对象', '400px', '340px', function () {
            //code值
            em.codeInput = basLeft.currentVal.objectCode;
            //不可点击
            em.disabled = true;
            em.firstIconEvent = true;
            em.nameInput = basLeft.currentVal.objectName;
            em.tableInput = basLeft.currentVal.tables;
            em.versionInput = basLeft.currentVal.relateTableRowId;
            em.dataId = basLeft.currentVal.proRowId;
          });
    },
    deleteEvent() {
      var deleteId = basLeft.currentVal.rowId;
      //console.log(deleteId);
      this.$http.jsonp(serverPath + "/businObj/delete", {
        rowId: deleteId
      }, {
        jsonp: 'callback'
      }).then(function (ref) {
        ibcpLayer.ShowOK(ref.data.message);
        basLeft.searchLeftTable();
        basRight.searchRightTable();
      });
    },
    addProp(){
      var htmlUrl = 'metadata-prop-add.html';
      divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增对象属性', '400px', '440px',function(){
        //键值类型(键值集合)
        proEm.$http.jsonp(serverPath + '/keySet/query', {
          str: ''
        }, {
          jsonp: 'callback'
        }).then(function (ref) {
          proEm.optionLeft=ref.data.data;
        });
        //值来源类型(键值集合)
        proEm.$http.jsonp(serverPath +'/keySet/query', {
          str: ''
        }, {
          jsonp: 'callback'
        }).then(function (ref) {
          proEm.optionRight=ref.data.data;
        });
      });

    },
    deleteProp(){
      //拿到ID
      var deleteId = basRight.rightVal
      this.$http.jsonp(serverPath + "/businObjPro/delete", {
        rowId: deleteId
      }, {
        jsonp: 'callback'
      }).then(function (ref) {
        ibcpLayer.ShowOK(ref.data.message);
        basLeft.searchLeftTable();
      });
    },
    affectProp(){
      this.$http.jsonp(serverPath + "/businObj/takeEffect", {
        rowId: basLeft.currentId
      }, {
        jsonp: 'callback'
      }).then(function (ref) {
        ibcpLayer.ShowOK(ref.data.message);
      });
    },
    changeProp(){

    }
  }
});

var basLeft=new Vue({
  "el":"#basLeft",
   data:{
    leftInput: '',
    leftHeight: '',
    myLeftData: [],
    //currentPage4: 1
  },
  methods:{
    searchLeftTable() {
      this.$http.jsonp(serverPath + "/businObj/query", {
        str: this.leftInput
      }, {
        jsonp: 'callback'
      }).then(function (res) {
        //console.log(res);
        if(res.data.data!==null){
          this.myLeftData = res.data.data;
          //默认选中第一行的数据
          this.currentChange(this.myLeftData[0]);
          basTop.editOpe=false;
          basTop.deleteOpe=false;
        }else{
          this.myLeftData=[];
          basTop.editOpe=true;
          basTop.deleteOpe=true;
        }
      });
    },
    currentChange(row, event, column) {
      //点击拿到这条数据的值
        console.log(row);
      //判断是否生效
      if(row.status==40){
        basTop.takeEffect=false;
      }else if(row.status==20){
        basTop.takeEffect=true;
      }
        this.currentVal = row;
        //关联表的数据
        this.relateTableRowId=row.relateTableRowId;
        //左边这一行的数据
        this.currentId = row.rowId;
        //查找右侧表的数据
        this.$http.jsonp(serverPath + "/businObjPro/query", {
          str:'',
          rowId: this.currentId
        }, {
          jsonp: 'callback'
        }).then(function (res) {
          console.log(res)
          if(res.data.data!==null) {
            basRight.myRightData = res.data.data;
            //右边有数据 默认点击第一行
            basRight.currentRChange(basRight.myRightData[0])

          }else{
            basRight.myRightData=[];
          }
        });
    },
    FindLFirstDate(row){
      this.$refs.myLeftData.setCurrentRow(row);
    },
    //handleSizeChange(val) {
    //  console.log(`每页 ${val} 条`);
    //},
    //handleCurrentChange(val) {
    //  console.log(`当前页: ${val}`);
    //}
  },
  created() {
    this.searchLeftTable();
    $(document).ready(function () {
      basLeft.leftHeight = $(window).height() - 154;
    });
    $(window).resize(function () {
      basLeft.leftHeight = $(window).height() - 154;
    })

  },
  updated() {
    this.FindLFirstDate(this.myLeftData[0]);
  }
});

var basRight=new Vue({
  "el":"#basRight",
  data:{
    rightInput: '',
    rightHeight: '',
    myRightData: []
  },
  methods:{
    searchRightTable() {
      this.$http.jsonp(serverPath + "/businObjPro/query", {
        str: this.rightInput,
        rowId:basLeft.currentId
      }, {
        jsonp: 'callback'
      }).then(function (res) {
        basRight.myRightData= res.data.data;
      });
    },
    currentRChange(row, event, column) {
      //点击拿到这条数据的值
      if(row!=undefined){
        //console.log(row)
        this.rightVal = row.rowId;
       // console.log(this.rightVal)
      }

    },
    FindRFirstDate(row){
      this.$refs.myRightData.setCurrentRow(row);
    }
  },
  created() {
    //this.searchRightTable();
    $(document).ready(function () {
      basRight.rightHeight = $(window).height() - 154;
    });
    $(window).resize(function () {
      basRight.rightHeight = $(window).height() - 154;
    })
  },
  updated() {
    if(this.myRightData!=null){
      this.FindRFirstDate(this.myRightData[0]);
    }
  }
})