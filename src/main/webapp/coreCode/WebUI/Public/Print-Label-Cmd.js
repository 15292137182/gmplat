/**
 * Created by WangGuoyan on 2017/2/23.
 */
$(function () {
  var jsCheck = isInclude("ibcpLayer.js") == false ? function () {
    document.write(getRootPath() + "/WebUI/Public/ibcpLayer.js");
  } : true;
});

//判断js/css是否存在
function isInclude(name) {
  var js = /js$/i.test(name);
  var es = document.getElementsByTagName(js ? 'script' : 'link');
  for (var i = 0; i < es.length; i++) {
    if (es[i][js ? 'src' : 'href'].indexOf(name) != -1) {
      return true;
    }
  }
  return false;
}

//获取根目录
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

function callPrint(type, id) {
  //称量标签信息初始化
  var weightingLabelPath = "/WebUI/Public/WeightLabel.html?resultId=" + id;
  if (type = "weighting") {
    var LabelPrintIndex = ibcpLayer.ShowIframe(getRootPath()
        + weightingLabelPath, "称量标签打印", "125mm", "130mm", false, function () {
    });
  }
}


