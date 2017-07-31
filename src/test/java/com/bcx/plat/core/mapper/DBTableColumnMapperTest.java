package com.bcx.plat.core.mapper;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.DBTableColumn;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Create By HCL at 2017/7/31
 */
public class DBTableColumnMapperTest extends BaseTest {

    @Autowired
    DBTableColumnMapper dbTableColumnMapper;

    /**
     * 测试 DBTableColumnMapper 接口
     */
    @Test
    public void test() {
        List<DBTableColumn> list = dbTableColumnMapper.select(null);
    }
}