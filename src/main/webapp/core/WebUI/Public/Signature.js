/**
 * Created by tudecai on 2017/1/13.
 */
/** 签名需要用到的js */
;var Signature = (function () {
  var signDataUrl = serverPath + 'signature/query';   //获取签名UI显示需要的数据的URL
  var signConfirmUrl = serverPath + 'signature/confirms';  //签名确认调用的后台地址
  var owerSid = 0;    //签名ID
  var comfirmCallBack = null; //签名成功回调函数
  var signType = 0;   //签名类型 1双人 0单人
  var signSelf = 0;   //签名类型 1本人签名 0可以不是本人
  var remark = 0;     //签名备注 1必填 0不必

//签名
  function CallSign(signId, callBack) {
    comfirmCallBack = callBack;
    $.ajax({
      url: signDataUrl,
      data: {"id": signId},
      xhrFields: {withCredentials: true},
      dataType: "json",
      success: function (result) {
        var errCode = result.errorCode;
        if (errCode == 0) {
          SignLoad(result);
        }
      },
      error: function () {
        alert('未找到相关签名的定义：' + signId);
      }
    });
  }

  /**
   * 弹出签名层时的 加载方法
   * @param sid   功能对应得签名的ID
   * @param callback  功能对应签名后 数据操作的回调方法
   * @constructor
   */
  function SignLoad(sdata) {
    signType = sdata.type;
    signSelf = sdata.signself;
    remark = sdata.remark;

    var signTitle = sdata.sign.description;
    owerSid = sdata.sign.id;
    if (signType == 0) {
      var htmlUrl = getRootPath() + '/WebUI/Public/Signature2.html';
      TuLayer.ShowDiv(htmlUrl, signTitle, '400px', '400px', function () {
        LoadSingleSignData(sdata);
        $("#sigBtn").on('click', function () {
          SignSave();
        });

        $("#cancelSigBtn").on('click', function () {
          CloseSign();
        })
      });
    }
    else {
      var htmlUrl = 'DoubleSignature.html';
      TuLayer.ShowDiv(htmlUrl, signTitle, '700px', '500px', function () {
        LoadDoubleSignData(sdata);
      });
    }
  }

//加载单人签名数据
  function LoadSingleSignData(sigData) {

    $('#sDescription').val(sigData.sign.meaning);
    var ugInfo1 = sigData.user1;
    for (var i = 0; i < ugInfo1.length; i++) {
      var ugId = ugInfo1[i].id;
      var ugName = ugInfo1[i].name;
      var ss = "";
      ss += "<option value='" + ugId + "'>" + ugName + "</option>"
      $("#sGroupName").append(ss);
    }
    var sigGroupId = $("#sGroupName").val();
    var userSelect = $("#sUsers");
    GetSigGroupMember(sigGroupId, userSelect);

  };

//加载双人签名的数据
  function LoadDoubleSignData(signBaseInfo) {
    //用户组1的信息
    LoadSingleSignData(signBaseInfo)
    //用户组2的信息
    $('#sDescription1').val(signBaseInfo.meaning2);
    var ugInfo2 = sigData.user2;
    for (var i = 0; i < ugInfo2.length; i++) {
      var id = ugInfo2[i].id;
      var name = ugInfo2[i].name;
      var ss = "";
      ss += "<option value='" + id + "'>" + name + "</option>"
      $("#sGroupName1").append(ss);
    }
    var sigGroupId = $("#sGroupName1").val();
    var userSelect1 = $("#sUsers1");
    GetSigGroupMember(sigGroupId, userSelect1);
  }

//获取签名组成员
  function GetSigGroupMember(sigGroupId, userSelect) {
    var getSigGroupMemberUrl = serverPath + 'userGroups/get/users';
    $.ajax({
      url: getSigGroupMemberUrl,
      data: {"id": sigGroupId},
      dataType: "json",
      xhrFields: {withCredentials: true},
      success: function (result) {
        if (result.errorCode == 0) {
          var data = result.data;
          FillSigGroupMember(data, userSelect);
        }

      },
      error: function () {
        alert('获取签名组成员失败！');
      }
    });
  }

//填充签名组成员
  function FillSigGroupMember(member, userSelect) {
    userSelect.empty();
    for (var i = 0; i < member.length; i++) {
      var account = member[i].account;
      var name = member[i].name;
      var ss = "";
      ss += "<option value='" + account + "'>" + name + "(" + account
          + ")</option>";
      userSelect.append(ss);
    }
  }

//画面绑定的事件
  $(function () {
    //单人签名，签名组改变事件
    $("body").on("change", "#sGroupName", function () {
      var sigGroupId = $("#sGroupName").val();
      var userSelect = $("#sUsers");
      GetSigGroupMember(sigGroupId, userSelect);
    });
    //双人签名，签名组改变事件
    $("body").on("change", "#sGroupName1", function () {
      var sigGroupId = $("#sGroupName1").val();
      var userSelect1 = $("#sUsers1");
      GetSigGroupMember(sigGroupId, userSelect1);
    });

  })

//签名
//确认签名
  function SignSave() {
    //单人签名
    var user1 = new Object;
    var user2 = new Object;
    var pwd = $("#sPassword").val();
    var sUser = $("#sUsers").val();
    var comment = $("#comment").val();
    var ugId = $("#sGroupName").val();
    user1.account = sUser;
    user1.password = pwd;
    user1.comment = comment;
    user1.ugId = ugId;

    if (signType == 0) {
      //密码
      if (pwd.length <= 0) {
        //ShowTips("请输入密码", $("#userGroupCode"));
        return;
      }
    } else {
      var password2 = $("#sPassword1").val();
      var account2 = $("#sUsers1").val();
      var comment2 = $("#comment1").val();
      var ugId = $("#sGroupName1").val();
      user2.account = account2;
      user2.password = password2;
      user2.comment = comment2;
      user2.ugId = ugId;
    }

    $.ajax({
      url: signConfirmUrl,
      data: {"sid": owerSid, "user1": user1, "user2": user2},
      dataType: "json",
      xhrFields: {withCredentials: true},
      success: function (result) {
        var errCode = result.errorCode;

        if (errCode == 0) {
          var rId = result.data;
          comfirmCallBack(rId);
        }
        else {
          TuLayer.ShowMsg(errCode);
        }
      },
      error: function () {
        //alert('签名服务异常！');
        TuLayer.ShowMsg("签名服务异常");
      }
    });
  };

  //获取项目根目录
  function getRootPath() {
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')
        + 1);
    return (localhostPaht + projectName);
  }

//取消签名
  function CloseSign() {
    TuLayer.Close();
  }

  var sig = {
    CallSign: CallSign,
    CloseSign: CloseSign
  }
  return sig;

})();



