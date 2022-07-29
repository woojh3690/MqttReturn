package kr.co.iwaz.exception;

import java.util.List;

public class DuplicateException extends CustomException {

    public final static int ERR_CODE = 301;

    public DuplicateException(String duplicateValue) {
        super(ERR_CODE,
                createErrorMsg(
                        "Duplicate Error",
                        "Duplicated Value: %s",
                        duplicateValue
                )
        );
    }

    public DuplicateException(List<String> duplicateValues) {
        super(ERR_CODE,
                createErrorMsg(
                        "Duplicate Error",
                        "Duplicated Value: [%s]",
                        String.join(", ", duplicateValues)
                )
        );
    }
}
