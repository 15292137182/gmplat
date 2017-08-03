/**
 * Created by Mr.Wang on 2017/3/29.
 */

/**
 * jQuery可缩放插件
 */
(function ($) {
  "use strict";
  $.fn.ibcpAreaResizable = function (options, callback) {
    /**
     * 设置参数
     * 拖动范围限制是根据当前元素所在的父元素百分比进行设置
     * @type {{maxWidth, minWidth}}
     */
    var option = {
      'maxWidth': options.maxWidth,
      'minWidth': options.minWidth
    };

    var $area = $(this);
    var $width = $area.width();
    var dgInFlg = false;            //鼠标在拖动区域上信标
    var dgIngFlg = false;           //缩放进行信标
    var $smx;                       //拖动前按下时鼠标坐标
    var dgb = {
      'left': $area.position().left + $area.width() - 3,
      'right': $area.position().left + $area.width() + 3
    };

    //鼠标在当前对象移动时，当在拖动边界上时，改变鼠标样式
    $area.on('mousemove', function (e) {
      if (e.clientX >= dgb.left && e.clientX <= dgb.right) {
        $area.css('cursor', 'e-resize');
        dgInFlg = true;
      } else {
        $area.css('cursor', 'default');
        dgInFlg = false;
      }
    });

    //鼠标离开当前拖动对象，将不可通过点击获取拖动开始事件
    $area.on('mouseout', function () {
      dgInFlg = false;
    });

    //鼠标在区域中按下时
    $(window).on('mousedown', function (e) {
      if (dgInFlg) {
        dgIngFlg = true;
        $smx = e.clientX;
      }
    });

    //鼠标拖动过程
    $(window).on('mousemove', function (e) {
      if (dgIngFlg) {
        var $mx = e.clientX;
        var $dis = ($mx - $smx) * 1;
        var $pWidth = $area.parent().width();

        //检查限制
        if (checkLimit($dis)) {
          //执行缩放事件
          $area.width($width + $dis);
        }

        //执行回调函数
        if (callback) {
          callback($area.width());
        }
      }
    });

    //鼠标松开事件
    $(window).on('mouseup', function (e) {
      if (dgIngFlg) {
        var $mx = e.clientX;
        var $dis = ($mx - $smx) * 1;

        //检查限制
        if (checkLimit($dis)) {
          //执行缩放事件
          $area.width($width + $dis);
        }

        reload();

        //执行回调函数
        if (callback) {
          callback($area.width());
        }
      }
      dgIngFlg = false;
    });

    //重置拖动边框
    var reload = function () {
      dgb = {
        'left': $area.position().left + $area.width() - 3,
        'right': $area.position().left + $area.width() + 3
      };
      $smx = '';
      $width = $area.width();
    };

    //检查是否超过限制
    var checkLimit = function ($dis) {
      var $pWidth = $area.parent().width();
      var checkResult = true;
      //如果有限制，进行限制判断
      if (option.minWidth) {
        var pec = option.minWidth / 100;
        if (($area.width() / $pWidth) <= pec) {
          if ($dis > 0) {
            checkResult = true;
          } else {
            $area.width($pWidth * pec);
            checkResult = false;
          }
        }
      }
      if (option.maxWidth) {
        var pec = option.maxWidth / 100;
        if (($area.width() / $pWidth) >= pec) {
          if ($dis < 0) {
            checkResult = true;
          } else {
            $area.width($pWidth * pec);
            checkResult = false;
          }
        }
      }
      return (checkResult);
    };
  };
})(jQuery);

/**
 * 项目中关联区域
 */
$(function () {
  "use strict";
  ibcpAreaResize.init();
});

/**
 * ibcp缩放关联区域
 * @type {{init}}
 */
var ibcpAreaResize = (function () {
  "use strict";
  var resizeFlg = false;          //缩放事件启动标志
  var $smx;                       //鼠标拖动起始横坐标

  //初始化可缩放区域
  var initArea = function () {
    //可缩放div区块（主体）
    $('.ibcp-area-main').each(function () {
      var $this = $(this);
      var max = $this.attr('maxwidth');
      var min = $this.attr('minwidth');
      var $width = $this.width();
      var $deputyArea = getDepatyArea($this.attr('id'));
      $this.ibcpAreaResizable({
        'maxWidth': max === null ? 50 : max,
        'minWidth': min === null ? 20 : min
      }, function (width) {
        $deputyArea.width($this.parent().width() - width - 10);
      });
    });
  };

  //获取当前id区域关联缩放附属区域对象
  var getDepatyArea = function (id) {
    var obj;
    $('.ibcp-area-deputy').each(function () {
      var $this = $(this);
      if ($this.attr('for') == id) {
        obj = $this;
      }
    });
    return (obj);
  };

  return {
    init: initArea
  };
})();
