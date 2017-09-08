package com.bcx.plat.core.base;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.bcx.plat.core.utils.UtilsTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.json.MappingJacksonValue;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * 基础控制器
 */
public abstract class BaseController<SERVER extends BaseService> {


  /**
   * logger 日志操作
   */
  protected Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * messageSource 资源文件管理器
   */
  @Autowired
  protected ResourceBundleMessageSource messageSource;

  /**
   * @return 参与空白查询的字段
   */
  protected List blankSelectFields() {
    return null;
  }

  protected Object selectList(Locale locale, Condition condition) {
    return select(null, locale, condition, null, false, 0, 0);
  }

  protected Object selectPage(Locale locale, Condition condition, List<Order> orders, int num, int size) {
    return select(null, locale, condition, orders, true, num, size);
  }

  /**
   * 默认的查询方法
   *
   * @param request 请求
   * @param locale  本地化信息
   * @param page    是否分页
   * @param size    大小
   * @param num     页面数
   * @return 返回处理结果
   */
  @SuppressWarnings("unchecked")
  protected Object select(HttpServletRequest request, Locale locale, Condition condition,
                          List<Order> orders, boolean page, int num, int size) {
    boolean success = true;
    Object o;
    // 创建排序信息
    List<Order> _orders = orders;
    if (_orders == null && request != null) {
      _orders = dataSort(request.getParameter("order"));
    }
    if (page) {
      PageResult result = getService().selectPageMap(condition, _orders, num, size);
      if (result.getResult().isEmpty()) {
        success = false;
      }
      o = result;
    } else {
      List<Map> result = getService().selectMap(condition, _orders);
      if (result.isEmpty()) {
        success = false;
      }
      o = result;
    }
    if (success) {
      return result(request, PlatResult.Msg(new ServerResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, o)), locale);
    }
    return result(request, PlatResult.Msg(new ServerResult(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL, o)), locale);
  }

  /**
   * 组装条件
   *
   * @param request 请求信息
   * @param extra   额外条件
   * @param replace 替换条件
   * @return 返回条件
   */
  protected Condition buildRequestCondition(HttpServletRequest request, Condition extra, Condition replace) {
    Condition _con;
    if (replace != null) {
      _con = replace;
    } else {
      List<Condition> conditions = new ArrayList<>();
      if (null != extra) {
        conditions.add(extra);
      }
      if (request != null) {
        // 构建 空白查询条件
        String _search = request.getParameter("search");
        if (isValid(_search)) {
          Set<String> _blankValues = UtilsTool.collectToSet(_search);
          List _fields = blankSelectFields();
          if (null == _fields) {
            _fields = new ArrayList<>(getBeanKeys());
          }
          conditions.add(createBlankQuery(_fields, _blankValues));
        }
      }
      // 开始解析前台传来的条件
      // TODO 考虑到前台没有确定，此处暂时不实现
      // conditionStr [{key:"code",value:"0000001",method:"EQUAL"},{key:"name",value:"0000001",method:"LIKE"}]
      _con = new And(conditions);
    }
    return _con;
  }

  /**
   * 通用新增方法
   *
   * @param entity  实体类
   * @param request 请求信息
   * @param locale  本地化信息
   * @return 返回
   */
  protected Object insert(BeanInterface entity, HttpServletRequest request, Locale locale) {
    if (entity instanceof BaseEntity) {
      BaseEntity _entity = (BaseEntity) entity;
      int insert = _entity.buildCreateInfo().insert();
      if (insert == -1) {
        return result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL)), locale);
      }
      return result(request, commonServiceResult(entity, Message.NEW_ADD_SUCCESS), locale);
    }
    return new Object();
  }

  /**
   * 根据 rowId进行更新
   *
   * @param entity  实体
   * @param request 请求
   * @param locale  本地化信息
   * @return 返回
   */
  protected Object updateById(BeanInterface entity, HttpServletRequest request, Locale locale) {
    if (entity instanceof BaseEntity) {
      BaseEntity _entity = (BaseEntity) entity;
      _entity.getBaseTemplateBean().buildModifyInfo();
      int update = _entity.updateById();
      if (update == -1) {
        return result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL)), locale);
      }
      return result(request, commonServiceResult(entity, Message.UPDATE_SUCCESS), locale);
    }
    return new Object();
  }

  /**
   * 根据 rowId进行删除
   *
   * @param request 请求
   * @param locale  本地化信息
   * @return 返回
   */
  protected Object deleteByIds(HttpServletRequest request, Locale locale, String... rowIds) {
    return deleteByIds(request, locale, Arrays.asList(rowIds));
  }

  /**
   * 根据 rowId进行删除
   *
   * @param request 请求
   * @param locale  本地化信息
   * @return 返回
   */
  protected Object deleteByIds(HttpServletRequest request, Locale locale, List<String> rowIds) {
    int delete = -1;
    Condition condition = new FieldCondition("rowId", Operator.IN, rowIds);
    try {
      delete = getBeanClass().newInstance().delete(condition);
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    if (delete != -1) {
      return result(request, commonServiceResult(delete, Message.DELETE_SUCCESS), locale);
    }
    return result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL)), locale);
  }


  /**
   * 转换消息
   *
   * @param msg    消息代码
   * @param locale 本地化信息
   * @return 返回消息
   */

  protected String convertMsg(String msg, Locale locale) {
    String _msg = null;
    try {
      _msg = messageSource.getMessage(msg, null, locale);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (null != _msg) {
      return _msg;
    }
    return msg;
  }

  /**
   * 对返回的结果进行处理
   *
   * @param request    请求
   * @param platResult 结果信息
   * @param locale     国际化
   * @return 返回
   */
  @SuppressWarnings("unchecked")
  protected Object result(HttpServletRequest request, PlatResult platResult, Locale locale) {
    Map map = new HashMap();
    ServerResult content = platResult.getContent();
    platResult.getContent().setMsg(convertMsg(content.getMsg(), locale));
    map.put("resp", platResult);
    if (null != request && isValid(request.getParameter("callback"))) {
      map.put("resp", platResult);
      MappingJacksonValue value = new MappingJacksonValue(map);
      value.setJsonpFunction(request.getParameter("callback"));
      return value;
    }
    return map;
  }

  /**
   * @return 对应的 javaBean 中的字段
   */
  @SuppressWarnings("unchecked")
  protected Set<String> getBeanKeys() {
    BeanInterface bean = null;
    try {
      bean = getBeanClass().newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (bean != null) {
      return bean.toMap().keySet();
    }
    return null;
  }

  protected Class<? extends BaseEntity> getBeanClass() {
    return (Class) ((ParameterizedType) getServiceClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  /**
   * @return 获取服务
   */
  protected SERVER getService() {
    return SpringContextHolder.getBean(getServiceClass());
  }

  /**
   * @return 获取 Service 类型
   */
  @SuppressWarnings("unchecked")
  protected Class<SERVER> getServiceClass() {
    return (Class<SERVER>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  /**
   * 接受参数和消息进行封装
   *
   * @param content 接受的参数
   * @param msg     消息
   * @param <T>     参数泛型
   * @return 返回
   */
  protected <T> PlatResult commonServiceResult(T content, String msg) {
    return PlatResult.Msg(new ServerResult<>(BaseConstants.STATUS_SUCCESS, msg, content));
  }
}
