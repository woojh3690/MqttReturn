package kr.co.iwaz.exception.valid;

import kr.co.iwaz.exception.CustomException;

public class NoScenarioException extends CustomException {
    public final static int ERR_CODE = 206;

    public NoScenarioException(String service_scenario_name)
    {
        super(ERR_CODE, createErrorMsg(
                "Service Scenario Not Exist",
                "Scenario Name: %s",
                service_scenario_name
        ));
    }
}
