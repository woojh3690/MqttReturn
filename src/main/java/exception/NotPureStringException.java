package exception;

public class NotPureStringException extends CustomException
{
    public final static int ERR_CODE = 153;

    public NotPureStringException(String subMsg, String param)
    {
        super(ERR_CODE,
                createErrorMsg(
                        "Not alphabet or Special character",
                        subMsg,
                        param
                )
        );
    }
}
