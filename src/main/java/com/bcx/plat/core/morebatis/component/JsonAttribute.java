package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;

public class JsonAttribute implements AliasedColumn{
    @Override
    public String getFieldSource() {
        return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
        return null;
    }

    @Override
    public String getAlias() {
        return null;
    }
}
