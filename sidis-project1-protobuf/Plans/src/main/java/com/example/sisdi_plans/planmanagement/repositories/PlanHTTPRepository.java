package com.example.sisdi_plans.planmanagement.repositories;

import com.example.sisdi_plans.planmanagement.api.EditPlanRequest;
import com.example.sisdi_plans.planmanagement.model.PlanJPA;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlanHTTPRepository {

    @Value("${replica.port}")
    private String port;


    private String getBaseUrl(){
        return "http://localhost:"+ this.port + "/api/plans";
    }

    public PlanJPA getPlanByName(String name) throws Exception {
        String url = this.getBaseUrl() + "/internal/name/" + name;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpRequest = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                Gson gson = new Gson();
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return gson.fromJson(EntityUtils.toString(response.getEntity()), PlanJPA.class);
                }
            }
        }
        return null;
    }

    public ArrayList<PlanJPA> getAllPlans() throws Exception {
        String url = this.getBaseUrl() + "/internal/all";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ArrayList<PlanJPA> array = new ArrayList<>();
            HttpGet httpRequest = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                Gson gson = new Gson();
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    PlanJPA[] plansJPA = gson.fromJson(EntityUtils.toString(response.getEntity()), PlanJPA[].class);
                    array.addAll(List.of(plansJPA));
                    return array;
                }
            }
        }
        return null;
    }

    public PlanJPA editPlanInternal(String name, EditPlanRequest request, String authorization) throws Exception {
        String url = this.getBaseUrl() + "/internal/" + name;

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/json");
        httpPatch.setEntity(new StringEntity(request.toString()));
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {
            Gson gson = new Gson();

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return gson.fromJson(EntityUtils.toString(response.getEntity()), PlanJPA.class);
            }
        }
        return null;
    }

    public PlanJPA deactivePlan(String name, String authorization) throws Exception{
        String url = this.getBaseUrl() + "/internal/deactivate/" + name;

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/json");
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {
            Gson gson = new Gson();

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return gson.fromJson(EntityUtils.toString(response.getEntity()), PlanJPA.class);
            }
        }
        return null;
    }
}
