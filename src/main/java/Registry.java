import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Registry {
    public int server_port;
    public String server_ip;
    public String jksPath;
	public boolean isKafka = false;
    public DBConnectionInfo dbInfo;
    public String mqttIP;
    public String mqttPort;

    public Registry(String confPath) throws IOException {
        dbInfo = new DBConnectionInfo();
        loadConfig(confPath);
    }

    public static class DBConnectionInfo {
        public String _driver;
        public String _url;
        public String _id;
        public String _pw;
    }

    private void loadConfig(String configPath) throws IOException {
        File file = new File(configPath + "/registry.conf");

        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) break;
                if (line.contains("#")) continue;
                if (!line.contains("=")) continue;

                int pos = line.indexOf('=');
                String key = line.substring(0, pos).toLowerCase().trim();
                String value = line.substring(pos + 1).trim();

                ///////////////// RestFull or Kafka
                if (key.equalsIgnoreCase("isKafka")) {
                    isKafka = Boolean.parseBoolean(value);
                } else if (key.equalsIgnoreCase("server_port")) {
                    server_port = Integer.parseInt(value);
                } else if (key.equals("server_ip")) {
                    server_ip = value;
                } else if (key.equals("jkspath")) {
                    jksPath = value;
                }

                ///////////////// MariaDB
                else if (key.equals("dbdriver")) {
                    dbInfo._driver = value;
                } else if (key.equals("dburl")) {
                    dbInfo._url = value;
                } else if (key.equals("dbid")) {
                    dbInfo._id = value;
                } else if (key.equals("dbpw")) {
                    dbInfo._pw = value;
                }

                ///////////////// hdfs
                else if (key.equals("mqtt_server_ip")) {
                    mqttIP = value;
                } else if (key.equals("mqtt_server_port")) {
                    mqttPort = value;
                }
            }
            br.close();
        }
    }
}
