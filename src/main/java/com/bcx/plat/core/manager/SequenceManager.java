package com.bcx.plat.core.manager;

import com.bcx.plat.core.entity.SequenceGenerate;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.mapper.SequenceGenerateMapper;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.bcx.plat.core.utils.extra.lang.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.getDateTimeNow;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 序列号生成类
 *
 * 流水号模块定义：
 *
 * BCX-TEST-SN BCX-20170810-000002
 *
 * @{BCX-}&&${d:yyyy-mm-dd:true}&&@{-}&&*{a:6} - @{c} 常量，不具有任何属性
 *
 * #{b;defaultValue;true}变量，从参数中取出;可设置为null时的默认值;是否显示
 *
 * ${d;format;true}日期类型模块 可以指定格式化样式，例如;yyyy-MM-dd 是否显示
 *
 * *{a;6-b&d} 序列号 位数 影响的变量将会使重置号重置
 *
 * eg:
 *
 * Create By HCL at 2017/8/8
 */
public class SequenceManager {

  private Logger logger = LoggerFactory.getLogger(getClass());
  // 变量的参数值取不到的时候默认值
  private static final String DEFAULT_ARG_VALUE = "";
  // 未设定流水号长度时的默认长度
  private static final int DEFAULT_SERIAL_LENGTH = 6;
  // 内容内部分隔符
  private static final String CONTENT_SEPARATOR_ = ";";
  private static boolean isMock = false;
  private String content;

  private static SequenceGenerateMapper sequenceGenerateMapper;

  private SequenceManager() {
  }

  private static class SequenceManagerHolder {

    private static final SequenceManager instance = new SequenceManager();
  }

  public static SequenceManager getInstance() {
    return SequenceManagerHolder.instance;
  }

  private Map<String, Object> variable;
  private Map<String, Integer> keys;
  private Map<String, Object> branchValues;

  /**
   * 生成序列号
   *
   * @param sequenceCode 序列号编码
   * @param args 参数map
   * @return 返回信息
   */
  public String buildSequenceNo(String sequenceCode, Map<String, Object> args) {
    return produceSequenceNo(sequenceCode, args, 1, false).get(0);
  }
//
//  /**
//   * 重置序列号
//   *
//   * @param sequenceCode 代码
//   * @return 返回
//   */
//  public int resetSequenceNo(String sequenceCode) {
//    if (isValid(sequenceCode)) {
//      Map<String, Object> cond = new HashMap<>();
//      cond.put("seqCodeS", sequenceCode);
//      SequenceRuleConfigMapper mapper = SpringContextHolder.getBean("sequenceRuleConfigMapper");
//      List<SequenceRuleConfig> li = mapper.select(cond);
//      if (!li.isEmpty()) {
//        SequenceRuleConfig sequenceRuleConfig = li.get(0);
//        SequenceGenerate generate = new SequenceGenerate();
//        generate.setSeqRowId(sequenceRuleConfig.getRowId());
//        generate.buildDeleteInfo();
//        return sequenceGenerateMapper.resetSequenceValue(generate);
//      }
//    }
//    return -1;
//  }

  /**
   * 直接生成流水号内容进行生成模拟
   *
   * @param content 内容
   * @param args 参数
   * @param num 数量
   * @return 返回流水号列表
   */
  public List<String> mockSequenceNo(String content, Map<String, Object> args, int num) {
    List<String> result = new ArrayList<>();
    if (isValid(content)) {
      isMock = true;
      this.content = content;
      return produceSequenceNo(null, args, num, true);
    }
    return result;
  }

