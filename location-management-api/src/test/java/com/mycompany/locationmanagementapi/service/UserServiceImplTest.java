package com.mycompany.locationmanagementapi.service;

import com.mycompany.locationmanagementapi.domain.entity.UserEntity;
import com.mycompany.locationmanagementapi.domain.model.UserModel;
import com.mycompany.locationmanagementapi.exception.BusinessException;
import com.mycompany.locationmanagementapi.exception.ErrorModel;
import com.mycompany.locationmanagementapi.repository.UserRepository;
import com.mycompany.locationmanagementapi.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserValidator userValidator;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    ModelMapper modelMapper;

    @Test
    void test_login_error() {
        UserModel userModel = new UserModel();
        List<ErrorModel> errorModelList = new ArrayList<>();

        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("invalid_email");
        errorModel.setMessage("invalid_email");
        errorModelList.add(errorModel);

        when(userValidator.validateRequest(userModel)).thenReturn(errorModelList);

        assertThrows(BusinessException.class, () -> {
            userService.login(userModel);
        });
    }

    @Test
    void test_login_with_wrong_credentials() {
        UserModel userModel = new UserModel();
        userModel.setEmail("asdsd@gmail.com");
        userModel.setPassword("sd2332");

        UserEntity userEntity = null;
        List<ErrorModel> errorModelList = new ArrayList<>();

        when(userValidator.validateRequest(userModel)).thenReturn(errorModelList);
        when(userRepository.findByEmailAndPassword(userModel.getEmail(), userModel.getPassword())).thenReturn(userEntity);

        assertThrows(BusinessException.class, () -> {
            userService.login(userModel);
        });
    }

    @Test
    void test_login_with_correct_credentials() {
        UserModel userModel = new UserModel();
        userModel.setEmail("gms@gmail.com");
        userModel.setPassword("1234");

        List<ErrorModel> errorModelList = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userModel.getEmail());
        userEntity.setPassword(userModel.getPassword());

        when(userValidator.validateRequest(userModel)).thenReturn(errorModelList);
        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(userEntity);
    }

    @Test
    void test_register_error() {
        UserModel userModel = new UserModel();
        List<ErrorModel> errorModelList = new ArrayList<>();

        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("invalid_email");
        errorModel.setMessage("invalid_email");
        errorModelList.add(errorModel);

        when(userValidator.validateRequest(userModel)).thenReturn(errorModelList);

        assertThrows(BusinessException.class, () -> {
           userService.register(userModel);
        });
    }

    @Test
    void test_register_user_with_existing_user() {
        UserModel userModel = new UserModel();
        userModel.setEmail("meraba@com.com");
        userModel.setPassword("sadas");

        List<ErrorModel> errorModelList = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userModel.getEmail());

        when(userValidator.validateRequest(userModel)).thenReturn(errorModelList);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userEntity);

        assertThrows(BusinessException.class, () -> {
           userService.register(userModel);
        });
    }

    @Test
    void test_register_successfully() throws BusinessException {
        UserModel userModel = new UserModel();
        userModel.setEmail("email@gmail.com");
        userModel.setPassword("password");

        List<ErrorModel> errorModelList = new ArrayList<>();

        UserEntity userEntity = null;

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail(userModel.getEmail());
        userEntity1.setPassword(userModel.getPassword());

        UserEntity realEntity = new UserEntity();
        realEntity.setEmail(userModel.getEmail());
        realEntity.setPassword(userModel.getPassword());
        realEntity.setId(11L);

        when(userValidator.validateRequest(userModel)).thenReturn(errorModelList);
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(userEntity);

        when(modelMapper.map(userModel, UserEntity.class)).thenReturn(userEntity1);
        when(userRepository.save(userEntity1)).thenReturn(realEntity);

        Long register = userService.register(userModel);
        assertEquals(realEntity.getId(), register);
    }

    @Test
    void find_all() {
        Iterable<UserEntity> all = userRepository.findAll();
        Set<UserModel> all1 = userService.findAll();

        assertEquals(all.spliterator().getExactSizeIfKnown(), all1.spliterator().getExactSizeIfKnown());
    }
}





