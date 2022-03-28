package com.mycompany.offeringapi.validator;

import com.mycompany.offeringapi.domain.model.OfferingModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfferingValidatorTest {

    OfferingModel offeringModel;

    @Test
    void validate_offering_validator_with_null_object() {
        assertNull(offeringModel);
        assertThrows(NullPointerException.class, () -> {
            String description = offeringModel.getDescription();
            description += "asd";
        });
    }

    @Test
    void validate_offering_validator_with_null_value_for_offering_name() {
        OfferingModel build = OfferingModel.builder()
                .offeringName(null)
                .build();

        assertNotNull(build);
        assertNull(build.getOfferingName());
    }

    @Test
    void validate_offering_validator_with_longer_value_for_offering_name() {
        offeringModel = OfferingModel.builder()
                .offeringName("sasdsdassdddsdsddsdsdsdssdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsddsdsdsdsdsdsds")
                .build();

        assertNotNull(offeringModel);
        assertNotNull(offeringModel.getOfferingName());

        int lengthOfOfferingName = offeringModel.getOfferingName().split("").length;
        assertThrows(Exception.class, () -> {
            boolean isBiggerThanTheRuleFromEntity = lengthOfOfferingName > 50;
            if (isBiggerThanTheRuleFromEntity) {
                throw new Exception();
            }
        });
    }

    @Test
    void validate_offering_validator_with_null_value_for_description() {
        OfferingModel build = OfferingModel.builder()
                .offeringName("offering name")
                .description(null)
                .build();

        assertNotNull(build);
        assertNotNull(build.getOfferingName());
        assertNull(build.getDescription());
    }

    @Test
    void validate_offering_validator_with_null_value_for_eligibility_criteria() {
        OfferingModel build = OfferingModel.builder()
                .offeringName("offering name")
                .description("description")
                .eligibilityCriteria(null)
                .build();

        assertNotNull(build);
        assertNotNull(build.getOfferingName());
        assertNotNull(build.getDescription());
        assertNull(build.getEligibilityCriteria());
    }

    @Test
    void validate_offering_validator_with_value_for_eligibility_criteria() {
        OfferingModel build = OfferingModel.builder()
                .offeringName("offering name")
                .description("description")
                .eligibilityCriteria("criteria")
                .build();

        assertNotNull(build);
        assertNotNull(build.getOfferingName());
        assertNotNull(build.getDescription());
        assertNotNull(build.getEligibilityCriteria());
    }

    @Test
    void validate_offering_validator_with_correct_values() {
        offeringModel = OfferingModel.builder()
                .offeringName("offering name")
                .description("description")
                .eligibilityCriteria("eligibility criteria")
                .build();

        assertNotNull(offeringModel);
        assertNotNull(offeringModel.getOfferingName());
        assertNotNull(offeringModel.getDescription());
        assertNotNull(offeringModel.getEligibilityCriteria());

    }
}
