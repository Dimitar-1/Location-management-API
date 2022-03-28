package com.mycompany.locationapibe.service;

import com.mycompany.locationapibe.apiclient.GeocodeApiService;
import com.mycompany.locationapibe.apiclient.OfferingApiService;
import com.mycompany.locationapibe.constant.ErrorType;
import com.mycompany.locationapibe.domain.entity.LocationEntity;
import com.mycompany.locationapibe.domain.entity.LocationOfferingEntity;
import com.mycompany.locationapibe.domain.models.LocationDetailModel;
import com.mycompany.locationapibe.domain.models.LocationModel;
import com.mycompany.locationapibe.domain.models.OfferingModel;
import com.mycompany.locationapibe.exception.BusinessException;
import com.mycompany.locationapibe.exception.ErrorModel;
import com.mycompany.locationapibe.repository.LocationOfferingRepository;
import com.mycompany.locationapibe.repository.LocationRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: This class is responsive for the main business logic
 * @author: John doe
 */
@Service
@Log4j2
public class LocationServiceImpl implements LocationService {

    private final GeocodeApiService geocodeApiService;
    private final OfferingApiService offeringApiService;
    private final LocationRepository locationRepository;
    private final LocationOfferingRepository locationOfferingRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, GeocodeApiService geocodeApiService, OfferingApiService offeringApiService, LocationOfferingRepository locationOfferingRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.geocodeApiService = geocodeApiService;
        this.offeringApiService = offeringApiService;
        this.locationOfferingRepository = locationOfferingRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * @args: void
     * @return List<LocationModel>
     * @author: John doe
     * @description: This method gets all locations
     */
    @Override
    public List<LocationModel> getAllLocation(Long userId) {
        List<LocationModel> locationModelList = locationRepository.findAllByUserId(userId).stream()
                .map(le -> modelMapper.map(le, LocationModel.class))
                .collect(Collectors.toList());

        return locationModelList;
    }

    /**
     * @args: Long userId, LocationModel locationModel
     * @return boolean
     * @author: John doe
     * @description: This method create location
     */
    @Override
    public Long createLocation(LocationModel locationModel) throws BusinessException {
        //call to external geocode api
        geocodeApiService.setCoordinates(locationModel);

        LocationEntity locationEntity = modelMapper.map(locationModel, LocationEntity.class);
        LocationEntity saved = locationRepository.save(locationEntity);
        if (saved == null) {
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode(ErrorType.UNKNOWN_SERVER_ERROR.toString());
            errorModel.setMessage("Unable to register location");
            log.error("Unable to register location! ***** For model " + locationModel);

            List<ErrorModel> errorModelList = new ArrayList<>();
            errorModelList.add(errorModel);

            throw new BusinessException(errorModelList);
        }

        LocationOfferingEntity locationOfferingEntity = new LocationOfferingEntity();
        locationModel.getOfferingIdSet().forEach(offeringId -> {
            locationOfferingEntity.setOfferingId(offeringId);
            locationOfferingEntity.setLocationId(saved.getLocationId());

            locationOfferingRepository.save(locationOfferingEntity);
        });

        return locationEntity.getLocationId();
    }

    /**
     * @args: Long locationId, LocationModel locationModel, Long userId
     * @return LocationModel
     * @author: John doe
     * @description: This method update a location
     */
    @Override
    public LocationModel updateLocation(Long locationId, LocationModel locationModel, Long userId) {
        return null;
    }

    /**
     * @args: Long locationId, Long userId
     * @return boolean
     * @author: John doe
     * @description: This method delete a location
     */
    @Override
    public boolean deleteLocation(Long locationId, Long userId) {
        return false;
    }

    @Override
    public LocationDetailModel getLocationModel(Long locationId, Long userId) {
        LocationEntity byLocationIdAndUserId = locationRepository.findByLocationIdAndUserId(locationId, userId);
        LocationModel locationModel;

        if (byLocationIdAndUserId != null) {
            locationModel = modelMapper.map(byLocationIdAndUserId, LocationModel.class);

            List<OfferingModel> offeringModels = new ArrayList<>();
            List<LocationOfferingEntity> offeringEntities = locationOfferingRepository.findByLocationId(locationId);
            for (LocationOfferingEntity offeringEntity : offeringEntities) {
                OfferingModel offeringModel = offeringApiService.fetchOfferingDetail(offeringEntity.getOfferingId());
                offeringModels.add(offeringModel);
            }
        }

        return null;
    }
}
