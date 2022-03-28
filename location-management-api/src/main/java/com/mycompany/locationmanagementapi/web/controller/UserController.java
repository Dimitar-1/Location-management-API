package com.mycompany.locationmanagementapi.web.controller;

import com.mycompany.locationmanagementapi.domain.entity.UserEntity;
import com.mycompany.locationmanagementapi.domain.model.UserModel;
import com.mycompany.locationmanagementapi.exception.BusinessException;
import com.mycompany.locationmanagementapi.exception.UserNotFoundException;
import com.mycompany.locationmanagementapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @description: This controller is accountable for providing crud operations.
 * @author: John Doe
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public Set<UserModel> getAllUsers()  {
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserModel> getUserById(@PathVariable(name = "userId") Long id) throws UserNotFoundException {
        UserModel userById = userService.findUserById(id);

        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Boolean> login(@RequestBody UserModel userModel) throws BusinessException {
        log.debug("Entering method login in web layer");
        ResponseEntity<Boolean> responseEntity;

        boolean login = userService.login(userModel);
        if (login) {
            responseEntity = new ResponseEntity<>(login, HttpStatus.OK);
            log.debug("Successful login");
        } else {
            responseEntity = new ResponseEntity<>(login, HttpStatus.BAD_REQUEST);
            log.debug("Unsuccessful login");
        }

        log.debug("Exiting method login in web layer");
        return responseEntity;
    }

    @PostMapping("/users/register")
    public ResponseEntity<Long> register(@RequestBody UserModel userModel, BindingResult bindingResult) throws BusinessException {
        if (bindingResult.hasErrors()) {
            userModel.setEmail(null);
            userModel.setPassword(null);
            userModel.setFullName(null);
            userModel.setMobileNumber(null);
        }
        log.debug("Entering method register in web layer");
        Long registerId = userService.register(userModel);

        ResponseEntity<Long> responseEntity = new ResponseEntity<>(registerId, HttpStatus.CREATED);
        log.debug("Exiting method register in web layer");
        return responseEntity;
    }
}
