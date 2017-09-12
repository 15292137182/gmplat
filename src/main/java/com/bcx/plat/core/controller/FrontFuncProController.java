package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.entity.TemplateObjectPro;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * 前端功能模块属性Controller层
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/fronFuncPro")
public class FrontFuncProController extends
        BaseController {

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
    protected List<String> blankSelectFields() {
        return Collections.singletonList("rowId");
    }


    /**
     * 通用新增方法
     *
     * @param paramEntity 接受一个实体参数
     * @return
     */
    @RequestMapping(value = "/add", method = POST)
    public PlatResult insert(@RequestParam Map<String, Object> paramEntity) {
        ServerResult result = new ServerResult();
        String relateBusiPro = String.valueOf(paramEntity.get("relateBusiPro"));
        List<FrontFuncPro> rowId = frontFuncProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
        int insert = -1;
        if (UtilsTool.isValid(rowId)) {
            FrontFuncPro frontFuncPro = new FrontFuncPro().buildCreateInfo().fromMap(paramEntity);
            insert = frontFuncPro.insert();
            if (insert == -1) {
                return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL));
            } else {
                return super.result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
            }
        } else {
            return super.result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.DATA_QUOTE, insert));
        }
    }

    /**
     * 通过功能块rowId查询功能块属性下对应的数据
     *
     * @param search 空格查询
     * @param rowId  功能块rowId
     * @return 返回serviceResult
     */
    @RequestMapping("/queryPro")
    public PlatResult singleQuery(String search, String rowId) {
        ServerResult result = new ServerResult();
        if (UtilsTool.isValid(rowId)) {
            List<FrontFuncPro> frontFuncPros = frontFuncProService
                    .select(new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
                            UtilsTool.createBlankQuery(Arrays.asList("funcCode", "funcName"), UtilsTool.collectToSet(search))));
//            frontFuncPros = queryResultProcess(frontFuncPros);
            if (frontFuncPros.size() == 0) {
                return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
            } else {
                ServerResult serverResult = new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, frontFuncPros);
                return result(serverResult);
            }
        }
        return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));

    }


    /**
     * 根据功能块rowId查找当前对象下的所有属性并分页显示
     *
     * @param search   按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @return PlatResult
     */
    @RequestMapping("/queryProPage")
    public PlatResult singleInputSelect(String rowId, String search,
                                        @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                        String order) {
        ServerResult serverResult = new ServerResult();
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        if (UtilsTool.isValid(rowId)) {
            PageResult<Map<String, Object>> pageResult = frontFuncProService.selectPageMap(
                    new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
                            UtilsTool.createBlankQuery(Collections.singletonList("displayTitle"), UtilsTool.collectToSet(search)))
                    , orders, pageNum, pageSize);
            PageResult<Map<String, Object>> result = pageResult;

//            result = queryResultProcess(result);
            return result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result));
        }
        return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }


    /**
     * 暂时先放这里 以后再重构
     *
     * @param result 接受ServiceResult
     * @return list
     */
    private List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
        List<String> rowIds = result.stream().map((row) ->
                (String) row.get("relateBusiPro")).collect(Collectors.toList());

//        businessPlatResultProService.selectMap(new FieldCondition("rowId", Operator.IN, rowIds)
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
                        res.put("ename", row.getColumnEname());
                    }
                }
            }
        }
        return result;
    }

    /**
     * 键值集合根据rowId修改数据
     *
     * @param param 接受一个实体参数
     * @return 返回操作信息
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public PlatResult update(@RequestParam Map<String, Object> param) {
        ServerResult result = new ServerResult();
        int update;
        if ((!param.get("rowId").equals("")) || param.get("rowId") != null) {
            FrontFuncPro frontFuncPro = new FrontFuncPro();
            FrontFuncPro modify = frontFuncPro.fromMap(param).buildModifyInfo();
            update = modify.updateById();
            if (update != -1) {
                return result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
            } else {
                return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
            }
        }
        return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }

}
