package com.bcx.plat.core.manager;

import com.bcx.plat.core.database.DynamicDataSource;
import com.bcx.plat.core.utils.SpringContextHolder;
import java.util.LinkedList;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 事务管理器
 *
 * Create By HCL at 2017/8/13
 */
public class TXManager {

  private static LinkedList<TransactionStatus> lt = new LinkedList<>();

  /**
   * 数据库操作接口
   */
  @FunctionalInterface
  public interface DBOperate {

    void operate();
  }

  /**
   * 比较全能的操作接口
   */
  @FunctionalInterface
  public interface QNNOperate {

    void operator(PlatformTransactionManager manager, TransactionStatus status);
  }

  private static DataSourceTransactionManager transactionManager;
  private static DynamicDataSource dataSource;

  public static void doInNewTX(DBOperate operate) {
    doInNewTX((manager, status) -> {
      operate.operate();
    });
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
   * 判断当前执行方法处是否处于事务中
   */
  public static boolean isInTx() {
    return getCurrentStatus() != null;
  }

  /**
   * 获取当前事务状态
   *
   * @return 返回
   */
  private static TransactionStatus getCurrentStatus() {
    return lt.getLast();
  }

  /**
   * 在新的事务中执行代码
   *
   * @param operate 操作
   */
  public static void doInNewTX(QNNOperate operate) {
    if (null != operate && init()) {
      DefaultTransactionDefinition def = new DefaultTransactionDefinition();
      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); //要求开启新的事务
      TransactionStatus status = transactionManager.getTransaction(def);
      lt.add(status);
      try {
        operate.operator(transactionManager, status);
        if (!status.isCompleted()) {
          transactionManager.commit(status);
        }
      } catch (Exception e) {
        if (!status.isCompleted()) {
          transactionManager.rollback(status);
        }
        e.printStackTrace();
      }
      if (status.isCompleted()) {
        lt.remove(status);
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
    if (null == lt) {
      lt = new LinkedList<>();
    }
    return true;
  }

}
