package com.bcx.plat.core.service;

import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.entity.MaintDBTables;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Wen Tiehu on 2017/8/11.
 */
@Service
public class MaintDBTablesService extends BaseServiceTemplate<MaintDBTables> {

//    final BusinessObjectService businessObjectService;
//    final DBTableColumnService dbTableColumnService;
//
//    @Autowired
//    public MaintDBTablesService(BusinessObjectService businessObjectService, DBTableColumnService dbTableColumnService) {
//        this.businessObjectService = businessObjectService;
//        this.dbTableColumnService = dbTableColumnService;
//    }

//
//    public PlatResult deleteMaint(String rowId){
//        Map<String, Object> map = new HashMap<>();
//        map.put("relateTableRowId", rowId);
//        List<Map<String, Object>> tableRowId = businessObjectService.select(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId));
//        List<Map<String, Object>> list = null;
//        if (tableRowId.size() == 0) {
//             list = select(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId));
//            if (UtilsTool.isValid(list)) {
//                List<String> rowIds = list.stream().map((row) -> {
//                    return (String) row.get("rowId");
//                }).collect(Collectors.toList());
//                dbTableColumnService.delete(new FieldCondition("rowId", Operator.IN, rowIds));
//            }
//        }
//        return PlatResult.Msg(new ServerResult().Msg(BaseConstants.STATUS_FAIL,Message.DATA_QUOTE));
//    }




    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}
