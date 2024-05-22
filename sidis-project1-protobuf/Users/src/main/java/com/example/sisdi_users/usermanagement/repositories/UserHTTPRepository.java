package com.example.sisdi_users.usermanagement.repositories;

import com.example.sisdi_users.usermanagement.api.UserDTOMapper;
import com.example.sisdi_users.usermanagement.model.UserJPA;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.AuthRequest;
import com.example.sisdi_users.usermanagement.model.proto.UserEntity.User;


@Component
@RequiredArgsConstructor
public class UserHTTPRepository {

    @Value("${replica.port}")
    private String port;

    private final UserDTOMapper mapper;

    private String getBaseUrl(){
        return "http://localhost:"+ this.port + "/api/users";
    }
    public UserJPA getByUsername(String username) throws Exception {
        String url = this.getBaseUrl() + "/internal/" + username;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpRequest = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return mapper.toJPAEntity(User.parseFrom(EntityUtils.toByteArray(response.getEntity())));
                }
            }
        }
        return null;
    }

    public String login(AuthRequest request) throws Exception {
        HttpPost post = new HttpPost(getBaseUrl() + "/internal/login");


        post.setHeader("Content-Type", "application/x-protobuf");
        post.setEntity(new ByteArrayEntity(request.toByteArray()));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            return EntityUtils.toString(response.getEntity());

        }
    }
}
