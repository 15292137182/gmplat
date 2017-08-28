package com.bcx.plat.core.manager;

import com.bcx.plat.core.entity.SequenceGenerate;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.bcx.plat.core.utils.UtilsTool;
import com.bcx.plat.core.utils.extra.lang.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bcx.plat.core.utils.UtilsTool.getDateTimeNow;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 序列号生成类
 * <p>
 * 流水号模块定义：
 * <p>
 * BCX-TEST-SN BCX-20170810-000002
 * <p>
 * 、  @{BCX-}&&${d:yyyy-mm-dd:true}&&@{-}&&*{a:6} - @{c} 常量，不具有任何属性
 * <p>
 * #{b;defaultValue;true}变量，从参数中取出;可设置为null时的默认值;是否显示
 * <p>
 * ${d;format;true}日期类型模块 可以指定格式化样式，例如;yyyy-MM-dd 是否显示
 * <p>
 * *{a;6-b&d} 序列号 位数 影响的变量将会使重置号重置
 * <p>
 * eg:
 * <p>
 * Create By HCL at 2017/8/8
 */
public class SequenceManager {

  private static MoreBatis moreBatis = SpringContextHolder.getBean("moreBatis");
  private Logger logger = LoggerFactory.getLogger(getClass());

  // 变量的参数值取不到的时候默认值
  private static final String DEFAULT_ARG_VALUE = "";
  // 未设定流水号长度时的默认长度
  private static final int DEFAULT_SERIAL_LENGTH = 6;
  // 内容内部分隔符
  private static final String CONTENT_SEPARATOR_ = ";";

  private static boolean isMock = false;
  private Map<String, Object> variable;
  private Map<String, Integer> keys;
  private Map<String, Object> branchValues;
  private String content;


  private static class SequenceManagerHolder {

    private static final SequenceManager instance = new SequenceManager();
  }

  public static SequenceManager getInstance() {
    return SequenceManagerHolder.instance;
  }

  /**
   * 生成流水号
   *
   * @param sequenceCode 流水号代码
   * @return 返回流水号
   */
  public String buildSequenceNo(String sequenceCode) {
    return buildSequenceNo(sequenceCode, null);
  }

  /**
   * 生成序列号
   *
   * @param sequenceCode 序列号编码
   * @param args         参数map
   * @return 返回信息
   */
  public String buildSequenceNo(String sequenceCode, Map<String, Object> args) {
    List<String> strings = produceSequenceNo(sequenceCode, args, 1, false);
    if (strings.size() == 1) {
      return strings.get(0);
    }
    return null;
  }

  private static Pattern serialNumPattern = Pattern.compile("[*][{].+[}]");
  private static Pattern serialNumWithVariablePattern = Pattern.compile("[*][{].+[;][\\d]+-.+[}]");

  /**
   * 重置序列号，如果序列号配置中只有一个流水模块，可以不输入，有多个需要指定！
   * <p>
   * 如果流水号受变量影响，则该流水号当前值现在是不允许重置的！ -.-
   *
   * @param rowId      需要重置的序列号的rowId
   * @param serialId   需要重置的序列号key，若只有一个可以传 null
   * @param startValue 目标值
   */
  public int resetSequenceNo(String rowId, String serialId, int startValue) {
    if (isValid(rowId)) {
      List<Map<String, Object>> ruleConfigs = moreBatis.select(SequenceRuleConfig.class)
              .where(new FieldCondition("rowId", Operator.EQUAL, rowId))
              .execute();
      if (ruleConfigs.size() == 1) {
        String content = (String) ruleConfigs.get(0).get("seqContent");
        // 如果传入的是全是数字
        // 检查是否存在有变量，并获得变量个数
        Matcher matcher = serialNumPattern.matcher(content);
        int groupCount = 0;
        if (matcher.find()) {
          Matcher countM = serialNumPattern.matcher(content);
          while (countM.find()) {
            groupCount++;
          }
        }
        if (groupCount == 0) {
          logger.warn("没有序列号可以重置！");
        } else if (groupCount == 1) {
          String matcherContent = matcher.group();
          if (serialNumWithVariablePattern.matcher(matcherContent).find()) {
            throwError("带有变量的的序列号不能被重置！");
          } else {
            // 重置这个流水号
            return setDBSerialValue(rowId, matcherContent, startValue);
          }
        } else if (isValid(serialId)) {
          // 找到带有该键值的模块
          Pattern pattern = Pattern.compile("[*][{]" + serialId + "[;][\\d]+-.+[}]");
          Matcher matcher1 = pattern.matcher(content);
          if (!matcher1.find()) {
            throwError("没有在序列号(rowId)：%s 中找到指定的序列号键值：%s！", rowId, serialId);
          } else {
            String module = matcher1.group();
            return setDBSerialValue(rowId, module, startValue);
          }
        } else {
          throwError("多序列号重置需要指定序列号的键值！");
        }
      } else {
        throwError("未查询到正确数量的流水号：%s，查询到的数量为: %d", rowId, ruleConfigs.size());
      }
    }
    return -1;
  }

