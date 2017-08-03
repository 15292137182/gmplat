/**
 * Created by WangGuoyan on 2017/2/20.
 */
var childPath = "/WebUI/Public/NumberKeyBoard.html";

$(function () {
  $(".Call-NumKeyBoard").each(function () {
    var $this = $(this);
    $this.focus(function () {
      ibcpNumberKeyBoard.Pop($this);
    });
  });
});

var ibcpNumberKeyBoard = (function () {
  //弹出键盘
  function showNumKeyBoard(input, callback) {
    var keyBoardIndex = ibcpLayer.ShowDiv(getRootPath() + childPath, "数字键盘",
        "230px", "350px", function () {
          initKeyBoard();
          //确认按钮
          $('#Num-KeyBoard-Confirm').on('click', function () {
            var value = $('[numType="input"]').val() * 1;
            input.val(value);
            if (callback) {
              callback(value);
            }
            ibcpLayer.Close(keyBoardIndex);
          });
          //取消按钮
          $('#Num-KeyBoard-Cancel').on('click', function () {
            ibcpLayer.Close(keyBoardIndex);
          });
        });
  }

  //初始化键盘
  function initKeyBoard() {
    'use strict';
    var $input = $('[numType="input"]');
    //键盘样式设置
    $(".NumBtn").addClass('btn btn-custom');

    //数字键盘功能
    $('.NumBtn').each(function () {
      var $this = $(this);
      //退格功能
      if ($this.attr("num") == "BKS") {
        $(this).on('click', function () {
          $input.val($input.val().substr(0, $input.val().length - 1));
        });
      }
      //小数点
      else if ($this.attr("num") == "POT") {
        $(this).on('click', function () {
          if ($input.val().indexOf(".") == -1) {
            $input.val(($input.val() + "."));
          }
        });
      }
      //数字功能
      else {
        $this.on('click', function () {
          $input.val($input.val() + $this.attr("num"));
        });
      }
    });
  }

  return {
    Pop: showNumKeyBoard
  }
})();

//获取文件根路径
function getRootPath() {
  //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
  var curWwwPath = window.document.location.href;
  //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
  var pathName = window.document.location.pathname;
  var pos = curWwwPath.indexOf(pathName);
  //获取主机地址，如： http://localhost:8083
  var localhostPaht = curWwwPath.substring(0, pos);
  //获取带"/"的项目名，如：/uimcardprj
  var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
  return (localhostPaht + projectName);
}