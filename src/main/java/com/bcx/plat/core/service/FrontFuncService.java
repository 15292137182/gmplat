package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 功能块业务层
 * Created by Wen Tiehu on 2017/8/9.
 */
@Service
public class FrontFuncService extends BaseServiceTemplate<FrontFunc> {

    @Autowired
    private MoreBatis moreBatis;

    @Override
    public boolean isRemoveBlank() {
        return false;
    }


    /**
     * 根据前端功能块的代码查询功能块属性的数据
     *
     * @param funcCode
     * @return
     */
    public PlatResult queryFuncCode(List funcCode) {
        LinkedList linkedList = new LinkedList();
        List<Map<String, Object>> funcRowId = null;
        for (Object key : funcCode) {
            List<Map<String, Object>> keysetCode = moreBatis.select(FrontFunc.class)
                    .where(new FieldCondition("funcCode", Operator.EQUAL, key)).execute();
            List<Map<String, Object>> list = UtilsTool.underlineKeyMapListToCamel(keysetCode);
            for (Map<String, Object> keySet : list) {
                funcRowId = moreBatis.select(FrontFuncPro.class)
                        .where(new FieldCondition("funcRowId", Operator.EQUAL, keySet.get("rowId").toString()))
                        .execute();
                for (Map<String, Object> map : funcRowId) {
                    map.put("funcType", keySet.get("funcType").toString());
                }
            }
            for (Map<String, Object> li : UtilsTool.underlineKeyMapListToCamel(funcRowId)){
                linkedList.add(li);
            }
        }
        logger.info("查询功能块属性的数据");
        return new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, linkedList);
    }
}
