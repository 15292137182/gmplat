package com.bcx.plat.core.service.Impl;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.MaintTableInfo;
import com.bcx.plat.core.mapper.MaintTableMapper;
import com.bcx.plat.core.service.MaintTableService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Went on 2017/7/31.
 */
@Service
@Transactional
public class MaintTableServiceImpl implements MaintTableService {

  @Autowired
  private MaintTableMapper maintTableMapperImpl;

  /**
   * 维护数据库表查询
   *
   * @param str 根据条件查询
   */
  @Override
  public ServiceResult<MaintTableInfo> selectMaint(String str) {
    try {
        Map<String, Object> map = new HashMap<>();
        map.put("strArr", collectToSet(str));
        List<MaintTableInfo> result = maintTableMapperImpl.selectMaint(map);
        return new ServiceResult(BaseConstants.STATUS_SUCCESS, Message.OPERATOR_SUCCESS,result);
    } catch (Exception e) {
      e.printStackTrace();
      return new ServiceResult().Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL);
    }
  }

  /**
   * 根据Id查询维护数据库字段
   */
  @Override
  public ServiceResult<MaintTableInfo> selectById(String rowId) {
    try {
        List<MaintTableInfo> result = maintTableMapperImpl.selectById(rowId);
        return new ServiceResult(BaseConstants.STATUS_SUCCESS, Message.OPERATOR_SUCCESS,result);
    } catch (Exception e) {
      e.printStackTrace();
      return new ServiceResult().Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL);
    }
  }


}
