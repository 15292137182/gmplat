package com.bcx.plat.core.database.action.singleton;

import com.bcx.plat.core.database.action.phantom.TableSource;
import com.bcx.plat.core.database.action.substance.Table;

/**
 * 供单例模式使用的不可变表
 */
public class ImmuteTable implements TableSource{
    TableSource tableSource;

    @Override
    public String getTableSourceSqlFragment() {
        return tableSource.getTableSourceSqlFragment();
    }
}