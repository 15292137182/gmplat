package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.UserGroup;
import com.bcx.plat.core.entity.UserRelateUserGroup;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 * <pre>History: 2017/10/11  Wen TieHu Create </pre>
 */
@Service
public class UserGroupService extends BaseService<UserGroup> {

  /**
   * 删除用户组下的用户信息
   *
   * @param groupRowId 用户组rowId
   * @param userRowIds 用户rowId 集合
   * @return 返回结果
   */
  public boolean deleteUserInGroup(String groupRowId, String[] userRowIds) {
    if (isValid(groupRowId) && null != userRowIds && userRowIds.length != 0) {
      Condition condition = new ConditionBuilder(UserRelateUserGroup.class)
              .and().equal("userGroupRowId", groupRowId).in("userRowId", Arrays.asList(userRowIds)).endAnd()
              .buildDone();
      new UserRelateUserGroup().delete(condition);
      return true;
    }
    return false;
  }

}
