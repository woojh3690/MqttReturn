import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

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

public class MQTT implements MqttCallback {

    private final MemoryPersistence persistence;
    private final MqttClient sampleClient;

    public MQTT() throws Exception {
        // Specify the domain name for connecting to IoT Platform.
        String broker = "ssl://dev.iwaz.co.kr:8883";
        String caFilePath = "C:/Users/user/Downloads/ca.pem";

        this.persistence = new MemoryPersistence();
        this.sampleClient = new MqttClient(broker, "woo_test", persistence);

        // Create a Paho MQTT client.
        try {
            sampleClient.setCallback(this);

            // Set the MQTT connection parameters.
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(180);

            SSLSocketFactory socketFactory = getSocketFactory(caFilePath);
            connOpts.setSocketFactory(socketFactory);

            System.out.println("starting connect the server...");
            sampleClient.connect(connOpts);

            Thread.sleep(1000);
            sampleClient.subscribe("test", 0);
            System.out.println("Broker: " + broker + " Connected");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
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
        SSLContext context = SSLContext.getInstance("TLSv1.3");
        context.init(null, tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Lost Connection : " + cause.getCause());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        System.out.println("Message arrived. : " + new String(message.getPayload(), StandardCharsets.UTF_8));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Message with " + token + " delivered.");
    }

    public void close() {
        try {
            this.sampleClient.close();
            this.persistence.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
