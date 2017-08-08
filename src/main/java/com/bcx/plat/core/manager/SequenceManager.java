package com.bcx.plat.core.manager;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.mapper.SequenceRuleConfigMapper;
import com.bcx.plat.core.utils.SpringContextHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列号生成类
 *
 * 流水号模块定义：
 *
 * @{c} 常量，不具有任何属性
 *
 * #{b:defaultValue}变量，从参数中取出，可设置为null时的默认值
 *
 * ${d:format}日期类型模块 可以指定格式化样式，例如:yyyy-MM-dd
 *
 * *{a:6-none-b&d} 变量
 *
 * Create By HCL at 2017/8/8
 */
public class SequenceManager {

  private Map<String, Object> temp = null;
  private Logger logger = LoggerFactory.getLogger(getClass());
  // 当参数取不到时的默认值
  private static final String DEFAULT_ARG_VALUE = "";

  private SequenceManager() {
  }

  private static class SequenceManagerHolder {

    private static final SequenceManager instance = new SequenceManager();
  }

  public static SequenceManager getInstance() {
    return SequenceManagerHolder.instance;
  }

  /**
   * 创建流水号,带参数
   *
   * @param sequenceCode 流水号名称
   * @param args 参数，将参数放入 map 中
   * @param num 生成的流水号个数
   * @return 返回生成的序列号
   */
  public String produceSequenceNo(String sequenceCode, Map<String, Object> args, int num,
      boolean test) {
    String result = null;
    if (isValid(sequenceCode) && init()) {
      SequenceRuleConfig ruleConfig = getRuleConfig(sequenceCode);
      if (null != ruleConfig) {
        ruleConfig.getSeqContent().split("&&");
      }
    }
    destroy();
    return result;
  }


  /**
   * 查询 序列号配置
   */
  private SequenceRuleConfig getRuleConfig(String sequenceCode) {
    SequenceRuleConfigMapper mapper = SpringContextHolder.getBean("sequenceRuleConfigMapper");
    Map<String, Object> cond = new HashMap<>();
    cond.put("sequenceCode", sequenceCode);
    List<SequenceRuleConfig> list = mapper.select(cond);
    if (list.size() == 1) {
      return list.get(0);
    }
    return null;
  }

  /**
   * 检查序列号生成是否可用
   */
  private boolean init() {
    return true;
  }

  /**
   * 销毁 历史数据
   */
  private void destroy() {
    temp = null;
  }

}
