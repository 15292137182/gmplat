package com.bcx.plat.core.service.Impl;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;

import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.mapper.BusinessObjectProMapper;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.List;
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
  public ServiceResult<BusinessObjectPro> select(String rowId) {
    try {
      if (rowId != null) {
        List<BusinessObject> select = businessObjectProMapper.selectById(rowId);
        return new ServiceResult<>("查询数据成功", select);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return new ServiceResult<>("查询数据失败", "");
    }
    return null;
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
      return new ServiceResult<BusinessObjectPro>("新增数据成功", rowId);
    } catch (Exception e) {
      e.printStackTrace();
      return new ServiceResult<>("新增数据失败", "");
    }
  }


  /**
   * 删除业务对象属性
   */
  @Override
  public ServiceResult<BusinessObjectPro> delete(String rowId) {
    try {
      businessObjectProMapper.delete(rowId);
      return new ServiceResult<>(STATUS_SUCCESS, "删除数据成功", "");
    } catch (Exception e) {
      e.printStackTrace();
      return new ServiceResult<>(STATUS_FAIL, "删除数据失败", "");
    }
  }
}
