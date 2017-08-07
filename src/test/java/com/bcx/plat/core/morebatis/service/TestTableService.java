package com.bcx.plat.core.morebatis.service;

import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.mapper.SuitMapper;
import com.bcx.plat.core.service.common.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Transactional
//@Service
public class TestTableService extends TableService<BusinessObject> {
  @Autowired
  SuitMapper suitMapper;

  public SuitMapper getSuitMapper() {
    return suitMapper;
  }

  public void setSuitMapper(SuitMapper suitMapper) {
    this.suitMapper = suitMapper;
  }
}
