package kr.co.iwaz.exception;

public class NoSuchTableException extends CustomException
{
    public final static int ERR_CODE = 207;

    public NoSuchTableException(String tableName)
    {
        super(ERR_CODE, createErrorMsg(
                "Data Not Exist",
                "Data Name: %s",
                tableName)
        );
    }
}
