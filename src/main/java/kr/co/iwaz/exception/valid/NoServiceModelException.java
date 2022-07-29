package kr.co.iwaz.exception.valid;

import kr.co.iwaz.exception.CustomException;

public class NoServiceModelException extends CustomException {

    public final static int ERR_CODE = 205;

    public NoServiceModelException(String modelName) {
        super(ERR_CODE,
                createErrorMsg(
                        "Service Model Not Exist",
                        "Model Name: %s",
                        modelName
                )
        );
    }
}
