package com.example.sisdi_users.usermanagement.service;


import com.example.sisdi_users.usermanagement.model.UserJPA;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.AuthRequest;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.CreateSubscriptionRequest;

public interface UserRepository {

    UserJPA create(UserJPA userJPA, CreateSubscriptionRequest request) throws Exception ;

    UserJPA findByUsername (String username, boolean internal) throws Exception;


    String login(AuthRequest request, boolean internal) throws Exception;

}
