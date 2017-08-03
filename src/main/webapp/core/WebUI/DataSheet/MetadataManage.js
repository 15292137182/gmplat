/**
 * Created by admin on 2017/8/1.
 */
var vm = new Vue({
  el: "#app",
  data: {
    input: '',
    leftHeight: '',
    rightHeight: '',
    myLeftData: [],
    myRightData: []
  },
  methods: {
    searchTable() {
      this.$http.jsonp(serverPath + "/businObj/select", {
        str: this.input
      }, {
        jsonp: 'callback'
      }).then(function (res) {
        //清空表格
        this.myLeftData = [];
        for (var i = 0; i < res.data.content.data.length; i++) {
          var obj = {};
          obj.data = res.data.content.data[i];
          this.myLeftData.push(obj.data);
        }
      });
    },
    handleCurrentChange(val) {
      //点击拿到这条数据的值
      this.currentVal = val;
      var currentId = val.rowId;
      //查找右侧表的数据
      this.$http.jsonp(serverPath + "/businObjPro/select", {
        rowId: currentId
      }, {
        jsonp: 'callback'
      }).then(function (res) {
        console.log(res.data.content.data);
        //清空表格
        this.myRightData = [];
        for (var i = 0; i < res.data.content.data.length; i++) {
          var obj = {};
          obj.data = res.data.content.data[i];
          this.myRightData.push(obj.data);
        }
      });
    },
    addEvent() {
      operate = 1;
      var htmlUrl = 'MetadataAdd.html';
      divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增业务对象', '400px', '340px');
    },
    editEvent() {
      operate = 2;
      var htmlUrl = 'MetadataAdd.html';
      divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑业务对象', '400px', '340px',
          function () {
            //code值
            em.codeInput = vm.currentVal.objectCode;
            //不可点击
            em.disabled = true;
            em.nameInput = vm.currentVal.objectName;
            em.tableInput = vm.currentVal.tables;
            em.versionInput = vm.currentVal.relateTableRowId;
            em.dataId = vm.currentVal.proRowId;
          });
    },
    deleteEvent() {
      var deleteId = vm.currentVal.rowId;
      console.log(deleteId);
      this.$http.jsonp(serverPath + "/businObj/delete", {
        rowId: deleteId
      }, {
        jsonp: 'callback'
      }).then(function () {
        ibcpLayer.ShowOK('删除成功');
        this.searchTable();
      });
    }
  },
  created() {
    this.searchTable();
    $(document).ready(function () {
      vm.leftHeight = $(window).height() - 165;
      vm.rightHeight = $(window).height() - 120;
    });
    $(window).resize(function () {
      vm.leftHeight = $(window).height() - 165;
      vm.rightHeight = $(window).height() - 120;
    })
  }
})
