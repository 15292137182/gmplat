package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.SysConfig;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.SysConfigService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.isValid;


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
  public PlatResult queryPageSysConfig(String search, String param, Integer pageNum, Integer pageSize, String order) {
    ServerResult serverResult = sysConfigService.queryPageSysConfig(search, param, pageNum, pageSize, order);
    return result(serverResult);
  }

  /**
   * 系统资源配置数据新增数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @PostMapping(value = "/add")
  public PlatResult insert(@RequestParam Map<String, Object> param) {
    ServerResult serverResult = sysConfigService.addSysConfig(param);
    return result(serverResult);
  }

  /**
   * 系统资源配置根据rowId修改数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  @PostMapping(value = "/modify")
  public PlatResult update(@RequestParam Map<String, Object> param) {
    if (isValid(param.get("rowId"))) {
      ServerResult serverResult = sysConfigService.updateSysConfig(param);
      return result(serverResult);
    } else {
      return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }

  /**
   * 根据系统资源配置rowId删除当前数据
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @PostMapping(value = "/delete")
  public PlatResult delete(String rowId) {
    Condition condition = new ConditionBuilder(SysConfig.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<Map> maps = sysConfigService.selectMap(condition);
    if (isValid(rowId)) {
      SysConfig sysConfig = new SysConfig();
      int del = sysConfig.deleteById(rowId);
      if (del != -1) {
        return super.result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS, maps));
      } else {
        return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
      }
    } else {
      return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }
  }


}
