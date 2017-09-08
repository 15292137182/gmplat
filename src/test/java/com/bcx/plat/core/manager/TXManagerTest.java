package com.bcx.plat.core.manager;

import com.bcx.BaseTest;
import com.bcx.plat.core.service.SequenceGenerateService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 对事务管理器进行单元测试
 * <p>
 * Create By HCL at 2017/8/14
 */
public class TXManagerTest extends BaseTest {

  @Autowired
  SequenceGenerateService mapper;

  /**
   * 测试事务,会抛出异常
   */
  @Test(expected = RuntimeException.class)
  public void test() throws Exception {
    /*int size1 = mapper.select(new HashMap()).size();
    SequenceGenerate delete = new SequenceGenerate();

    TXManager.doInNewTX(() -> { // 事务一
      SequenceGenerate generate = new SequenceGenerate();
      generate.buildCreateInfo();
      generate.buildModifyInfo();
      mapper.insert(generate.toMap());
      try {
        TXManager.doInNewTX(() -> { // 事务二
          SequenceGenerate generate1 = new SequenceGenerate();
          generate1.buildCreateInfo();
          generate1.buildModifyInfo();
          mapper.insert(generate1.toMap());
          throw Lang.makeThrow("抛出一号异常-----");  // 抛出异常，事务二回滚，事务一不回滚
        });
      } catch (RuntimeException ignore) {

      }
      int size2 = mapper.select(new HashMap()).size();
      assert (size2 - size1 == 1); // 所以成功一个

      TXManager.doInNewTX(() -> { // 事务三
        SequenceGenerate generate2 = new SequenceGenerate();
        generate2.buildCreateInfo();
        generate2.buildModifyInfo();
        mapper.insert(generate2.toMap());

        delete.setRowId(generate2.getRowId());
      });

      int size3 = mapper.select(new HashMap()).size();
      assert size3 - size1 == 2;
      // 制造异常
      throw Lang.makeThrow("抛出二号异常-----");  // 事务一回滚，事务三正常插入
    });
    int curr = mapper.select(new HashMap()).size();
    // 上面的情况，应该成功了一个
    assert curr - size1 == 1;
    mapper.delete(delete.toMap()); // 删除事务三产生的数据*/
  }

}