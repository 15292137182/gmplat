package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BaseOrg;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
  public ServerResult insertEntityMap(Map<String, Object> params) {
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
          pId = orgs.get(0).getOrgId();
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

}