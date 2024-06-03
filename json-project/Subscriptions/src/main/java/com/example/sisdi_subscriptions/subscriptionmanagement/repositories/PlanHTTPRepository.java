package com.example.sisdi_subscriptions.subscriptionmanagement.repositories;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("plan repository for subscribers")
public class PlanHTTPRepository {

    @Value("${plans.port}")
    private String port;

    public  int checkIfPlanExists(String planName) throws Exception {

        HttpGet request = new HttpGet(getBaseUrl() + "/name/" + planName);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            return response.getStatusLine().getStatusCode();
        }
    }

    public  String checkIfPlanExistsResponse(String planName) throws Exception {

        HttpGet request = new HttpGet(getBaseUrl() + "/name/" + planName);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            return EntityUtils.toString(response.getEntity());

        }
    }

    private String getBaseUrl(){
        return "http://localhost:"+ this.port + "/api/plans";
    }

}
