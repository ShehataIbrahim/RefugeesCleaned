package net.hitachifbbot.node;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HubotCaller {

    public static final String SCREENING_REQUEST_URL = "http://localhost:8088/requestScreening/";

    public static void requestScreening(int userID) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost(SCREENING_REQUEST_URL + userID);

        httpclient.execute(request);
    }

    public static final String NOTIFICATION_REQUEST_URL = "http://localhost:8088/requestNotification/";

    public static void requestNotification(int userID) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost(NOTIFICATION_REQUEST_URL + userID);

        httpclient.execute(request);
    }
}
