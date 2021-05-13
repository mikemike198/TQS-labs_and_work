package ua.pt.tqs.HomeWorkTQS.services;

import org.json.simple.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HttpClient {

    public JSONObject getResponse(String url) throws IOException {
        CloseableHttpClient session = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = session.execute(request);
        try {
            HttpEntity responseData = response.getEntity();
            if (responseData != null) {
                String data = EntityUtils.toString(responseData);
                System.out.println("HTTP data " +data);
                // parsing JSON
                JSONParser parser = new JSONParser();
                JSONObject result = (JSONObject) parser.parse(data);
                System.out.println("HTTP result " + result);
                return result;
            }
        } catch (Exception e) {
        } finally {
            if ( response != null) {
                response.close();
            }
        }
        return null;
    }
}
