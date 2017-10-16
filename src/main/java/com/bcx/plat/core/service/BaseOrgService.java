package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BaseOrg;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
      org.buildCreateInfo().insert();
      return success(Message.NEW_ADD_SUCCESS);
    }
    return fail(Message.INVALID_REQUEST);
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

      while (!ORG_ROOT_NODE_ID.equalsIgnoreCase(pId)) {
        // 查询 pid 的父节点
        Condition condition = new ConditionBuilder(BaseOrg.class)
                .and().equal("orgId", pId).endAnd()
                .buildDone();
        List<BaseOrg> orgs = select(condition);
        if (orgs.size() != 1) {
          return false;
        } else {
          pId = orgs.get(0).getOrgPid();
          if (pId.equals(id)) {
            return false;
          }
          if (ORG_ROOT_NODE_ID.equalsIgnoreCase(pId)) {
            return true;
          }
        }
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
  public List<Map> queryUserInOrg(String orgRowId) {
    if (isValid(orgRowId)) {
      Condition condition = new ConditionBuilder(User.class)
              .and().equal("belongOrg", orgRowId).endAnd().buildDone();
      return userService.selectMap(condition);
    }
    return new ArrayList<>();
  }
}