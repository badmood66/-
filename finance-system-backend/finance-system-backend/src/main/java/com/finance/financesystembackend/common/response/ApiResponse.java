package com.finance.financesystembackend.common.response;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private Integer code;
    private String message;
    private T data;

    /* ===== 成功 ===== */

    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setCode(200);
        r.setMessage("success");
        r.setData(data);
        return r;
    }

    /* ===== 失败 ===== */

    public static <T> ApiResponse<T> error(int code, String message) {
        return error(code, message, null);
    }

    public static <T> ApiResponse<T> error(int code, String message, T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }
}
