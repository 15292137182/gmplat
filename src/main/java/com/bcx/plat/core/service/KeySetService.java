package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 键值集合信息
 * Created by Went on 2017/8/3.
 */
@Service
public class KeySetService extends BaseServiceTemplate<KeySet> {

    @Autowired
    private MoreBatis moreBatis;

    /**
     * 根据编号number查询，以数组的形式传入数据进来["demo","test"]
     *
     * @param list  搜索条件
     * @return  ServiceResult
     */
    public ServiceResult queryKeySet(List list){
        Map<Object, Object> map = new HashMap<>();
        for (Object li : list) {
            List lists = new ArrayList();
            lists.add(li);
            List<Map<String, Object>> result = moreBatis.selectStatement()
                    .select(Arrays.asList(QueryAction.ALL_FIELD))
                    .from(moreBatis.getTable(KeySet.class))
                    .where(UtilsTool.createBlankQuery(Arrays.asList("number"), lists))
                    .execute();
            map.put(li, result);
        }
        String toJson = UtilsTool.objToJson(map);
        if (map.size() == 0) {
            return ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
        }
        return ServiceResult.Msg(new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS,toJson));
    }

    /**
     * 根据键值集合编号查询对应的数据
     * @param search
     * @return
     */
    public ServiceResult queryNumber(String search){
        List<Map<String, Object>> result = moreBatis.selectStatement().select(Arrays.asList(QueryAction.ALL_FIELD))
                .from(moreBatis.getTable(KeySet.class))
                .where(new FieldCondition("number", Operator.EQUAL, search))
                .execute();
        if (result.size() == 0) {
            return ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
        }
        return ServiceResult.Msg(new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS,result));
    }

    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}
