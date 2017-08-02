/**
 * Created by Mr.Wang on 2017/4/11.
 */
/**
 * 权限类模块
 */
var ibcpAuthority = (function () {
  /**
   * 显示权限列表页面
   * @param options
   * @param callback
   */
  var showPage = function (callback) {
    var url = getRootPath() + "/WebUI/Public/ibcpAuthority.html";
    var page = ibcpLayer.ShowDiv(url, '权限查询', '800px', '500px', function () {
      var aDatas = null;
      //初始化表格
      $('#VFFTAuthorityTable').bootstrapTable();
      //表格数据加载
      _loadVFCAuthority();
      //初始化查询权限按钮事件
      _searchAuthority();

      //表格点击事件
      $('#VFFTAuthorityTable').on('check.bs.table', function (e, row) {
        aDatas = {
          'id': row['id'],
          'name': row['name'],
          'description': row['description']
        };
      });

      //确定选择权限按钮事件
      $('#btnVFASave').on('click', function () {
        ibcpLayer.Close(page);
        if (callback) {
          callback(aDatas);
        }
      });

      //取消选择权限按钮事件
      $('#btnVFACancel').on('click', function () {
        ibcpLayer.Close(page);
      });
    });
  };

  /**
   * 加载所有的权限列表
   * @param str
   * @private
   */
  var _loadVFCAuthority = function (str) {
    if (!str) {
      str = '';
    }

    var url = serverPath + 'authorities/findAuthorityDetail';
    var datas = {
      'str': str
    };
    ibcpAjax.Select(url, datas, true, function (result) {
      //列出所有的对象类型
      var data = result.data;
      $("#VFFTAuthorityTable").bootstrapTable("load", data);
    });
  };

  /**
   * 查询权限按钮事件
   * @private
   */
  var _searchAuthority = function () {
    $('#btnVFASearch').on('click', function () {
      var url = serverPath + 'authorities/findAuthorityDetail';
      var datas = {
        'str': $('#txtVFACondition').val()
      };
      ibcpAjax.Select(url, datas, true, function (result) {
        //列出所有的对象类型
        var data = result.data;
        $("#VFFTAuthorityTable").bootstrapTable("load", data);
      });
    });
  };

  return {
    show: showPage
  };
})();

