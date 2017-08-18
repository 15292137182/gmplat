package com.bcx.plat.core.morebatis.app;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.Fields;
import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.configuration.EntityEntry;
import com.bcx.plat.core.morebatis.mapper.SuitMapper;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MoreBatis {
  private SuitMapper suitMapper;
  private SqlComponentTranslator translator;
  private Map<Class,Collection<Column>> entityColumns;
  private Map<Class,Map<String,Column>> aliesMaps;
  private Map<Class,TableSource> entityTables;

  public MoreBatis(SuitMapper suitMapper,SqlComponentTranslator translator,List<EntityEntry> entityEntries) {
    this.suitMapper = suitMapper;
    this.translator = translator;
    entityColumns=new HashMap<>();
    entityTables =new HashMap<>();
    aliesMaps=new HashMap<>();
    for (EntityEntry entityEntry : entityEntries) {
      final Class<? extends BaseEntity> entryClass = entityEntry.getEntityClass();
      Map<String, Column> aliesMap = aliesMaps.get(entryClass);
      if (aliesMap==null) {
        aliesMap=new HashMap<>();
        aliesMaps.put(entryClass,aliesMap);
      }
      for (Column column : entityEntry.getFields()) {
        aliesMap.put(column.getAlies(),column);
      }
      entityColumns.put(entryClass,entityEntry.getFields());
      entityTables.put(entryClass,entityEntry.getTable());
    }
    System.out.println("done");
  }
  public QueryAction select(Class<? extends BaseEntity> entity){
    return new QueryAction(this,translator).select(Fields.getFields(entity));
  }
  public QueryAction select(){
    return new QueryAction(this,translator);
  }
  public InsertAction insert(){
    return new InsertAction(this,translator);
  }
  public UpdateAction update(){
    return new UpdateAction(this,translator);
  }
  public DeleteAction delete(){
    return new DeleteAction(this,translator);
  }

  public List<Map<String, Object>> execute(QueryAction queryAction){return suitMapper.select(queryAction);}
  public int execute(InsertAction insertAction){
    return suitMapper.insert(insertAction);
  }
  public int execute(UpdateAction updateAction){
    return suitMapper.update(updateAction);
  }
  public int execute(DeleteAction deleteAction){
    return suitMapper.delete(deleteAction);
  }
}