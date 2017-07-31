package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.entity.MaintTableInfo;
import com.bcx.plat.core.mapper.MaintTableMapper;
import com.bcx.plat.core.service.MaintTableService;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;

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
                Map<String, Object> map = new HashMap<>();
                map.put("strArr", collectToSet(str));
                List<MaintTableInfo> maintTableInfos = maintTableMapperImpl.selectMaint(map);
                return maintTableInfos;
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
    public List<MaintTableInfo> selectById(int rowId) {
        try {
            if (rowId != 0) {
                List<MaintTableInfo> maintTableInfos = maintTableMapperImpl.selectById(rowId);
                return maintTableInfos;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
