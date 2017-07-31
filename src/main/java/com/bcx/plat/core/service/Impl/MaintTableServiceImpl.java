package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.domain.MaintTablePojo;
import com.bcx.plat.core.service.MaintTableService;
import com.bcx.plat.core.sqlmapper.MaintTableMapper;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.SymbolChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Went on 2017/7/31.
 */
@Service
public class MaintTableServiceImpl implements MaintTableService {
    @Autowired
    private MaintTableMapper maintTableMapperImpl;

    /**
     * 维护数据库表查询
     * @param str 根据条件查询
     * @return
     */
    @Override
    public List selectMaint(String str) {
        try {
            if (str != null) {
                String symbol = SymbolChange.Symbol(str);
                String[] split = symbol.split(",");
                Map<String, Object> map = new HashMap<>();
                map.put("strArr", split);
                List<MaintTablePojo> maintTablePojos = maintTableMapperImpl.selectMaint(map);
                return maintTablePojos;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据Id查询维护数据库字段
     * @param rowId
     * @return
     */
    @Override
    public List<MaintTablePojo> selectById(int rowId) {
        try {
            if (rowId != 0) {
                List<MaintTablePojo> maintTablePojos = maintTableMapperImpl.selectById(rowId);
                return maintTablePojos;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
