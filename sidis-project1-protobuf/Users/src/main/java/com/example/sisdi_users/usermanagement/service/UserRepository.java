package com.example.sisdi_users.usermanagement.service;


import com.example.sisdi_users.auth.api.AuthRequest;
import com.example.sisdi_users.usermanagement.api.CreateSubscriberRequest;
import com.example.sisdi_users.usermanagement.model.UserJPA;


public interface UserRepository {

    UserJPA create(UserJPA userJPA, CreateSubscriberRequest request) throws Exception ;

    UserJPA findByUsername (String username, boolean internal) throws Exception;


    String login(AuthRequest request, boolean internal) throws Exception;

}
