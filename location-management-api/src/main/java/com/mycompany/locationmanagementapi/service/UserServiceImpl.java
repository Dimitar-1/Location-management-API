package com.mycompany.locationmanagementapi.service;

import com.mycompany.locationmanagementapi.constant.ErrorType;
import com.mycompany.locationmanagementapi.domain.entity.UserEntity;
import com.mycompany.locationmanagementapi.domain.model.UserModel;
import com.mycompany.locationmanagementapi.exception.BusinessException;
import com.mycompany.locationmanagementapi.exception.ErrorModel;
import com.mycompany.locationmanagementapi.exception.UserNotFoundException;
import com.mycompany.locationmanagementapi.repository.UserRepository;
import com.mycompany.locationmanagementapi.validation.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @description: This class is responsive for main business logic
 * @author: John Doe
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserValidator userValidator, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * @args: void
     * @return: Set<UserModel>
     * @author: John Doe
     * @description: This method return all users
     */
    @Override
    public Set<UserModel> findAll() {
        Set<UserModel> userModelSet = new LinkedHashSet<>();
        userRepository.findAll().forEach(userEntity -> {
            UserModel mapped = modelMapper.map(userEntity, UserModel.class);
            userModelSet.add(mapped);
        });

        return userModelSet;
    }

    /**
     * @args: Long id
     * @return: UserModel
     * @author: John Doe
     * @description: This method return user by id
     */
    @Override
    public UserModel findUserById(Long id) throws UserNotFoundException {
        Optional<UserEntity> byId = userRepository.findById(id);
        List<ErrorModel> errorModelList = new ArrayList<>();

        if (!byId.isPresent()) {
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode(ErrorType.NOT_FOUND.toString());
            errorModel.setMessage("User with id " + id + " is not found.");

            errorModelList.add(errorModel);
            throw new UserNotFoundException(errorModelList);
        }

        return modelMapper.map(byId.get(), UserModel.class);
    }

    /**
     * @args: UserModel userModel
     * @return: boolean
     * @author: John Doe
     * @description: This method check login logic, if user exist - ok, if input data is invalid or user is not found throw BusinessException
     */
    @Override
    public boolean login(UserModel userModel) throws BusinessException {
        log.debug("Entering method login in service layer");
        List<ErrorModel> errorModels = userValidator.validateRequest(userModel);
        if (!CollectionUtils.isEmpty(errorModels)) {
            log.warn("Incorrect input data. Empty password or email");
            throw new BusinessException(errorModels);
        }

        String userModelEmail = userModel.getEmail();
        String userModelPassword = userModel.getPassword();

        boolean isLoginValid = false;
        UserEntity userEntityByEmail = userRepository.findByEmail(userModelEmail);
        if (userEntityByEmail != null) {
            isLoginValid = BCrypt.checkpw(userModelPassword, userEntityByEmail.getPassword());
        }

//        UserEntity userEntity = userRepository.findByEmailAndPassword(userModelEmail, userModelPassword);
        if (!isLoginValid) {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode(ErrorType.AUTH_INVALID_CREDENTIALS.toString());
            errorModel.setMessage("Incorrect email or password");

            log.warn("Invalid attempt to login. Wrong email or password");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        } else {
            log.info("Login was success");
            log.info("Exiting method login in service layer");
            return true;
        }
    }

    /**
     * @args: UserModel userModel
     * @return: Long
     * @author: John Doe
     * @description: This method is logic for registering new user , if user input is correct then ok, if input data is invalid or user with this email is found throw BusinessException
     */
    @Override
    public Long register(UserModel userModel) throws BusinessException {
        log.debug("Entering method register in service layer");
        List<ErrorModel> errorModels = userValidator.validateRequest(userModel);
        if (!CollectionUtils.isEmpty(errorModels)) {
            log.warn("Incorrect input data");
            throw new BusinessException(errorModels);
        }

        UserEntity ueByEmail = userRepository.findByEmail(userModel.getEmail());
        if (ueByEmail != null) {
            List<ErrorModel> errorModelList = new ArrayList<>();

            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode(ErrorType.ALREADY_EXIST.toString());
            errorModel.setMessage("User with this email already exist, please select another.");

            log.warn("Entered user's email is used, cannot register");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }

        UserEntity convertedEntity = modelMapper.map(userModel, UserEntity.class);
        convertedEntity.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));

        UserEntity savedEntity = userRepository.save(convertedEntity);

        log.info("Exiting method register in service layer");
        return savedEntity.getId();
    }
}
