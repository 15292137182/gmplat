package com.bcx.plat.core.enclosure;

/**
 * <p>Title:ExcelModule </p>
 * <p>Description: excel模板枚举</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>History: 2017/10/15  Wen TieHu Create </pre>
 */
public enum ExcelModule {

  id("工号"), name("姓名"), nickname("昵称"), belongOrg("所属部门"), email("邮箱"),
  idCard("身份证"), mobilePhone("移动电话"), officePhone("办公电话"), gender("性别"),
  job("职务"), hiredate("入职日期"), passwordUpdateTime("密码更新日期"), lastLoginTime("上次登录时间"),
  accountLockedTime("账号锁定日期"), description("说明"), remarks("备注");


  private String value;

  ExcelModule(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static String getExcelModuleVal(String code) {
    for (ExcelModule excelModule : ExcelModule.values()) {
      if (code.equals(excelModule.getValue())) {
        return excelModule + "";
      }
    }
    return null;
  }
}
