package exception;

import exception.CustomException;

public class DeniedKeyException extends CustomException
{
    public DeniedKeyException(int ERROR_CODE, String name, String notExistKey)
    {
        super(ERROR_CODE, createErrorMsg(
                name + " Key Not Exist",
                name + " Key: %s",
                notExistKey
        ));
    }
}
