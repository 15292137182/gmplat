/**
 * Created by Oswald on 2016/12/27.
 */

;(function ($) {
  //带有清除按钮的输入框
  $.fn.inputWithClear = function (option) {

    return this.each(function () {
      var $this = $(this);

      var thisInput = $this.find("input");
      var thisSpan = $this.find("span");

      thisInput.keyup(function () {
        var valueText = thisInput.val();
        if (valueText == "") {
          thisSpan.css("display", "none");
        } else {
          thisSpan.css("display", "inline-block");
        }
        ;
      });

      thisSpan.click(function () {
        thisInput.val("").focus();
        thisSpan.css("display", "none");
      });

    })

  }

  //弹出框的取消按钮
  //$.fn.closeLayerBtn = function(option){
  //    return this.each(function (){
  //        var $this = $(this);
  //
  //        var thisDiv = $this.parents("div .layui-layer-content");
  //        var i = thisDiv.attr("index");
  //        $this.click(function() {
  //            ibcpLayer.Close(i);
  //        });
  //    })
  //}

  //隐藏table的checkbox
  $.fn.tableHideCheckbox = function (option) {
    //return this.each(function(){
    var $this = $(this);
    $this.css('border-left-width', '0px')
    var th = $this.find('thead').find('.bs-checkbox');
    th.css('width', '0px');

    var td = $this.find('tbody').find('.bs-checkbox');

    td.each(function () {
      $(this).css('width', '0px');
      $(this).css("padding", "0px");

      var ch = $(this).find('input');
      ch.css('width', '0px');

      /**
       * extended by WangGY
       */
      var $this = $(this);
      if (option) {
        $this.on('click', function () {
          ibcpLayer.ShowTips($this.html().$this);
        });
      }

    });
    //})

  }

  //表格滚动到选中行
  $.fn.scrollToSelected = function () {
    var $this = $(this),
        thisH = $this.outerHeight();//table实际高度
    pElemH = $this.parent().height() + 40;//父元素高度

    var scrollH = 0;
    //如果有滚动条
    if (thisH > pElemH) {
      //获取被选中的行
      var selectedRow = $this.find(".selected");
      if (selectedRow.length == 0) {
        return;
      }

      var tTop = $this.offset().top,//表格距离浏览器顶部的距离
          rowTop = selectedRow.offset().top;//选中行距离浏览器顶部的距离
      var rowToTop = rowTop - tTop,//选中行距离表格 顶部 的高度
          rowToBottom = thisH - rowToTop;//选中行距离表格 底部 的高度

      //保证在允许范围内 滚动到可见区域 中间
      if (rowToTop > pElemH / 2) {
        rowToBottom > pElemH / 2 ? scrollH = (rowToTop - pElemH / 2)
            : scrollH = "bottom";
      }
    }
    $this.bootstrapTable('scrollTo', scrollH)
  }

})(jQuery);

//$(function (){
//    $("div.input-group").inputWithClear();
//});

//节点点击符号改变
$.extend($.fn.bootstrapTable.defaults, {
  icons: {
    detailOpen: 'fa fa-th-list',
    detailClose: 'glyphicon-minus icon-minus'
  }
});