package com.bcx.plat.core.morebatis.service;

import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.mapper.SuitMapper;
import com.bcx.plat.core.service.common.BaseServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Transactional
@Service
public class TestTableService extends BaseServiceTemplate<BusinessObject> {
  @Autowired
  SuitMapper suitMapper;

  public SuitMapper getSuitMapper() {
    return suitMapper;
  }

  public void setSuitMapper(SuitMapper suitMapper) {
    this.suitMapper = suitMapper;
  }
}
