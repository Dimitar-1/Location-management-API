package com.mycompany.offeringapi.service;

import com.mycompany.offeringapi.domain.entity.OfferingEntity;
import com.mycompany.offeringapi.domain.model.OfferingModel;
import com.mycompany.offeringapi.exception.BusinessException;
import com.mycompany.offeringapi.repository.OfferingRepository;
import com.mycompany.offeringapi.validator.OfferingValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class OfferingServiceImplTest {

    @InjectMocks
    OfferingServiceImpl offeringService;
    @Autowired
    OfferingRepository offeringRepository;

    OfferingModel offeringModel;




    @Test
    void create_offering_with_empty_input_data() {
        offeringModel = OfferingModel.builder().build();
        assertNotNull(offeringModel);
        assertThrows(BusinessException.class, () -> {
            offeringService.createOffering(offeringModel);
        });
    }

    @Test
    void get_all_offerings() {
        List<OfferingModel> before = offeringService.getAllOfferings();
        assertNotNull(before);
        assertEquals(0, before.size());
    }

    @Test
    void fail_finding_offer_by_id() {
        offeringModel = new OfferingModel();
        assertThrows(BusinessException.class, () -> {
            offeringService.findOfferingById(offeringModel.getId());
        });
    }

    @Test
    void find_offering_by_id() throws BusinessException {
        offeringModel = OfferingModel.builder()
                .id(101L)
                .offeringName("sdfs")
                .description("sddssdsds")
                .eligibilityCriteria("sddssdds")
                .build();

        ModelMapper modelMapper = new ModelMapper();
        OfferingEntity entity = modelMapper.map(offeringModel, OfferingEntity.class);
        OfferingEntity saved = offeringRepository.save(entity);

        //todo -> null pointer exception
        Optional<OfferingModel> offeringById = offeringService.findOfferingById(saved.getId());
        offeringById.ifPresent(model -> assertTrue(model.getId() > 0));

        offeringRepository.delete(saved);
    }
}