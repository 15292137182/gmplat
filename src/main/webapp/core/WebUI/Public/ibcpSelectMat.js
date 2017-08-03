/**
 * Created by Oswald on 2017/2/27.
 */
//选择物料
;var ibcpSelectMat = (function () {
  var proDivIndex = -1;

  //弹出单位选择框
  function ShowMat(CallBackFunc) {
    var url = getRootPath() + '/WebUI/Public/SelectMaterial.html';

    proDivIndex = ibcpLayer.ShowDiv(url, '选择产品/物料', '600px', '460px',
        function () {
          //获取产品代码和名称
          LoadMaterialCodeAndName();

          //搜索按钮事件
          $("#searchMaterialBtn").on('click', function () {
            LoadMaterialCodeAndName();
          });

          //单击行触发的事件
          $('#SelectMatTable').on('check.bs.table', function (e, row, element) {
            //有选中的行，按钮可用
            $("#selectEnsureBtn").attr("disabled", false);
          });

          //确认按钮事件
          $("#selectEnsureBtn").on('click', function () {
            var selectedData = $("#SelectMatTable").bootstrapTable(
                'getSelections')[0];
            CallBackFunc(selectedData);
            Close();
          });

          $("#selectCancelBtn").on('click', function () {
            Close();
          })
        });
  }

  //获取物料代码和名称数据
  function LoadMaterialCodeAndName() {
    var condition = $("#materialSearchInput").val();
    var param = {"str": condition, "valid": 1, "typeId": 1};//valid 1 生效 typeId 1 物料
    var url = serverPath + "materialCodes/findMaterialCodes";
    //设置确认按钮不可用
    $("#selectEnsureBtn").attr("disabled", true);

    ibcpAjax.Select(url, param, true, function (result) {
      $("#SelectMatTable").bootstrapTable();
      $("#SelectMatTable").bootstrapTable('load', result.data);
      $("#SelectMatTable").tableHideCheckbox();
    });
  }

  function Close() {
    ibcpLayer.Close(proDivIndex);
  }

  //给外部的接口
  return {
    CallMat: ShowMat,
    Close: Close
  };

})();