  /**
   * 更新序列号的分支值
   *
   * @param seqRowId 序列号主键
   * @param content  序列号内容
   * @param aimValue 目标值
   * @return 返回操作类型状态
   */
  private int setDBSerialValue(String seqRowId, String content, int aimValue) {
    if (String.valueOf(aimValue).length() <= getVariableLength(content)) {
      List<Condition> conditions = new ArrayList<>();
      conditions.add(new FieldCondition("seqRowId", Operator.EQUAL, seqRowId));
      conditions.add(new FieldCondition("variableKey", Operator.EQUAL, getVariableKey(content)));
      List<Map<String, Object>> result = moreBatis.select(SequenceGenerate.class)
              .where(new And(conditions)).execute();
      if (result.size() == 1) {
        SequenceGenerate generate = new SequenceGenerate().fromMap(result.get(0));
        String _aimV = String.valueOf(aimValue - 1);
        generate.setCurrentValue(_aimV);
        return moreBatis.updateEntity(generate);
      }
    } else {
      throwError("需要重置的序列：%s 长度溢出 ！", seqRowId);
    }
    return -1;
  }

  /**
   * 获取序列号模块中的 Key
   *
   * @param module 模块
   * @return 返回key
   */
  private String getVariableKey(String module) {
    if (isValid(module) && serialNumPattern.matcher(module).find()) {
      int splitEnd = module.contains(";") ? module.indexOf(";") : module.indexOf("}");
      return module.substring(module.indexOf("{") + 1, splitEnd);
    }
    return null;
  }

  /**
   * 获取序列号定义的长度
   *
   * @param module 模块
   * @return 返回长度
   */
  private Integer getVariableLength(String module) {
    if (isValid(module) && serialNumPattern.matcher(module).find()) {
      StringBuilder sb = new StringBuilder(module);
      String[] ss = sb.substring(1, sb.length() - 1).split(";");
      int length = DEFAULT_SERIAL_LENGTH;
      if (ss.length >= 2) {
        length = Integer.parseInt(ss[1].split("-")[0]);
      }
      return length;
    }
    return 0;
  }

  /**
   * 扔出并记录错误
   *
   * @param message 错误内容信息
   * @param objects 需要进行格式化对象
   */
  private void throwError(String message, Object... objects) {
    logger.error(message, objects);
    throw Lang.makeThrow(message, objects);
  }

