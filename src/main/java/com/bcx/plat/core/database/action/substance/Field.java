package com.bcx.plat.core.database.action.substance;

import com.bcx.plat.core.database.action.singleton.ImmuteField;

/**
 * 普通字段
 */
public class Field extends ImmuteField {
    public Field(String fieldName){
        super();
        this.fieldName=fieldName;
    }

    public Field(String fieldName, String alies) {
        super();
        this.fieldName=fieldName;
        this.alies=alies;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setAlies(String alies) {
        this.alies = alies;
    }
}