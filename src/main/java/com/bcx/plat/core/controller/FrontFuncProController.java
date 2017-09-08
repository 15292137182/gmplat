package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.*;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.DBTableColumnService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.service.TemplateObjectProService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;


/**
 * 前端功能模块属性Controller层
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/fronFuncPro")
public class FrontFuncProController extends
        BaseController<FrontFuncProService> {

    private final TemplateObjectProService templateObjectProService;
    private final BusinessObjectProService businessObjectProService;
    private final DBTableColumnService dbTableColumnService;
    private final FrontFuncProService frontFuncProService;

    @Autowired
    public FrontFuncProController(FrontFuncProService frontFuncProService, BusinessObjectProService businessObjectProService, TemplateObjectProService templateObjectProService, DBTableColumnService dbTableColumnService) {
        this.businessObjectProService = businessObjectProService;
        this.templateObjectProService = templateObjectProService;
        this.dbTableColumnService = dbTableColumnService;
        this.frontFuncProService = frontFuncProService;
    }

    /**
     * 模糊查询的字段
     *
     * @return 字段
     */
    @Override
    protected List<String> blankSelectFields() {
        return Collections.singletonList("rowId");
    }


//    /**
//     * 通用新增方法
//     *
//     * @param entity  接受一个实体参数
//     * @param request request请求
//     * @param locale  国际化参数
//     * @return
//     */
//    @RequestMapping("/add")
//    @Override
//    public Object insert(FrontFuncPro entity, HttpServletRequest request, Locale locale) {
//        String relateBusiPro = entity.getRelateBusiPro();
//        List<Map<String, Object>> rowId = frontFuncProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
//        int insert =0;
//        if (UtilsTool.isValid(rowId)) {
//             insert = frontFuncProService.insert(entity.buildCreateInfo().toMap());
//            if (insert != 1) {
//                return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL)), locale);
//            }else {
//                return super.result(request, PlatResult.Msg(new ServerResult(BaseConstants.STATUS_SUCCESS,Message.NEW_ADD_SUCCESS,insert)), locale);
//            }
//        }else {
//            return super.result(request, PlatResult.Msg(new ServerResult(BaseConstants.STATUS_SUCCESS,Message.DATA_QUOTE,insert)), locale);
//        }
//    }

    /**
     * 通过功能块rowId查询功能块属性下对应的数据
     *
     * @param str     空格查询
     * @param rowId   功能块rowId
     * @param request 请求
     * @param locale  国际化参数
     * @return 返回serviceResult
     */
    @RequestMapping("/queryPro")
    public Object singleQuery(String str, String rowId, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(rowId)) {

            List<FrontFuncPro> frontFuncPros = frontFuncProService
                    .select(new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
                            UtilsTool.createBlankQuery(Arrays.asList("funcCode", "funcName"), UtilsTool.collectToSet(str))));
//            frontFuncPros = queryResultProcess(frontFuncPros);
            if (frontFuncPros.size() == 0) {
                return result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
            } else {
                ServerResult serverResult = new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, frontFuncPros);
                return result(request, PlatResult.Msg(serverResult), locale);
            }
        }
        return result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);

    }


    /**
     * 根据功能块rowId查找当前对象下的所有属性并分页显示
     *
     * @param search   按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return PlatResult
     */
    @RequestMapping("/queryProPage")
    public Object singleInputSelect(String rowId, String search,
                                    @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                    HttpServletRequest request, Locale locale, String order) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        if (UtilsTool.isValid(rowId)) {
            PageResult<Map<String, Object>> pageResult = frontFuncProService.selectPageMap(
                    new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
                            UtilsTool.createBlankQuery(Collections.singletonList("displayTitle"), UtilsTool.collectToSet(search)))
                    , orders, pageNum, pageSize);
            PageResult<Map<String, Object>> result = pageResult;

//            result = queryResultProcess(result);
            return result(request, PlatResult.Msg(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result)), locale);
        }
        return result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
    }


    /**
     * 暂时先放这里 以后再重构
     *
     * @param result 接受ServiceResult
     * @return list
     */
    protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
        List<String> rowIds = result.stream().map((row) ->
                (String) row.get("relateBusiPro")).collect(Collectors.toList());

//        businessObjectProService.selectMap(new FieldCondition("rowId", Operator.IN, rowIds)
//                , Arrays.asList(new Field("row_id", "rowId")
//                        , new Field("property_name", "propertyName"),null));

                /*.selectColumns(new FieldCondition("rowId", Operator.IN, rowIds)
                        , Arrays.asList(new Field("row_id", "rowId")
                                , new Field("property_name", "propertyName")), null);*/
        HashMap<String, Object> map = new HashMap<>();
        //遍历获取业务对象基本属性
//        for (Map<String, Object> row : results) {
//            map.put((String) row.get("rowId"), row.get("propertyName"));
//        }
        //拿到业务对象基本属性给功能块赋值
        for (Map<String, Object> row : result) {
            row.put("propertyName", map.get(row.get("relateBusiPro")));
        }
        //遍历模板对象
        for (Map<String, Object> res : result) {
            String attrSource = res.get("attrSource").toString();
            if (attrSource.equals(BaseConstants.ATTRIBUTE_SOURCE_MODULE)) {
                String relateBusiPro = res.get("relateBusiPro").toString();
                List<TemplateObjectPro> proRowId = templateObjectProService.select(new FieldCondition("proRowId", Operator.EQUAL, relateBusiPro));
                //拿到模板对象属性给功能块属性赋值
                for (TemplateObjectPro pro : proRowId) {

                    res.put("propertyName", pro.getCname());
                    res.put("ename", pro.getEname());
                }
            } else if (attrSource.equals(BaseConstants.ATTRIBUTE_SOURCE_BASE)) {
                String relateBusiPro = res.get("relateBusiPro").toString();
                List<BusinessObjectPro> relateTableColumn = businessObjectProService.select(new FieldCondition("relateTableColumn", Operator.EQUAL, relateBusiPro));
                for (BusinessObjectPro relate : relateTableColumn) {
                    String relateTableRowId = relate.getRelateTableColumn();
                    List<DBTableColumn> rowId = dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableRowId));
                    for (DBTableColumn row : rowId) {
                        res.put("ename",row.getColumnEname());
                    }
                }
            }
        }
        return result;
    }



    /**
     * 通用新增方法
     *
     * @param entity  接受一个实体参数
     * @param request request请求
     * @param locale  国际化参数
     * @return 返回操作信息
     */
    @RequestMapping("/add")
    public Object insert(Map entity, HttpServletRequest request, Locale locale) {
        return super.insert(new FrontFuncPro().fromMap(entity), request, locale);
    }


    /**
     * 通过修改方法
     *
     * @param entity  接受一个实体参数
     * @param request request请求
     * @param locale  国际化参数
     * @return 返回操作信息
     */
    @RequestMapping("/modify")
    public Object update(Map entity, HttpServletRequest request, Locale locale) {
        return super.updateById(new TemplateObject().fromMap(entity), request, locale);
    }
}
