/**
 * Created by qijialin on 2017/3/17.
 */

//选择阶段模块
;var ibcpSign = (function () {
  var signDataUrl = serverPath + 'signature/query';   //获取签名UI显示需要的数据的URL
  var signConfirmUrl = serverPath + 'signature/confirms';  //签名确认调用的后台地址
  var signDivIndex = 0;   //弹出的签名层的Index
  var owerSid = 0;    //签名ID
  var comfirmCallBack = null; //签名成功回调函数
  var signType = 0;   //签名类型 1双人 0单人
  var signSelf = 0;   //签名类型 1本人签名 0可以不是本人
  var remark = 0;     //签名备注 1必填 0不必

  //弹出阶段选择框
  function CallSign(signId, callBack) {
    comfirmCallBack = callBack;
    var signParam = {"id": signId};
    // var signParam = {"id": 1};
    ibcpAjax.Select(signDataUrl, signParam, true, function (result) {
      SignLoad(result);
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

    // var signTitle =  sdata.sign.description;
    var signTitle = '签名';
    owerSid = sdata.sign.id;
    if (signType == 0) {
      signHtmlUrl = getRootPath() + '/WebUI/Public/Sign.html';
      signDivIndex = ibcpLayer.ShowDiv(signHtmlUrl, signTitle, '400px', '400px',
          function () {
            ListenBtnEvents();
            LoadSingleSignData(sdata);
          });
    }
    else {
      signHtmlUrl = getRootPath() + '/WebUI/Public/DoubleSignature.html';
      signDivIndex = ibcpLayer.ShowDiv(signHtmlUrl, signTitle, '700px', '500px',
          function () {
            ListenBtnEvents();
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
    var param = {"id": sigGroupId};
    ibcpAjax.Select(getSigGroupMemberUrl, param, true, function (result) {
      var data = result.data;
      FillSigGroupMember(data, userSelect);
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

  //按钮绑定的事件
  function ListenBtnEvents() {
    //单人签名，签名组改变事件
    $("#sGroupName").on("change", function () {
      var sigGroupId = $("#sGroupName").val();
      var userSelect = $("#sUsers");
      GetSigGroupMember(sigGroupId, userSelect);
    });
    //双人签名，签名组改变事件
    $("#sGroupName1").on("change", function () {
      var sigGroupId = $("#sGroupName1").val();
      var userSelect1 = $("#sUsers1");
      GetSigGroupMember(sigGroupId, userSelect1);
    });

    //签名确认按钮事件
    $("#btnSignOk").on("click", function () {
      SignSave();
    });

    //签名取消
    $("#btnSignCancel").on("click", function () {
      Close();
    });
  }

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
    user1.password = sha256_digest(pwd);
    user1.comment = comment;
    user1.ugId = ugId;

    if (signType == 0) {
      //密码
      if (pwd.length <= 0) {
        ibcpLayer.ShowTips("请输入密码", $("#sPassword"));
        return;
      }
      // if(comment==''){
      //     ibcpLayer.ShowTips('请输入备注内容',$("#comment"));
      //     return;
      // }
    } else {
      var password2 = $("#sPassword1").val();
      var account2 = $("#sUsers1").val();
      var comment2 = $("#comment1").val();
      var ugId = $("#sGroupName1").val();
      user2.account = account2;
      user2.password = sha256_digest(password2);
      user2.comment = comment2;
      user2.ugId = ugId;
    }
    //签名提交
    var param = {"sid": owerSid, "user1": user1, "user2": user2};
    ibcpAjax.Select(signConfirmUrl, param, true, function (result) {
      Close();
      var rId = result.data;
      comfirmCallBack(rId);
    });
  };

  function Close() {
    ibcpLayer.Close(signDivIndex);
  }

  //给外部的接口
  return {
    CallSign: CallSign,
    Close: Close
  };

})();