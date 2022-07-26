package exception;

public class NoPermission extends CustomException{
    public static final int ERROR_CODE = 123;

    public NoPermission(String permission, String access) {
        super(
                ERROR_CODE,
                createErrorMsg(
                        "No Permission",
                        String.format("Denied Permission: %s, To Access: %s", permission, access)
                )
        );
    }
}
