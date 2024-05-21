package com.example.sisdi_users.usermanagement.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.sisdi_users.usermanagement.api.proto.UserRequests;
import com.example.sisdi_users.usermanagement.model.Gender;
import com.example.sisdi_users.usermanagement.model.UserJPA;
import com.example.sisdi_users.usermanagement.model.proto.UserEntity.User;
import com.example.sisdi_users.usermanagement.model.proto.UserEntity.UserList;
import com.example.sisdi_users.utils.Utils;
import org.springframework.stereotype.Component;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.CreateUserRequest;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.CreateSubscriptionRequest;

@Component
public class UserDTOMapper {
    public UserJPA toJPAEntity(User dto) {

        String name = dto.getName();
        String citizenCardNumber = dto.getCitizenCardNumber();
        LocalDateTime birthday = Utils.parseString(dto.getBirthday());
        String phoneNumber = dto.getPhoneNumber();
        String username = dto.getUsername();
        String role = dto.getRole();
        Gender gender = Gender.fromInt(dto.getGenderValue());

        return  UserJPA.newUser("NULLABLE",username,birthday,name,citizenCardNumber,phoneNumber, gender, role);
    }

    public User toDTOEntity(UserJPA jpaEntity) {
        return User.newBuilder()
                .setName(jpaEntity.getName())
                .setCitizenCardNumber(jpaEntity.getCitizenCardNumber())
                .setBirthday(jpaEntity.getBirthday().toString())
                .setPhoneNumber(jpaEntity.getPhoneNumber())
                .setUsername(jpaEntity.getUsername())
                .setRole(jpaEntity.getRole())
                .setGenderValue(Gender.toInt(jpaEntity.getSex()))
                .build();
    }


    public List<User> toDTOEntityList(List<UserJPA> jpaList) {
        List<User> dtos = new ArrayList<>();
        for(UserJPA jpa: jpaList) {
            dtos.add(toDTOEntity(jpa));
        }
        return dtos;
    }

    public List<UserJPA> toJPAEntityList(List<User> list) {
        List<UserJPA> jpa = new ArrayList<>();
        for(User dto: list) {
            jpa.add(toJPAEntity(dto));
        }
        return jpa;
    }

    public UserList toDTOPlanList(List<User> dtos) {
        return UserList.newBuilder()
                .addAllUser(dtos)
                .build();
    }
    public List<User> toDTOEntityList(UserList list) {
        return list.getUserList();
    }

    public UserJPA createJPA(CreateUserRequest resource) {
        if (resource == null) {
            return null;
        }
        String password = resource.getPassword();
        String name = resource.getName();
        String username = resource.getUsername();
        String phoneNumber = resource.getPhoneNumber();
        String birthday = resource.getBirthday();
        String sex = resource.getSex();
        String citizerCardNumber = resource.getCitizenCardNumber();
        String role = resource.getRole();


        LocalDateTime date = Utils.parseString(resource.getBirthday());


        UserJPA userJPA = UserJPA.newUser(password,username,date,name,citizerCardNumber,phoneNumber,Gender.fromString(sex), role);

        return userJPA;
    }

    public CreateSubscriptionRequest createSubscriptionRequest(CreateUserRequest resource) {
        return CreateSubscriptionRequest.newBuilder()
                .setUsername(resource.getUsername())
                .setPlan(resource.getPlan())
                .setFeeType(resource.getFeeType())
                .setPaymentMethod(resource.getPaymentMethod())
                .setInitialDate(resource.getInitialDate())
                .build();
    }
}
