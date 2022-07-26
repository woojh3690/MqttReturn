package exception.valid;

import exception.CustomException;

public class NoAnlzTitleException extends CustomException {
    public final static int ERR_CODE = 210;

    public NoAnlzTitleException(String anlz_title)
    {
        super(ERR_CODE, createErrorMsg(
                "Analyze Not Exist",
                "Analyze Name: %s",
                anlz_title
        ));
    }
}
