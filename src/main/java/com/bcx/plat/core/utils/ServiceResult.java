package com.bcx.plat.core.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Went on 2017/7/30.
 */
public class ServiceResult<T> implements Serializable{
    private T Data;
    private int error;

    public ServiceResult(T data) {
        Data = data;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ServiceResult{"+Data+"}";
    }
}
