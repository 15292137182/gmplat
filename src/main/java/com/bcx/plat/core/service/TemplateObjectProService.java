package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: TemplateObjectProService</p>
 * <p>Description: 模板对象属性业务层</p>
 * <p>Copyright: Shanghai BatchSight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 * <pre>History:
 * 2017/8/28  Wen TieHu Create
 * </pre>
 */
@Service
public class TemplateObjectProService extends BaseService<TemplateObjectPro> {


  /**
   * 模板对象属性方法
   *
   * @return 返回操作信息
   */
  public ServerResult addTemplatePro(Map<String, Object> param) {
    ServerResult serverResult = new ServerResult();
    if (!UtilsTool.isValid(param.get("ename").toString().trim())) { // ename不能为纯空格
      return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_EMPTY);
    }
    Condition condition = new ConditionBuilder(TemplateObjectPro.class).and()
        .equal("ename", String.valueOf(param.get("ename")))
        .equal("templateObjRowId", String.valueOf(param.get("templateObjRowId")))
        .endAnd().buildDone();
    List<TemplateObjectPro> select = select(condition);
    if (select.size() == 0) {
      TemplateObjectPro templateObjectPro = new TemplateObjectPro().buildCreateInfo().fromMap(param);
      int insert = templateObjectPro.insert();
      if (insert != -1) {
        return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS, templateObjectPro);
      } else {
        return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL);
      }
    }
    return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_DUPLICATED);
  }



  /**
   * 模板对象属性修改方法
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  public ServerResult modifyTemplatePro( Map<String, Object> param) {
    ServerResult serverResult = new ServerResult();
      if (!UtilsTool.isValid(param.get("ename").toString().trim())) { // ename不能为纯空格
        return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_EMPTY);
      }
      Condition validCondition = new ConditionBuilder(TemplateObjectPro.class).and()
          .equal("ename", String.valueOf(param.get("ename")))
          .equal("templateObjRowId", String.valueOf(param.get("templateObjRowId")))
          .endAnd().buildDone();
      List<TemplateObjectPro> valid = select(validCondition);
      if (valid.size() == 0) { // 判断英文名是否重复
        TemplateObjectPro templateObjectPro = new TemplateObjectPro().buildModifyInfo().fromMap(param);
        Condition condition = new ConditionBuilder(TemplateObjectPro.class).and()
            .equal("proRowId", param.get("proRowId"))
            .endAnd().buildDone();
        int update = templateObjectPro.update(condition);
        if (update != -1) {
          return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, templateObjectPro);
        } else {
          return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL);
        }
      } else {
        return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_DUPLICATED);
      }
  }

}
