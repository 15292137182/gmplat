package com.bcx.plat.core.morebatis.app;

import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.mapper.SuitMapper;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MoreBatis {
  private SuitMapper suitMapper;
  private SqlComponentTranslator translator;

  public MoreBatis(SuitMapper suitMapper,SqlComponentTranslator translator) {
    this.suitMapper = suitMapper;
    this.translator = translator;
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

  public List<Map<String, Object>>
  execute(QueryAction queryAction){return suitMapper.select(queryAction);}
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
