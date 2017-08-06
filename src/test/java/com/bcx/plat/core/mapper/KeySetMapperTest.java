package com.bcx.plat.core.mapper;



import com.bcx.BaseTest;
import com.bcx.plat.core.entity.KeySet;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Went on 2017/8/6.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KeySetMapperTest extends BaseTest {

    @Autowired
    private KeySetMapper keySetMapper;

    static String newRowId = null;

    /**
     * 测试前端功能块属性Mapper接口的查询
     */
    @Test
    public void ATestSelect() {
        List<KeySet> select = keySetMapper.select(null);
        for (int i = 0; i < select.size(); i++) {
            String  result = select.get(i).getRowId();
            logger.info("++++++++++++" + result);
        }
    }

    /**
     * 测试前端功能块属性Mapper接口的新增
     */
    @Test
    public void BTestAdd() {
        KeySet result = new KeySet();
        result.buildCreateInfo();
        keySetMapper.insert(result);
        newRowId = result.getRowId();
    }

    /**
     * 测试前端功能块属性Mapper接口的更新
     */
    @Test
    public void CupdateTest(){
        KeySet result = new KeySet();
        result.setVersion("001");
        result.setConfKey("3232");
        result.setRowId(newRowId);
        result.buildModifyInfo();
        keySetMapper.update(result);
    }

    /**
     * 测试前端功能块属性Mapper接口的删除
     */
    @Test
    public void DTestDel() {
        KeySet result = new KeySet();
        String rowId = newRowId;
        result.setRowId(rowId);
        result.getRowId();
        keySetMapper.delete(result);
    }


}
