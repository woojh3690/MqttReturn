package exception;

public class DuplicateUserTableName extends CustomException
{
    public final static int ERR_CODE = 302;

    public DuplicateUserTableName(String userTableName)
    {
        super(ERR_CODE, createErrorMsg(
                "Train Data Name Duplicated",
                "Data Name: %s",
                userTableName
        ));
    }
}
