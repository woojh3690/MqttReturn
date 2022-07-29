package kr.co.iwaz.exception;

public class WrongTableException extends CustomException
{
    public final static int ERR_CODE = 501;

    public WrongTableException(Exception e)
    {
        super(ERR_CODE, e.getMessage());
    }
}
