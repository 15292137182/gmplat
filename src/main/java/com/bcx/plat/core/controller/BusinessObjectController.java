package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessRelateTemplate;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.BusinessRelateTemplateService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 业务对象controller层
 * Created by wth on 2017/8/8.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/businObj")
public class BusinessObjectController extends BaseController {

    private final BusinessObjectService businessObjectService;

    @Autowired
    public BusinessObjectController(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    protected List<String> blankSelectFields() {
        return Arrays.asList("objectCode", "objectName");
    }


    /**
     * 业务对象新增方法
     *
     * @param param   接受一个实体参数
     * @param request request请求
     * @param locale  国际化参数
     * @return
     */
    @RequestMapping("/add")
    public Map insert(@RequestParam Map<String, Object> param, HttpServletRequest request, Locale locale) {
        //新增业务对象数据
        BusinessObject businessObject = new BusinessObject().buildCreateInfo().fromMap(param);
        ServerResult serverResult = businessObjectService.addBusiness(businessObject);
        return super.result(request, PlatResult.success(serverResult), locale);
    }

    /**
     * 根据业务对象rowId查询当前数据
     *
     * @param rowId   唯一标识
     * @param request request请求
     * @param locale  国际化参数
     * @return PlatResult
     */
    @RequestMapping("/queryById")
    public Map queryById(String rowId, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(rowId)) {
            return super.result(request, PlatResult.success(businessObjectService.queryById(rowId)), locale);
        }
        return PlatResult.success(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }

    /**
     * 查询业务对象全部数据并分页显示
     *
     * @param search   按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return PlatResult
     */
    @RequestMapping("/queryPage")
    public Map singleInputSelect(String search,
                                 @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                 String order, HttpServletRequest request, Locale locale) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        pageNum = !UtilsTool.isValid(search) ? 1 : pageNum;
        return super.result(request, PlatResult.success(businessObjectService.queryPage(search, pageNum, pageSize, orders)), locale);
    }


    /**
     * 根据业务对象rowId查找当前对象下的所有属性并分页显示
     *
     * @param search   按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return PlatResult
     */
    @RequestMapping("/queryProPage")
    public Map queryProPage(String rowId, String search,
                            @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                            @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                            String order, HttpServletRequest request, Locale locale) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        if (UtilsTool.isValid(rowId)) {
            ServerResult serverResult = businessObjectService.queryProPage(search, rowId, pageNum, pageSize, orders);
            return super.result(request, PlatResult.success(serverResult), locale);
        } else {
            return super.result(request, PlatResult.success(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
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
    public Map changeOperat(String rowId, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(rowId)) {
            ServerResult serverResult = new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.OPERATOR_SUCCESS,
                    businessObjectService.changeOperat(rowId));
            return PlatResult.success(serverResult);
        } else {
            logger.error("执行变更操作失败");
            return super.result(request, PlatResult.success(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
        }
    }


    /**
     * 编辑业务对象
     *
     * @param paramEntity 接受一个实体参数
     * @param request     request请求
     * @param locale      国际化参数
     * @return 返回操作信息
     */
    @RequestMapping("/modify")
    public Object update(@RequestParam Map<String, Object> paramEntity, HttpServletRequest request, Locale locale) {
        String rowId = paramEntity.get("rowId").toString();
        ServerResult serverResult = null;
        if (UtilsTool.isValid(rowId)) {
            BusinessObject businessObject = new BusinessObject().fromMap(paramEntity);
            int update = businessObject.update(new FieldCondition("rowId", Operator.EQUAL, rowId));
            serverResult = new ServerResult(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, update);
        }
        return super.result(request, PlatResult.success(serverResult), locale);
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
    public Map delete(String rowId, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(rowId)) {
            return super.result(request, PlatResult.success(businessObjectService.delete(rowId)), locale);
        } else {
            return super.result(request, PlatResult.success(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL)), locale);
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
    public Map queryTemplate(String rowId, String order, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(rowId)) {
            LinkedList<Order> orders = UtilsTool.dataSort(order);
            ServerResult<List<Map<String, Object>>> serverResult = businessObjectService.queryTemplatePro(rowId, orders);
            return super.result(request, PlatResult.success(serverResult), locale);
        } else {
            logger.error("查询出业务关联模板属性失败");
            return super.result(request, PlatResult.success(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
        }
    }


//    /**
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
