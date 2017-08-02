/**
 * Created by Oswald on 2017/3/23.
 */
//选择配方模块
;var ibcpSelectRecipe = (function () {
  var index = -1;

  function Call(callBackFunc) {
    var html = getRootPath() + '/WebUI/Public/SelectRecipe.html';
    index = ibcpLayer.ShowDiv(html, '选择配方', '700px', '540px', function () {
      //初始化获取数据
      findRecipeData();

      //确认按钮事件
      $('#Recipe_Info_Confirm').on('click', function () {
        var selectedRecipe = $('#Recipe_Select_Table').bootstrapTable(
            'getSelections')[0];
        callBackFunc(selectedRecipe);
        CloseThisLayer();
      });

      //查找按钮事件
      $('#PSCSearch').on('click', function () {
        findRecipeData();
      });

      //取消
      $("#Recipe_Info_Cancel").on('click', function () {
        CloseThisLayer();
      })
    });
  }

  //获取数据
  function findRecipeData() {
    // var matCode = $('#Product_Code').val();
    var matId = $('#Product_Code').attr('ibcdata');
    var keyStr = $('#txtPSCcontent').val();
    var datas = {'matId': matId, 'str': keyStr};
    // var datas = {'matCode':matCode,'str':keyStr};

    var recipeSelectUrl = serverPath + "recipe/findRecipeDetail";
    ibcpAjax.Select(recipeSelectUrl, datas, true, function (result) {
      var data = result.data;
      $('#Recipe_Select_Table').bootstrapTable();
      $('#Recipe_Select_Table').bootstrapTable('load', data);
      if (data.length > 0) {
        //设置选中行
        $('#Recipe_Select_Table').bootstrapTable('check', 0);
      }
    });
  }

  //关闭
  function CloseThisLayer() {
    ibcpLayer.Close(index);
  }

  return {
    Call: Call
  }

})();