package com.example.sisdi_users.usermanagement.repositories;

import com.example.sisdi_users.usermanagement.api.proto.UserRequests.CreateSubscriptionRequest;
import com.example.sisdi_users.utils.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
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

    public  int postSubscription(CreateSubscriptionRequest request) throws Exception {

        HttpPost post = new HttpPost(getBaseUrl() + "/newSub");

        post.setHeader("Content-Type", "application/x-protobuf");
        post.setEntity(new ByteArrayEntity(request.toByteArray()));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            return response.getStatusLine().getStatusCode();

        }
    }

    private String getBaseUrl(){
        return "http://localhost:"+ this.port + "/api/subscriptions";
    }

}
