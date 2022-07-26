import db.MariaDB;
import mqtt.MQTT;
import org.apache.log4j.PropertyConfigurator;
import process.ProcessMsg;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("arg 0 = user_source_key (pk)");
            return;
        }

        // 유저 소스키
        final String userSourceKey = args[0];

        // 로그 설정
        String confPath = "conf";
        PropertyConfigurator.configure(confPath + "/mqtt.log4j.properties");
        Registry registry = new Registry(confPath);

        // mqtt 토픽명 조회
        MariaDB db = new MariaDB(
            registry.dbInfo._url,
            registry.dbInfo._id,
            registry.dbInfo._pw
        );
        String topicName = db.getMqttTopic(userSourceKey);
        db.close();

        // mqtt 클라이언트 시작
        MQTT mqtt = new MQTT(
            registry.mqttIP, registry.mqttPort, registry.jksPath + "/ca.pem",
            userSourceKey, topicName,
            new ProcessMsg(registry.server_ip, registry.server_port, registry.jksPath)
        );

        Thread.sleep(Long.MAX_VALUE);
        mqtt.close();
    }


}
