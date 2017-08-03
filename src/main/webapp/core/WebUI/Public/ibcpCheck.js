/**
 * Created by liyuanquan on 2017/4/22.
 */
var ibcpCheck = (function () {
  function CheckFormat(datas, callback) {
    var url = serverPath + "foramt/formatCheck";
    var params = {
      "valueList": JSON.stringify(datas)
    }

    ibcpAjax.Select(url, params, true, function (result) {
      var datas = result.data;
      if (datas.length > 0) {
        for (var i = 0; i < datas.length; i++) {
          var CallBackParams = result.data[i];
          var ErrorDesc = CallBackParams["errorDesc"];
          var CallBackDOM = $("#" + CallBackParams["dom"]);

          if (CallBackParams) {
            ibcpLayer.ShowTips(ErrorDesc, CallBackDOM);
            return;
          }
        }
      } else if (callback) {
        callback();
      }
    });
  };

  // 给外部接口
  return {
    Format: CheckFormat
  }
})();

/**
 *@description:检查必填对象
 *@author:tudecai
 */
var MustInput = (function () {

  var validate = {
    //普通input
    commonInput: function (dom, eMsg) {
      var value = dom.attr("pvVal"),
          ret = false;
      //如果是空，弹出提示信息，返回false,否则返回true
      isNonEmpty(value) ? ibcpLayer.ShowTips(eMsg, dom) : ret = true;
      return ret;
    },
    stageType: function (dom, eMsg) {
      var valObj = JSON.parse(dom.attr("pvVal"));
      var procssTime = valObj.procssTime;
      var ret = false;
      //如果是空，弹出提示信息，返回false,否则返回true
      isNonEmpty(procssTime) ? ibcpLayer.ShowTips(eMsg, dom) : ret = true;
      return ret;
    }
  }

  //检查非空
  var isNonEmpty = function (value) {
    if (value === "" || value === null || value === "N/A" || value
        === undefined) {
      return true;
    }
    return false;
  }

  //缓存校验对象
  var cache = [];

  //添加校验对象方法,传入类型，dom节点，错误提示信息
  var addValidateObj = function (dom, errMsg, type) {
    if (type === "" || type === undefined || type === null) {
      type = "commonInput";
    }
    //添加缓存
    cache.push(function () {
      return validate[type].call(null, dom, errMsg);
    });
  }

  //开始遍历校验
  var startValidate = function () {
    for (var i = 0, validateFun; validateFun = cache[i++];) {
      //如果返回false,说明有空值
      var ret = validateFun();
      if (!ret) {
        return false;
      }
    }
    return true;
  }

  //外部接口
  return {
    add: addValidateObj,
    start: startValidate
  }
})();
