package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BaseOrg;
import com.bcx.plat.core.entity.BaseOrgRelateRole;
import com.bcx.plat.core.entity.Role;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 组织机构服务类
 * Create By HCL at 2017/10/11
 */
@Service
public class BaseOrgService extends BaseService<BaseOrg> {

  // 组织机构根节点 id 不区分大小写的 root
  private static final String ORG_ROOT_NODE_ID = "ROOT";

  /**
   * @param params 实体类参数
   * @return 返回操作信息
   */
  public ServerResult insertOrgMap(Map<String, Object> params) {
    if (null != params) {
      BaseOrg org = new BaseOrg().fromMap(params);
      org.setOrgSort(Integer.valueOf(params.get("orgSort").toString()));
      if (!isValid(org.getOrgPid()) || ORG_ROOT_NODE_ID.equalsIgnoreCase(org.getOrgId())) {
        return fail("无效的组织机构编号！");
      }
      // 判断是否已经存在
      if (isOrgIdExists(org.getOrgId())) {
        return fail("该组织机构编号已经存在！");
      }
      if (!isPidLegal(org.getOrgId(), org.getOrgPid())) {
        return fail("无效的父组织机构！");
      }
      org.buildCreateInfo();
      org.setRowId(makeOrgRowId(org.getOrgPid()));
      org.insert();
      return success(Message.NEW_ADD_SUCCESS);
    }
    return fail(Message.INVALID_REQUEST);
  }

  /**
   * 构建 主键
   *
   * @param orgPid 父组织机构
   * @return 返回主键
   */
  private String makeOrgRowId(String orgPid) {
    // 当不是全大写 ROOT 的时候转为 ROOT ，主要处理大小写的问题
    if (ORG_ROOT_NODE_ID.equalsIgnoreCase(orgPid)) {
      orgPid = ORG_ROOT_NODE_ID;
    }
    if (!isValid(orgPid)) {
      orgPid = ORG_ROOT_NODE_ID;
    }

    Condition condition = new ConditionBuilder(BaseOrg.class)
            .and().equal("orgPid", orgPid).endAnd().buildDone();
    List<BaseOrg> orgList = select(condition);
    if (orgList.isEmpty()) {
      // 为空是说明没有数据
      if (ORG_ROOT_NODE_ID.equals(orgPid)) {
        return "001";
      } else {
        Condition condition1 = new ConditionBuilder(BaseOrg.class)
                .and().equal("orgId", orgPid).endAnd().buildDone();
        return select(condition1).get(0).getRowId() + "001";
      }
    } else {
      // 如果该组织机构下面有，找出最大的
      String[] maxString = new String[]{""};
      orgList.forEach(baseOrg -> {
        if (baseOrg.getRowId().compareTo(maxString[0]) > 0) {
          maxString[0] = baseOrg.getRowId();
        }
      });
      int next = Integer.valueOf(maxString[0].substring(maxString[0].length() - 3)) + 1;
      return maxString[0].substring(0, maxString[0].length() - 3) + next;
    }
  }

  /**
   * 更新数据
   *
   * @param params 参数
   * @return 返回操作结果
   */
  public ServerResult updateOrgMap(Map<String, Object> params) {
    if (null != params) {
      BaseOrg org = new BaseOrg().fromMap(params);
      org.setOrgSort(Integer.valueOf(params.get("orgSort").toString()));
      if (isValid(org.getRowId())) {
        if (!isValid(org.getOrgPid()) || ORG_ROOT_NODE_ID.equalsIgnoreCase(org.getOrgId())) {
          return fail("无效的组织机构编号！");
        }
        if (!isPidLegal(org.getOrgId(), org.getOrgPid())) {
          return fail("无效的父组织机构！");
        }
        org.buildModifyInfo().updateById();
        return success(Message.UPDATE_SUCCESS);
      }
    }
    return fail(Message.INVALID_REQUEST);
  }

