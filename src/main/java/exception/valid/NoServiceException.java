package exception.valid;

import exception.CustomException;

public class NoServiceException extends CustomException {
    public static final int ERR_CODE = 211;

    public NoServiceException(String srv_title) {
        super(211, createErrorMsg("Service Not Exist", "Service Name: %s", srv_title));
    }
}
