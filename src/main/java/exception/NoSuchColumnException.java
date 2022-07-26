package exception;

import java.util.List;

public class NoSuchColumnException extends CustomException
{
    public final static int ERR_CODE = 208;

    public NoSuchColumnException(List<String> notExistColumns)
    {
        super(ERR_CODE, createErrorMsg(
                "Data Field Not Exist",
                "Data Field: %s",
                String.join("\n", notExistColumns)
        ));
    }
}
