package com.example.sisdi_plans.planmanagement.repositories;

import com.example.sisdi_plans.planmanagement.api.EditPlanRequest;
import com.example.sisdi_plans.planmanagement.api.PlanDTO;
import com.example.sisdi_plans.planmanagement.model.Plan;
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
import com.example.sisdi_plans.utils.ServerPortListener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
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

    public Plan getPlanByName(String name) throws Exception {
        String url = this.getBaseUrl() + "/internal/name/" + name;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpRequest = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                Gson gson = new Gson();
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return gson.fromJson(EntityUtils.toString(response.getEntity()), Plan.class);
                }
            }
        }
        return null;
    }

    public ArrayList<Plan> getAllPlans() throws Exception {
        String url = this.getBaseUrl() + "/internal/all";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ArrayList<Plan> array = new ArrayList<>();
            HttpGet httpRequest = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                Gson gson = new Gson();
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    Plan[] plans = gson.fromJson(EntityUtils.toString(response.getEntity()), Plan[].class);
                    array.addAll(List.of(plans));
                    return array;
                }
            }
        }
        return null;
    }

    public Plan editPlanInternal(String name,EditPlanRequest request, String authorization) throws Exception {
        String url = this.getBaseUrl() + "/internal/" + name;

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/json");
        httpPatch.setEntity(new StringEntity(request.toString()));
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {
            Gson gson = new Gson();

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return gson.fromJson(EntityUtils.toString(response.getEntity()), Plan.class);
            }
        }
        return null;
    }

    public Plan deactivePlan(String name, String authorization) throws Exception{
        String url = this.getBaseUrl() + "/internal/deactivate/" + name;

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/json");
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {
            Gson gson = new Gson();

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return gson.fromJson(EntityUtils.toString(response.getEntity()), Plan.class);
            }
        }
        return null;
    }
}
