package com.example.sisdi_subscriptions.subscriptionmanagement.repositories;

import com.example.sisdi_subscriptions.subscriptionmanagement.model.SubscriptionJPA;
import com.example.sisdi_subscriptions.utils.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SubscriptionHTTPRepository {


    @Value("${replica.port}")
    private String port;


    private String getBaseUrl(){
        return "http://localhost:"+ this.port + "/api/subscriptions";
    }

    public String getDetailsByUsername(String authorization) throws Exception{
        String url = this.getBaseUrl() + "/internal/userPlanDetails/";

        HttpGet httpRequest = new HttpGet(url);
        httpRequest.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toString(response.getEntity());
                }
            }
        }
        return null;
    }

    public SubscriptionJPA cancelSubscription(String authorization) throws Exception {
        String url = this.getBaseUrl() + "/internal/cancel";

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/json");
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                    .create();

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return gson.fromJson(EntityUtils.toString(response.getEntity()), SubscriptionJPA.class);
            }
        }
        return null;
    }

    public SubscriptionJPA renewSubscription(String authorization) throws Exception {
        String url = this.getBaseUrl() + "/internal/renew";

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/json");
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                    .create();

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return gson.fromJson(EntityUtils.toString(response.getEntity()), SubscriptionJPA.class);
            }
        }
        return null;
    }

    public SubscriptionJPA swichtPlan(String name, String authorization) throws Exception {
        String url = this.getBaseUrl() + "/internal/switch/" + name;

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/json");
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                    .create();

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return gson.fromJson(EntityUtils.toString(response.getEntity()), SubscriptionJPA.class);
            }
        }
        return null;
    }

}
