package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 业务对象业务层
 * Created by Wen Tiehu on 2017/8/7.
 */
@Service
public class BusinessObjectService extends BaseServiceTemplate<BusinessObject> {


    private MaintDBTablesService maintDBTablesService;
    private BusinessObjectProService businessObjectProService;
    private DBTableColumnService dbTableColumnService;
    private FrontFuncService frontFuncService;
    private FrontFuncProService frontFuncProService;

    @Autowired
    public BusinessObjectService(MaintDBTablesService maintDBTablesService, BusinessObjectProService businessObjectProService, DBTableColumnService dbTableColumnService, FrontFuncService frontFuncService, FrontFuncProService frontFuncProService) {
        this.maintDBTablesService = maintDBTablesService;
        this.businessObjectProService = businessObjectProService;
        this.dbTableColumnService = dbTableColumnService;
        this.frontFuncService = frontFuncService;
        this.frontFuncProService = frontFuncProService;
    }

    public List<String> blankSelectFields() {
        return Arrays.asList("objectCode", "objectName");
    }

    /**
     * 根据rowId查询数据
     *
     * @param rowId 唯一标示
     * @return  PlatResult
     */
    public PlatResult queryById(String rowId) {
        List<Map<String, Object>> result = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
        String relateTableRowId = (String) result.get(0).get("relateTableRowId");

        List<Map<String, Object>> rowId1 =
                maintDBTablesService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableRowId));
        for (Map<String, Object> row : result) {
            row.put("tableCname", rowId1.get(0).get("tableCname"));
        }
        return PlatResult.Msg(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS);
    }

    /**
     * 分页显示查询
     *
     * @param search   搜索条件
     * @param pageNum  页码
     * @param pageSize 一页显示条数
     * @param order    排序
     * @return          PlatResult
     */
    public PlatResult queryPage(String search, int pageNum, int pageSize, List<Order> order) {
        if (!UtilsTool.isValid(search)) {
            pageNum = 1;
        }
        PageResult<Map<String, Object>> result;
        result = singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(search),
                pageNum, pageSize, Collections.singletonList(QueryAction.ALL_FIELD), order);
        if (result.getResult().size() == 0) {
            return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
        }
        for (Map<String, Object> rest : result.getResult()) {
            rest.put("testDemo", false);
        }
        return new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result);
    }

    /**
     * 根据业务对象rowId查找当前对象下的所有属性并分页显示
     *
     * @param search    搜索条件
     * @param rowId     唯一标识
     * @param pageNum   页码
     * @param pageSize  一页显示条数
     * @param order     排序
     * @return      PlatResult
     */
    public PlatResult queryProPage(String search, String rowId, int pageNum, int pageSize, List<Order> order) {
        PageResult<Map<String, Object>> result;
        if (UtilsTool.isValid(search)) {
            result = businessObjectProService.select(
                    new ConditionBuilder(BusinessObjectPro.class).and()
                            .equal("objRowId", rowId).or()
                            .addCondition(UtilsTool.createBlankQuery(Arrays.asList("propertyCode", "propertyName"),
                                    UtilsTool.collectToSet(search))).endOr().endAnd().buildDone()
                    , Arrays.asList(QueryAction.ALL_FIELD), order, pageNum, pageSize);
            if (result.getResult().size() == 0) {
                return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
            }
            PageResult<Map<String, Object>> result1 = queryProPage(result);
            return new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result1);
        }
        result =
                businessObjectProService.select(
                        new ConditionBuilder(BusinessObjectPro.class).and()
                                .equal("objRowId", rowId).endAnd().buildDone()
                        , Arrays.asList(QueryAction.ALL_FIELD), order, pageNum, pageSize);
        if (result.getResult().size() == 0) {
            return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
        }
        PageResult<Map<String, Object>> result1 = queryProPage(result);
        return new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result1);
    }

    /**
     * 根据业务对象rowId查找当前对象下的所有属性
     *
     * @param result result
     * @return
     */
    private PageResult<Map<String, Object>> queryProPage(PageResult<Map<String, Object>> result) {
        Map<String, Object> map = new HashMap<>();
        for (Map<String, Object> rest : result.getResult()) {
            String relateTableColumn = (String) rest.get("relateTableColumn");
            List<Map<String, Object>> mapList = dbTableColumnService.select(new ConditionBuilder(DBTableColumn.class)
                    .and()
                    .equal("rowId", relateTableColumn).endAnd().buildDone());
            for (int i = 0; i < mapList.size(); i++) {
                map.put((String) mapList.get(i).get("rowId"), mapList.get(i).get("columnCname"));
            }
        }
        for (Map<String, Object> rest : result.getResult()) {
            rest.put("testDemo", false);
            rest.put("columnCname", map.get(rest.get("relateTableColumn")));
        }
        return result;
    }

    /**
     * 根据rowId来执行变更操作
     *
     * @param rowId     唯一标示
     * @return          PlatResult
     */
    public PlatResult changeOperat(String rowId) {
        Map<String, Object> oldRowId = new HashMap<>();
        List<Map<String, Object>> list = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
        List<String> row = list.stream().map((rowIds) -> {
            return (String) rowIds.get("changeOperat");
        }).collect(Collectors.toList());
        if (UtilsTool.isValid(rowId) && row.get(0).equals(BaseConstants.CHANGE_OPERAT_FAIL)) {
            List<Map<String, Object>> mapList = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
            Map<String, Object> objectMap = mapList.get(0);
            //将Map数据转换为json结构的数据
            String toJson = UtilsTool.objToJson(objectMap);
            //将json数据转换为实体类
            BusinessObject businessObject = UtilsTool.jsonToObj(toJson, BusinessObject.class);
            //复制一份数据出来
            businessObject.setChangeOperat(BaseConstants.CHANGE_OPERAT_FAIL);
            businessObject.buildCreateInfo();
            //获取当前版本号
            String version = (String) mapList.get(0).get("version");
            //转换为double类型
            double dou = new Double(version).doubleValue();
            //++当前版本号
            String str = ++dou + "";
            // TODO 设置版本号
            businessObject.setVersion(str);
            String objectCode = (String) mapList.get(0).get("objectCode");
            businessObject.setObjectCode(objectCode);
            insert(businessObject.toMap());

            oldRowId.put("rowId", rowId);
            oldRowId.put("status", BaseConstants.INVALID);
            oldRowId.put("changeOperat", BaseConstants.CHANGE_OPERAT_SUCCESS);
            update(oldRowId);
            return PlatResult.Msg(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS);
        }
        return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL);
    }

    /**
     * 根据rowId删除业务对象数据
     *
     * @param rowId 唯一标示
     * @return
     */
    public PlatResult delete(String rowId) {
        Map<String, Object> map = new HashMap<>();
        map.put("relateBusiObj", rowId);
        List<Map<String, Object>> businObj = frontFuncService.select(new FieldCondition("relateBusiObj", Operator.EQUAL, rowId));
        for (Map<String, Object> busin : businObj) {
            String rowId1 = (String) busin.get("rowId");
            List<Map<String, Object>> funcRowId = frontFuncProService.select(new FieldCondition("funcRowId", Operator.EQUAL, rowId1));
            if (funcRowId.size() != 0) {
                return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE);
            }
        }
        if (businObj.size() == 0) {
            List<Map<String, Object>> list = businessObjectProService
                    .select(new FieldCondition("objRowId", Operator.EQUAL, rowId));
            if (UtilsTool.isValid(list)) {
                List<String> rowIds = list.stream().map((row) -> {
                    return (String) row.get("rowId");
                }).collect(Collectors.toList());
                businessObjectProService.delete(new FieldCondition("rowId", Operator.IN, rowIds));
            }
            if (rowId != null && rowId.length() > 0) {
                Map<String, Object> args = new HashMap<>();
                args.put("rowId", rowId);
                delete(args);
                return PlatResult.Msg(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS);
            }
        }
        return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE);

    }


    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}