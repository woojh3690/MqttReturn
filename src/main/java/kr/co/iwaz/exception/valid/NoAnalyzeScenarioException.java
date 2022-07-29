package kr.co.iwaz.exception.valid;

import kr.co.iwaz.exception.CustomException;

public class NoAnalyzeScenarioException extends CustomException {

    public final static int ERR_CODE = 203;

    public NoAnalyzeScenarioException(String scenarioName) {
        super(ERR_CODE,
                createErrorMsg(
                        "Analyze Scenario Not Exist",
                        "Scenario Name: %s",
                        scenarioName
                )
        );
    }
}
