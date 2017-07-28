package com.bcx.plat.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 处理 springmvc 在处理数字类型的时候造成的精度丢失问题
 */
public class LongToStringAdapter extends ObjectMapper {

    public LongToStringAdapter() {
        super();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        this.registerModule(simpleModule);
    }

}
