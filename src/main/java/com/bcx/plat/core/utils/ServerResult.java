package com.bcx.plat.core.utils;

/**
 * <p>Title: ServerResult</p>
 * <p>Description: the is ServerResult</p>
 * <p>Copyright: Shanghai BatchSight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 * <pre>Histroy:
 * 2017/8/27  Wen TieHu Create
 * </pre>
 */
public class ServerResult<T> {

  private int state;
  private String msg;
  private T data;

  public ServerResult() {
  }

  public ServerResult(int state, String msg, T data) {
    this.state = state;
    this.msg = msg;
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
    this.msg = ServletUtils.getMessage(msgKey, strings);
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
}
