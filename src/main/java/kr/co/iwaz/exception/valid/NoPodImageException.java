package kr.co.iwaz.exception.valid;

import kr.co.iwaz.exception.CustomException;

public class NoPodImageException extends CustomException
{
    public final static int ERR_CODE = 218;

    public NoPodImageException(String imageName)
    {
        super(ERR_CODE, createErrorMsg(
                "No Pod Image",
                "Image Name: %s",
                imageName)
        );
    }
}
