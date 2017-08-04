/**
 * Created by andim on 2017/8/1.
 */
var vm = new Vue({
  el: "#app",
  data: {
    input: '',
    myData: [],
    leftHeight: '',
  },
  methods: {
    get () {
      this.$http.jsonp(serverPath+'maint/select', {
        "str": this.input
      }, {
        jsonp: 'callback'
      }).then(function (res) {
        this.myData = res.data.content.data;
        vm1.FindData(vm.myData[0].rowId);
      })
    },
    click(row, event, column) {
      vm1.FindData(row.rowId);
    },
    FindOk(row) {
      this.$refs.myTable.setCurrentRow(row);
    }
  },
  created() {
    this.get();
    $(document).ready(function () {
      vm.leftHeight = $(window).height() - 107;
    });
    $(window).resize(function () {
      vm.leftHeight = $(window).height() - 107;
    })
  },
  updated() {
    this.FindOk(this.myData[0]);
  }
});

var vm1 = new Vue({
  el: '#right',
  data: {
    rightData: [],
    rightHeight: '',
  },
  methods: {
    FindData(id) {
      this.$http.jsonp(serverPath+'/maint/selectById', {
        "rowId": id
      }, {
        jsonp: 'callback'
      }).then(function (res) {
        this.rightData = res.data.content.data;
      })
    }
  },
  created() {
    $(document).ready(function () {
      vm1.rightHeight = $(window).height() - 107;
    });
    $(window).resize(function () {
      vm1.rightHeight = $(window).height() - 107;
    })
  },
});

var mb = new Vue({
  el: '#myButton'
})