package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.entity.KeySetPro;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 键值集合信息
 * Created by Went on 2017/8/3.
 */
@Service
public class KeySetService extends BaseService<KeySet> {

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
     * @return PlatResult
     */
    public ServerResult queryKeySet(List list) {
        Map<Object, Object> maps = new HashMap<>();
        for (Object li : list) {
            List<Object> lists = new ArrayList<>();
            lists.add(li);
            QueryAction joinTableTest = moreBatis.select(KeySet.class, KeySetPro.class, "rowId", "relateKeysetRowId", JoinType.LEFT_JOIN)
                    .where(new FieldCondition(moreBatis.getColumnByAlias(KeySet.class, "keysetCode"), Operator.EQUAL, lists));
            List<Map<String, Object>> list1 = UtilsTool.underlineKeyMapListToCamel(joinTableTest.execute());
            maps.put(li, list1);
            if (maps.size() == 0) {
                return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
            }
        }
        return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, UtilsTool.objToJson(maps));
    }

    /**
     * 根据键值集合编号查询对应的数据
     *
     * @param keyCode 键值代码
     * @return ServerResult
     */
    public ServerResult queryKeyCode(String keyCode) {
        List<Map<String, Object>> result = moreBatis.select(KeySet.class)
                .where(new FieldCondition("keysetCode", Operator.EQUAL, keyCode))
                .execute();
        List<Map<String, Object>> list = UtilsTool.underlineKeyMapListToCamel(result);
        List relateKeysetRowId = null;
        for (Map<String, Object> results : list) {
            String rowId = (String) results.get("rowId");
            relateKeysetRowId = keySetProService.selectMap(new FieldCondition("relateKeysetRowId", Operator.EQUAL, rowId));
        }
        List<Map<String, Object>> relateKeysetRowIds = UtilsTool.underlineKeyMapListToCamel(relateKeysetRowId);
        if (result.size() == 0) {
            return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
        }
        return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, relateKeysetRowIds);
    }


    /**
     * 根据rowId查询数据
     *
     * @param rowId 唯一标示
     * @return ServerResult
     */
    public ServerResult<List<Map>> queryPro(String rowId) {
        List<Map> rowId1 =
                keySetProService.selectMap(new FieldCondition("relateKeysetRowId", Operator.EQUAL, rowId));
        return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, rowId1);
    }


    /**
     * 根据键值集合rowId查找当前键值集合属性并分页显示
     *
     * @param search   搜索条件
     * @param rowId    唯一标识
     * @param pageNum  页码
     * @param pageSize 一页显示条数
     * @param orders   排序
     * @return ServerResult
     */
    public ServerResult queryProPage(String search, String rowId, int pageNum, int pageSize, List<Order> orders) {
        //查询属性的搜索条件
        Or blankQuery = search.isEmpty() ? null : UtilsTool.createBlankQuery(Arrays.asList("confKey", "confValue"), UtilsTool.collectToSet(search));
        Condition condition;
        if (UtilsTool.isValid(search)) {
            condition = new ConditionBuilder(KeySetPro.class)
                    .and().equal("relateKeysetRowId", rowId)
                    .or().addCondition(blankQuery).endOr()
                    .endAnd().buildDone();
        } else {
            condition = new ConditionBuilder(KeySetPro.class).and().equal("relateKeysetRowId", rowId).endAnd().buildDone();
        }
        PageResult<Map<String, Object>> result = keySetProService.selectPageMap(condition, orders, pageNum, pageSize);
        if (result.getResult().size() == 0) {
            return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
        } else {
            return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result);
        }
    }


    /**
     * 根据rowId删除业务对象数据
     *
     * @param rowId 唯一标示
     * @return ServerResult
     */
    public ServerResult delete(String rowId) {
        List<Map> rkrd = keySetProService.selectMap(new FieldCondition("relateKeysetRowId", Operator.EQUAL, rowId));
        for (Map<String, Object> rk : rkrd) {
            String _rowId = rk.get("rowId").toString();
            new KeySetPro().deleteById(_rowId);
        }
        new KeySet().deleteById(rowId);
        return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE);

    }

}
