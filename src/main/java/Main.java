import org.apache.log4j.PropertyConfigurator;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("arg 0 = user_id (pk)");
            return;
        }

        // 로그 설정
        String confPath = "conf";
        PropertyConfigurator.configure(confPath + "/mqtt.log4j.properties");
        Registry registry = new Registry(confPath);

        final String userSourceKey = args[0];

        MariaDB db = new MariaDB(
            registry.dbInfo._url,
            registry.dbInfo._id,
            registry.dbInfo._pw
        );

        String topicName = db.getMqttTopic(userSourceKey);
        db.close();

        MQTT mqtt = new MQTT(
                registry.mqttIP, registry.mqttPort, registry.jksPath + "/ca.pem",
                userSourceKey, topicName
        );

        Thread.sleep(Long.MAX_VALUE);
        mqtt.close();
    }


}
