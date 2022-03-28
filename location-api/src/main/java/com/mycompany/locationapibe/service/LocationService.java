package com.mycompany.locationapibe.service;

import com.mycompany.locationapibe.domain.models.LocationDetailModel;
import com.mycompany.locationapibe.domain.models.LocationModel;
import com.mycompany.locationapibe.exception.BusinessException;

import java.util.List;

public interface LocationService {

    List<LocationModel> getAllLocation(Long userId);

    Long createLocation(LocationModel locationModel) throws BusinessException;

    LocationModel updateLocation(Long locationId, LocationModel locationModel, Long userId);

    boolean deleteLocation(Long locationId, Long userId);

    LocationDetailModel getLocationModel(Long locationId, Long userId);
}
