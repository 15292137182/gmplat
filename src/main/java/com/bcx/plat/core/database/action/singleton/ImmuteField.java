package com.bcx.plat.core.database.action.singleton;

import com.bcx.plat.core.database.action.phantom.Column;


/**
 * 不可变字段
 */
public class ImmuteField implements Column {
    /**
     * 字段名
     */
    protected String fieldName;
    /**
     * 别名
     */
    protected String alies;

    public String getFieldName() {
        return fieldName;
    }

    public String getAlies() {
        return alies;
    }

    protected ImmuteField(){

    }

    @Override
    public String getColumnSqlFragment() {
        String alies=getAlies();
        if (alies==null||alies.isEmpty()) {
            return getFieldSource();
        }else{
            return getFieldSource()+" as "+getAlies();
        }
    }

    @Override
    public String getFieldSource() {
        return getFieldName();
    }
}
