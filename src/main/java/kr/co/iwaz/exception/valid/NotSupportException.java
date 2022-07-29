package kr.co.iwaz.exception.valid;

import kr.co.iwaz.exception.CustomException;

public class NotSupportException extends CustomException
{
    public final static int ERR_CODE = 217;

    public NotSupportException(String value)
    {
        super(ERR_CODE, createErrorMsg(
                "Not Support Value",
                "Data Name: %s",
                value)
        );
    }
}
