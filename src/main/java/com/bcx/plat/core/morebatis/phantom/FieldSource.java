package com.bcx.plat.core.morebatis.phantom;

import com.bcx.plat.core.morebatis.translator.Translator;

import java.util.LinkedList;

public interface FieldSource<T> {
    default LinkedList translate(SqlComponentTranslator sqlComponentTranslator,LinkedList list){
        return sqlComponentTranslator.translateFieldSource(this,list);
    }
}
