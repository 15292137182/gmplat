package com.bcx.plat.core.utils;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.constants.Message;

/**
 * <p>Title: PlatResult</p>
 * <p>Description: the is PlatResult</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *                         2017/8/27  Wen TieHu Create
 *          </pre>
 */
public class PlatResult<T> {
    private int state;
    private String msg;
    private T data;

    public PlatResult() {
    }

    public PlatResult(int state, String msg, T data) {
        this.state = state;
        this.msg = msg;
        this.data = data;
    }

    public static PlatResult Msg(int state, String msg) {
        return new PlatResult(state, msg, null);
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
