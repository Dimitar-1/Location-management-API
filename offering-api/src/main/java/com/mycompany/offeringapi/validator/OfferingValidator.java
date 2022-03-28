package com.mycompany.offeringapi.validator;

import com.mycompany.offeringapi.constant.ErrorType;
import com.mycompany.offeringapi.domain.model.OfferingModel;
import com.mycompany.offeringapi.exception.ErrorModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OfferingValidator {

    public List<ErrorModel> validateOffering(OfferingModel offeringModel) {
        List<ErrorModel> errorModelList = new ArrayList<>();

        if (offeringModel != null && offeringModel.getOfferingName() == null) {
            ErrorModel errorModel = ErrorModel.builder()
                    .code(ErrorType.NOT_EMPTY.toString())
                    .message("Offering model name cannot be empty")
                    .build();

            errorModelList.add(errorModel);
        }

        if (offeringModel != null && offeringModel.getOfferingName() != null && offeringModel.getOfferingName().split("").length > 50) {
            ErrorModel errorModel = ErrorModel.builder()
                    .code(ErrorType.TOO_LONG_NAME.toString())
                    .message("Offering name cannot be more than 50 symbols")
                    .build();

            errorModelList.add(errorModel);
        }

        if (offeringModel != null && offeringModel.getDescription() == null) {
            ErrorModel errorModel = ErrorModel.builder()
                    .code(ErrorType.NOT_EMPTY.toString())
                    .message("Offering description cannot be empty")
                    .build();

            errorModelList.add(errorModel);
        }


        return errorModelList;
    }
}
