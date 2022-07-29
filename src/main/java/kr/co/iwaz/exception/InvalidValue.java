package kr.co.iwaz.exception;

public class InvalidValue extends CustomException {

    public final static int ERR_CODE = 505;

    public InvalidValue(String invalidValue)
    {
        super(
                ERR_CODE,
                createErrorMsg(
                "The Value Is Wrong Value",
                        "Invalid Value: %s",
                        invalidValue
                )
        );
    }
}
