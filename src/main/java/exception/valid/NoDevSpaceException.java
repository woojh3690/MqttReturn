package exception.valid;

import exception.CustomException;

public class NoDevSpaceException extends CustomException {
    public final static int ERR_CODE = 204;

    public NoDevSpaceException(String service_space_name)
    {
        super(ERR_CODE, createErrorMsg(
                "Developer Space Not Exist",
                "Space Name: %s",
                service_space_name
        ));
    }
}
