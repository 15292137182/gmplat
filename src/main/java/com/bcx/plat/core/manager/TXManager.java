package com.bcx.plat.core.manager;

import com.bcx.plat.core.database.DynamicDataSource;
import com.bcx.plat.core.utils.SpringContextHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 事务管理器
 * <p>
 * Create By HCL at 2017/8/13
 */
public class TXManager {

  /**
   * 数据库操作接口
   */
  @FunctionalInterface
  public interface DBOperate {

    void operate() throws Exception;
  }

  /**
   * 比较全能的操作接口
   */
  @FunctionalInterface
  public interface QNNOperate {

    void operator(PlatformTransactionManager manager, TransactionStatus status) throws Exception;
  }

  private static DataSourceTransactionManager transactionManager;
  private static DynamicDataSource dataSource;

  public static void doInNewTX(DBOperate operate) throws Exception {
    doInNewTX((manager, status) -> operate.operate());
  }

  /**
   * 获取事务管理器
   */
  public static DataSourceTransactionManager getTxMananer() {
    if (null == transactionManager) {
      transactionManager = SpringContextHolder.getBean(DataSourceTransactionManager.class);
    }
    return transactionManager;
  }

  /**
   * 不参与事务
   *
   * @param operate 操作
   */
  public static void doInNoTX(QNNOperate operate) throws Exception {
    doInTx(operate, TransactionDefinition.PROPAGATION_NEVER);
  }

  /**
   * 需要事务，有则加入，没有则创建
   *
   * @param operate 操作
   */
  public static void doInRequiredTX(QNNOperate operate) throws Exception {
    doInTx(operate, TransactionDefinition.PROPAGATION_REQUIRED);
  }

  /**
   * 在新的事务中执行代码，无论是否有都加入
   *
   * @param operate 操作
   */
  public static void doInNewTX(QNNOperate operate) throws Exception {
    doInTx(operate, TransactionDefinition.PROPAGATION_REQUIRES_NEW);
  }

  private static void doInTx(QNNOperate operate, int PROPAGATION) throws Exception {
    if (null != operate && init()) {
      DefaultTransactionDefinition def = new DefaultTransactionDefinition();
      def.setPropagationBehavior(PROPAGATION); //要求开启新的事务
      def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);// 提交后对其他事务可读
      TransactionStatus status = transactionManager.getTransaction(def);
      try {
        operate.operator(transactionManager, status);
        if (!status.isCompleted()) {
          transactionManager.commit(status);
        }
      } catch (Exception e) {
        if (!status.isCompleted()) {
          transactionManager.rollback(status);
        }
        throw e;
      }
      if (status.isCompleted()) {
      }
    }
  }

  /**
   * 初始化本类
   *
   * @return 返回
   */
  private static boolean init() {
    if (null == transactionManager) {
      transactionManager = SpringContextHolder.getBean(DataSourceTransactionManager.class);
    }
    if (null == dataSource) {
      dataSource = SpringContextHolder.getBean(DynamicDataSource.class);
    }
    return true;
  }

}
