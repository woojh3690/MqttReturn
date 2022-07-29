package kr.co.iwaz.exception;

public class CsdSQLException extends CustomException{
    public static final int ERROR_CODE = 503;

    public CsdSQLException(String errorMsg) {
        super(
                ERROR_CODE,
                createErrorMsg(
                        "Cassandra Query Error",
                        "Error Msg: %s",
                        errorMsg
                )
        );
    }
}