  /**
   * 创建流水号方法
   *
   * @param sequenceCode 流水号规则内容
   * @param args 参数，将参数放入 map 中
   * @param num 连续生成几个流水号
   * @param test 是否为测试？测试生成的流水号不会走动，不会对流水号本身造成影响
   * @return 返回生成的序列号模块
   */
  public List<String> produceSequenceNo(String sequenceCode, Map<String, Object> args, int num,
      boolean test) {
    List<String> result = new ArrayList<>();
    if (init()) {
      SequenceRuleConfig ruleConfig = getRuleConfig(sequenceCode);
      if (null != ruleConfig) {
        String[] modules = ruleConfig.getSeqContent().split("&&");
        // 解析之后存储数据
        Map<String, Object> rm = new HashMap<>();
        Map<String, Object> serialMap = new HashMap<>();
        for (String modular : modules) {
          // @{c} 如果是常量，直接解析之后存储
          if (modular.matches("^@[{].*[}]$")) {
            rm.put(modular, modular.substring(2, modular.length() - 1));
            // #{b:defaultValue:true} 如果是变量，直接解析
          } else if (modular.matches("^#[{].*[}]$")) {
            String[] a = modular.substring(2, modular.length() - 1).split(CONTENT_SEPARATOR_, 3);
            String key = a[0];
            String value;
            String defaultValue = a.length >= 2 ? a[1] : DEFAULT_ARG_VALUE;
            if (isValid(key)) {
              value = null == args ? defaultValue : (String) args.get(key);
            } else {
              value = DEFAULT_ARG_VALUE;
            }
            String visible = a.length >= 3 ? a[2] : "";
            if (!isValid(visible) || visible.equalsIgnoreCase("1") || visible
                .equalsIgnoreCase("true")) {
              rm.put(modular, value);
            }
            // ${d:format}日期类型模块 可以指定格式化样式，例如:yyyy-MM-dd，直接解析
          } else if (modular.matches("^[$][{].*[}]$")) {
            String[] a = modular.substring(2, modular.length() - 1).split(CONTENT_SEPARATOR_, 3);
            String key = a[0];
            String format = a.length >= 2 ? a[1] : "yyyy-MM-dd";
            String value = "";
            if (isValid(format)) {
              value = getDateTimeNow(format);
            }
            String visible = a.length >= 3 ? a[2] : "";
            if (!isValid(visible) || visible.equalsIgnoreCase("1") || visible
                .equalsIgnoreCase("true")) {
              rm.put(modular, value);
            }
            if (isValid(key)) {
              variable.put(key, value);
            }
            // *{a:6-none-b&d} 序列号
          } else if (modular.matches("^[*][{].*[}]$")) {
            serialMap.put(modular, "");
          }
        }
        while (num-- != 0) {
          analysisSerialNo(ruleConfig, serialMap, rm);
          StringBuilder sb = new StringBuilder();
          for (String modular : modules) {
            if (rm.containsKey(modular)) {
              sb.append(rm.get(modular));
            }
          }
          logger.info("新的流水号已生成：" + sb.toString());
          result.add(sb.toString());
        }
        finish(ruleConfig.getRowId(), test);
      }
    }
    return result;
  }

  /**
   * 解析变量模块
   */
  private void analysisSerialNo(SequenceRuleConfig ruleConfig, Map<String, Object> serialMap,
      Map<String, Object> rm) {
    if (!serialMap.isEmpty()) {
      for (String modular : serialMap.keySet()) {
        String[] a = modular.substring(2, modular.length() - 1).split(CONTENT_SEPARATOR_, 3);
        String rule = a.length >= 2 ? a[1] : "";
        String[] r = rule.split("-", 2);
        int length = r[0].matches("^\\d+$") ? Integer.valueOf(r[0]) : DEFAULT_SERIAL_LENGTH;
        // 获取分支
        StringBuilder branchValue;
        if (!branchValues.containsKey(a[0])) {
          branchValue = new StringBuilder();
          String[] variables = r.length >= 2 ? r[1].split("&") : null;
          if (isValid(variables)) {
            for (String v : variables) {
              branchValue.append("[").append(v).append(":").append(variable.getOrDefault(v, ""))
                  .append("]");
            }
          }
        } else {
          branchValue = new StringBuilder(branchValues.get(a[0]).toString());
        }
        int nextValue =
            getCurrentVariableValue(ruleConfig.getRowId(), a[0], branchValue.toString()) + 1;
        keys.put(a[0], nextValue);
        branchValues.put(a[0], branchValue);
        StringBuilder nv = new StringBuilder(String.valueOf(nextValue));
        if (nv.length() <= length) {
          while (nv.length() < length) {
            nv.insert(0, 0);
          }
          // 若数据溢出时有异常，在此处追加 异常
        } else {
          throw Lang.makeThrow("Class [%s]: [%s] 该流水号已经用尽！请修改流水号规则或联系管理员！", getClass(),
              ruleConfig.getSeqCode());
        }
        rm.put(modular, nv);
      }
    }
  }

