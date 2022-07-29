package kr.co.iwaz.exception;

public class WrongJsonStructureException extends CustomException
{
    public WrongJsonStructureException()
    {
        // Json 구조 에러
        super(
                101,
                "JSON Format Error"
        );
    }

    public WrongJsonStructureException(int ERROR_CODE, String subMsg)
    {
        super(
                ERROR_CODE,
                createErrorMsg("JSON Value Not Exist", subMsg)
        );
    }

    public WrongJsonStructureException(int ERROR_CODE, String subMsg, String param)
    {
        super(
                ERROR_CODE,
                createErrorMsg("JSON Value Not Exist", subMsg, param)
        );
    }
}
