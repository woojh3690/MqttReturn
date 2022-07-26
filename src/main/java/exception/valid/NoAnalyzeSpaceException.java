package exception.valid;

import exception.CustomException;

public class NoAnalyzeSpaceException extends CustomException {

    public final static int ERR_CODE = 201;

    public NoAnalyzeSpaceException(String spaceName) {
        super(ERR_CODE,
                createErrorMsg(
                        "Analyze Space Not Exist",
                        "Space Name: %s",
                        spaceName
                )
        );
    }

}
