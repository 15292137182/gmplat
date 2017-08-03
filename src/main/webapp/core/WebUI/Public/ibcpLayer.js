/**
 * Created by qijialin on 2017/02/27.
 */

/**
 * 自定义 ibcpLayer 类
 * @type {{}}
 */
var ibcpLayer = (function () {
  //自定义索引器
  var ibcpLayerIndex = 29891014;
  //公开 原生的layer 采用 自定义索引器 ibcpLayerIndex
  //options 参见
  var myLayer = function (options) {
    layer.zIndex = ibcpLayerIndex + 10;
    layer.open(options);
    //options.zIndex = zIndex
  };

  /**
   * 弹出 Div Html 层 此方法动态创建 div 自动销毁 目前有缺陷
   * @param divHtmlUrl       div元素 对应的 html Url
   * @param loadFunction  加载div或Html时执行的方法
   * @param title         标题
   * @param width         宽度
   * @param height        高度
   * @returns {*}         返回弹出层的index
   * @constructor
   */
  var ShowDiv = function (divHtmlUrl, title, width, height, loadFunction) {
    ibcpLayerIndex = ibcpLayerIndex + 10;
    var divIndex = layer.open({
      //layer提供了5种层类型。可传入的值有：0（信息框，默认）；1（页面层）；2（iframe层）；3（加载层）；4（tips层）。
      // 若你采用layer.open({type: 1})方式调用，则type为必填项（信息框除外）
      type: 1,
      //动画
      anim: 5,
      //title ：String
      //title: ['文本', 'font-size:18px;']
      //title: false 不显示标题栏
      title: [title, 'font-size:18px;font-weight:bold;'],
      //是否固定弹出
      fix: false,
      //最大小化按钮
      maxmin: false,
      //控制点击弹层外区域关闭
      shadeClose: false,
      //窗口大小 area: ['400px', '500px'] or auto 自适应大小
      area: [width, height],
      //area: '350px',
      //iframe层的url
      //content: 'http://www.baidu.com',
      //content: "",
      //是否允许拉伸
      resize: false,
      //浏览器滚动条
      scrollbar: false,
      zIndex: ibcpLayerIndex, //重点1
      //成功弹出      layero:当前layer层对象；index 当前layer层对象索引
      success: function (layero, index) {
        //找出当前layer弹出层的div对象 作为 容器
        var div = layero.find(".layui-layer-content");
        div.attr("layerIndex", index);
        div.css("overflow", 'hidden');
        div.load(divHtmlUrl, function () {
          //取消父页面的焦点
          var aaa = layero.find(
              ".layui-layer-ico,.layui-layer-close,.layui-layer-close1");
          aaa.focus();    //先设置弹出页的焦点
          aaa.blur();     //再取消焦点
          if (loadFunction) {
            loadFunction();
          }
        });
      }
    });
    return divIndex;
  };

  /**
   * 弹出 iframe 层
   * @param divElement    div元素
   * @param title         标题
   * @param width         宽度
   * @param height        高度
   * @param hasMinMax     是否显示最大化最小化按钮
   * @param cancelFunction   层
   * @returns {*}         返回弹出层的index
   * @constructor
   */
  var ShowReport = function (iframeUrl, title, width, height, hasMinMax,
      cancelFunction) {
    ibcpLayerIndex = ibcpLayerIndex + 10;
    var divIndex = layer.open({
      //layer提供了5种层类型。可传入的值有：0（信息框，默认）；1（页面层）；2（iframe层）；3（加载层）；4（tips层）。
      // 若你采用layer.open({type: 1})方式调用，则type为必填项（信息框除外）
      type: 2,
      //动画
      anim: 5,
      //title ：String
      //title: ['文本', 'font-size:18px;']
      //title: false 不显示标题栏
      title: [title, 'font-size:18px;font-weight:bold;'],
      //是否固定弹出
      fix: false,
      //最大小化按钮
      maxmin: hasMinMax,
      //控制点击弹层外区域关闭
      shadeClose: false,
      //窗口大小 area: ['400px', '500px'] or auto 自适应大小
      area: [width, height],
      //area: '350px',
      //iframe层的url
      //content: 'http://www.baidu.com',
      content: iframeUrl,
      btn: ['打印', '关闭'],
      btn1: function (index, layero) {
        //小心跨域
        //找到iframe
        var pt = layero.find("iframe")[0];
        //打印
        pt.contentWindow.print();
      },
      btn2: function (index, layero) {
        //按钮【按钮二】的回调
        layer.close(index);
      },
      //是否允许拉伸
      resize: false,
      //浏览器滚动条
      scrollbar: false,
      zIndex: ibcpLayerIndex, //重点1
      cancel: cancelFunction,
      end: function () {
      }
    });
    //alert('return');
    return divIndex;
  };

  /**
   * 弹出 iframe 层
   * @param divElement    div元素
   * @param title         标题
   * @param width         宽度
   * @param height        高度
   * @param hasMinMax     是否显示最大化最小化按钮
   * @param cancelFunction   层
   * @returns {*}         返回弹出层的index
   * @constructor
   */
  var ShowIframe = function (iframeUrl, title, width, height, hasMinMax,
      cancelFunction) {
    ibcpLayerIndex = ibcpLayerIndex + 10;
    var divIndex = layer.open({
      //layer提供了5种层类型。可传入的值有：0（信息框，默认）；1（页面层）；2（iframe层）；3（加载层）；4（tips层）。
      // 若你采用layer.open({type: 1})方式调用，则type为必填项（信息框除外）
      type: 2,
      //动画
      anim: 5,
      //title ：String
      //title: ['文本', 'font-size:18px;']
      //title: false 不显示标题栏
      title: [title, 'font-size:18px;font-weight:bold;'],
      //是否固定弹出
      fix: false,
      //最大小化按钮
      maxmin: hasMinMax,
      //控制点击弹层外区域关闭
      shadeClose: false,
      //窗口大小 area: ['400px', '500px'] or auto 自适应大小
      area: [width, height],
      //area: '350px',
      //iframe层的url
      //content: 'http://www.baidu.com',
      content: iframeUrl,
      //是否允许拉伸
      resize: false,
      //浏览器滚动条
      scrollbar: false,
      zIndex: ibcpLayerIndex, //重点1
      cancel: cancelFunction,
      end: function () {
      }
    });
    //alert('return');
    return divIndex;
  };

  /**
   * 弹出 操作提示 消息层
   * @param msg           提示信息
   * @param OkCallBack    确认的回调方法 取消为关闭提示信息放弃操作
   * @constructor
   */
  var ShowConfirm = function (msg, OkCallBack) {
    ibcpLayerIndex = ibcpLayerIndex + 10;
    layer.confirm(msg, {
      title: '提示',
      zIndex: ibcpLayerIndex,
      btn: ['确认', '取消'],
      closeBtn: 0,
      yes: function (index) {
        if (OkCallBack) {
          OkCallBack();
        }
        layer.close(index);
      }
    });
  };

  /**
   * 弹出 操作提示 消息层
   * @param msg           提示信息
   * @param OkCallBack    确认的回调方法 取消为关闭提示信息放弃操作
   * @constructor
   */
  var ShowConfirmOkCancel = function (msg, OkCallBack, CancelCallBack) {
    ibcpLayerIndex = ibcpLayerIndex + 10;
    layer.confirm(msg, {
      title: '提示',
      zIndex: ibcpLayerIndex,
      btn: ['确认', '取消'],
      yes: function (index) {
        if (OkCallBack) {
          OkCallBack();
        }
        layer.close(index);
      },
      btn2: function (index) {
        if (CancelCallBack) {
          CancelCallBack();
        }
        layer.close(index);
      }
    });
  };

  /**
   * 弹出 消息层  需要点击关闭
   * @param msg       提示信息
   * @constructor
   */
  var ShowMsg = function (msg) {
    ibcpLayerIndex = ibcpLayerIndex + 10;
    layer.open({
      type: 0,
      skin: 'layui-layer-lan',
      anim: 5,
      title: '提示',
      fix: false,
      maxmin: false,
      content: msg,
      resize: false,
      scrollbar: false,
      zIndex: ibcpLayerIndex,
      end: function () {
      }
    });
    //layer.alert(msg, {
    //    skin: 'layui-layer-lan',
    //    closeBtn: 0,
    //    zIndex: ibcpLayerIndex,
    //    anim: 5 //动画类型
    //});
  };

  /**
   * 弹出 消息层  需要点击关闭
   * @param msg       提示信息
   * @constructor
   */
  var ShowMsgCB = function (msg, callBack) {
    ibcpLayerIndex = ibcpLayerIndex + 10;
    layer.alert(msg, {
      skin: 'layui-layer-lan',
      closeBtn: 0,
      zIndex: ibcpLayerIndex,
      anim: 5 //动画类型
    }, function () {
      callBack
    });
  };

  /**
   * 为 某个html元素 显示tips 消息提示
   * @param msg           消息提示
   * @param element       html元素
   * @constructor
   */
  var ShowTips = function (msg, element) {
    ibcpLayerIndex = ibcpLayerIndex + 10;
    layer.tips(msg, element, {
      tipsMore: true,
      zIndex: ibcpLayerIndex
    });
  };

  /**
   * Created by WangGuoyan on 2017/3/15
   * @param msg             消息提示
   * @param e               event
   * @param options       配置参数
   * @param options.obj   目标元素（可选）若有此参数，tip存在于目标元素的第一父元素body中，若没有参数，则默认存在于当前代码执行dom的body中（注：这里规定只允许传入（$）对象，此方法依赖于jQuery）
   * @param options.lastTime  tip显示时间（秒）
   */
  var ShowMoTips = function (msg, e, options) {
    'use strict';
    var opt = {
      'obj': options.obj,
      'lastTime': options.lastTime,        //tip显示时间
    };
    var $outTmr, $chgTmr;
    var obj = opt.obj;
    var $tip = $(
        '<div ibcplayerType="mo_tips" style="border:0.5px red solid;background-color:#FFD06C;font-size:12px;color:black;z-index:9999999999999999999;position:absolute;width:auto;height:auto;padding:3px;"></div>');
    $tip.append(msg);
    var ev = e || event;
    $tip.css('left', ev.pageX + 5);
    $tip.css('top', ev.pageY + 10);
    var top = window.top.document.body;
    var $body = $('body');
    if (obj) {
      $body = obj.parents('body');
      obj.on('mouseout mouseup', function () {
        $tip.remove();
        window.clearTimeout($outTmr);
        window.clearTimeout($chgTmr);
      });
    }
    $body.append($tip);

    if (opt.lastTime) {
      $outTmr = setTimeout(function () {
        $chgTmr = setInterval(function () {
          $tip.css('opacity', $tip.css('opacity') * 1 - 0.1);
          if ($tip.css('opacity') <= 0) {
            $tip.remove();
            clearInterval($chgTmr);
            clearTimeout($outTmr);
          }
        }, 50);
      }, opt.lastTime * 1000);
    }
  };

  /**
   * 提示成功 1秒自动消失的 消息层
   * @param msg   提示信息
   * @constructor
   */
  var ShowOK = function (msg) {
    ibcpLayerIndex = ibcpLayerIndex + 10;
    layer.msg(msg, {
      icon: 1,
      time: 1000, //1秒关闭（如果不配置，默认是3秒）
      zIndex: ibcpLayerIndex //重点1
    });
  };

  //Loadig 加载动画效果 转圈
  function Load(icon) {
    ibcpLayerIndex = ibcpLayerIndex + 10;
    return layer.load(icon, {zIndex: ibcpLayerIndex});
  }

  /**
   * 关闭层
   * @param index   层索引
   * @constructor
   */
  var Close = function (index) {
    layer.close(index);
  };

  //给外部的接口
  return {
    //myLayer : myLayer,
    ibcpLayerIndex: ibcpLayerIndex,
    ShowDiv: ShowDiv,
    ShowIframe: ShowIframe,
    ShowConfirm: ShowConfirm,
    ShowConfirmOkCancel: ShowConfirmOkCancel,
    ShowMsg: ShowMsg,
    ShowMsgCB: ShowMsgCB,
    ShowTips: ShowTips,
    ShowOK: ShowOK,
    Load: Load,
    Close: Close,
    ShowMoTips: ShowMoTips,
    ShowReport: ShowReport
  };
})();
