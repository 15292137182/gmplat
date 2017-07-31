package com.bcx.plat.core.base;

import com.bcx.BaseTest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By HCL at 2017/7/31
 */
public class BaseEntityTest extends BaseTest{

    /**
     * 对基础entity中的方法进行测试
     */
    @Test
    public void test() {
        // 保证 toMap 方法和 fromMap 方法都能正常工作
        BaseEntity entity = new BaseEntity();
        entity.buildCreateInfo()
                .buildDeleteInfo()
                .buildModifyInfo();
        @SuppressWarnings("unchecked")
        Map<String, Object> map = entity.toMap();
        BaseEntity entity1 = new BaseEntity().fromMap(map);
        assert (entity1.getRowId().equals(entity.getRowId()));

        Map<String,Object> map2 = new HashMap<>();
        map2.put("etc","{\"id\":\"00001\"}");
        entity1 = entity1.fromMap(map2);
        map2.put("etc",map);
        entity1 = entity1.fromMap(map2);
        assert entity1.getEtc().size() == map.size();
    }
}