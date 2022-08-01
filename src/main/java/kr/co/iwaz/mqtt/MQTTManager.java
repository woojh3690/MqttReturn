package kr.co.iwaz.mqtt;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import kr.co.iwaz.process.ProcessMsg;
import kr.co.iwaz.util.LogManager;
import kr.co.iwaz.util.LogManager.LOG_TYPE;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class MQTTManager implements MqttCallbackExtended {

    private final MemoryPersistence persistence;
    private final MqttClient client;
    private final ProcessMsg processMsg;
    private final LogManager logManager;

    private final String broker;            // 브로커 커넥션 경로
    private final String topic;             // 토픽명

    public MQTTManager(String ip, String port, String caFilePath, String clientId, String topic,
                       LogManager logManager,
                       ProcessMsg processMsg) throws Exception {
        this.broker = String.format("ssl://%s:%s", ip, port);
        this.persistence = new MemoryPersistence();
        this.client = new MqttClient(broker, clientId, persistence);
        this.logManager = logManager;
        this.topic = topic;

        // Create a Paho MQTT client.
        try {
            client.setCallback(this);

            // Set the MQTT connection parameters.
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setAutomaticReconnect(true);
            connOpts.setKeepAliveInterval(180);

            // 사설 인증서 등록
            SSLSocketFactory socketFactory = getSocketFactory(caFilePath);
            connOpts.setSocketFactory(socketFactory);

            // 연결 시도
            client.connect(connOpts);
        } catch (MqttException e) {
            printException(e);
            throw e;
        }

        this.processMsg = processMsg;
    }

    private static SSLSocketFactory getSocketFactory(final String caCrtFile) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        X509Certificate caCert = null;

        FileInputStream fis = new FileInputStream(caCrtFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        while (bis.available() > 0) {
            caCert = (X509Certificate) cf.generateCertificate(bis);
        }

        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
        tmf.init(caKs);

        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        logManager.writeLog("Broker: " + broker + " Connected", LOG_TYPE.INFO, "MQTT");

        try{
            client.subscribe(topic, 0);
            logManager.writeLog("Subscribe topic: " + topic, LOG_TYPE.INFO, "MQTT");
        } catch (MqttException e) {
            printException(e);
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String utf8msg = new String(message.getPayload(), StandardCharsets.UTF_8);
        logManager.writeLog("Arrived msg : " + utf8msg, LOG_TYPE.INFO, "MQTT");
        try {
            processMsg.sendResponse(utf8msg);
        } catch (Exception e) {
            logManager.writeLog(e.getMessage(), LOG_TYPE.ERROR, "MQTT");
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logManager.writeLog("Message with " + token + " delivered.", LOG_TYPE.INFO, "MQTT");
    }

    @Override
    public void connectionLost(Throwable cause) {
        logManager.writeLog("Lost Connection : " + cause.getCause(), LOG_TYPE.WARN, "MQTT");
        logManager.writeLog("Lost Connection : " + cause.getMessage(), LOG_TYPE.WARN, "MQTT");
    }

    /**
     * MQTT Exception 로그로 기록한다.
     * @param me    로그로 기록할 MQTT 예외
     */
    private void printException(MqttException me) {
        String errMsgForm = "reason : %s \nmsg : %s \nloc : %s \ncause : %s \n excep : %s";
        String errMsg = String.format(errMsgForm,
                me.getReasonCode(),
                me.getMessage(),
                me.getLocalizedMessage(),
                me.getCause(),
                me);
        logManager.writeLog(errMsg, LOG_TYPE.ERROR, "MQTT");
        me.printStackTrace();
    }

    public void close() {
        try {
            this.client.disconnect();
            this.client.close();
            this.persistence.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
