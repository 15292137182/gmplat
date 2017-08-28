package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Wen Tiehu on 2017/8/7.
 */
@Service
public class BusinessObjectService extends BaseServiceTemplate<BusinessObject> {

    final private MaintDBTablesService maintDBTablesService;

    @Autowired
    public BusinessObjectService(MaintDBTablesService maintDBTablesService) {
        this.maintDBTablesService = maintDBTablesService;
    }

    /**
     * 根据rowId查询数据
     * @param rowId
     * @return
     */
    public ServiceResult queryById(String rowId){
        List<Map<String, Object>> result = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
        String relateTableRowId = (String)result.get(0).get("relateTableRowId");

        List<Map<String, Object>> rowId1 =
                maintDBTablesService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableRowId));
        for (Map<String ,Object> row :result){
            row.put("tableCname",rowId1.get(0).get("tableCname"));
        }
        return ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS));
    }

    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}