package kr.co.iwaz.util;

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
        public String driver;
        public String url;
        public String id;
        public String pw;
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
                    dbInfo.driver = value;
                } else if (key.equals("dburl")) {
                    dbInfo.url = value;
                } else if (key.equals("dbid")) {
                    dbInfo.id = value;
                } else if (key.equals("dbpw")) {
                    dbInfo.pw = value;
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
