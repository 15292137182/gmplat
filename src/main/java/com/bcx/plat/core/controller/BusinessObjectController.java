package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectService;
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

/**
 * 业务对象controller层
 * Created by wth on 2017/8/8.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/businObj")
public class BusinessObjectController extends BaseController {

  @Autowired
  private BusinessObjectService businessObjectService;

  public List<String> blankSelectFields() {
    return Arrays.asList("objectCode", "objectName");
  }

  /**
   * 业务对象新增方法
   *
   * @param param 接受一个实体参数
   * @return PlatResult
   */
  @RequestMapping("/add")
  public PlatResult insert(@RequestParam Map<String, Object> param) {
    //新增业务对象数据
    ServerResult serverResult = businessObjectService.addBusiness(param);
    return result(serverResult);
  }

  /**
   * 根据业务对象rowId查询当前数据
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public PlatResult queryById(String rowId) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult serverResult = businessObjectService.queryById(rowId);
      return result(serverResult);
    }
    return PlatResult.success(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
  }

  /**
   * 查询业务对象全部数据并分页显示
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
                                      String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(order);
    Or blankQuery = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    ServerResult serverResult = businessObjectService.queryPage(blankQuery, pageNum, pageSize, orders);
    return super.result(serverResult);
  }


  /**
   * 根据业务对象rowId查找当前对象下的所有属性并分页显示
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @return PlatResult
   */
  @RequestMapping("/queryProPage")
  public PlatResult queryProPage(String rowId, String search,
                                 @RequestParam(value = "pageNum",required = false) Integer pageNum,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                 String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(order);
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult serverResult = businessObjectService.queryProPage(search, rowId, pageNum, pageSize, orders);
      return result(serverResult);
    } else {
      return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }


  /**
   * 执行变更操作
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @RequestMapping("/changeOperat")
  public PlatResult changeOperation(String rowId) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult serverResult = new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.OPERATOR_SUCCESS,
          businessObjectService.changeOperat(rowId));
      return PlatResult.success(serverResult);
    } else {
      logger.error("执行变更操作失败");
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }


  /**
   * 编辑业务对象
   *
   * @param paramEntity 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping("/modify")
  public PlatResult update(@RequestParam Map<String, Object> paramEntity) {
    String rowId = paramEntity.get("rowId").toString();
    ServerResult serverResult = null;
    if (UtilsTool.isValid(rowId)) {
      BusinessObject businessObject = new BusinessObject().fromMap(paramEntity);
      int update = businessObject.update(new FieldCondition("rowId", Operator.EQUAL, rowId));
      serverResult = new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, update);
    }
    return super.result(serverResult);
  }


  /**
   * 判断当前业务对象下是否有业务对象属性数据,有就全部删除
   *
   * @param rowId 业务对象rowId
   * @return serviceResult
   */
  @RequestMapping("/delete")
  public PlatResult delete(String rowId) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      ServerResult delete = businessObjectService.delete(rowId);
      return super.result(delete);
    } else {
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
    }
  }

  /**
   * 根据业务对象唯一标识查询出业务关联模板属性的信息
   *
   * @param rowId 唯一标示
   * @param order 排序
   * @return PlatResult
   */
  @RequestMapping("/queryTemplatePro")
  public PlatResult queryTemplate(String rowId, String order) {
    ServerResult result = new ServerResult();
    if (UtilsTool.isValid(rowId)) {
      LinkedList<Order> orders = UtilsTool.dataSort(order);
      ServerResult<List<Map<String, Object>>> serverResult = businessObjectService.queryTemplatePro(rowId, orders);
      if (serverResult != null) {
        return super.result(serverResult);
      } else {
        return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
      }
    } else {
      logger.error("查询出业务关联模板属性失败");
      return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }
}
