package exception.valid;

import exception.CustomException;

public class NoAlgorithmException extends CustomException
{
    public final static int ERR_CODE = 202;

    public NoAlgorithmException(String algorithm_type)
    {
        super(ERR_CODE, createErrorMsg(
                "Algorithm Not Exist",
                "Algorithm Name: %s",
                algorithm_type
        ));
    }
}