  /**
   * 根据 rowIds 删除数据
   *
   * @param rowIds rowId 集合
   * @return 返回
   */
  public ServerResult deleteByRowIds(String[] rowIds) {
    if (null != rowIds && rowIds.length > 0) {
      Set<String> canDelete = new HashSet<>();
      for (String rowId : rowIds) {
        if (canDelete(rowId)) {
          canDelete.add(rowId);
        }
      }
      Condition condition = new ConditionBuilder(BaseOrg.class)
              .and().in("rowId", canDelete).endAnd().buildDone();
      delete(condition);
      return success(String.format("成功删除了 %d 数据！", canDelete.size()));
    }
    return fail(Message.INVALID_REQUEST);
  }

  /**
   * @param rowId 组织机构 rowId
   * @return 该组织机构是否能被删除
   */
  private boolean canDelete(String rowId) {
    // 查询当前 rowId 的编号
    BaseOrg org = new BaseOrg().selectOneById(rowId);
    if (null != org) {
      Condition condition = new ConditionBuilder(BaseOrg.class)
              .and().equal("orgPid", org.getOrgId()).endAnd().buildDone();
      return select(condition).isEmpty();
    }
    return false;
  }

  /**
   * 查询当前的组织机构编号是否已经存在
   *
   * @param orgId 组织机构编号
   * @return 返回结果
   */
  private boolean isOrgIdExists(String orgId) {
    Condition condition = new ConditionBuilder(BaseOrg.class)
            .and().equal("orgId", orgId).endAnd().buildDone();
    return !select(condition).isEmpty();
  }

  /**
   * 验证父分类代码是否合法
   *
   * @param id  组织机构代码
   * @param pId 父节点
   * @return 返回是否合法
   */
  private boolean isPidLegal(String id, String pId) {
    if (isValid(pId)) {
      // 父节点与当前节点不能相同
      if (pId.equals(id)) {
        return false;
      }
      // ORG_ROOT_NODE_ID 为顶层节点
      if (ORG_ROOT_NODE_ID.equalsIgnoreCase(pId)) {
        return true;
      }
      // 查询 pid 节点
      Condition condition = new ConditionBuilder(BaseOrg.class)
              .and().equal("orgId", pId).endAnd()
              .buildDone();
      List<BaseOrg> orgList = select(condition);
      if (orgList.size() != 1) {
        return false;
      } else {
        pId = orgList.get(0).getOrgPid();
        return isPidLegal(id, pId);
      }
    }
    return false;
  }

  @Resource
  private UserService userService;

  /**
   * 查询组织机构下面的人员信息
   *
   * @param orgRowId 组织机构的rowId
   * @return 返回人员列表
   */
  public PageResult<Map<String, Object>> queryUserInOrg(String orgRowId, Condition condition, List<Order> orders, Integer pageNum, Integer pageSize) {
    if (isValid(orgRowId)) {
      Condition and = new ConditionBuilder(User.class)
              .and().equal("belongOrg", orgRowId).endAnd().buildDone();
      if (null != condition) {
        and = new And(and, condition);
      }
      return userService.selectPageMap(and, orders, pageNum, pageSize);
    }
    return null;
  }

  @Resource
  private RoleService roleService;

  /**
   * 查询组织部门下的组织机构，传入部门编号无效返回 null
   *
   * @param orgRowId 部门主键
   * @return 返回查询结果
   */
  public PageResult<Map<String, Object>> queryRoleInOrg(String orgRowId, Condition condition, List<Order> orders, Integer pageNum, Integer pageSize) {
    if (isValid(orgRowId)) {
      Condition temp = new ConditionBuilder(BaseOrgRelateRole.class)
              .and().equal("orgRowId", orgRowId).endAnd().buildDone();
      List<BaseOrgRelateRole> relateRoles = new BaseOrgRelateRole().selectSimple(temp);
      if (!relateRoles.isEmpty()) {
        Set<String> roleRowId = new HashSet<>();
        relateRoles.forEach(baseOrgRelateRole -> roleRowId.add(baseOrgRelateRole.getRoleRowId()));
        Condition condition1 = new ConditionBuilder(Role.class)
                .and().in("rowId", roleRowId).endAnd().buildDone();
        if (null != condition1) {
          condition1 = new And(condition, condition1);
        }
        return roleService.selectPageMap(condition1, orders, pageNum, pageSize);
      }
    }
    return null;
  }

}