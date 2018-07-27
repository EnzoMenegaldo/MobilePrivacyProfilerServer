import java.io.*;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.*;
import sun.net.www.protocol.http.HttpURLConnection;


import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


public class AuthHttpServer {

    private static final int AUTHENTICATION_PORT = 8000;
    //We are using the same key and self certificate than the database server
//    private static final String KEY_PASSWORD = "password";
 //   private static final String KEY_PATH = "ssl/keystore.jks";



    public static void main(String[] args) throws Exception {
        new AuthHttpServer().start();
        UserGenerator.INSTANCE.createUser();
    }

    void start() throws Exception {
        HttpServer httpsServer = HttpServer.create(new InetSocketAddress(AUTHENTICATION_PORT), 0);

//        SSLContext sslContext = getSslContext();
 //       httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext));

        httpsServer.createContext("/Authenticate", new AuthenticationHandler());
        httpsServer.start();
        System.out.println("Server started");
    }

   /* private SSLContext getSslContext() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(KEY_PATH), KEY_PASSWORD.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, KEY_PASSWORD.toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        SSLContext sslContext = SSLContext.getInstance("TLSv1");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return sslContext;
    }*/

    static class AuthenticationHandler implements HttpHandler {
        public void handle(HttpExchange http) throws IOException {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNodeRoot = objectMapper.readTree(http.getRequestBody());

            String username = jsonNodeRoot.get("username").asText();
            String password = jsonNodeRoot.get("password").asText();
            String device = jsonNodeRoot.get("device").asText();

            String result = DataBaseHelper.checkCredentials(username,password,device);

            byte[] byteResult = result.getBytes();

            Headers responseHeaders = http.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/html");

            if(result.equals(DataBaseHelper.SUCCESSFUL_AUTHENTICATION))
                http.sendResponseHeaders(200, byteResult.length);
            else{
                http.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, byteResult.length);
            }

            OutputStream responseBody = http.getResponseBody();
            responseBody.write(byteResult);
            responseBody.flush();
            responseBody.close();
        }
    }

}
