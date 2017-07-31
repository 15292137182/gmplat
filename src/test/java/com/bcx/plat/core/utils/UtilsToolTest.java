package com.bcx.plat.core.utils;

import com.bcx.BaseTest;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * 测试工具类中的方法
 */
public class UtilsToolTest extends BaseTest {

    /**
     * 对工具类中的方法进行测试
     */
    @Test
    public void testMethod() {
        // 测试 json 的互转功能
        Map<String, Object> map = new HashMap<>();
        map.put("A", "2017-07-28");
        String jsonMap = objToJson(map);
        HashMap map1 = jsonToObj(jsonMap, HashMap.class);
        assert (null != map1 && map1.get("A").equals(map.get("A")));

        // 测试 lengthUUID 方法
        String uuid32 = lengthUUID(32);
        assert uuid32.length() == 32;
        System.out.println(getDateBy10());

        // 测试 isValid方法，确保返回合适的结果
        assert !isValid(null);
        assert isValid(new HashMap<>());

        String s = "123    456   qwer;sdjr";
        Set<String> set = collectToSet(s);
        assert set.size() == 4;
    }

}