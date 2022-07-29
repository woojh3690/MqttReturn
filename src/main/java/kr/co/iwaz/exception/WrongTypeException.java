package kr.co.iwaz.exception;

public class WrongTypeException extends CustomException
{
    public final static int ERR_CODE = 151;

    public WrongTypeException(String wrongType)
    {
        super(ERR_CODE,
                createErrorMsg(
                        "Field Type is Unknown Type",
                        "Invalid Type: %s",
                        wrongType
                )
        );
    }
}
