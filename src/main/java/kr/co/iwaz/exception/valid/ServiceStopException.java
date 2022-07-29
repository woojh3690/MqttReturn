package kr.co.iwaz.exception.valid;

import kr.co.iwaz.exception.CustomException;

public class ServiceStopException extends CustomException {
    public static final int ERR_CODE = 213;

    public ServiceStopException(String srv_title) {
        super(213, createErrorMsg("Service is not running", "Service Name: %s", srv_title));
    }
}
