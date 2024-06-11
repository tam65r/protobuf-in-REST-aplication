package com.example.sisdi_subscriptions.subscriptionmanagement.repositories;

import com.example.sisdi_subscriptions.subscriptionmanagement.api.SubscriptionDTOMapper;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.SubscriptionJPA;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.proto.SubscriptionEntity;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.proto.SubscriptionEntity.Subscription;
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

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionHTTPRepository {


    @Value("${replica.port}")
    private String port;

    private final SubscriptionDTOMapper mapper;

    private String getBaseUrl(){
        return "http://localhost:"+ this.port + "/api/subscriptions";
    }

    public byte [] getDetailsByUsername(String authorization) throws Exception{
        String url = this.getBaseUrl() + "/internal/userPlanDetails/";

        HttpGet httpRequest = new HttpGet(url);
        httpRequest.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toByteArray(response.getEntity());
                }
            }
        }
        return null;
    }

    public SubscriptionJPA cancelSubscription(String authorization) throws Exception {
        String url = this.getBaseUrl() + "/internal/cancel";

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/x-protobuf");
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {


            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return mapper.toJPAEntity(Subscription.parseFrom(EntityUtils.toByteArray(response.getEntity())));
            }
        }
        return null;
    }

    public SubscriptionJPA renewSubscription(String authorization) throws Exception {
        String url = this.getBaseUrl() + "/internal/renew";

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/x-protobuf");
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return mapper.toJPAEntity(Subscription.parseFrom(EntityUtils.toByteArray(response.getEntity())));
            }
        }
        return null;
    }

    public SubscriptionJPA swichtPlan(String name, String authorization) throws Exception {
        String url = this.getBaseUrl() + "/internal/switch/" + name;

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/x-protobuf");
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return mapper.toJPAEntity(Subscription.parseFrom(EntityUtils.toByteArray(response.getEntity())));
            }
        }
        return null;
    }

    public List<SubscriptionJPA> subscriptionByPlan(String plan, String authorization) throws Exception {
        String url = this.getBaseUrl() + "/internal/details?plan=" + plan;

        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("Content-Type", "application/x-protobuf");
        httpGet.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return mapper.toJPAEntityList(mapper.toDTOEntityList(SubscriptionEntity.SubscriptionList.parseFrom(EntityUtils.toByteArray(response.getEntity()))));
            }
        }
        return null;
    }

}
