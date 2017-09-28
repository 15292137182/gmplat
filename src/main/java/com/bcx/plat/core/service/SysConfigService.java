package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.SysConfig;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.dataSort;

/**
 * 系统资源配置信息维护 service层
 * Created by Wen Tiehu on 2017/8/7.
 */
@Service
public class SysConfigService extends BaseService<SysConfig> {

  protected List<String> blankSelectFields() {
    return Arrays.asList("confKey", "confValue");
  }


  /**
   * 新增系统资源配置信息
   *
   * @param param 新增参数
   * @return ServerResult
   */
  public ServerResult addSysConfig(Map<String, Object> param) {
    ServerResult serverResult = new ServerResult();
    String confKey = String.valueOf(param.get("confKey")).trim();
    param.put("confKey", confKey);
    if (!("".equals(confKey))) {
      Condition condition = new ConditionBuilder(SysConfig.class).and().equal("confKey", param.get("confKey")).endAnd().buildDone();
      List<SysConfig> select = select(condition);
      if (select.size() == 0) {
        SysConfig sysConfig = new SysConfig().buildCreateInfo().fromMap(param);
        int insert = sysConfig.insert();
        if (insert != -1) {
          return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS, sysConfig);
        } else {
          return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL);
        }
      }
      return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_DUPLICATED);
    }
    return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_EMPTY);
  }


  /**
   * 修改系统资源配置
   *
   * @param param 需要修改的参数
   * @return ServerResult
   */
  public ServerResult updateSysConfig(Map<String, Object> param) {
    ServerResult serverResult = new ServerResult();
    String confKey = String.valueOf(param.get("confKey")).trim();
    param.put("confKey", confKey);
    if (!("".equals(confKey))) {
      Condition condition = new ConditionBuilder(SysConfig.class).and().equal("confKey", param.get("confKey")).endAnd().buildDone();
      List<SysConfig> select = select(condition);
      if (select.size() == 0 || select.get(0).getRowId().equals(param.get("rowId"))) {
        SysConfig sysConfig = new SysConfig();
        SysConfig modify = sysConfig.fromMap(param).buildModifyInfo();
        int update = modify.updateById();
        if (update != -1) {
          return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, modify);
        } else {
          return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL);
        }
      } else {
        return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_DUPLICATED);
      }
    } else {
      return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_EMPTY);
    }
  }


  /**
   * 系统资源配置分页查询
   *
   * @param search   按照空格查询
   * @param param    精确查询
   * @param pageNum  显示条数
   * @param pageSize 页码
   * @param order    排序
   * @return ServerResult
   */
  public ServerResult queryPageSysConfig(String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = dataSort(SysConfig.class, order);
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(SysConfig.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult<Map<String, Object>> result;
    if (UtilsTool.isValid(pageSize)) { // 判断分页查询
      result = selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      result = new PageResult(selectMap(condition, orders));
    }
    return new ServerResult<>(result);
  }

}
