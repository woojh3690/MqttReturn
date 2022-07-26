package exception.valid;

import exception.CustomException;

public class NotFoundException extends CustomException {
    public final static int ERR_CODE = -1;

    public NotFoundException(String element)
    {
        super(ERR_CODE, createErrorMsg(
                "Not Found",
                "Element Name: %s",
                element
        ));
    }
}
