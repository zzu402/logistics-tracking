package com.htkfood.exception;

public class CommonException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3821158132847159496L;
	private String code;
    private String errorMessage;

    private CommonException(String code, String message, Throwable cause) {
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

    public static CommonException customException(String type, int code, String message, Throwable cause) {
        return new CommonException(String.format("%s_%d", type, code), message, cause);
    }
}
