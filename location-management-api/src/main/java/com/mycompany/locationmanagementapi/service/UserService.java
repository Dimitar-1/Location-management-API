package com.mycompany.locationmanagementapi.service;

import com.mycompany.locationmanagementapi.domain.model.UserModel;
import com.mycompany.locationmanagementapi.exception.BusinessException;
import com.mycompany.locationmanagementapi.exception.UserNotFoundException;

import java.util.Set;

public interface UserService {
    Set<UserModel> findAll();

    UserModel findUserById(Long id) throws UserNotFoundException;

    boolean login(UserModel userModel) throws BusinessException;

    Long register(UserModel userModel) throws BusinessException;
}
