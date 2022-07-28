import db.MariaDB;
import mqtt.MQTT;
import process.ProcessMsg;
import util.LogManager;
import util.LogManager.LOG_TYPE;
import util.Registry;


public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("arg 0 = user_source_key (pk)");
            return;
        }

        // 유저 소스키
        final String userSourceKey = args[0];

        // 로그 및 환경 설정 초기화
        String confPath = "conf";
        LogManager logManager = new LogManager(confPath);
        Registry registry = new Registry(confPath);

        logManager.writeLog("Read config success.", LOG_TYPE.INFO, "Main");

        // mqtt 토픽명 조회
        MariaDB db = new MariaDB(
            registry.dbInfo.url,
            registry.dbInfo.id,
            registry.dbInfo.pw
        );
        String topicName = db.getMqttTopic(userSourceKey);
        db.close();

        logManager.writeLog("Mqtt-topic : " + topicName, LogManager.LOG_TYPE.INFO, "Main");

        // mqtt 클라이언트 시작
        MQTT mqtt = new MQTT(
            registry.mqttIP, registry.mqttPort, registry.jksPath + "/ca.pem",
            userSourceKey, topicName,
            logManager,
            new ProcessMsg(registry.server_ip, registry.server_port, registry.jksPath, logManager)
        );

        Thread.sleep(Long.MAX_VALUE);
        mqtt.close();
    }


}
