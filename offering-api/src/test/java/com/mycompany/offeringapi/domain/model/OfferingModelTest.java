package com.mycompany.offeringapi.domain.model;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.Test;

class OfferingModelTest {

    @Test
    void test_model() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(OfferingModel.class);
    }
}