  /**
   * 获取下一个变量的值
   *
   * @return 返回值
   */
  private int getCurrentVariableValue(String seqRowId, String variableKey, String branchSign) {
    if (keys.containsKey(variableKey)) {
      return keys.get(variableKey);
    } else {
      Map<String, Object> cond = new HashMap<>();
      cond.put("seqRowId", seqRowId);
      cond.put("variableKey", variableKey);
      cond.put("branchSign", branchSign);
      List<SequenceGenerate> li = sequenceGenerateMapper.select(cond);
      if (li.size() == 1) {
        SequenceGenerate sequenceGenerate = li.get(0);
        if (sequenceGenerate.getCurrentValue().matches("^\\d+$")) {
          return Integer.valueOf(sequenceGenerate.getCurrentValue());
        }
      }
    }
    return 0;
  }

  /**
   * 查询 序列号配置
   */
  private SequenceRuleConfig getRuleConfig(String sequenceCode) {
    // 当处于模拟状态时，mock一个规则出来
    if (isMock && isValid(content)) {
      SequenceRuleConfig ruleConfig = new SequenceRuleConfig();
      ruleConfig.setSeqContent(content);
      ruleConfig.buildCreateInfo();
      return ruleConfig;
    }
//    if (isValid(sequenceCode)) {
//      SequenceRuleConfigMapper mapper = SpringContextHolder.getBean("sequenceRuleConfigMapper");
//      Map<String, Object> cond = new HashMap<>();
//      cond.put("seqCodeS", sequenceCode);
//      List<SequenceRuleConfig> list = mapper.select(cond);
//      if (list.size() == 1) {
//        return list.get(0);
//      }
//    }
    return null;
  }

  /**
   * 检查序列号生成是否可用
   */
  private boolean init() {
    if (null == sequenceGenerateMapper) {
      sequenceGenerateMapper = SpringContextHolder.getBean("sequenceGenerateMapper");
    }
    variable = new HashMap<>();
    keys = new HashMap<>();
    branchValues = new HashMap<>();
    return true;
  }

  /**
   * 完成流水号
   *
   * @param seqRowId 流水号编号
   * @param test 是否测试
   */
  private void finish(String seqRowId, boolean test) {
    // 如果不是测试，存入数据库
    if (!test && !isMock) {
      for (String variableKey : keys.keySet()) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("seqRowId", seqRowId);
        cond.put("variableKey", variableKey);
        cond.put("branchSign", branchValues.get(variableKey).toString());
        List<SequenceGenerate> li = sequenceGenerateMapper.select(cond);
        SequenceGenerate sg = new SequenceGenerate();
        sg.setSeqRowId(seqRowId);
        sg.setVariableKey(variableKey);
        sg.setCurrentValue(keys.get(variableKey).toString());
        sg.setBranchSign(branchValues.get(variableKey).toString());
        if (li.isEmpty()) {
          sg.buildCreateInfo();
          sequenceGenerateMapper.insert(sg);
        } else {
          sg.buildModifyInfo();
          sequenceGenerateMapper.updateCurrentValue(sg);
        }
      }
    }
    variable = null;
    keys = null;
    branchValues = null;
  }

}
