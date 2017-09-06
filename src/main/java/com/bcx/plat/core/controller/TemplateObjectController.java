package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.TemplateObjectProService;
import com.bcx.plat.core.service.TemplateObjectService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * <p>Title: TemplateObjectController</p>
 * <p>Description: 模板对象控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 * <pre>Histroy:
 *                2017/8/28  Wen TieHu Create
 *          </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/templateObj")
public class TemplateObjectController extends BaseController {

  @Autowired
  private TemplateObjectService templateObjectService;
  @Autowired
  private TemplateObjectProService templateObjectProService;

  /**
   * @return 需要参与参与 空格查询 的字段
   */
  protected List<String> blankSelectFields() {
    return Arrays.asList("templateCode", "templateCode", "templateName");
  }

  /**
   * 根据业务对象 rowId 查找当前对象下的所有属性并分页显示
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param request  request请求
   * @param locale   国际化参数
   * @return ServiceResult
   */
  @RequestMapping("/queryProPage")
  public Object queryProPage(String rowId,
                             String search,
                             @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                             String order,
                             HttpServletRequest request,
                             Locale locale) {
    // 构造查询条件
    List<Condition> ors = new ArrayList<>();
    if (isValid(search)) {
      ors.add(createBlankQuery(blankSelectFields(), collectToSet(search)));
    }
    if (isValid(rowId)) {
      ors.add(new FieldCondition("templateObjRowId", Operator.EQUAL, rowId));
    }
    Condition condition = new Or(ors);

    // 构建排序信息
    List<Order> orders = dataSort(order);

    // 返回结果
    PageResult<Map<String, Object>> result = templateObjectProService.selectPageMap(condition, orders, pageNum, pageSize);

    if (result.getResult().size() != 0) {
      return super.result(request, ServiceResult.Msg(new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result)), locale);
    }
    return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
  }

  @RequestMapping("/query")
  public Object singleInputSelect(String search, HttpServletRequest request, Locale locale) {
    List<TemplateObject> result = templateObjectService.select(createBlankQuery(blankSelectFields(), collectToSet(search)));
    if (result.size() == 0) {
      return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
    }
    return super.result(request, ServiceResult.Msg(new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result)), locale);
  }

  /**
   * 根据功能块rowId查询当前数据
   *
   * @param rowId   功能块rowId
   * @param request request请求
   * @param locale  国际化参数
   * @return ServiceResult
   */
  @RequestMapping("/queryById")
  public Object queryById(String rowId, HttpServletRequest request, Locale locale) {
    List result = templateObjectService
            .select(new FieldCondition("rowId", Operator.EQUAL, rowId));
//    result = queryResultProcess(result);
    if (result.size() == 0) {
      return result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
    }
    return result(request, ServiceResult.Msg(new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result)), locale);
  }

  /**
   * 通用查询方法
   *
   * @param search   按照空格查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param request  request请求
   * @param locale   国际化参数
   * @return ServiceResult
   */
  @RequestMapping("/queryPage")
  public Object singleInputSelect(String search, @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize, String order, HttpServletRequest request, Locale locale) {
    LinkedList<Order> orders = dataSort(order);
    pageNum = search == null || search.isEmpty() ? 1 : pageNum;
    PageResult<Map<String, Object>> result = templateObjectService
            .selectPageMap(createBlankQuery(blankSelectFields(), collectToSet(search)), orders, pageNum, pageSize);
    return result(request, ServiceResult.Msg(new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result)), locale);
  }


  /**
   * 通用新增方法
   *
   * @param entity  接受一个实体参数
   * @param request request请求
   * @param locale  国际化参数
   * @return
   */
  @RequestMapping("/add")
  public Object insert(TemplateObject entity, HttpServletRequest request, Locale locale) {
    int insert = entity.buildCreateInfo().insert();
    if (insert != 1) {
      return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL)), locale);
    }
    return super.result(request, commonServiceResult(entity, Message.NEW_ADD_SUCCESS), locale);
  }


  /**
   * 通过修改方法
   *
   * @param entity  接受一个实体参数
   * @param request request请求
   * @param locale  国际化参数
   * @return
   */
  @RequestMapping("/modify")
  public Object update(TemplateObject entity, HttpServletRequest request, Locale locale) {
    entity.getBaseTemplateBean().buildModifyInfo();
    int update = entity.updateById();
    if (update != 1) {
      return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL)), locale);
    }
    return super.result(request, commonServiceResult(entity, Message.UPDATE_SUCCESS), locale);
  }

  /**
   * 通用删除方法
   *
   * @param rowId   按照rowId查询
   * @param request request请求
   * @param locale  国际化参数
   * @return
   */
  @RequestMapping("/delete")
  public Object delete(String rowId, HttpServletRequest request, Locale locale) {
    if (isValid(rowId)) {
      int i = new TemplateObject().deleteById(rowId);
      return super.result(request, commonServiceResult(i, Message.DELETE_SUCCESS), locale);
    }
    return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL)), locale);

  }
//
//  /**
//   * 通用状态生效方法
//   *
//   * @param rowId   接受的唯一标示
//   * @param request
//   * @param locale
//   * @return
//   */
//  @RequestMapping("/takeEffect")
//  public Object updateTakeEffect(String rowId, HttpServletRequest request, Locale locale) {
//    HashMap<String, Object> map = new HashMap<>();
//    map.put("rowId", rowId);
//    map.put("status", BaseConstants.TAKE_EFFECT);
//    map.put("modifyTime", UtilsTool.getDateTimeNow());
//    entityService.update(map);
//    return super.result(request, commonServiceResult(rowId, Message.UPDATE_SUCCESS), locale);
//  }


  /**
   * 接受参数和消息进行封装
   *
   * @param content 接受的参数
   * @param msg     消息
   * @param <T>
   * @return
   */
  private <T> ServiceResult<T> commonServiceResult(T content, String msg) {
    return ServiceResult.Msg(new PlatResult<>(BaseConstants.STATUS_SUCCESS, msg, content));
  }

}
