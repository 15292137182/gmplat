package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 前端功能块属性项信息维护 service层
 * Created by Wen Tiehu on 2017/8/4.
 */
@Service
public class FrontFuncProService extends BaseService<FrontFuncPro> {

  @Autowired
  private BusinessObjectProService businessObjectProService;
  @Autowired
  private DBTableColumnService dbTableColumnService;
  @Autowired
  private TemplateObjectProService templateObjectProService;

  /**
   *
   * 根据功能块 rowId 查找当前对象下的所有属性并分页显示
   *
   * @param result 接受ServiceResult
   * @return list
   */
  public List<Map<String, Object>> queryProPage(List<Map<String, Object>> result) {
    //遍历模板对象
    for (Map<String, Object> res : result) {
      String attrSource = res.get("attrSource").toString();
      if (attrSource.equals(BaseConstants.ATTRIBUTE_SOURCE_MODULE)) {
        String relateBusiPro = res.get("relateBusiPro").toString();
        List<TemplateObjectPro> proRowId = templateObjectProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
        //拿到模板对象属性给功能块属性赋值
        for (TemplateObjectPro pro : proRowId) {
          res.put("propertyName", pro.getCname());
          res.put("ename", pro.getEname());
        }
      } else if (attrSource.equals(BaseConstants.ATTRIBUTE_SOURCE_BASE) || attrSource.equals(BaseConstants.ATTRIBUTE_SOURCE_EXTEND)) {
        String relateBusiPro = res.get("relateBusiPro").toString();
        List<BusinessObjectPro> relateTableColumn = businessObjectProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
        for (BusinessObjectPro relate : relateTableColumn) {
          if (UtilsTool.isValid(relate.getFieldAlias())) {
            res.put("ename", relate.getFieldAlias());
            res.put("propertyName", relate.getPropertyName());
          } else {
            String relateTableRowId = relate.getRelateTableColumn();
            List<DBTableColumn> rowId = dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableRowId));
            for (DBTableColumn row : rowId) {
              res.put("ename", row.getColumnEname());
              res.put("propertyName", row.getColumnCname());
            }
          }
        }
      }
    }
    return result;
  }
}
