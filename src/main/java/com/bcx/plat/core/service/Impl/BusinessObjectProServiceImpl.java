package com.bcx.plat.core.service.Impl;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.mapper.BusinessObjectProMapper;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Went on 2017/8/1.
 */
@Transactional
@Service
public class BusinessObjectProServiceImpl implements BusinessObjectProService {

  @Value("#{conf.BusinessObjectPro}")
  private String BusinessObjectPro;

  @Autowired
  private BusinessObjectProMapper businessObjectProMapper;



  /**
   * 查询业务对象属性
   */
  @Override
  public ServiceResult<BusinessObjectPro> select(Map<String,Object> map) {
    try {
        List<BusinessObjectPro> select = businessObjectProMapper.select(map);
      return new ServiceResult(select,BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ServiceResult(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL );
    }
  }

  /**
   * 新增业务对象属性
   */
  @Override
  public ServiceResult<BusinessObjectPro> insert(BusinessObjectPro businessObjectPro) {
    try {
      String rowId = businessObjectPro.getRowId();
      businessObjectPro.setPropertyCode(BusinessObjectPro);
      businessObjectPro.buildCreateInfo();
      businessObjectProMapper.insert(businessObjectPro);
      //将用户新增的rowId返回
      return new ServiceResult(rowId,BaseConstants.STATUS_SUCCESS,Message.NEW_ADD_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ServiceResult(BaseConstants.STATUS_FAIL,Message.NEW_ADD_FAIL);
    }
  }


  /**
   * 删除业务对象属性
   */
  @Override
  public ServiceResult<BusinessObjectPro> delete(String rowId) {
    try {
      businessObjectProMapper.delete(rowId);
      return new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.DELETE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      return new ServiceResult(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL);
    }
  }
}
