package com.bcx.plat.core.service.common;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.morebatis.QueryAction;
import com.bcx.plat.core.morebatis.mapper.SuitMapper;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.TableAnnoUtil;
import com.github.pagehelper.Page;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class TableService<T extends BaseEntity<T>> {
  public Class entityClass=this.getClass().getGenericSuperclass().getClass();
  public Set<String> fieldNames= new HashSet<String>(
      Arrays.asList(this.getClass().getGenericSuperclass().getClass().getFields()).stream().map((field)->{
    return field.getName();
  }).collect(Collectors.toList())
  );

  protected abstract SuitMapper getSuitMapper();

  public Page<Map<String, Object>> select(Map<String,Object> args,int pageNum,int pageSize){
    QueryAction queryAction=new QueryAction().selectAll()
        .from(TableAnnoUtil.getTableSource(entityClass))
        .where(convertMapToFieldConditions(args));
    return queryAction.pageQuery(getSuitMapper(),pageNum,pageSize);
  }

  public ServiceResult insert(Map<String,Object> args){
    return null;
  }

  public ServiceResult update(Map<String,Object> args){
    return null;
  }

  public ServiceResult delete(Map<String,Object> args){
    return null;
  }

  protected List<FieldCondition> convertMapToFieldConditions(Map<String,Object> args){
    return args.entrySet().stream().filter((entry)->{
      return fieldNames.contains(entry.getKey());
    }).map((entry)->{
      final Object value = entry.getValue();
      if (value instanceof Collection){
        return new FieldCondition(entry.getKey(), Operator.IN, value);
      }else {
        return new FieldCondition(entry.getKey(), Operator.EQUAL, value);
      }
    }).collect(Collectors.toList());
  }
}
