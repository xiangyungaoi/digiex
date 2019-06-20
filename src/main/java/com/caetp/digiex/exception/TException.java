package com.caetp.digiex.exception;

import com.caetp.digiex.response.ResponseEnum;
import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public abstract class TException extends RuntimeException {

    private ResponseEnum response;

    TException(ResponseEnum response) {
        this.response = response;
    }

    /**
     * exceptions with data should always with data
     *
     * @param data
     * @return
     */
    public TException withData(Object data) {
        this.response.setData(data);
        return this;
    }

    /**
     * exceptions with stacktrace
     *
     * @param e
     * @return
     */
    public TException withExp(Exception e) {
        super.setStackTrace(e.getStackTrace());
        return this;
    }


}
