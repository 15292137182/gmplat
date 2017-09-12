package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.entity.MaintDBTables;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 维护表信息Service层
 * Created by Wen Tiehu on 2017/8/11.
 */
@Service
public class MaintDBTablesService extends BaseService<MaintDBTables> {
    @Autowired
    private BusinessObjectService businessObjectService;
    @Autowired
    private DBTableColumnService dbTableColumnService;

    /**
     * 根据维护表信息rowId删除数据
     *
     * @param rowId 唯一标识
     * @return ServerResult
     */
    public ServerResult delete(String rowId) {
        ServerResult serverResult = new ServerResult();
        AtomicReference<Map<String, Object>> map = new AtomicReference<>(new HashMap<>());
        if (UtilsTool.isValid(rowId)) {
            map.get().put("relateTableRowId", rowId);
            List<Map> relateTableRowId = businessObjectService.selectMap(new FieldCondition("relateTableRowId"
                    , Operator.EQUAL, rowId));
            if (relateTableRowId.size() == 0) {
                List<Map> list = dbTableColumnService.selectMap(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId));
                if (UtilsTool.isValid(list)) {
                    List<String> rowIds = list.stream().map((row) ->
                            (String) row.get("rowId")).collect(Collectors.toList());
                    ConditionBuilder conditionBuilder = new ConditionBuilder(MaintDBTables.class);
                    conditionBuilder.and().equal("rowId", rowIds);
                    new DBTableColumn().buildDeleteInfo().delete(new FieldCondition("rowId", Operator.EQUAL, rowIds));
                } else {
                    MaintDBTables maintDBTables = new MaintDBTables().buildDeleteInfo();
                    maintDBTables.delete(new FieldCondition("rowId", Operator.EQUAL, rowId));
                    return serverResult.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS);
                }
            }
        }
        return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE);
    }

}
