package com.mycompany.locationmanagementapi.validation;

import com.mycompany.locationmanagementapi.constant.ErrorType;
import com.mycompany.locationmanagementapi.domain.model.UserModel;
import com.mycompany.locationmanagementapi.exception.ErrorModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public List<ErrorModel> validateRequest(UserModel userModel) {
        List<ErrorModel> errorModelList = new ArrayList<>();

        validateData(userModel, errorModelList, userModel.getEmail(), "Email Cannot Be Empty", "Please, enter a email.", true);
//        validateEmailByPattern(userModel, errorModelList);
        validateData(userModel, errorModelList, userModel.getPassword(), "Password Cannot Be Empty", "Please, enter a password.", false);

        return errorModelList;
    }

    private void validateData(UserModel userModel, List<ErrorModel> errorModelList, String data, String firstCaseValidationMessage, String secondCaseValidationMessage, boolean isEmail) {
        if (userModel != null && data == null) {
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode(ErrorType.NOT_EMPTY.toString());
            errorModel.setMessage(firstCaseValidationMessage);

            errorModelList.add(errorModel);
        }

        if (userModel != null && data != null) {
            if (data.isBlank()) {
                ErrorModel errorModel = new ErrorModel();
                errorModel.setCode(ErrorType.NOT_EMPTY.toString());
                errorModel.setMessage(secondCaseValidationMessage);

                errorModelList.add(errorModel);
            }
            if (isEmail) validateEmailByPattern(userModel, errorModelList);
        }
    }


    private void validateEmailByPattern(UserModel userModel, List<ErrorModel> errorModelList) {
        if (!userModel.getEmail().isBlank() && !validate(userModel.getEmail())) {
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode(ErrorType.BAD_REQUEST.toString());
            errorModel.setMessage("Incorrect email address. Please, enter a valid email.");

            errorModelList.add(errorModel);
        }
    }

}
