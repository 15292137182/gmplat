package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Role;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色业务层
 *
 * @author YoungerOu
 * <p>
 * Created on 2017/10/13.
 */
@Service
public class RoleService extends BaseService<Role> {

  public ServerResult add(Map<String, Object> param) {
    Object roleId = param.get("roleId");
    Object roleName = param.get("roleName");
    Object roleType = param.get("roleType");
    if (UtilsTool.isValidAll(roleId, roleName, roleType)) {//验证非空
      //角色编号唯一
      Condition condition = new ConditionBuilder(Role.class).and().equal("roleId", roleId).endAnd().buildDone();
      List<Role> list = select(condition);
      if (list.isEmpty()) {
        param.put("roleId", roleId.toString().trim());
        param.put("roleName", roleName.toString().trim());
        param.put("roleType", roleType.toString().trim());
        Role role = new Role().buildCreateInfo().fromMap(param);
        if (role.insert() != -1) {
          return successData(Message.NEW_ADD_SUCCESS, role);
        } else {
          return fail(Message.NEW_ADD_FAIL);
        }
      } else {
        return fail(Message.DATA_CANNOT_BE_DUPLICATED);
      }
    } else {
      return fail(Message.DATA_CANNOT_BE_EMPTY);
    }
  }
}
