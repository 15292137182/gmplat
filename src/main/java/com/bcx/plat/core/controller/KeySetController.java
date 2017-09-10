package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 键值集合Controller层
 * Created by Went on 2017/8/3.
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/keySet")
@RestController
public class KeySetController extends BaseController/*<KeySetService>*/ {
//
//  private final KeySetService keySetService;
//
//  @Autowired
//  public KeySetController(KeySetService keySetService) {
//    this.keySetService = keySetService;
//  }
//
//  protected List<String> blankSelectFields() {
//    return Arrays.asList("keysetCode", "keysetName");
//  }
//
//  /**
//   * 根据keysetCode查询，以数组的形式传入数据进来["demo","test"]
//   *
//   * @param search  按照空格查询
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return PlatResult
//   * ["demo","test"]
//   */
//  @RequestMapping("/queryKeySet")
//  public Object queryKeySet(String search, HttpServletRequest request, Locale locale) {
//    if (UtilsTool.isValid(search)) {
//      List list = UtilsTool.jsonToObj(search, List.class);
//      return super.result(request, PlatResult.Msg(keySetService.queryKeySet(list)), locale);
//    } else {
//      return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
//    }
//  }
//
//  /**
//   * 根据主表键值集合代码查询数据
//   *
//   * @param keyCode 按照空格查询
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return PlatResult
//   */
//  @RequestMapping("/queryKeyCode")
//  public Object queryKeyCode(String keyCode, HttpServletRequest request, Locale locale) {
//    if (UtilsTool.isValid(keyCode)) {
//      return super.result(request, PlatResult.Msg(keySetService.queryKeyCode(keyCode)), locale);
//    } else {
//      return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
//    }
//  }
//
//  /**
//   * 根据键值集合主表查询从表详细信息
//   *
//   * @param rowId   唯一标识
//   * @param request 请求
//   * @param locale  国际化
//   * @return
//   */
//  @RequestMapping("/queryPro")
//  public Object queryPro(String rowId, HttpServletRequest request, Locale locale) {
//    if (UtilsTool.isValid(rowId)) {
//      return super.result(request, PlatResult.Msg(keySetService.queryPro(rowId)), locale);
//    } else {
//      return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
//    }
//  }
//
//
//  /**
//   * 根据业务对象rowId查找当前对象下的所有属性并分页显示
//   *
//   * @param search   按照空格查询
//   * @param pageNum  当前第几页
//   * @param pageSize 一页显示多少条
//   * @param request  request请求
//   * @param locale   国际化参数
//   * @return PlatResult
//   */
//  @RequestMapping("/queryProPage")
//  public Object queryProPage(String rowId, String search,
//                             @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
//                             @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
//                             String order, HttpServletRequest request, Locale locale) {
//    LinkedList<Order> orders = UtilsTool.dataSort(order);
//    if (UtilsTool.isValid(rowId)) {
//      return super.result(request, PlatResult.Msg(keySetService.queryProPage(search, rowId, pageNum, pageSize, orders)), locale);
//    } else {
//      return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
//    }
//  }
//
//
//  /**
//   * 判断当前业务对象下是否有业务对象属性数据,有就全部删除
//   *
//   * @param rowId   业务对象rowId
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return serviceResult
//   */
//  @RequestMapping("/delete")
//  public Object delete(String rowId, HttpServletRequest request, Locale locale) {
//    if (UtilsTool.isValid(rowId)) {
//      return super.result(request, PlatResult.Msg(keySetService.delete(rowId)), locale);
//    } else {
//      return super.result(request, PlatResult.Msg(null), locale);
//    }
//  }
//
//  /**
//   * 通用新增方法
//   *
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return 返回操作信息
//   */
//  @RequestMapping("/add")
//  public Object insert(HttpServletRequest request, Locale locale) {
//    return super.insert(new KeySet().fromMap(request.getParameterMap()), request, locale);
//  }
//
//  /**
//   * 通过修改方法
//   *
//   * @param entity  接受一个实体参数
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return 返回操作信息
//   */
//  @RequestMapping("/modify")
//  public Object update(Map entity, HttpServletRequest request, Locale locale) {
//    return super.updateById(new KeySet().fromMap(entity), request, locale);
//  }
//
//  /**
//   * 根据编号查询数据
//   *
//   * @param search  按照空格查询
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return ServiceResult
//   */
//  @RequestMapping("/queryNumber")
//  public Object queryNumber(String search, HttpServletRequest request, Locale locale) {
//    return super.result(request, PlatResult.Msg(keySetService.queryKeyCode(search)), locale);
//  }
}
