package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BaseOrg;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * 用户信息业务层
 * Created by YoungerOu on 2017/10/10.
 */
@Service
public class UserService extends BaseService<User> {
  protected List<String> blankSelectFields() {
    return Arrays.asList("id", "name", "nickname", "belongOrg", "idCard", "job", "hiredate");
  }

  /**
   * 人员信息分页查询
   *
   * @param search   按照空格查询
   * @param param    按照指定字段查询
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @param order    排序方式
   * @return ServerResult
   */
  public ServerResult queryPage(String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(User.class, order);
    Condition condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));//or;
    if (UtilsTool.isValid(param)) {//根据指定字段查询
      if (null == condition) {
        condition = UtilsTool.convertMapToAndConditionSeparatedByLike(User.class, UtilsTool.jsonToObj(param, Map.class));
      } else {
        condition = new And(UtilsTool.convertMapToAndConditionSeparatedByLike(User.class, UtilsTool.jsonToObj(param, Map.class)), condition);
      }
    }
    condition = UtilsTool.addNotDeleteCondition(condition, User.class);
    //左外联查询,查询出用户信息的所有字段，以及用户所属部门的名称
    Collection<Field> fields = new LinkedList<>(moreBatis.getColumns(User.class));
    fields.add(moreBatis.getColumnByAlias(BaseOrg.class, "orgName"));
    PageResult<Map<String, Object>> users;
    if (UtilsTool.isValid(pageNum)) {//判断是否分页查询
      users = leftAssociationQueryPage(User.class, BaseOrg.class, "belongOrg", "rowId", fields, condition, pageNum, pageSize, orders);
    } else {
      users = new PageResult<>(leftAssociationQuery(User.class, BaseOrg.class, "belongOrg", "rowId", fields, condition, orders));
    }
    if (UtilsTool.isValid(null == users ? null : users.getResult())) {
      return new ServerResult<>(users);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 根据组织机构查询用户信息
   *
   * @param list 组织机构代码列表
   * @return ServerResult
   */
  public ServerResult queryByOrg(List list, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(User.class, order);
    Condition condition = new FieldCondition("belongOrg", Operator.IN, list);
    Collection<Field> fields = new LinkedList<>(moreBatis.getColumns(User.class));
    fields.add(moreBatis.getColumnByAlias(BaseOrg.class, "orgName"));
    PageResult result;
    if (UtilsTool.isValid(pageNum)) {
      result = leftAssociationQueryPage(User.class, BaseOrg.class, "belongOrg", "rowId", fields, condition, pageNum, pageSize, orders);
    } else {
      result = new PageResult<>(leftAssociationQuery(User.class, BaseOrg.class, "belongOrg", "rowId", fields, condition, orders));
    }
    if (UtilsTool.isValid(null == result ? null : result.getResult())) {
      return successData(Message.QUERY_SUCCESS, result);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  private static Map<String, String> fieldNames;
  protected static String[] defaultExportFields = new String[]{"id", "name", "nickname", "status", "belongOrg", "idCard", "mobilePhone",
      "officePhone", "email", "gender", "job", "hiredate", "lastLoginTime", "description", "remarks"};
  protected static List<String> lockedFields = Arrays.asList("password", "passwordUpdateTime", "accountLockedTime", "deleteFlag", "etc");

  /**
   * @param field 列
   * @return 字段名名称
   */
  private String getFieldNames(String field) {
    if (null == fieldNames) {
      fieldNames = new HashMap<>();
      fieldNames.put("id", "编号");
      fieldNames.put("name", "名称");
      fieldNames.put("nickname", "昵称");
      fieldNames.put("status", "状态");
      fieldNames.put("belongOrg", "归属部门");
      fieldNames.put("idCard", "身份证");
      fieldNames.put("mobilePhone", "移动电话");
      fieldNames.put("officePhone", "办公电话");
      fieldNames.put("email", "邮箱");
      fieldNames.put("gender", "性别");
      fieldNames.put("job", "工作");
      fieldNames.put("hiredate", "入职时间");
      fieldNames.put("lastLoginTime", "上次登陆时间");
      fieldNames.put("description", "描述");
      fieldNames.put("remarks", "备注");

      fieldNames.put("password", "密码");
      fieldNames.put("passwordUpdateTime", "密码更新时间");
      fieldNames.put("accountLockedTime", "账户锁定时间");
      fieldNames.put("deleteFlag", "删除标记");
      fieldNames.put("etc", "其他");
    }
    String name = fieldNames.get(field);
    return null == name ? field : name;
  }


  /**
   * 将用户列表导出为 excel 字节
   *
   * @param rowIds 需要导出的用户 rowId，如果无效，则导出全部用户信息
   * @param fields 需要导出的字段，如果无效，则默认选取上次配置
   *               如果上次配置不存在，则选用默认数据
   * @return excel 字节，返回类型为 xls 类型字节
   */
  public HSSFWorkbook exportToExcelByte(String[] rowIds, String[] fields) {
    List<Map> users;
    if (null != rowIds && rowIds.length != 0) {
      Condition condition = new ConditionBuilder(User.class)
          .and().in("rowId", Arrays.asList(rowIds)).endAnd()
          .buildDone();
      users = selectMap(condition);
    } else {
      users = selectAllMap();
    }
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // 第一步，创建一个 workbook，对应一个Excel文件
    HSSFWorkbook workbook = new HSSFWorkbook();
    // 第二步，在 workbook 中添加一个sheet,对应Excel文件中的sheet
    HSSFSheet sheet = workbook.createSheet("用户列表");
    if (fields == null) {
      fields = defaultExportFields;
    }

    HSSFCell cell;
    // 填充标题
    HSSFRow titleRow = sheet.createRow(0);
    for (int i = 0; i < fields.length; i++) {
      cell = titleRow.createCell(i);
      cell.setCellValue(getFieldNames(fields[i]));
    }

    // 填充数据
    for (int i = 0; i < users.size(); i++) {
      HSSFRow dataRow = sheet.createRow(i + 1);
      Map data = users.get(i);
      for (int c = 0; c < fields.length; c++) {
        cell = dataRow.createCell(c);
        String field = fields[c];
        String value = "";
        if (!lockedFields.contains(field) && null != data.get(field)) {
          value = data.get(field).toString();
        }
        cell.setCellValue(value);
      }
    }
    return workbook;
  }

}
