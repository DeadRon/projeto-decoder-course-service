package com.ead.course.dtos;

import com.ead.course.enums.UserStatus;
import com.ead.course.models.UserModel;
import lombok.Data;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
public class UserEventDTO {

    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private String userStatus;
    private String userType;
    private String password;
    private String phoneNumber;
    private String oldPassword;
    private String cpf;
    private String imageUrl;
    private String actionType;

    public UserModel convertToUserModel(){
        var userModel = new UserModel();
        userModel.setUserId(userId);
        userModel.setEmail(email);
        userModel.setFullName(fullName);
        userModel.setUserStatus(userStatus);
        userModel.setUserType(userType);
        userModel.setCpf(cpf);
        userModel.setImageUrl(imageUrl);
        return userModel;
    }

}