package com.bcx.plat.core.base;

import static com.bcx.plat.core.base.BaseConstants.DELETE_FLAG;
import static com.bcx.plat.core.utils.UtilsTool.getDateTimeNow;
import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;
import static com.bcx.plat.core.utils.UtilsTool.underlineToCamel;

import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.condition.And;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.bcx.plat.core.utils.TableAnnoUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基础 entity 类，建议所有实体类均继承此类
 *
 * Create By HCL at 2017/7/31
 */
public class BaseEntity<T extends BaseEntity> implements Serializable {

  private String status;//状态
  private String version;//版本
  private String createUser;//创建人
  private String createUserName;//创建名称
  private String createTime;//创建时间
  private String modifyUser;//修改人
  private String modifyUserName;//修改名称
  private String modifyTime;//修改时间
  private String deleteUser;//删除人
  private String deleteUserName;//删除名称
  private String deleteTime;//删除时间
  private String deleteFlag;//删除标记

  private Map etc;

  /**
   * 构建 - 创建信息
   *
   * @return 返回自身
   */
  @SuppressWarnings("unchecked")
  public T buildCreateInfo() {
    setCreateTime(getDateTimeNow());
    setCreateUser("admin");
    setCreateUserName("系统管理员");
    return (T) this;
  }

  /**
   * 构建 - 修改信息
   *
   * @return 返回自身
   */
  @SuppressWarnings("unchecked")
  public T buildModifyInfo() {
    setModifyTime(getDateTimeNow());
    setModifyUser("admin");
    setModifyUserName("系统管理员");
    return (T) this;
  }

  /**
   * 构建 - 删除信息
   *
   * @return 自身
   */
  @SuppressWarnings("unchecked")
  public T buildDeleteInfo() {
    setDeleteTime(getDateTimeNow());
    setDeleteUser("admin");
    setDeleteUserName("系统管理员");
    setDeleteFlag(DELETE_FLAG);
    return (T) this;
  }

  /**
   * 将 entity 实体类转换为 map
   *
   * @return map
   */
  @SuppressWarnings("unchecked")
  public Map<String, Object> toMap() {
    return jsonToObj(objToJson(this), HashMap.class);
  }

  /**
   * 尝试从 map 中读取 entity 类
   *
   * 为了满足需求，我决定造一个轮子
   *
   * @param map map数据
   * @param isUnderline 传入map的key是否为下划线命名
   * @return 返回实体类
   */
  @SuppressWarnings("unchecked")
  public T fromMap(Map<String, Object> map, boolean isUnderline) {
    Class current = getClass();
    do {
      Method[] methods = current.getDeclaredMethods();
      Object temp;
      for (Method method : methods) {
        if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
          String fieldName = underlineToCamel(
              method.getName().substring(3, method.getName().length()), false);
          temp = isUnderline ? map.get(underlineToCamel(fieldName, false)) : map.get(fieldName);
          if (null != temp && !temp.getClass().equals(method.getParameterTypes()[0])) {
            if (temp instanceof String) {
              temp = jsonToObj((String) temp, method.getParameterTypes()[0]);
            } else {
              temp = jsonToObj(objToJson(temp), method.getParameterTypes()[0]);
            }
          }
          try {
            method.invoke(this, temp);
          } catch (IllegalAccessException | InvocationTargetException e) {
            // 我拒绝抛出异常 e.printStackTrace();
          }
        }
      }
      current = current.getSuperclass();
    } while (current != Object.class);

    return (T) this;
  }

  /**
   * 尝试从 map 中读取 entity 类
   *
   * 为了满足需求，我决定造一个轮子
   *
   * @param map map数据
   * @return 返回实体类
   */
  public T fromMap(Map<String, Object> map) {
    return fromMap(map, false);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  public String getCreateUserName() {
    return createUserName;
  }

  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getModifyUser() {
    return modifyUser;
  }

  public void setModifyUser(String modifyUser) {
    this.modifyUser = modifyUser;
  }

  public String getModifyUserName() {
    return modifyUserName;
  }

  public void setModifyUserName(String modifyUserName) {
    this.modifyUserName = modifyUserName;
  }

  public String getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(String modifyTime) {
    this.modifyTime = modifyTime;
  }

  public String getDeleteUser() {
    return deleteUser;
  }

  public void setDeleteUser(String deleteUser) {
    this.deleteUser = deleteUser;
  }

  public String getDeleteUserName() {
    return deleteUserName;
  }

  public void setDeleteUserName(String deleteUserName) {
    this.deleteUserName = deleteUserName;
  }

  public String getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(String deleteTime) {
    this.deleteTime = deleteTime;
  }

  public String getDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(String deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public Map getEtc() {
    return etc;
  }

  public void setEtc(Map etc) {
    this.etc = etc;
  }

  private MoreBatis getMoreBatis() {
    return (MoreBatis) SpringContextHolder.getBean("moreBatis");
  }

  public T insert() {
    List<String> pks = TableAnnoUtil.getPkAnnoField(this.getClass());
    TableSource table = TableAnnoUtil.getTableSource(this.getClass());
    Map<String, Object> values = toMap();
    Map etc = (Map) values.remove("etc");
    getMoreBatis().insert().into(table).cols(values.keySet()).values(Arrays.asList(values))
        .execute();
    return (T) this;
  }

  public T delete() {
    List<String> pks = TableAnnoUtil.getPkAnnoField(this.getClass());
    TableSource table = TableAnnoUtil.getTableSource(this.getClass());
    Map<String, Object> values = toMap();
    Map etc = (Map) values.remove("etc");
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> {
          return new FieldCondition(pk, Operator.EQUAL, values.get(pk));
        })
        .collect(Collectors.toList());
    getMoreBatis().delete().from(table).where(new And(pkConditions)).execute();
    return (T) this;
  }

  public T update() {
    List<String> pks = TableAnnoUtil.getPkAnnoField(this.getClass());
    TableSource table = TableAnnoUtil.getTableSource(this.getClass());
    Map<String, Object> values = toMap();
    Map etc = (Map) values.remove("etc");
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> {
          return new FieldCondition(pk, Operator.EQUAL, values.get(pk));
        })
        .collect(Collectors.toList());
    getMoreBatis().update().from(table).set(values).where(new And(pkConditions)).execute();
    return (T) this;
  }


  public T selectByPks() {
    List<String> pks = TableAnnoUtil.getPkAnnoField(this.getClass());
    TableSource table = TableAnnoUtil.getTableSource(this.getClass());
    Map<String, Object> values = toMap();
    Map etc = (Map) values.remove("etc");
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> {
          return new FieldCondition(pk, Operator.EQUAL, values.get(pk));
        })
        .collect(Collectors.toList());
    List<Map<String, Object>> result = getMoreBatis().select().selectAll().from(table)
        .where(new And(pkConditions)).execute();
    if (result.size() == 1) {
      HashMap<String, Object> _obj = new HashMap<>();
      result.get(0).entrySet().stream().forEach((entry) -> {
        _obj.put(underlineToCamel(entry.getKey(), false), entry.getValue());
      });
      return fromMap(_obj);
    } else {
      return null;
    }
  }


}
