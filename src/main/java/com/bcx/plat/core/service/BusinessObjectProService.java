package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.PlatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * Created by Wen Tiehu on 2017/8/7.
 */
@Service
public class BusinessObjectProService extends BaseServiceTemplate<BusinessObjectPro> {

    @Override
    public boolean isRemoveBlank() {
        return false;
    }
    @Autowired
    DBTableColumnService dbTableColumnService;

    /**
     * 根据业务对象属性rowId查询当前数据
     *
     * @param rowId     唯一标识
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    public PlatResult queryById(String rowId, HttpServletRequest request, Locale locale) {

        List<Map<String, Object>> result = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
        String relateTableColumn = (String)result.get(0).get("relateTableColumn");

        List<Map<String, Object>> rowId1 =
                dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableColumn));
        if (rowId1.size()==0) {
            return PlatResult.Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL);
        }
        for (Map<String ,Object> row :result){
            row.put("columnCname",rowId1.get(0).get("columnCname"));
        }
        return new  PlatResult(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS,result);
    }

}