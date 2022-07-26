import exception.InvalidValue;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaDB {
    // 커넥션

    private final Connection conn;

    public MariaDB(String url, String id, String pw) throws InvalidValue, SQLException {
        // 유효한 값인지 검사
        checkPropValue(url);
        checkPropValue(id);
        checkPropValue(pw);

        try (BasicDataSource ds = new BasicDataSource()) {
            ds.setUrl(url);
            ds.setUsername(id);
            ds.setPassword(pw);
            ds.setMinIdle(5);

            ds.setMaxOpenPreparedStatements(20000);
            ds.setValidationQuery("SELECT 1");
            ds.setTestOnBorrow(true);
            ds.setTestOnReturn(true);
            ds.setTestWhileIdle(true);

            conn = ds.getConnection();
        }
    }

    public String getMqttTopic(String userSourceKey) throws SQLException {
        String mqttTopic;
        String sql = String.format(Query.MQTT_TOPIC_QUERY_FORM, Query.MQTT_TOPIC_COL, userSourceKey);
        try (Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery(sql);
            rs.next();
            mqttTopic = rs.getString(Query.MQTT_TOPIC_COL);
        }

        return mqttTopic;
    }

    /**
     * 유효한 설정 값인지 검사한다.
     * @param value 검사할 변수
     * @throws InvalidValue null 또는 비어있는 값일 경우 발생
     */
    private void checkPropValue(String value) throws InvalidValue {
        if (value == null || value.isEmpty()) {
            throw new InvalidValue(value);
        }
    }

    public void close() {
        if (conn == null) return;

        try {
            conn.close();
        } catch (SQLException ignored) {}
    }
}
