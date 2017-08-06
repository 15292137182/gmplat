package com.bcx.plat.core.mapper;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static com.bcx.plat.core.base.BaseConstants.INVALID;

/**
 * Created by Went on 2017/8/6.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessObjectProMapperTest extends BaseTest {

    @Autowired
    private BusinessObjectProMapper businessObjectProMapper;

    static String newRowId = null;

    /**
     * 测试业务对象属性Mapper接口的查询
     */
    @Test
    public void ATestSelect() {
        List<BusinessObjectPro> select = businessObjectProMapper.select(null);
        for (int i = 0; i < select.size(); i++) {
            BusinessObjectPro result = select.get(i);
            String rowId = result.getRowId();
            logger.info("++++++++++++" + rowId);
        }
    }

    /**
     * 测试业务对象属性Mapper接口的新增
     */
    @Test
    public void BTestAdd() {
        BusinessObjectPro result = new BusinessObjectPro();
        result.setPropertyCode("001");
        result.setRelateTableColumn("3232");
        result.setPropertyName("12");
        result.buildCreateInfo();
        businessObjectProMapper.insert(result);
        newRowId = result.getRowId();
    }

    /**
     * 测试业务对象属性Mapper接口的删除
     */
    @Test
    public void DTestDel() {
        String rowId = newRowId;
        businessObjectProMapper.delete(rowId);
    }


}
