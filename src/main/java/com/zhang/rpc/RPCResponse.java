package com.zhang.rpc;

import java.io.Serializable;

/**
 * Created by zhangc on 2018/5/28.
 */
public class RPCResponse implements Serializable {
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
