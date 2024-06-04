package com.example.sisdi_plans.planmanagement.repositories;
import com.example.sisdi_plans.planmanagement.api.PlanDTOMapper;
import com.example.sisdi_plans.planmanagement.model.proto.PlanEntity.Plan;
import com.example.sisdi_plans.planmanagement.model.proto.PlanEntity.PlanList;
import com.example.sisdi_plans.planmanagement.api.proto.PlanRequests.EditPlanRequest;
import com.example.sisdi_plans.planmanagement.model.PlanJPA;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.ByteArrayEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlanHTTPRepository {

    @Value("${replica.port}")
    private String port;

    private final PlanDTOMapper mapper;

    private String getBaseUrl(){
        return "http://localhost:"+ this.port + "/api/plans";
    }

    public PlanJPA getPlanByName(String name) throws Exception {
        String url = this.getBaseUrl() + "/internal/name/" + name;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpRequest = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return mapper.toJPAEntity( Plan.parseFrom(EntityUtils.toByteArray(response.getEntity())));
                }
            }
        }
        return null;
    }

    public List<PlanJPA> getAllPlans() throws Exception {
        String url = this.getBaseUrl() + "/internal/all";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpRequest = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return mapper.toJPAEntityList(mapper.toDTOEntityList(PlanList.parseFrom(EntityUtils.toByteArray(response.getEntity()))));
                }
            }
        }
        return null;
    }

    public PlanJPA editPlanInternal(String name, EditPlanRequest request, String authorization) throws Exception {
        String url = this.getBaseUrl() + "/internal/" + name;

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/x-protobuf");
        httpPatch.setEntity(new ByteArrayEntity(request.toByteArray()));
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {


            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return mapper.toJPAEntity( Plan.parseFrom(EntityUtils.toByteArray(response.getEntity())));
            }
        }
        return null;
    }

    public PlanJPA deactivatePlan(String name, String authorization) throws Exception{
        String url = this.getBaseUrl() + "/internal/deactivate/" + name;

        HttpPatch httpPatch = new HttpPatch(url);

        httpPatch.setHeader("Content-Type", "application/x-protobuf");
        httpPatch.setHeader("Authorization", authorization);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPatch)) {

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return mapper.toJPAEntity( Plan.parseFrom(EntityUtils.toByteArray(response.getEntity())));
            }
        }
        return null;
    }
}
