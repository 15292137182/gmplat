package com.bcx.plat.core.common;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.TableAnnoUtil;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class BaseServiceTemplate<T extends BaseEntity<T>> implements BaseService<T> {
    private final Class entityClass = (Class) ((ParameterizedType) this.getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    private final Set<String> fieldNames = getFieldNamesFromClass(entityClass);
    private final TableSource table = TableAnnoUtil.getTableSource(entityClass);
    private final List<String> pkFields = TableAnnoUtil.getPkAnnoField(entityClass);


    @Autowired
    private MoreBatis moreBatis;

    public void setMoreBatis(MoreBatis moreBatis) {
        this.moreBatis = moreBatis;
    }

    final public List<Map<String, Object>> select(Condition condition) {
        /**
         * 此处先将就
         * 等扩展字段完善以后再来完善
         */
        return select(condition, Arrays.asList(QueryAction.ALL_FIELD), null);
    }

    private List<Order> emptyDefaultModifyTime(List<Order> orders){
        return orders;
    }

    final public List<Map<String, Object>> select(Condition condition, List<Column> columns, List<Order> orders) {
        final List<Map<String, Object>> queryResult = moreBatis.selectStatement().select(columns)
                .from(TableAnnoUtil.getTableSource(entityClass))
                .where(UtilsTool.excludeDeleted(condition)).orderBy(emptyDefaultModifyTime(orders)).execute();
        final List<Map<String, Object>> camelizedResult = UtilsTool.underlineKeyMapListToCamel(queryResult);
        return camelizedResult;
    }

    final public PageResult<Map<String, Object>> select(Condition condition, List<Column> columns, List<Order> orders, int pageNum, int pageSize) {
        final PageResult<Map<String, Object>> queryResult = moreBatis.selectStatement().select(columns)
                .from(TableAnnoUtil.getTableSource(entityClass))
                .where(UtilsTool.excludeDeleted(condition)).orderBy(emptyDefaultModifyTime(orders)).selectPage(pageNum, pageSize);
        final PageResult<Map<String, Object>> camelizedResult = UtilsTool.underlineKeyMapListToCamel(queryResult);
        return camelizedResult;
    }

    final public PageResult<Map<String, Object>> select(Condition condition, int pageNum, int pageSize) {
        //同上
        return select(condition, Arrays.asList(QueryAction.ALL_FIELD), null, pageNum, pageSize);
    }


    @Deprecated
    public PageResult<Map<String, Object>> select(Map args, int pageNum, int pageSize) {
        args = mapFilter(args);
        return select(UtilsTool.convertMapToFieldConditions(args), pageNum, pageSize);
    }

    @Deprecated
    public List<Map<String, Object>> select(Map args) {
        args = mapFilter(args);
        return select(UtilsTool.convertMapToFieldConditions(args));
    }

    @Deprecated
    public PageResult<Map<String, Object>> singleInputSelect(Collection<String> column,
                                                             Collection<String> value, int pageNum, int pageSize, List<Column> columns, List<Order> orders) {
        return select(UtilsTool.createBlankQuery(column, value),columns,orders, pageNum, pageSize);
    }

    @Deprecated
    public List<Map<String, Object>> singleInputSelect(Collection<String> column,
                                                       Collection<String> value) {
        return select(UtilsTool.createBlankQuery(column, value));
    }

    public int insert(Map args) {
        args = mapFilter(args);
        args.remove("etc");
        InsertAction insertAction = moreBatis.insertStatement().into(table).cols(fieldNames).values(args);
        return insertAction.execute();
    }

    public int update(Map args) {
        args = mapFilter(args);
        args.remove("etc");
        final Map<String, Object> finalCopy = args;
        return update(finalCopy, new And(pkFields.stream().map((pk) -> {
            return new FieldCondition(pk, Operator.EQUAL, finalCopy.get(pk));
        }).collect(Collectors.toList())));
    }

    public int update(Map args, Condition condition) {
        UpdateAction updateAction = moreBatis.updateStatement()
                .from(table)
                .set(args)
                .where(condition);
        return updateAction.execute();
    }

    public int delete(Map args) {
        args = mapFilter(args);
        args.remove("etc");
        return delete(UtilsTool.convertMapToFieldConditions(args));
    }

    public int delete(Condition condition) {
        DeleteAction deleteAction = moreBatis.deleteStatement()
                .from(table)
                .where(condition);
        return deleteAction.execute();
    }


    public Map<String, Object> mapFilter(Map<String, Object> map) {
        return mapFilter(map, isRemoveNull(), isRemoveBlank());
    }

    private Map<String, Object> mapFilter(Map<String, Object> map, boolean removeNull, boolean removeBlank) {
        Map<String, Object> outputMap = new HashMap<>();
        if (removeBlank == false && removeNull == false) {
            return outputMap;
        }
        for (Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value == null && removeNull) {
                continue;
            }
            if (value.equals("") && removeBlank) {
                continue;
            }
            outputMap.put(entry.getKey(), entry.getValue());
        }
        return outputMap;
    }

    final private Set<String> getFieldNamesFromClass(Class clz) {
        HashSet<String> result = new HashSet<>();
        while (clz != Object.class) {
            result.addAll(Arrays.stream(clz.getDeclaredFields()).map((field -> field.getName())).collect(
                    Collectors.toList()));
            clz = clz.getSuperclass();
        }
        result.remove("etc");
        return result;
    }

    public boolean isRemoveBlank() {
        return true;
    }

    private boolean isRemoveNull() {
        return true;
    }
}
