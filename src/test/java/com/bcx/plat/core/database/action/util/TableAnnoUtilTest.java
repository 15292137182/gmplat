package com.bcx.plat.core.database.action.util;

import com.bcx.plat.core.base.BaseEntity;
import org.junit.Test;

import java.util.List;

/**
 * Create By HCL at 2017/8/1
 */
public class TableAnnoUtilTest {

    @Test
    public void test() {
        List<String> pks = TableAnnoUtil.getPkAnnoField(BaseEntity.class);
    }
}