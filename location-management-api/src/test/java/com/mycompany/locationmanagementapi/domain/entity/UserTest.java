package com.mycompany.locationmanagementapi.domain.entity;

import com.mycompany.locationmanagementapi.domain.model.UserModel;
import com.mycompany.locationmanagementapi.exception.ErrorModel;
import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class UserTest {

    @Test
    void test_model_entities() {
        BeanTester beanTester = new BeanTester();

        beanTester.testBean(UserEntity.class);
        beanTester.testBean(UserModel.class);
        beanTester.testBean(ErrorModel.class);
    }

}