package kr.co.iwaz.exception;

public class CustomException extends Exception {

    private final int errCode;
    private final String description;

    public CustomException(int errCode, String description) {
        super();
        this.errCode = errCode;
        this.description = description;
    }

    /**
     * 형식에 맞춘 에러 메시지를 생성한다. 하위 메시지가 존재한다.
     * @param mainMsg       메인 메시지
     * @param subMsg        하위 메시지
     */
    public static String createErrorMsg(String mainMsg, String subMsg) {
        String errorMsg = mainMsg;
        if (!subMsg.isEmpty()) {
            errorMsg += ":\n- " + subMsg;
        }
        return errorMsg;
    }

    /**
     * 형식에 맞춘 에러 메시지를 생성한다. 하위 메시지에 파라미터가 들어간다.
     * @param mainMsg       메인 메시지
     * @param subMsg        하위 메시지
     * @param param         하위 메시지 파라미터
     */
    public static String createErrorMsg(String mainMsg, String subMsg, String param) {
        subMsg = String.format(subMsg, param);
        return createErrorMsg(mainMsg, subMsg);
    }

    /**
     * 에러 코드를 반환한다.
     * @return  에러 코드
     */
    public final int getErrCode() {
        return errCode;
    }

    /**
     * 형식에 맞춘 에러 메시지를 반환한다.
     * @return  에러 메시지
     */
    public final String getDescription() {
        return description;
    }

}
