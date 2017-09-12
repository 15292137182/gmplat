package com.bcx.plat.core.utils;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.constants.Message;

import static com.bcx.plat.core.utils.ServletUtils.getMessage;

/**
 * <p>Title: ServerResult</p>
 * <p>Description: the is ServerResult</p>
 * <p>Copyright: Shanghai BatchSight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 * <pre>History:
 * 2017/8/27  Wen TieHu Create
 * </pre>
 */
public class ServerResult<T> {

  private int state;
  private String msg;
  private T data;

  public ServerResult() {
    this.state = BaseConstants.STATUS_SUCCESS;
    this.msg = getMessage(Message.OPERATOR_SUCCESS);
  }

  public ServerResult(T data) {
    this.state = BaseConstants.STATUS_SUCCESS;
    this.msg = getMessage(Message.QUERY_SUCCESS);
    this.data = data;
  }



  /**
   * 全参构造方法
   *
   * @param state 状态
   * @param msg   消息
   * @param data  数据
   */
  public ServerResult(int state, String msg, T data) {
    this.state = state;
    this.msg = ServletUtils.getMessage(msg);
    this.data = data;
  }

  /**
   * 设置状态和消息,该构造参数中的消息会被自动国际化处理
   *
   * @param state  状态
   * @param msgKey 消息
   * @return 返回
   */
  public ServerResult<T> setStateMessage(int state, String msgKey, String... strings) {
    this.state = state;
    this.msg = getMessage(msgKey, strings);
    return this;
  }


  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public static ServerResult Msg(Object object,Object object1){
    return null;
  }
}
