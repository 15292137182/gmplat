package com.bcx.plat.core.morebatis.phantom;

import java.util.LinkedList;

public interface TableSource<T extends TableSource<T>> {
    default LinkedList translate(SqlComponentTranslator sqlComponentTranslator,LinkedList list){
        return sqlComponentTranslator.translateTableSource(this,list);
    }
}