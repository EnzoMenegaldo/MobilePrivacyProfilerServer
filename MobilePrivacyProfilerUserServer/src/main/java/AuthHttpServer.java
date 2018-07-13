import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.security.KeyStore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.*;


import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


public class AuthHttpServer {

    private static final int AUTHENTICATION_PORT = 8000;
    //We are using the same key and self certificate than the database server
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PATH ="./ssl/keystore.jks";

    public static void main(String[] args) throws Exception {
        new AuthHttpServer().start();
        UserGenerator.INSTANCE.createUser(UserGenerator.email_file);
    }

    void start() throws Exception {
        HttpsServer httpsServer = HttpsServer.create(new InetSocketAddress(AUTHENTICATION_PORT), 0);

        SSLContext sslContext = getSslContext();
        httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext));

        httpsServer.createContext("/Authenticate", new AuthenticationHandler());
        httpsServer.start();
    }

    private SSLContext getSslContext() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(KEY_PATH), KEY_PASSWORD.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, KEY_PASSWORD.toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        SSLContext sslContext = SSLContext.getInstance("TLSv1");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return sslContext;
    }

    static class AuthenticationHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNodeRoot = objectMapper.readTree(t.getRequestBody());

            String username = jsonNodeRoot.get("username").asText();
            String password = jsonNodeRoot.get("password").asText();

            if(DataBaseHelper.checkCredentials(username,password))
                t.sendResponseHeaders(200, -1);
            else
                t.sendResponseHeaders(404,-1);
        }
    }

}
