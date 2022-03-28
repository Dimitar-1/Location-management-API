package com.mycompany.offeringapi.service;

import com.mycompany.offeringapi.domain.model.OfferingModel;
import com.mycompany.offeringapi.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public interface OfferingService {

    boolean createOffering(OfferingModel offeringModel) throws BusinessException;

    List<OfferingModel> getAllOfferings();

    Optional<OfferingModel> findOfferingById(Long id) throws BusinessException;
}
