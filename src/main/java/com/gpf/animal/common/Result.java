package com.gpf.animal.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gengpengfei
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Boolean success;
    private String msg;
    private Object data;

    public static Result ok() {
        return new Result(true, null, null);
    }

    public static Result ok(Object data) {
        return new Result(true, null, data);
    }

    public static Result ok(Object data, String msg) {
        return new Result(true, msg, data);
    }

    public static Result ok(List<?> data, Long total) {
        return new Result(true, null, data);
    }

    public static Result fail(String msg) {
        return new Result(false, msg, null);
    }
}
