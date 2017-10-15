package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BaseOrg;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.JoinTable;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户信息业务层
 * Created by YoungerOu on 2017/10/10.
 */
@Service
public class UserService extends BaseService<User> {
  private List<String> blankSelectFields() {
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
  public ServerResult queryPage(String rowId, String search, String searchBy, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(order);
    Condition condition;
    if (UtilsTool.isValid(param)) {//判断是否根据指定字段查询
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(User.class, UtilsTool.jsonToObj(param, Map.class));
      condition = new ConditionBuilder(User.class).and().equal("belongOrg", rowId).addCondition(condition).endAnd().buildDone();

    } else {
      if (UtilsTool.isValid(search)) {
        condition = UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
        if (UtilsTool.isValid(searchBy)) {
          condition = new ConditionBuilder(User.class)
              .and().addCondition(UtilsTool.convertMapToAndCondition(User.class, UtilsTool.jsonToObj(searchBy, Map.class)))
              .or().addCondition(condition).endOr().endAnd()
              .buildDone();
        }
        condition = new ConditionBuilder(User.class).and().equal("belongOrg", rowId).addCondition(condition).endAnd().buildDone();
      } else {
        condition = null;
      }
    }
    //左外联查询,查询出用户信息的所有字段，以及用户所属部门的名称
    Collection<Field> fields = moreBatis.getColumns(User.class);
    fields.add(moreBatis.getColumnByAlias(BaseOrg.class, "orgName"));
    QueryAction queryAction = moreBatis.selectStatement().select(fields)
        .from(new JoinTable(moreBatis.getTable(User.class), JoinType.LEFT_JOIN, moreBatis.getTable(BaseOrg.class))
            .on(new FieldCondition(moreBatis.getColumnByAlias(User.class, "belongOrg"),
                Operator.EQUAL, moreBatis.getColumnByAlias(BaseOrg.class, "rowId"))))
        .where(condition).orderBy(orders);

    PageResult<Map<String, Object>> users;
    if (UtilsTool.isValid(pageNum)) {//判断是否分页查询
      users = queryAction.selectPage(pageNum, pageSize);
    } else {
      users = new PageResult<>(queryAction.execute());
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
  public ServerResult queryByOrg(List list) {
    Condition condition = new ConditionBuilder(User.class).and().in("belongOrg", list).endAnd().buildDone();
    List<Map> employees = selectMap(condition);
    if (!employees.isEmpty()) {
      return successData(Message.QUERY_SUCCESS, employees);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }
}
