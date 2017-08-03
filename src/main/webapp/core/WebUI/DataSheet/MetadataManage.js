/**
 * Created by admin on 2017/8/1.
 */
var basTop=new Vue({
  el:'#basTop',
  data:{

  },
  methods:{
    addEvent() {
      operate = 1;
      var htmlUrl = 'MetadataAdd.html';
      divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增业务对象', '400px', '340px');
    },
    editEvent() {
      operate = 2;
      var htmlUrl = 'MetadataAdd.html';
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
      console.log(deleteId);
      this.$http.jsonp(serverPath + "/businObj/delete", {
        rowId: deleteId
      }, {
        jsonp: 'callback'
      }).then(function (ref) {
        console.log(ref);
        ibcpLayer.ShowOK(ref.data.message);
        basLeft.searchLeftTable();
      });
    },
    addProp(){
      var htmlUrl = 'MetadataPropAdd.html';
      divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增对象属性', '400px', '440px',function(){
          this.$http.jsonp()
      });
    },
    editProp(){

    },
    affectProp(){

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
    myLeftData: []
  },
  methods:{
    searchLeftTable() {
      this.$http.jsonp(serverPath + "/businObj/select", {
        str: this.leftInput
      }, {
        jsonp: 'callback'
      }).then(function (res) {
        if(res.data.content.data!==''){
          console.log(res);
          this.myLeftData = res.data.content.data;
          //默认查找第一行右边的数据
          this.currentChange(this.myLeftData[0])
        }
      });
    },
    currentChange(row, event, column) {
      //点击拿到这条数据的值
      //console.log(row)
      this.currentVal = row;
      this.currentId = row.rowId;
      //查找右侧表的数据
      this.$http.jsonp(serverPath + "/businObjPro/select", {
        rowId: this.currentId
      }, {
        jsonp: 'callback'
      }).then(function (res) {
          basRight.myRightData = res.data.content.data;
      });
    },
    FindFirstDate(row){
      this.$refs.myLeftData.setCurrentRow(row);
    }
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
    this.FindFirstDate(this.myLeftData[0]);
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
      this.$http.jsonp(serverPath + "/businObjPro/select", {
        str: this.rightInput,
        rowId:basLeft.currentId
      }, {
        jsonp: 'callback'
      }).then(function (res) {
        basRight.myRightData= res.data.content.data;
      });
    },
    FindFirstDate(row){
      this.$refs.myRightData.setCurrentRow(row);
    }
  },
  created() {
    this.searchRightTable();
    $(document).ready(function () {
      basRight.rightHeight = $(window).height() - 154;
    });
    $(window).resize(function () {
      basRight.rightHeight = $(window).height() - 154;
    })
  },
  updated() {
    this.FindFirstDate(this.myRightData[0]);
  }
})