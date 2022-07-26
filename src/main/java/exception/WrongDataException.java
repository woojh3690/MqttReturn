package exception;

public class WrongDataException extends CustomException
{
    public final static int ERROR_CODE = 6;

    public WrongDataException(String userDataName)
    {
        super(
                ERROR_CODE,
                createErrorMsg("Wrong user data", "Fail to insert: %s", userDataName)
        );
    }
}
