package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.SysConfig;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.SysConfigService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * 系统资源配置controller层
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/sysConfig")
public class SysConfigController extends BaseController {

  @Autowired
  SysConfigService sysConfigService;

  protected List<String> blankSelectFields() {
    return Arrays.asList("confKey", "confValue");
  }

  /**
   * 系统资源配置查询方法
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult singleInputSelect(String search,
                                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      String param,
                                      String order) {
    LinkedList<Order> orders = dataSort(order);
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndCondition(SysConfig.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }

    PageResult<Map<String, Object>> result;
    if (UtilsTool.isValid(pageSize)) { // 判断分页查询
      result = sysConfigService.selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      result = new PageResult(sysConfigService.selectMap(condition, orders));
    }
    return result(new ServerResult<>(result));
  }

  /**
   * 系统资源配置数据新增数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping(value = "/add", method = POST)
  public PlatResult insert(@RequestParam Map<String, Object> param) {
    SysConfig sysConfig = new SysConfig().buildCreateInfo().fromMap(param);
    int insert = sysConfig.insert();
    if (insert != -1) {
      return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
    } else {
      return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_FAIL));
    }
  }

  /**
   * 系统资源配置根据rowId修改数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping(value = "/modify", method = POST)
  public PlatResult update(@RequestParam Map<String, Object> param) {
    int update;
    if ((!param.get("rowId").equals("")) || param.get("rowId") != null) {
      SysConfig sysConfig = new SysConfig();
      SysConfig modify = sysConfig.fromMap(param).buildModifyInfo();
      update = modify.updateById();
      if (update != -1) {
        return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
      } else {
        return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
      }
    }
    return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
  }

  /**
   * 根据系统资源配置rowId删除当前数据
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @RequestMapping(value = "/delete", method = POST)
  public PlatResult delete(String rowId) {
    int del;
    if (!rowId.isEmpty()) {
      SysConfig sysConfig = new SysConfig();
      del = sysConfig.deleteById(rowId);
      if (del != -1) {
        return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS));
      } else {
        return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
      }
    } else {
      return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }


}
