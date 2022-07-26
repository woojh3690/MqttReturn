package exception.valid;

import exception.CustomException;

public class ServiceStartDuplication extends CustomException {

    public final static int ERR_CODE = 214;

    public ServiceStartDuplication(String serviceName) {
        super(ERR_CODE,
                createErrorMsg(
                        "Service is already running",
                        "Service Name: %s",
                        serviceName
                )
        );
    }
}
