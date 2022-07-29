package kr.co.iwaz.exception.valid;

import kr.co.iwaz.exception.CustomException;

public class NoSrvScenarioException extends CustomException {
    public final static int ERR_CODE = 215;

    public NoSrvScenarioException(String service_scenario_name)
    {
        super(ERR_CODE, createErrorMsg(
                "Not Available Parameter",
                "Service Name: %s",
                service_scenario_name
        ));
    }
}
