package com.bcx.plat.core.morebatis.configuration.builder;

import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.Table;
import com.bcx.plat.core.morebatis.configuration.EntityEntry;
import com.bcx.plat.core.morebatis.configuration.annotation.IgnoredField;
import com.bcx.plat.core.utils.UtilsTool;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultEntryBuilder implements EntityEntryBuilder{
    private Class entityClass;

    private String tableName;

    private String schema;

    private Collection<String> pks;

    public DefaultEntryBuilder() {
    }

    public DefaultEntryBuilder(Class entityClass, String tableName, Collection<String> pks) {
        this.entityClass = entityClass;
        this.tableName = tableName;
        this.pks = pks;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Collection<String> getPks() {
        return pks;
    }

    public void setPks(Collection<String> pks) {
        this.pks = pks;
    }

    @Override
    public EntityEntry getEntry() {
        final Table table = new Table(schema,tableName);
        final List<Field> fields=new LinkedList<>();
        final HashMap<String,Field> aliasMap=new HashMap<>();
        Class clz=entityClass;
        while (clz != Object.class) {
            for (java.lang.reflect.Field field : clz.getDeclaredFields()) {
                // TODO 父类必须遍历反射
                if (Modifier.isStatic(field.getModifiers())
                        ||field.getName().startsWith("$")
                        ||field.getAnnotation(IgnoredField.class)!=null) continue;
                String fieldName = field.getName();
                Field f = new Field(table, UtilsTool.camelToUnderline(fieldName), fieldName);
                aliasMap.put(fieldName,f);
                fields.add(f);
            }
            clz=clz.getSuperclass();
        }
        List<Field> pkFields = pks.stream().map((pk) -> {
            Field field = aliasMap.get(pk);
            if (field==null) {
                throw new NullPointerException("类："+entityClass.getName()+"错误的主键别名 "+pk);
            }
            return field;
        }).collect(Collectors.toList());
//        Arrays.stream(entityClass.getDeclaredFields()).map((field)->{
//            String fieldName = field.getName();
//            return new Field(table, UtilsTool.underlineToCamel(fieldName,false),fieldName);
//        }).collect(Collectors.toList());
//        pks.stream().map((pk)->{
//            new Field()
//        }).collect(Collectors.toList());
        return new EntityEntry(entityClass, table,fields,pkFields);
    }
}
