package kr.co.iwaz.process;

import com.google.gson.Gson;
import kr.co.iwaz.util.LogManager;
import kr.co.iwaz.util.LogManager.LOG_TYPE;

public class ProcessMsg {
    final Gson gson;
    final KafkaProducerEZ kafka;
    final LogManager logManager;

    public ProcessMsg(String kafkaIp, int kafkaPort, String jksPath, LogManager logManager) {
        this.gson = new Gson();
        this.kafka = new KafkaProducerEZ(kafkaIp, kafkaPort, jksPath);
        this.logManager = logManager;
    }

    public void sendResponse(String message) {
        DstDeviceJsonFormat dstDeviceJsonFormat = gson.fromJson(message, DstDeviceJsonFormat.class);
        dstDeviceJsonFormat.msg.get(0).msg_header.msg_type = "command_result";
        dstDeviceJsonFormat.msg.get(0).msg_header.msg_err_code = "1";
        String responseMsg = gson.toJson(dstDeviceJsonFormat);
        kafka.send("molit", responseMsg);
        logManager.writeLog("Complete send response msg.", LOG_TYPE.INFO, "ProcessMsg");
    }
}
