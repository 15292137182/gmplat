package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.ButtonRelatePermission;
import com.bcx.plat.core.entity.InterfaceRelatePermission;
import com.bcx.plat.core.entity.MenuRelatePermission;
import com.bcx.plat.core.entity.PageRelatePermission;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * Create By HCL at 2017/10/24
 */
@Service
public class PermissionResourceService {

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
          menuRelatePermission.insert();
        });
      case PermissionService.PERMISSION_TYPE_PAGE:
        set.forEach(s -> {
          PageRelatePermission pageRelatePermission = new PageRelatePermission();
          pageRelatePermission.setPageRowId(s);
          pageRelatePermission.setPermissionRowId(permissionRowId);
          pageRelatePermission.insert();
        });
      case PermissionService.PERMISSION_TYPE_BUTTON:
        set.forEach(s -> {
          ButtonRelatePermission buttonRelatePermission = new ButtonRelatePermission();
          buttonRelatePermission.setPermissionRowId(permissionRowId);
          buttonRelatePermission.setButtonRowId(s);
          buttonRelatePermission.insert();
        });
      case PermissionService.PERMISSION_TYPE_INTERFACE:
        set.forEach(s -> {
          InterfaceRelatePermission interfaceRelatePermission = new InterfaceRelatePermission();
          interfaceRelatePermission.setInterfaceRowId(s);
          interfaceRelatePermission.setPermissionRowId(permissionRowId);
          interfaceRelatePermission.insert();
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
    return "";
  }
}
