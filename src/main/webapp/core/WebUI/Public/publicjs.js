/**
 * Created by admin on 2017/8/13.
 */
var path = getRootPath()+'/core';

document.write('<link href="' + path + '/webjar/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">');
document.write('<link href="' + path + '/webjar/css/mystyle.css" rel="stylesheet">');
document.write('<link href="' + path + '/webjar/element-ui/lib/theme-default/index.css" rel="stylesheet">');
//自定义样式
document.write('<link href="' + path + '/webjar/css/coreStyle.css" rel="stylesheet">');


document.write('<script type="text/javascript" src="' + path + '/webjar/vue/dist/vue.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/webjar/element-ui/lib/index.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/webjar/vue/vue-resource.js" charset="UTF-8"></script>');

document.write('<script type="text/javascript" src="' + path + '/webjar/plugins/js/TabPage.js" charset="UTF-8"></script>');

document.write('<script type="text/javascript" src="' + path + '/webjar/jquery/3.1.1/jquery.min.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/webjar/layer/layer.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/webjar/plugins/layui/layui.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/sha256.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ServerPath.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcp.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/cookie_util.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpLayer.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpAjax.js" charset="UTF-8"></script>');
// document.write('<script type="text/javascript" src="' + path +  '/WebUI/Public/ibcpSign.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpSignature.js" charset="UTF-8"></script>');
//document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpPlugin.js" charset="UTF-8"></script>');
//document.write('<script type="text/javascript" src="' + path + '/WebUI/Version/ibcpVS.js" charset="UTF-8"></script>');
//document.write('<script type="text/javascript" src="' + path + '/WebUI/Uom/UomChose.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/DateFormat.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpValidate.js"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/NumberKeyBoard.js"></script>');
// document.write('<script type="text/javascript" src="' + path + '/WebUI/PV/Chart/highcharts.js" charset="UTF-8"></script>');
// document.write('<script type="text/javascript" src="' + path + '/WebUI/PV/Chart/exporting.js" charset="UTF-8"></script>');
// document.write('<script type="text/javascript" src="' + path + '/WebUI/PV/Chart/highcharts-zh_CN.js" charset="UTF-8"></script>')
//document.write('<script type="text/javascript" src="' + path + '/WebUI/PV/RevisePV.js" charset="UTF-8"></script>');
//document.write('<script type="text/javascript" src="' + path + '/WebUI/PV/PV_Edit/PV_DateTime_Edit.js" charset="UTF-8"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpAreaResize.js"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpAuthority.js"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpDate.js"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpSelect.js"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpCheck.js"></script>');
document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpAfter.js"></script>');

document.write('<script type="text/javascript" src="' + path + '/WebUI/Public/ibcpKeyboard.js"></script>');

function getRootPath() {
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(2).indexOf('/') + 2);
    return (localhostPaht + projectName);
}

