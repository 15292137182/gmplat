package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.MaintDBTables;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.MaintDBTablesService;
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
import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * 数据库表信息Controller层
 * Created by Wen Tiehu on 2017/8/11.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/maintTable")
public class MaintDBTablesController extends BaseController {

    @Autowired
    private BusinessObjectService businessObjectService;
    @Autowired
    private MaintDBTablesService maintDBTablesService;


    protected List<String> blankSelectFields() {
        return Arrays.asList("tableSchema", "tableEname", "tableCname");
    }

    @RequestMapping("/queryPage")
    public PlatResult singleInputSelect(String search,
                                        @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                        String order) {
        LinkedList<Order> orders = dataSort(order);
        pageNum = search == null || search.isEmpty() ? 1 : pageNum;
        Or blankQuery = search.isEmpty() ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
        PageResult<MaintDBTables> maintDBTablesPageResult = maintDBTablesService.selectPage(blankQuery, orders, pageNum, pageSize);
        ServerResult<PageResult<MaintDBTables>> pageResultServerResult = new ServerResult<>(maintDBTablesPageResult);
        return result(pageResultServerResult);
    }

    /**
     * 表信息查询方法
     *
     * @param search 按照空格查询
     * @return PlatResult
     */
    @RequestMapping("/query")
    public PlatResult singleInputSelect(String search) {
        Or blankQuery = UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
        List<Map<String, Object>> list = maintDBTablesService.singleSelect(MaintDBTables.class, blankQuery);
        for (Map<String,Object> li : list){
            li.put("value",li.get("rowId"));
            li.put("lable",li.get("tableCname"));
        }
        if (list == null) {
            return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
        }
        return super.result(new ServerResult<>(list));
    }

    /**
     * 新增表信息属性
     *
     * @param paramEntity 接受实体参数
     * @return Map
     */
    @RequestMapping("/add")
    public PlatResult addMaintDB(@RequestParam Map<String, Object> paramEntity) {
        MaintDBTables maintDBTables = new MaintDBTables().buildCreateInfo().fromMap(paramEntity);
        int insert = maintDBTables.insert();
        if (insert != -1) {
            return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
        } else {
            return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_FAIL));
        }
    }


    /**
     * 编辑业务对象属性
     *
     * @param paramEntity 实体参数
     * @return Map
     */
    @RequestMapping("/modify")
    public PlatResult modifyBusinessObjPro(@RequestParam Map<String, Object> paramEntity) {
        if (UtilsTool.isValid(paramEntity.get("rowId"))) {
            MaintDBTables maintDBTables = new MaintDBTables().buildModifyInfo().fromMap(paramEntity);
            int update = maintDBTables.updateById();
            if (update != -1) {
                return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
            } else {
                return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_FAIL));
            }
        }
        return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
    }
//
//    /**
//     * 通用删除方法
//     *
//     * @param rowId   按照rowId查询
//     * @return Object
//     */
//    @RequestMapping("/delete")
//    public Object delete(String rowId) {
//        AtomicReference<Map<String, Object>> map = new AtomicReference<>(new HashMap<>());
//        if (UtilsTool.isValid(rowId)) {
//            map.get().put("relateTableRowId", rowId);
//            List<Map> relateTableRowId = businessObjectService.selectMap(new FieldCondition("relateTableRowId"
//                    , Operator.EQUAL, rowId));
//            if (relateTableRowId.size() == 0) {
//                List<Map> list = maintDBTablesService.selectMap(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId));
//                if (UtilsTool.isValid(list)) {
//                    List<String> rowIds = list.stream().map((row) ->
//                            (String) row.get("rowId")).collect(Collectors.toList());
//                    ConditionBuilder conditionBuilder = new ConditionBuilder(MaintDBTables.class);
//                    conditionBuilder.and().equal("rowId",rowIds);
//                    new MaintDBTables().buildDeleteInfo().delete(rowIds);
//                    return super.deleteByIds(request, locale, rowIds);
//                } else {
//                    return super.deleteByIds(request, locale, rowId);
//                }
//            }
//        }
//        return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE)), locale);
//    }


}
