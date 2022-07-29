package kr.co.iwaz.exception.valid;

import kr.co.iwaz.exception.CustomException;

public class NoServiceSpaceException extends CustomException {
    public final static int ERR_CODE = 204;

    public NoServiceSpaceException(String service_space_name)
    {
        super(ERR_CODE, createErrorMsg(
                "Service Space Not Exist",
                "Space Name: %s",
                service_space_name
        ));
    }
}
