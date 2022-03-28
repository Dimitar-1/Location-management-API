package com.mycompany.offeringapi.service;

import com.mycompany.offeringapi.constant.ErrorType;
import com.mycompany.offeringapi.domain.entity.OfferingEntity;
import com.mycompany.offeringapi.domain.model.OfferingModel;
import com.mycompany.offeringapi.exception.BusinessException;
import com.mycompany.offeringapi.exception.ErrorModel;
import com.mycompany.offeringapi.repository.OfferingRepository;
import com.mycompany.offeringapi.validator.OfferingValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description: This class is responsive for main business logic
 * @author: John Doe
 */
@Service
@Slf4j
public class OfferingServiceImpl implements OfferingService {

    private final OfferingRepository offeringRepository;
    private final OfferingValidator offeringValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferingServiceImpl(OfferingRepository offeringRepository, OfferingValidator offeringValidator, ModelMapper modelMapper) {
        this.offeringRepository = offeringRepository;
        this.offeringValidator = offeringValidator;
        this.modelMapper = modelMapper;
    }

    /**
     * @args: OfferingModel offeringModel
     * @return: boolean
     * @author: John Doe
     * @description: This method create a new offer, if input data is invalid will throw BusinessException
     */
    @Override
    public boolean createOffering(OfferingModel offeringModel) throws BusinessException {
        log.info("Entering createOffering in service layer");

        List<ErrorModel> errorModels = offeringValidator.validateOffering(offeringModel);
        if (!CollectionUtils.isEmpty(errorModels)) {
            log.warn("Incorrect input data");
            throw new BusinessException(errorModels);
        }
        Optional<OfferingEntity> byId = offeringRepository.findById(offeringModel.getId());
        if (byId.isPresent()) {
            ErrorModel errorModel = ErrorModel.builder()
                    .code(ErrorType.ALREADY_EXIST.toString())
                    .message("Offer with this id already exist!")
                    .build();
            List<ErrorModel> errorModelsList = new ArrayList<>();
            errorModelsList.add(errorModel);

            throw new BusinessException(errorModelsList);
        }

        OfferingEntity entity = modelMapper.map(offeringModel, OfferingEntity.class);
        OfferingEntity savedEntity = offeringRepository.save(entity);

        if (savedEntity == null) {
            ErrorModel errorModel = ErrorModel.builder()
                    .code(ErrorType.UNKNOWN_SERVER_ERROR.toString())
                    .message("Not created")
                    .build();
            log.warn("Not Created");

            errorModels.add(errorModel);
            throw new BusinessException(errorModels);
        }

        log.info("Successes for creating offering");
        log.debug("Exiting createOffering in service layer");
        return true;
    }

    /**
     * @args: void
     * @return: List<OfferingModel>
     * @author: John Doe
     * @description: This method return all offers
     */
    @Override
    public List<OfferingModel> getAllOfferings() {
        log.debug("Entering findOfferingById in service layer");
        List<OfferingModel> offeringModels = offeringRepository.findAll().stream()
                .map(offeringEntity -> modelMapper.map(offeringEntity, OfferingModel.class))
                .collect(Collectors.toList());

        log.debug("Exiting findOfferingById in service layer");
        return offeringModels;
    }


    /**
     * @args: Long id
     * @return: Optional<OfferingModel>
     * @author: John Doe
     * @description: This method offer by id
     */
    @Override
    public Optional<OfferingModel> findOfferingById(Long id) throws BusinessException {
        log.debug("Entering findOfferingById in service layer");
        Optional<OfferingEntity> byId = offeringRepository.findById(id);

        if (byId.isEmpty()) {
            ErrorModel errorModel = ErrorModel.builder()
                    .code(ErrorType.NOT_FOUND.toString())
                    .message("Not found")
                    .build();
            List<ErrorModel> errorModels = new ArrayList<>();
            errorModels.add(errorModel);

            log.debug("Throw BusinessException in findOfferingById at service layer");
            throw new BusinessException(errorModels);
        }

        OfferingModel offeringModel = modelMapper.map(byId.get(), OfferingModel.class);
        log.debug("Exiting findOfferingById in service layer");
        return Optional.of(offeringModel);
    }
}
