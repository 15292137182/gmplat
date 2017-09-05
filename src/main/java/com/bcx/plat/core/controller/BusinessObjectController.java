package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessRelateTemplate;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.BusinessRelateTemplateService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 业务对象controller层
 * Created by wth on 2017/8/8.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/businObj")
public class BusinessObjectController extends BaseControllerTemplate<BusinessObjectService, BusinessObject> {

  private final BusinessObjectService businessObjectService;
  private final BusinessRelateTemplateService businessRelateTemplateService;

  @Autowired
  public BusinessObjectController(BusinessObjectService businessObjectService, BusinessRelateTemplateService businessRelateTemplateService) {
    this.businessObjectService = businessObjectService;
    this.businessRelateTemplateService = businessRelateTemplateService;
  }

  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("objectCode", "objectName");
  }


  /**
   * 通用新增方法
   *
   * @param businessObject 接受一个实体参数
   * @param request        request请求
   * @param locale         国际化参数
   * @return
   */
  @RequestMapping("/add")
  public Object insert(BusinessObject businessObject, HttpServletRequest request, Locale locale) {
    BusinessRelateTemplate brt = new BusinessRelateTemplate();
    int insert = businessObjectService.insert(businessObject.buildCreateInfo().toMap());
    String rowId = businessObject.getRowId();
    String rto = businessObject.getRelateTemplateObject();
    List list = UtilsTool.jsonToObj(rto, List.class);
    for (Object li : list) {
      brt.setBusinessRowId(rowId);
      brt.setTemplateRowId(li.toString());
      businessRelateTemplateService.insert(brt.buildCreateInfo().toMap());
    }
    if (insert != 1) {
      return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL)), locale);
    } else {
      return super.result(request, ServiceResult.Msg(new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS, insert)), locale);
    }
  }

  /**
   * 根据业务对象rowId查询当前数据
   *
   * @param rowId   唯一标识
   * @param request request请求
   * @param locale  国际化参数
   * @return ServiceResult
   */
  @RequestMapping("/queryById")
  @Override
  public Object queryById(String rowId, HttpServletRequest request, Locale locale) {
    if (UtilsTool.isValid(rowId)) {
      return super.result(request, ServiceResult.Msg(businessObjectService.queryById(rowId)), locale);
    }
    return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL)), locale);
  }

  /**
   * 查询业务对象全部数据并分页显示
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param request  request请求
   * @param locale   国际化参数
   * @return ServiceResult
   */
  @RequestMapping("/queryPage")
  @Override
  public Object singleInputSelect(String search,
                                  @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                  String order, HttpServletRequest request, Locale locale) {
    LinkedList<Order> orders = UtilsTool.dataSort(order);
    pageNum = !UtilsTool.isValid(search) ? pageNum = 1 : pageNum;
    return super.result(request, ServiceResult.Msg(businessObjectService.queryPage(search, pageNum, pageSize, orders)), locale);
  }


  /**
   * 根据业务对象rowId查找当前对象下的所有属性并分页显示
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param request  request请求
   * @param locale   国际化参数
   * @return ServiceResult
   */
  @RequestMapping("/queryProPage")
  public Object queryProPage(String rowId, String search,
                             @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                             String order, HttpServletRequest request, Locale locale) {
    LinkedList<Order> orders = UtilsTool.dataSort(order);
    if (UtilsTool.isValid(rowId)) {
      return super.result(request, ServiceResult.Msg(businessObjectService.queryProPage(search, rowId, pageNum, pageSize, orders)), locale);
    }else{
      logger.error("业务对象rowId查找当前对象下的所有属性是失败");
      return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL)), locale);
    }
  }


  /**
   * 执行变更操作
   *
   * @param rowId   业务对象rowId
   * @param request request请求
   * @param locale  国际化参数
   * @return serviceResult
   */
  @RequestMapping("/changeOperat")
  public Object changeOperat(String rowId, HttpServletRequest request, Locale locale) {
    if (UtilsTool.isValid(rowId)) {
      PlatResult platResult = new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.OPERATOR_SUCCESS,
              businessObjectService.changeOperat(rowId));
      return super.result(request, ServiceResult.Msg(platResult), locale);
    } else {
      logger.error("执行变更操作失败");
      return ServiceResult.ErrorMsg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }
  }


  /**
   * 判断当前业务对象下是否有业务对象属性数据,有就全部删除
   *
   * @param rowId   业务对象rowId
   * @param request request请求
   * @param locale  国际化参数
   * @return serviceResult
   */
  @RequestMapping("/delete")
  @Override
  public Object delete(String rowId, HttpServletRequest request, Locale locale) {
    if (UtilsTool.isValid(rowId)) {
      return super.result(request, ServiceResult.Msg(businessObjectService.delete(rowId)), locale);
    } else {
      logger.error("删除业务对象失败");
      return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL)), locale);
    }
  }

  /**
   * 根据业务对象唯一标识查询出业务关联模板属性的信息
   *
   * @param rowId
   * @param order
   * @param request
   * @param locale
   * @return
   */
  @RequestMapping("/queryTemplatePro")
  public Object queryTemplate(String rowId, String order, HttpServletRequest request, Locale locale) {
    if (UtilsTool.isValid(rowId)) {
      LinkedList<Order> orders = UtilsTool.dataSort(order);
      return super.result(request, ServiceResult.Msg(businessObjectService.queryTemplatePro(rowId, orders)), locale);
    } else {
      logger.error("查询出业务关联模板属性失败");
      return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
    }
  }


//    /**
//     * 暂时先放这里 以后再重构
//     *
//     * @param result 返回值
//     * @return 返回值
//     */
//    @Override
//    protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
//        List<String> rowIds = result.stream().map((row) -> {
//            return (String) row.get("relateTableRowId");
//        }).collect(Collectors.toList());
//        List<Map<String, Object>> results = maintDBTablesService
//                .select(new FieldCondition("rowId", Operator.IN, rowIds)
//                        , Arrays.asList(new Field("row_id", "rowId")
//                                , new Field("table_cname", "tableCname")
//                                , new Field("table_schema", "tableSchema")), null);
//        HashMap<String, Object> map = new HashMap<>();
//        for (Map<String, Object> row : results) {
//            map.put((String) row.get("rowId"), row.get("tableCname"));
//        }
//        for (Map<String, Object> row : result) {
//            row.put("associatTable", map.get(row.get("relateTableRowId")));
//        }
//        return result;
//    }
}
