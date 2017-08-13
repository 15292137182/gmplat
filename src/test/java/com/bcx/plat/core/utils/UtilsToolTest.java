package com.bcx.plat.core.utils;

import static com.bcx.plat.core.utils.UtilsTool.camelToUnderline;
import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.getDateBy10;
import static com.bcx.plat.core.utils.UtilsTool.isValid;
import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;
import static com.bcx.plat.core.utils.UtilsTool.underlineToCamel;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.manager.TXManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

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
    DBTableColumn column = new DBTableColumn()
        .buildCreateInfo()
        .buildModifyInfo()
        .buildDeleteInfo();
    DBTableColumn column1 = new DBTableColumn();
    column1.fromMap(column.toMap());
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

    String underLine = "_abc_came_def";
    assert ("AbcCameDef".equals(underlineToCamel(underLine, true)));
    assert ("abcCameDef".equals(underlineToCamel(underLine, false)));
    assert ("abc_came_def".equals(camelToUnderline("abcCameDef")));

    TXManager.doInNewTX(((manager, status) -> {
      try {
        // do something
        manager.commit(status);
      } catch (Exception e) {
        manager.rollback(status);
      }
      assert TXManager.isInTx();
    }));

    TXManager.doInNewTX(() -> {
      assert TXManager.isInTx();
    });

  }

}