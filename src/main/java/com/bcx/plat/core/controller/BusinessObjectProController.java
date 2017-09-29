package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 业务对象属性Controller层
 * Created by Wen Tiehu on 2017/8/8.
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/businObjPro")
@RestController
public class BusinessObjectProController extends BaseController {

  @Autowired
  private BusinessObjectProService businessObjectProService;


  /**
   * 根据业务对象属性rowId查询当前数据
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public PlatResult queryById(String rowId) {
    if (isValid(rowId)) {
      ServerResult serverResult = businessObjectProService.queryById(rowId);
      return result(serverResult);
    } else {
      return error(Message.QUERY_FAIL);
    }
  }

  /**
   * 供前端功能块属性使用
   * 根据业务对象rowId查询当前业务对象下的所有属性
   *
   * @param objRowId   根据业务对象rowId查找业务对象下所有属性
   * @param frontRowId 功能块rowId
   * @return PlatResult
   */
  @RequestMapping("/queryBusinPro")
  public PlatResult queryBusinPro(String objRowId, String frontRowId) {
    if (isValid(objRowId)) {
      ServerResult serverResult = businessObjectProService.queryBusinPro(objRowId, frontRowId);
      return result(serverResult);
    } else {
      return error(Message.QUERY_FAIL);
    }
  }

  /**
   * 新增业务对象属性
   *
   * @param paramEntity 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/add")
  public PlatResult addBusinessObjPro(@RequestParam Map<String, Object> paramEntity) {
    ServerResult serverResult = businessObjectProService.insertBusinessPro(paramEntity);
    return result(serverResult);
  }

  /**
   * 编辑业务对象属性
   *
   * @param paramEntity 实体参数
   * @return Map
   */
  @PostMapping("/modify")
  public PlatResult modifyBusinessObjPro(@RequestParam Map<String, Object> paramEntity) {
    if (isValid(paramEntity.get("rowId"))) {
      ServerResult serverResult = businessObjectProService.updateBusinessPro(paramEntity);
      return result(serverResult);
    } else {
      return error(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 业务对象属性删除方法
   *
   * @param rowId 按照rowId查询
   * @return PlatResult
   */
  @PostMapping("/delete")
  public PlatResult delete(String rowId) {
    if (isValid(rowId)) {
      ServerResult serverResult = businessObjectProService.deleteBusinessPro(rowId);
      return result(serverResult);
    } else {
      return error(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }
}
