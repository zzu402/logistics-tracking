package com.htkfood.exception;

public class CommonRuntimeException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
    private String errorMessage;

    private CommonRuntimeException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.errorMessage=message;
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static CommonRuntimeException customException(String type, int code, String message, Throwable cause) {
        return new CommonRuntimeException(String.format("%s_%d", type, code), message, cause);
    }
}
