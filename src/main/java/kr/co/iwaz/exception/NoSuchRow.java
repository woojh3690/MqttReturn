package kr.co.iwaz.exception;

public class NoSuchRow extends CustomException {
    public static final int ERROR_CODE = 123;

    public NoSuchRow(String tableName, String where) {
        super(
                ERROR_CODE,
                createErrorMsg(
                        "No Such Row",
                        String.format("Table Name: %s, Where: %s", tableName, where)
                )
        );
    }
}
