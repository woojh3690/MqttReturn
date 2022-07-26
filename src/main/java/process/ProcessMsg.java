package process;

import com.google.gson.Gson;

public class ProcessMsg {
    final Gson gson;
    final KafkaProducerEZ kafka;

    public ProcessMsg(String kafkaIp, int kafkaPort, String jksPath) {
        gson = new Gson();
        kafka = new KafkaProducerEZ(kafkaIp, kafkaPort, jksPath);
    }

    public void sendResponse(String message) {
        DstDeviceJsonFormat dstDeviceJsonFormat = gson.fromJson(message, DstDeviceJsonFormat.class);
        dstDeviceJsonFormat.msg.get(0).msg_header.msg_type = "command_result";
        dstDeviceJsonFormat.msg.get(0).msg_header.msg_err_code = "1";
        String responseMsg = gson.toJson(dstDeviceJsonFormat);
        kafka.send("molit", responseMsg);
    }
}
