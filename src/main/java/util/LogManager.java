package util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogManager {
    final Logger logger = Logger.getLogger(LogManager.class);

    public LogManager(String confPath) {
        PropertyConfigurator.configure(confPath + "/mqtt.log4j.properties");
    }

    public enum LOG_TYPE {
        FATAL,
        ERROR,
        WARN,
        INFO,
        DEBUG
    }

    public void writeLog(String msgStr, LOG_TYPE type, String source) {

        if (type == LOG_TYPE.FATAL)
            logger.fatal("[" + source + "] " + msgStr);
        else if (type == LOG_TYPE.ERROR)
            logger.error("[" + source + "] " + msgStr);
        else if (type == LOG_TYPE.WARN)
            logger.warn("[" + source + "] " + msgStr);
        else if (type == LOG_TYPE.DEBUG)
            logger.debug("[" + source + "] " + msgStr);
        else if (type == LOG_TYPE.INFO)
            logger.info("[" + source + "] " + msgStr);
    }

}