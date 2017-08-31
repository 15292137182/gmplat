package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.entity.KeySetPro;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.JoinTable;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
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

    private final MoreBatis moreBatis;
    private final KeySetProService keySetProService;

    @Autowired
    public KeySetService(MoreBatis moreBatis, KeySetProService keySetProService) {
        this.moreBatis = moreBatis;
        this.keySetProService = keySetProService;
    }


    /**
     * 根据编号number查询，以数组的形式传入数据进来
     *
     * @param list 搜索条件
     * @return ServiceResult
     */
   public PlatResult queryKeySet(List list) {
       Map<Object, Object> maps = new HashMap<>();
       for (Object li : list) {
           List lists = new ArrayList();
           lists.add(li);
           QueryAction joinTableTest = moreBatis.selectStatement().select(QueryAction.ALL_FIELD)
                   .from(new JoinTable(moreBatis.getTable(KeySet.class), JoinType.LEFT_JOIN,
                           moreBatis.getTable(KeySetPro.class))
                           .on(new FieldCondition(moreBatis.getColumnByAlies(KeySet.class, "rowId"), Operator.EQUAL,
                                   moreBatis.getColumnByAlies(KeySetPro.class, "relateKeysetRowId"))))
                   .where(new FieldCondition(moreBatis.getColumnByAlies(KeySet.class, "keysetCode"), Operator.EQUAL, lists));
           List<Map<String, Object>> list1 = UtilsTool.underlineKeyMapListToCamel(joinTableTest.execute());
           maps.put(li, list1);
           if (maps.size() == 0) {
               return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
           }
       }
       return new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, UtilsTool.objToJson(maps));
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


    /**
     * 根据rowId查询数据
     *
     * @param rowId 唯一标示
     * @return  PlatResult
     */
    public PlatResult queryPro(String rowId) {
        List<Map<String, Object>> rowId1 =
                keySetProService.select(new FieldCondition("relateKeysetRowId", Operator.EQUAL, rowId));
        return new PlatResult(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS,rowId1);
    }

}
