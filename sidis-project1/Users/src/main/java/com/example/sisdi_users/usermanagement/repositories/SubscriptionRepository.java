package com.example.sisdi_users.usermanagement.repositories;

import com.example.sisdi_users.usermanagement.api.CreateSubscriberRequest;
import com.example.sisdi_users.utils.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("Subscription repository for user")
public class SubscriptionRepository {

    @Value("${subscriptions.port}")
    private String port;

    public  int postSubscription(CreateSubscriberRequest request) throws Exception {

        HttpPost post = new HttpPost(getBaseUrl() + "/newSub");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();

        String resource = gson.toJson(request);

        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(resource));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            return response.getStatusLine().getStatusCode();

        }
    }

    private String getBaseUrl(){
        return "http://localhost:"+ this.port + "/api/subscriptions";
    }

}
