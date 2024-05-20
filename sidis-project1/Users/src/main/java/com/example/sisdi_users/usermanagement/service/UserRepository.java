package com.example.sisdi_users.usermanagement.service;


import com.example.sisdi_users.auth.api.AuthRequest;
import com.example.sisdi_users.usermanagement.api.CreateSubscriberRequest;
import com.example.sisdi_users.usermanagement.model.User;


public interface UserRepository {

    User create(User user, CreateSubscriberRequest request) throws Exception ;

    User findByUsername (String username, boolean internal) throws Exception;


    String login(AuthRequest request, boolean internal) throws Exception;

}