  /**
   * 直接生成流水号内容进行生成模拟
   *
   * @param content 内容
   * @param args    参数
   * @param num     数量
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
   * @param args         参数，将参数放入 map 中
   * @param num          连续生成几个流水号
   * @param test         是否为测试？测试生成的流水号不会走动，不会对流水号本身造成影响
   * @return 返回生成的序列号模块
   */
  public List<String> produceSequenceNo(String sequenceCode, Map<String, Object> args, int num,
                                        boolean test, String... strings) {
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
              value = null == args || args.isEmpty() ? defaultValue :
                      isValid(args.get(key)) ? args.get(key).toString() : defaultValue;
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
    if (result.isEmpty()) {
      logger.error("未能根据传入的序列号: " + sequenceCode + ", 生成正确的序列号，请检查您的序列号配置信息！");
      throw Lang.makeThrow("无效的序列号: [%s], 请检查！", sequenceCode);
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
        branchValues.put(a[0], branchValue);
        int nextValue =
                getCurrentVariableValue(ruleConfig.getRowId(), a[0]) + 1;
        keys.put(a[0], nextValue);
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
  private int getCurrentVariableValue(String seqRowId, String variableKey) {
    if (keys.containsKey(variableKey)) {
      return keys.get(variableKey);
    } else {
      List<Condition> fieldConditions = new ArrayList<>();
      fieldConditions.add(new FieldCondition("seqRowId", Operator.EQUAL, seqRowId));
      fieldConditions.add(new FieldCondition("variableKey", Operator.EQUAL, variableKey));
      fieldConditions
              .add(new FieldCondition("branchSign", Operator.EQUAL,
                      isValid(branchValues.get(variableKey)) ? branchValues.get(variableKey).toString() : ""));
      List<Map<String, Object>> list = moreBatis.select(SequenceGenerate.class)
              .where(new And(fieldConditions)).execute();
      if (list.size() == 1) {
        String json = UtilsTool.objToJson(list.get(0));
        SequenceGenerate generate = UtilsTool.jsonToObj(json, SequenceGenerate.class);
        if (null == generate) {
          throw Lang.makeThrow("获取当前变量值时出现错误！");
        }
        if (generate.getCurrentValue().matches("^\\d+$")) {
          return Integer.valueOf(generate.getCurrentValue());
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
    if (isValid(sequenceCode)) {
      List<Map<String, Object>> code = moreBatis.select(SequenceRuleConfig.class)
              .where(new FieldCondition("seqCode", Operator.EQUAL, sequenceCode)).execute();
      if (code.size() == 1) {
        String toJson = UtilsTool.objToJson(code.get(0));
        return UtilsTool.jsonToObj(toJson, SequenceRuleConfig.class);
      }
    }
    return null;
  }

  /**
   * 检查序列号生成是否可用
   */
  private boolean init() {
    variable = new HashMap<>();
    keys = new HashMap<>();
    branchValues = new HashMap<>();
    return true;
  }

  /**
   * 完成流水号
   *
   * @param seqRowId 流水号编号
   * @param test     是否测试
   */
  private void finish(String seqRowId, boolean test) {
    // 如果不是测试，存入数据库
    if (!test && !isMock) {
      for (String variableKey : keys.keySet()) {
        List<Condition> fieldConditions = new ArrayList<>();
        fieldConditions.add(new FieldCondition("seqRowId", Operator.EQUAL, seqRowId));
        fieldConditions.add(new FieldCondition("variableKey", Operator.EQUAL, variableKey));
        String branchSign = branchValues.get(variableKey) == null ? "" : branchValues.get(variableKey).toString();
        fieldConditions.add(new FieldCondition("branchSign", Operator.EQUAL, branchSign));
        List<Map<String, Object>> list = moreBatis.select(SequenceGenerate.class)
                .where(new And(fieldConditions)).execute();
        SequenceGenerate sg = new SequenceGenerate();
        if (list.isEmpty()) {
          sg.setSeqRowId(seqRowId);
          sg.setVariableKey(variableKey);
          sg.setCurrentValue(keys.get(variableKey).toString());
          sg.setBranchSign(branchSign);
          sg.buildCreateInfo();
          moreBatis.insertEntity(sg);
        } else {
          sg.fromMap(list.get(0));
          sg.setCurrentValue(keys.get(variableKey).toString());
          sg.buildModifyInfo();
          moreBatis.updateEntity(sg);
        }
      }
    }
    isMock = false;
    variable = null;
    keys = null;
    branchValues = null;
    content = null;
    System.gc();
  }

}
