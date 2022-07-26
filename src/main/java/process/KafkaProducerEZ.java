package process;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SslConfigs;

import java.util.Properties;

public class KafkaProducerEZ
{
    private KafkaProducer<String, String> producer;

    public KafkaProducerEZ(String ip, int port)
    {
        construct(ip, port, "");
    }

    public KafkaProducerEZ(String ip, int port, String jksPath)
    {
        construct(ip, port, jksPath);
    }

    private void construct(String ip, int port, String jksPath)
    {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", ip + ":" + port);
        // 자신이 보낸 메시지에 대해 카프카로부터 확인을 기다리지 않습니다.
        prop.put(ProducerConfig.ACKS_CONFIG, "all");
        // 서버로 보낼 레코드를 버퍼링 할 때 사용할 수 있는 전체 메모리의 바이트수
        prop.put("block.on.buffer.full", "true");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "16777216");
        prop.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "335544320");

        if (!jksPath.isEmpty()) {
            // 신뢰 저장소 인증서 추가
            prop.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
            prop.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, jksPath + "/kafka.client.truststore.jks");
            prop.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  "iwaz123");
            prop.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG,  "pkcs12");

            // TLS 인증서 추가
            prop.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, jksPath + "/kafka.client.keystore.jks");
            prop.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "iwaz123");
            prop.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "iwaz123");
            prop.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG,  "pkcs12");
        }

        // producer 생성
        producer = new KafkaProducer<>(prop);
    }

    public void send(String topic, String cnt)
    {
        producer.send(new ProducerRecord<>(topic, cnt));
        producer.flush();
    }

    public void close()
    {
        producer.close();
    }

}
