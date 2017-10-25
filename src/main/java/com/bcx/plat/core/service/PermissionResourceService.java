package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.*;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * Create By HCL at 2017/10/24
 */
@Service
public class PermissionResourceService {

  /**
   * @param permissionRowId 权限编号
   * @return 返回查询结果
   */
  public ServerResult<PageResult> queryResource(String permissionRowId
          , String search, String param, Integer pageNum, Integer pageSize, String order) {
    ServerResult<PageResult> serverResult = new ServerResult<>();
    if (isValid(permissionRowId)) {
      Permission permission = new Permission().selectOneById(permissionRowId);
      List<String> resourceRowIds = getResourceRowIds(permission.getPermissionType(), permissionRowId);
      if (!resourceRowIds.isEmpty()) {
        Condition condition = null;
        List<Order> orders = new ArrayList<>();
        if (isValid(param)) {//判断是否根据指定字段查询
          switch (permission.getPermissionType()) {
            case PermissionService.PERMISSION_TYPE_MENU:
              condition = convertMapToAndConditionSeparatedByLike(Menu.class, jsonToObj(param, Map.class));
              orders = dataSort(Menu.class, order);
              break;
            case PermissionService.PERMISSION_TYPE_PAGE:
              condition = convertMapToAndConditionSeparatedByLike(Page.class, jsonToObj(param, Map.class));
              orders = dataSort(Page.class, order);
              break;
            case PermissionService.PERMISSION_TYPE_BUTTON:
              condition = convertMapToAndConditionSeparatedByLike(Button.class, jsonToObj(param, Map.class));
              orders = dataSort(Button.class, order);
          /*case PermissionService.PERMISSION_TYPE_INTERFACE:
            condition = convertMapToAndConditionSeparatedByLike(Role.class, jsonToObj(param, Map.class));*/
          }
        } else {
          condition = !isValid(search) ? null : createBlankQuery(getBlankSelectFields(permission.getPermissionType()), collectToSet(search));
        }
        if (null != condition) {
          condition = new And(condition,
                  new FieldCondition(getValidResourceKey(permission.getPermissionType()), Operator.IN, resourceRowIds));
          PageResult pageResult = queryData(permission.getPermissionType(), condition, orders, pageNum, pageSize);
          serverResult.setData(pageResult);
          serverResult.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS);
          return serverResult;
        }
      }
    }
    serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.INVALID_REQUEST);
    return serverResult;
  }

  /**
   * @param type      权限类型
   * @param condition 条件
   * @param orders    排序
   * @param pageNum   页面号
   * @param pageSize  页面大小
   * @return 返回查询结果
   */
  private PageResult queryData(String type, Condition condition, List<Order> orders, Integer pageNum, Integer pageSize) {
    switch (type) {
      case PermissionService.PERMISSION_TYPE_MENU:
        return new Menu().selectPageMap(condition, pageNum, pageSize, orders);
      case PermissionService.PERMISSION_TYPE_PAGE:
        return new Page().selectPageMap(condition, pageNum, pageSize, orders);
      case PermissionService.PERMISSION_TYPE_BUTTON:
        return new Button().selectPageMap(condition, pageNum, pageSize, orders);
      /*case PermissionService.PERMISSION_TYPE_INTERFACE:
        return "interfaceRowId";*/
    }
    return null;
  }

  /**
   * @param type            权限类型
   * @param permissionRowId 权限主键
   * @return 返回资源主键集合
   */
  private List<String> getResourceRowIds(String type, String permissionRowId) {
    // 查询资源 rowId
    Condition temp = new FieldCondition("permissionRowId", Operator.EQUAL, permissionRowId);
    List<String> resourceRowIds = new ArrayList<>();
    switch (type) {
      case PermissionService.PERMISSION_TYPE_MENU:
        List<MenuRelatePermission> menuRelatePermissions = new MenuRelatePermission().selectSimple(temp);
        menuRelatePermissions.forEach(menuRelatePermission -> resourceRowIds.add(menuRelatePermission.getMenuRowId()));
      case PermissionService.PERMISSION_TYPE_PAGE:
        List<PageRelatePermission> pageRelatePermissions = new PageRelatePermission().selectSimple(temp);
        pageRelatePermissions.forEach(pageRelatePermission -> resourceRowIds.add(pageRelatePermission.getPageRowId()));
      case PermissionService.PERMISSION_TYPE_BUTTON:
        List<ButtonRelatePermission> buttonRelatePermissions = new ButtonRelatePermission().selectSimple(temp);
        buttonRelatePermissions.forEach(buttonRelatePermission -> resourceRowIds.add(buttonRelatePermission.getButtonRowId()));
      case PermissionService.PERMISSION_TYPE_INTERFACE:
        List<InterfaceRelatePermission> interfaceRelatePermissions = new InterfaceRelatePermission().selectSimple(temp);
        interfaceRelatePermissions.forEach(interfaceRelatePermission -> resourceRowIds.add(interfaceRelatePermission.getInterfaceRowId()));
    }
    return resourceRowIds;
  }

  private Set<String> MENU_FIELD_SET = null;
  private Set<String> PAGE_FIELD_SET = null;
  private Set<String> BUTTON_FIELD_SET = null;

  private void refreshSet() {
    if (null == MENU_FIELD_SET) {
      MENU_FIELD_SET = new Menu().toMap().keySet();
    }
    if (null == PAGE_FIELD_SET) {
      PAGE_FIELD_SET = new Page().toMap().keySet();
    }
    if (null == BUTTON_FIELD_SET) {
      BUTTON_FIELD_SET = new Button().toMap().keySet();
    }
  }

  /**
   * @param type 类型
   * @return 获取空白查询的字段
   */
  private Set<String> getBlankSelectFields(String type) {
    refreshSet();
    switch (type) {
      case PermissionService.PERMISSION_TYPE_MENU:
        return MENU_FIELD_SET;
      case PermissionService.PERMISSION_TYPE_PAGE:
        return PAGE_FIELD_SET;
      case PermissionService.PERMISSION_TYPE_BUTTON:
        return BUTTON_FIELD_SET;
//      case PermissionService.PERMISSION_TYPE_INTERFACE:
//        return "interfaceRowId";
    }
    return null;
  }

  /**
   * 新建资源关联事件
   *
   * @param type            类型
   * @param permissionRowId 权限主键
   * @param resourceRowIds  资源主键集合
   * @return 返回操作结果
   */
  public ServerResult insertPermissionRelate(String type, String permissionRowId, String[] resourceRowIds) {
    ServerResult serverResult = new ServerResult();
    if (isValid(permissionRowId) && (null != resourceRowIds && resourceRowIds.length != 0)) {
      // 去除已经存在的权限
      Set<String> resourceRowIdSet = new HashSet<>();
      resourceRowIdSet.addAll(Arrays.asList(resourceRowIds));
      Set<String> validRowIds = getValidKeys(type, permissionRowId, resourceRowIdSet);
      insertResource(type, permissionRowId, validRowIds);
      serverResult.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS);
      return serverResult;
    }
    serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.INVALID_REQUEST);
    return serverResult;
  }

  /**
   * 插入资源
   *
   * @param type            类型
   * @param permissionRowId 权限主键
   * @param set             资源主键集合
   */
  private void insertResource(String type, String permissionRowId, Set<String> set) {
    switch (type) {
      case PermissionService.PERMISSION_TYPE_MENU:
        set.forEach(s -> {
          MenuRelatePermission menuRelatePermission = new MenuRelatePermission();
          menuRelatePermission.setMenuRowId(s);
          menuRelatePermission.setPermissionRowId(permissionRowId);
          menuRelatePermission.buildCreateInfo().insert();
        });
      case PermissionService.PERMISSION_TYPE_PAGE:
        set.forEach(s -> {
          PageRelatePermission pageRelatePermission = new PageRelatePermission();
          pageRelatePermission.setPageRowId(s);
          pageRelatePermission.setPermissionRowId(permissionRowId);
          pageRelatePermission.buildCreateInfo().insert();
        });
      case PermissionService.PERMISSION_TYPE_BUTTON:
        set.forEach(s -> {
          ButtonRelatePermission buttonRelatePermission = new ButtonRelatePermission();
          buttonRelatePermission.setPermissionRowId(permissionRowId);
          buttonRelatePermission.setButtonRowId(s);
          buttonRelatePermission.buildCreateInfo().insert();
        });
      case PermissionService.PERMISSION_TYPE_INTERFACE:
        set.forEach(s -> {
          InterfaceRelatePermission interfaceRelatePermission = new InterfaceRelatePermission();
          interfaceRelatePermission.setInterfaceRowId(s);
          interfaceRelatePermission.setPermissionRowId(permissionRowId);
          interfaceRelatePermission.buildCreateInfo().insert();
        });
    }
  }

  /**
   * @param type 类型
   * @param set  数值集合
   * @return 获取验证的条件
   */
  private Set<String> getValidKeys(String type, String permissionRowId, Set<String> set) {
    Condition condition;
    switch (type) {
      case PermissionService.PERMISSION_TYPE_MENU:
        condition = new ConditionBuilder(MenuRelatePermission.class)
                .and().in(getValidResourceKey(type), set).equal("permissionRowId", permissionRowId).endAnd()
                .buildDone();
        List<MenuRelatePermission> menuRelatePermissions =
                new MenuRelatePermission().selectSimple(condition);
        if (!menuRelatePermissions.isEmpty()) {
          menuRelatePermissions.forEach(menuRelatePermission -> set.remove(menuRelatePermission.getMenuRowId()));
        }
      case PermissionService.PERMISSION_TYPE_PAGE:
        condition = new ConditionBuilder(PageRelatePermission.class)
                .and().in(getValidResourceKey(type), set).equal("permissionRowId", permissionRowId).endAnd()
                .buildDone();
        List<PageRelatePermission> pageRelatePermissions =
                new PageRelatePermission().selectSimple(condition);
        if (!pageRelatePermissions.isEmpty()) {
          pageRelatePermissions.forEach(menuRelatePermission -> set.remove(menuRelatePermission.getPageRowId()));
        }
      case PermissionService.PERMISSION_TYPE_BUTTON:
        condition = new ConditionBuilder(ButtonRelatePermission.class)
                .and().in(getValidResourceKey(type), set).equal("permissionRowId", permissionRowId).endAnd()
                .buildDone();
        List<ButtonRelatePermission> buttonRelatePermissions =
                new ButtonRelatePermission().selectSimple(condition);
        if (!buttonRelatePermissions.isEmpty()) {
          buttonRelatePermissions.forEach(buttonRelatePermission -> set.remove(buttonRelatePermission.getButtonRowId()));
        }
      case PermissionService.PERMISSION_TYPE_INTERFACE:
        condition = new ConditionBuilder(InterfaceRelatePermission.class)
                .and().in(getValidResourceKey(type), set).equal("permissionRowId", permissionRowId).endAnd()
                .buildDone();
        List<InterfaceRelatePermission> interfaceRelatePermissions =
                new InterfaceRelatePermission().selectSimple(condition);
        if (!interfaceRelatePermissions.isEmpty()) {
          interfaceRelatePermissions.forEach(interfaceRelatePermission -> set.remove(interfaceRelatePermission.getInterfaceRowId()));
        }
    }
    return set;
  }

  /**
   * @param type 类型
   * @return 返回 resource key
   */
  private String getValidResourceKey(String type) {
    switch (type) {
      case PermissionService.PERMISSION_TYPE_MENU:
        return "menuRowId";
      case PermissionService.PERMISSION_TYPE_PAGE:
        return "pageRowId";
      case PermissionService.PERMISSION_TYPE_BUTTON:
        return "buttonRowId";
      case PermissionService.PERMISSION_TYPE_INTERFACE:
        return "interfaceRowId";
    }
    return null;
  }
}
