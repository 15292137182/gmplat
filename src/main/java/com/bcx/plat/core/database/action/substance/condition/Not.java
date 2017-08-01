package com.bcx.plat.core.database.action.substance.condition;

import com.bcx.plat.core.database.action.phantom.Condition;

import java.util.List;

public class Not implements Condition{
    List<Condition> conditions;
    @Override
    public String getConditionSqlFragment() {
        return null;
    }
}
