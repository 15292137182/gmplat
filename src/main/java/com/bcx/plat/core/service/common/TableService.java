package com.bcx.plat.core.service.common;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.base.impl.BaseServiceImpl;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.morebatis.DeleteAction;
import com.bcx.plat.core.morebatis.InsertAction;
import com.bcx.plat.core.morebatis.QueryAction;
import com.bcx.plat.core.morebatis.UpdateAction;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.mapper.SuitMapper;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.TableAnnoUtil;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TableService<T extends BaseEntity<T>> implements BaseService<T> {
  private final Class entityClass= (Class) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  private final Set<String> fieldNames=getFieldNamesFromClass(entityClass);
  private final TableSource table=TableAnnoUtil.getTableSource(entityClass);
  private final List<String> pkFields=TableAnnoUtil.getPkAnnoField(entityClass);

  protected SuitMapper getSuitMapper() {
    return null;
  }

//  public void TableService() {
//    Class entityClass=this.getClass().getGenericSuperclass().getClass();
//    Set<String> fieldNames= new HashSet<String>(
//        Arrays.asList(this.getClass().getGenericSuperclass().getClass().getFields()).stream().map((field)->{
//          return field.getName();
//        }).collect(Collectors.toList())
//    );
//    TableSource table=TableAnnoUtil.getTableSource(entityClass);
//    List<String> pkFields=TableAnnoUtil.getPkAnnoField(entityClass);
//  }

  public ServiceResult select(T entity,int pageNum,int pageSize){
    Map<String, Object> args = entity.toMap();
    QueryAction queryAction=new QueryAction().selectAll()
        .from(TableAnnoUtil.getTableSource(entityClass))
        .where(convertMapToFieldConditions(args));
    ServiceResult<PageResult<Map<String, Object>>> serviceResult = new ServiceResult<>();
    try {
      PageResult<Map<String, Object>> pageResult = queryAction.pageQuery(getSuitMapper(), pageNum, pageSize);
      serviceResult = new ServiceResult<>(pageResult);
      serviceResult.setMessage(Message.QUERY_SUCCESS);
      serviceResult.setState(BaseConstants.STATUS_SUCCESS);
    }catch (Exception e){
      serviceResult.setMessage(Message.QUERY_FAIL);
      serviceResult.setState(BaseConstants.STATUS_FAIL);
    }
    return serviceResult;
  }

  public ServiceResult insert(T entity){
    Map<String, Object> args = entity.toMap();
    InsertAction insertAction=new InsertAction().into(table).cols(fieldNames).values(args);
    try {
      getSuitMapper().insert(insertAction);
    } catch (Exception e) {
      e.printStackTrace();
      return ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL);
    }
    return ServiceResult.Msg(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS);
  }

  public ServiceResult update(T entity){
    Map<String, Object> args = entity.toMap();
    UpdateAction updateAction=new UpdateAction()
        .from(table)
        .set(args)
        .where(pkFields.stream().map((pk)->{
          return new FieldCondition(pk,Operator.EQUAL,args.get(pk));
        }).collect(Collectors.toList()));
    try {
      getSuitMapper().update(updateAction);
    } catch (Exception e) {
      e.printStackTrace();
      return ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL);
    }
    return ServiceResult.Msg(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS);
  }

  public ServiceResult delete(T entity){
    Map<String, Object> args = entity.toMap();
    DeleteAction deleteAction=new DeleteAction()
        .from(table)
        .where(convertMapToFieldConditions(args));
    try {
      getSuitMapper().delete(deleteAction);
    } catch (Exception e) {
      e.printStackTrace();
      return ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL);
    }
    return ServiceResult.Msg(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS);
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

  private Set<String> getFieldNamesFromClass(Class clz){
    HashSet<String> result=new HashSet<>();
    while (clz!=Object.class){
      result.addAll(Arrays.stream(clz.getDeclaredFields()).map((field -> field.getName())).collect(
          Collectors.toList()));
      clz=clz.getSuperclass();
    }
    return result;
  }
}
