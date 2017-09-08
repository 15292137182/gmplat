//package com.bcx.plat.core.controller;
//
//import com.bcx.plat.core.base.BaseConstants;
//import com.bcx.plat.core.base.BaseController;
//import com.bcx.plat.core.entity.SysConfig;
//import com.bcx.plat.core.morebatis.component.Order;
//import com.bcx.plat.core.service.SysConfigService;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//
//import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
//import static com.bcx.plat.core.utils.UtilsTool.*;
//import static org.springframework.web.bind.annotation.RequestMethod.POST;
//
//
///**
// * 系统资源配置controller层
// * Created by Wen Tiehu on 2017/8/4.
// */
//@RestController
//@RequestMapping(PLAT_SYS_PREFIX + "/core/sysConfig")
//public class SysConfigController extends BaseController<SysConfigService> {
//
//  @Override
//  protected List<String> blankSelectFields() {
//    return Arrays.asList("confKey", "confValue");
//  }
//
//  /**
//   * 通用查询方法
//   *
//   * @param search   按照空格查询
//   * @param pageNum  当前第几页
//   * @param pageSize 一页显示多少条
//   * @param locale   国际化参数
//   * @return PlatResult
//   */
//  @RequestMapping("/queryPage")
//  public Object singleInputSelect(String search,
//                                  @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
//                                  @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
//                                  String order,
//                                  Locale locale, HttpServletRequest request) {
//    LinkedList<Order> orders = dataSort(order);
//    pageNum = search == null || search.isEmpty() ? 1 : pageNum;
//    return selectPage(locale, createBlankQuery(blankSelectFields(), collectToSet(search)), orders, pageNum, pageSize);
//  }
//
//
//  /**
//   * 通用新增方法
//   *
////   * @param entity  接受一个实体参数
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return 返回操作信息
//   */
//  @RequestMapping(value = "/add" ,method = POST)
//  public Object insert(@RequestParam Map<String,Object> param, HttpServletRequest request, Locale locale) {
//    SysConfig sysConfig = new SysConfig().fromMap(param);
//    return super.insert(sysConfig, request, locale);
//  }
//
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
//    return super.updateById(new SysConfig().fromMap(entity), request, locale);
//  }
//
//  /**
//   * 通用删除方法
//   *
//   * @param rowId   按照rowId查询
//   * @param request request请求
//   * @param locale  国际化参数
//   * @return 返回操作信息
//   */
//  @RequestMapping("/delete")
//  public Object delete(String rowId, HttpServletRequest request, Locale locale) {
//    return deleteByIds(request, locale, rowId);
//  }
//
//}
