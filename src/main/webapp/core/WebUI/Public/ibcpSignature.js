/**
 * Created by Oswald on 2017/5/25.
 */

/**
 *@description:签名模块
 *@author:tudecai
 */
var ibcpSign = (function () {
  var _sigDefine = {
        "type": 0, //签名类型 0单人 1双人
        "signSelf": 0, //0可以不是本人 1本人签名
        "remark": 0, //签名备注 1必填 0不必
      },
      _userGp1 = [],
      _ug1Users = {},
      _userGp2 = [],
      _ug2Users = {};

  var callbackFun = undefined,
      divIndex = -1;

  /**
   *@description:获取签名数据
   *@author:tudecai
   */
  function GetSignatureDatas(auId, callback) {
    callbackFun = callback;

    var url = serverPath + 'signature/querySignatureDefinition',
        param = {"aid": auId};
    ibcpAjax.Select(url, param, true, function (result) {
      //缓存用户
      CacheUsers(result);
      //显示
      ShowSignPage();
    });

  };

  /**
   *@description:缓存用户
   *@author:tudecai
   */
  function CacheUsers(data) {
    _sigDefine.type = data.type;
    _sigDefine.signSelf = data.signself;
    _sigDefine.remark = data.remark;
    $.extend(_sigDefine, data.sign);

    //用户组1
    var userGroup1 = data.userGroup1;
    _userGp1.length = 0;//先清空数据,防止重复添加
    $.each(userGroup1, function (i, ug) {
      var ugObj = {};
      ugObj.id = ug.id;
      ugObj.name = ug.name;
      ugObj.code = ug.code;
      _userGp1.push(ugObj);

      //用户组成员 用户组id(key)---成员数组users(value)
      var users = ug.users;
      _ug1Users[ug.id] = users;
    })

    //用户组2
    var userGroup2 = data.userGroup2;
    _userGp2.length = 0;//先清空数据,防止重复添加
    $.each(userGroup2, function (i, ug) {
      var ugObj = {};
      ugObj.id = ug.id;
      ugObj.name = ug.name;
      ugObj.code = ug.code;
      _userGp2.push(ugObj);

      //用户组成员 用户组id(key)---成员数组users(value)
      var users = ug.users;
      _ug2Users[ug.id] = users;
    })

  }

  /**
   *@description:判断单双人签名 显示画面
   *@author:tudecai
   */
  function ShowSignPage() {
    var type = _sigDefine.type;
    // type = 1;

    //单人签名
    if (type == 0) {
      var html = getRootPath() + '/WebUI/Public/Sign.html';
      divIndex = ibcpLayer.ShowDiv(html, "签名", "360px", "370px", function () {
        ShowSingleSignData();

        //绑定按钮事件
        BindBtnEvents();
      });
    }

    //双人签名
    if (type == 1) {
      var html = getRootPath() + '/WebUI/Public/DoubleSignature.html';
      divIndex = ibcpLayer.ShowDiv(html, "签名", '700px', '440px', function () {
        ShowSingleSignData();
        ShowDoubleSignData();

        //绑定按钮事件
        BindBtnEvents();
      });
    }

  }

  /**
   *@description:绑定按钮事件
   *@author:tudecai
   */
  function BindBtnEvents() {
    //确认按钮事件
    $("#btnSignOk").click(function () {
      var param = GetSignDatasFromPage();//判断必填，获取数据
      var signself = MustLoggedUserSig();//判断是否当前用户签名
      if (param && signself) {
        SignatureServer(param);
      }
    });

    //取消按钮事件
    $("#btnSignCancel").click(function () {
      Close();
    });
  };

  /**
   *@description:显示单人签名数据
   *@author:tudecai
   */
  function ShowSingleSignData() {
    var meaning = _sigDefine.meaning;
    //签名含义
    $("#inputSigMeaning").val(meaning);
    //签名组1
    InitUserGroupSelect(_userGp1, $("#selectUG1"));
    //签名人
    UpdateUsersDataOne();

    //改变签名组事件
    $("#selectUG1").change(function () {
      //更新签名人
      UpdateUsersDataOne();
    })

    //备注是否必填
    var remark = _sigDefine.remark;
    if (remark == 1) {
      $("#inputComment1").attr("placeholder", "必填");
    }

  };

  /**
   *@description:显示双人签名数据
   *@author:tudecai
   */
  function ShowDoubleSignData() {
    var meaning2 = _sigDefine.meaning2;
    //签名含义
    $("#inputSigMeaning2").val(meaning2);
    //签名组1
    InitUserGroupSelect(_userGp2, $("#selectUG2"));
    //签名人
    UpdateUsersDataTwo();

    //改变签名组事件
    $("#selectUG2").change(function () {
      //更新签名人
      UpdateUsersDataTwo();
    })

    //备注是否必填
    var remark = _sigDefine.remark;
    if (remark == 1) {
      $("#inputComment2").attr("placeholder", "必填");
    }
  };

  /**
   *@description:初始化用户组下拉框方法
   *@author:tudecai
   */
  function InitUserGroupSelect(ugData, $select) {
    $select.empty()
    var ugs = ""
    $.each(ugData, function (i, ug) {
      var ugId = ug.id,
          ugName = ug.name;
      ugs += "<option value='" + ugId + "'>" + ugName + "</option>";
    })
    $select.append(ugs);
  };

  /**
   *@description:初始化用户下拉框方法
   *@author:tudecai
   */
  function InitUsersSelect(usersData, $select) {
    $select.empty();
    var users = "";
    $.each(usersData, function (i, user) {
      var account = user.account,
          name = user.name;
      users += "<option value='" + account + "'>" + name + "(" + account
          + ")</option>";
    })

    $select.append(users);
  };

  /**
   *@description:改变用户1数据
   *@author:tudecai
   */
  function UpdateUsersDataOne() {
    //签名人
    var ugId = $("#selectUG1").val();
    var users = _ug1Users[ugId];
    InitUsersSelect(users, $("#selectUsers1"));
  };

  /**
   *@description:改变用户2数据
   *@author:tudecai
   */
  function UpdateUsersDataTwo() {
    //签名人
    var ugId = $("#selectUG2").val();
    var users = _ug2Users[ugId];
    InitUsersSelect(users, $("#selectUsers2"));
  };

  /**
   *@description:获取签名页面数据
   *@author:tudecai
   */
  function GetSignDatasFromPage() {
    var ugId = $("#selectUG1").val(),
        account = $("#selectUsers1").val(),
        pwd = $("#inputPWD1").val(),
        comment = $("#inputComment1").val();
    if (pwd == "") {
      ibcpLayer.ShowTips("请输入密码!", $("#inputPWD1"));
      return false;
    }

    if (comment == "" && _sigDefine.remark == 1) {
      ibcpLayer.ShowTips("请输入备注!", $("#inputComment1"));
      return false;
    }

    var user1 = {
      "account": account,
      "password": sha256_digest(pwd),
      "comment": comment,
      "ugId": ugId
    }

    //如果是双人签名
    var user2 = {};
    if (_sigDefine.type == 1) {

      var ugId2 = $("#selectUG2").val(),
          account2 = $("#selectUsers2").val(),
          pwd2 = $("#inputPWD2").val(),
          comment2 = $("#inputComment2").val();
      if (pwd2 == "") {
        ibcpLayer.ShowTips("请输入密码!", $("#inputPWD2"));
        return false;
      }

      if (comment2 == "" && _sigDefine.remark == 1) {
        ibcpLayer.ShowTips("请输入备注!", $("#inputComment2"));
        return false;
      }

      user2 = {
        "account": account2,
        "password": sha256_digest(pwd2),
        "comment": comment2,
        "ugId": ugId2
      }
    }

    return {
      "sid": _sigDefine.id,
      "user1": user1,
      "user2": user2
    }
  };

  /**
   *@description:判断是否必须由本人签名
   *@author:tudecai
   */
  function MustLoggedUserSig() {
    if (_sigDefine.signSelf == 1) {
      //从cookie获取当前登录用户
      var user = getCookie("account");
      var sUer = $("#selectUsers1").val();
      if (user != sUer) {
        ibcpLayer.ShowMsg("必须由当前登录人签名!")
        return false;
      }
      return true;
    }
    return true;

  };

  /**
   *@description:确认签名调用服务
   *@author:tudecai
   */
  function SignatureServer(params) {
    var url = serverPath + 'signature/confirms';
    ibcpAjax.Select(url, params, true, function (result) {
      Close();
      var rId = result.data;
      callbackFun(rId);
    });
  };

  /**
   *@description:关闭签名窗
   *@author:tudecai
   */
  function Close() {
    ibcpLayer.Close(divIndex);
    _userGp1.length = 0;
    _userGp2.length = 0;
    _ug1Users = {};
    _ug2Users = {};
  };

  return {
    CallSign: GetSignatureDatas
  }

})();