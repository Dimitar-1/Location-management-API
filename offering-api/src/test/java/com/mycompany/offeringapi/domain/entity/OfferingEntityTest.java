package com.mycompany.offeringapi.domain.entity;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.Test;

class OfferingEntityTest {

    @Test
    void test_offering_family() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(OfferingEntity.class);
    }
}