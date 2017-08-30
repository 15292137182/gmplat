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
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 键值集合信息
 * Created by Went on 2017/8/3.
 */
@Service
public class KeySetService extends BaseServiceTemplate<KeySet>{

    @Autowired
    private MoreBatis moreBatis;
    @Autowired
    private KeySetProService keySetProService;

    /**
     * 根据编号number查询，以数组的形式传入数据进来
     *
     * @param list 搜索条件
     * @return ServiceResult
     */
    public PlatResult queryKeySet(List list) {
        Map<Object, Object> map = new HashMap<>();
        Map<Object, Object> maps = new HashMap<>();

        for (Object li : list) {
            List lists = new ArrayList();
            lists.add(li);
            List<Map<String, Object>> result = moreBatis.selectStatement()
                    .select(Arrays.asList(QueryAction.ALL_FIELD))
                    .from(moreBatis.getTable(KeySet.class))
                    .where(UtilsTool.createBlankQuery(Arrays.asList("keysetCode"), lists))
                    .execute();
            List<Map<String, Object>> results = UtilsTool.underlineKeyMapListToCamel(result);
            for (Map<String,Object> res : results){
                String  rowId = (String) res.get("rowId");
                List<Map<String, Object>> relateKeysetRowId =
                        keySetProService.select(new FieldCondition("relateKeysetRowId", Operator.EQUAL, rowId));
                for (Map<String, Object> relate : relateKeysetRowId){

                    res.put("confKey",relate.get("confKey"));
                    res.put("confValue",relate.get("confValue"));
                    map.put(li, results);
                }
            }
        }

        String toJson = UtilsTool.objToJson(map);
        if (map.size() == 0) {
            return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
        }
        return new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, toJson);
    }

    /**
     * 根据键值集合编号查询对应的数据
     *
     * @param search
     * @return
     */
    public PlatResult queryNumber(String search) {
        List<Map<String, Object>> result = moreBatis.selectStatement().select(Arrays.asList(QueryAction.ALL_FIELD))
                .from(moreBatis.getTable(KeySet.class))
                .where(new FieldCondition("keysetCode", Operator.EQUAL, search))
                .execute();
        List<Map<String, Object>> list = UtilsTool.underlineKeyMapListToCamel(result);
        List<Map<String, Object>> relateKeysetRowId = null;
        for (Map<String, Object> results : list) {
            String rowId = (String) results.get("rowId");
            relateKeysetRowId =
                    keySetProService.select(new FieldCondition("relateKeysetRowId", Operator.EQUAL, rowId));
        }
        List<Map<String, Object>> relateKeysetRowIds = UtilsTool.underlineKeyMapListToCamel(relateKeysetRowId);
        if (result.size() == 0) {
            return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
        }
        return new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, relateKeysetRowIds);
    }

}
