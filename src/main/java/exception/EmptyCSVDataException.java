package exception;

public class EmptyCSVDataException extends CustomException
{
    public final static int ERROR_CODE = 504;

    public EmptyCSVDataException()
    {
        super(ERROR_CODE, "Empty CSV Data");
    }
}