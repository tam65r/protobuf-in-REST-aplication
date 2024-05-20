package com.example.sisdi_users.usermanagement.repositories;

import com.example.sisdi_users.auth.api.AuthRequest;
import com.example.sisdi_users.usermanagement.model.User;
import com.example.sisdi_users.utils.LocalDateTimeTypeAdapter;
import com.example.sisdi_users.utils.ServerPortListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class UserHTTPRepository {

    @Value("${replica.port}")
    private String port;


    private String getBaseUrl(){
        return "http://localhost:"+ this.port + "/api/users";
    }
    public User getByUsername(String username) throws Exception {
        String url = this.getBaseUrl() + "/internal/" + username;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpRequest = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                        .create();
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return gson.fromJson(EntityUtils.toString(response.getEntity()), User.class);
                }
            }
        }
        return null;
    }

    public String login(AuthRequest request) throws Exception {
        HttpPost post = new HttpPost(getBaseUrl() + "/internal/login");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();

        String resource = gson.toJson(request);

        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(resource));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            return EntityUtils.toString(response.getEntity());

        }
    }
}
