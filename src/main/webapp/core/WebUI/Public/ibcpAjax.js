/**
 * Created by qijialin on 2016/12/26.
 */

/**
 * 数据通信 ibcpAjax 类
 * @type {{}}
 */
;var ibcpAjax = (function () {

  /**
   *查询方法
   * @param dataUrl   数据请求的URL
   * @param jsonData  Json数据参数
   * @param isAsync   是否异步 true/false
   * @param callBack  成功回调函数
   * @param noLoop    不显示等待动画
   * @constructor
   */
  var ibcpSelect = function (dataUrl, jsonData, isAsync, callBack, noLoop) {
    var type = 'get';
    CallAjax(dataUrl, jsonData, type, isAsync, callBack, noLoop);
  }

  /**
   *查询方法  PV 专用 不处理 异常信息
   * @param dataUrl   数据请求的URL
   * @param jsonData  Json数据参数
   * @param isAsync   是否异步 true/false
   * @param callBack  成功回调函数
   * @param noLoop    不显示等待动画
   * @constructor
   */
  var ibcpSelectPV = function (dataUrl, jsonData, isAsync, callBack, noLoop) {
    var type = 'get';
    CallAjax(dataUrl, jsonData, type, isAsync, callBack);
    $.ajax({
      url: dataUrl,
      data: jsonData,
      type: type,
      traditional: tra,
      xhrFields: {withCredentials: true},
      dataType: "json",
      async: isAsync,        //默认为ture表示异步，false同步--ajax的回调方法中弱还需要掉用其他ajax方法 必须false
      success: function (result) {
        callBack(result);
      },
      error: function () {
        ibcpLayer.ShowMsg('通讯异常!');
      }
    });
  }

  /**
   *添加方法
   * @param dataUrl   数据请求的URL
   * @param jsonData  Json数据参数
   * @param isAsync   是否异步 true/false
   * @param callBack  成功回调函数
   * @param noLoop    不显示等待动画
   * @constructor
   */
  var ibcpInsert = function (dataUrl, jsonData, isAsync, callBack, noLoop) {
    var type = 'post';
    CallAjax(dataUrl, jsonData, type, isAsync, callBack, noLoop);
  }

  /**
   *修改方法
   * @param dataUrl   数据请求的URL
   * @param jsonData  Json数据参数
   * @param isAsync   是否异步 true/false
   * @param callBack  成功回调函数
   * @param noLoop    不显示等待动画
   * @constructor
   */
  var ibcpUpdate = function (dataUrl, jsonData, isAsync, callBack, noLoop) {
    var type = 'put';
    CallAjax(dataUrl, jsonData, type, isAsync, callBack, noLoop);
  }

  /**
   *删除方法
   * @param dataUrl   数据请求的URL
   * @param jsonData  Json数据参数
   * @param isAsync   是否异步 true/false
   * @param callBack  成功回调函数
   * @param noLoop    不显示等待动画
   * @constructor
   */
  var ibcpDelete = function (dataUrl, jsonData, isAsync, callBack, noLoop) {
    var type = 'delete';
    CallAjax(dataUrl, jsonData, type, isAsync, callBack, noLoop);
  }

  /**
   *Ajax 访问后台数据
   * @param dataUrl   数据请求的URL
   * @param jsonData  Json数据参数
   * @param type      请求类型
   * @param isAsync   是否异步 true/false
   * @param callBack  成功回调函数
   * @param noLoop  不显示等待动画
   * @constructor
   */
  var CallAjax = function (dataUrl, jsonData, type, isAsync, callBack, noLoop) {
    var tra = true; //是否对参数进行序列化 get模式下不需要
    if (!noLoop) {
      var index = ibcpLayer.Load(2);
    }
    if (type == 'get') {
      tra = false;
    }
    $.ajax({
      url: dataUrl,
      data: jsonData,
      type: type,
      traditional: tra,
      //xhrFields:{ withCredentials:true},
      dataType: "json",
      async: isAsync,        //默认为ture表示异步，false同步--ajax的回调方法中弱还需要掉用其他ajax方法 必须false
      success: function (result) {
        var errCode = result.errorCode;
        if (errCode == 0) {
          ibcpLayer.Close(index);
          callBack(result);
        }
        else {
          ibcpLayer.Close(index);
          //ShowErrInfo(errCode);
        }
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        var status = XMLHttpRequest.status;
        if (status == 404 || status == 405 || status == 500) {
          AjaxServerErr[status](XMLHttpRequest.responseText);
        } else {
          ibcpLayer.ShowMsg('通讯异常!');
        }
        ibcpLayer.Close(index);
      }
    });
  }

  //ajax异常
  var AjaxServerErr = new Object({
    404: function () {
      ibcpLayer.ShowMsg("[404]路径未找到!");
    },
    405: function () {
      ibcpLayer.ShowMsg("[405]请求方式错误!");
    },
    500: function (responseText) {
      var errMes = responseText.match(/\<h1\>(.*)\<\/h1\>/)[1];
      //var errMes1 = responseText.match(/\<h1\>(.*)\<\/h1\>/)[1].split(":")[1];
      if (errMes == undefined) {
        ibcpLayer.ShowMsg(errMes);
        //ibcpLayer.ShowMsg("[500]内部服务器错误!");
      } else {
        ibcpLayer.ShowMsg(errMes);
      }
    },
  })

  function ShowErrInfo(errCode) {
    var dataUrl = serverPath + 'errorCode/findErrorCode';
    var jsonData = {'code': errCode};
    $.ajax({
      url: dataUrl,
      data: jsonData,
      type: 'get',
      traditional: true,
      xhrFields: {withCredentials: true},
      dataType: "json",
      async: true,        //默认为ture表示异步，false同步--ajax的回调方法中弱还需要掉用其他ajax方法 必须false
      success: function (result) {
        var eCode = result.errorCode;
        if (eCode == 0) {
          if (result.data == null) {
            ibcpLayer.ShowMsg('[' + errCode + ']：未找到该代码错误描述');
          }
          else {
            ibcpLayer.ShowMsg('[' + errCode + ']：' + result.data.description);
          }
        }
        else {
          ibcpLayer.ShowMsg('网络异常或服务器故障,请联系管理员');
        }
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        //ibcpLayer.Close(index);
        ibcpLayer.ShowMsg('通讯异常!');
      }
    });
  }

  return {
    Select: ibcpSelect,
    Insert: ibcpInsert,
    Update: ibcpUpdate,
    Delete: ibcpDelete,
    Ajax: CallAjax
  };
})();




