package com.mycompany.locationapibe.web.controller;

import com.mycompany.locationapibe.domain.models.LocationDetailModel;
import com.mycompany.locationapibe.domain.models.LocationModel;
import com.mycompany.locationapibe.exception.BusinessException;
import com.mycompany.locationapibe.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: This controller is accountable for providing crud operations.
 * @author: John Doe
 */
@RestController
@RequestMapping("/api/v1")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations/users/{userId}")
    public ResponseEntity<List<LocationModel>> getAllLocations(@PathVariable("userId") Long userId) {
        List<LocationModel> allLocation = locationService.getAllLocation(userId);
        return new ResponseEntity<>(allLocation, HttpStatus.OK);
    }

    @PostMapping("/locations/users")
    public ResponseEntity<String> createLocation(@RequestBody LocationModel locationModel) throws BusinessException {
        Long location = locationService.createLocation(locationModel);
        return new ResponseEntity<>("Location is created with Id " + location, HttpStatus.CREATED);
    }


    @GetMapping("/locations/{locationId}/users/{userId}")
    public ResponseEntity<LocationDetailModel> getLocationModel(@PathVariable("locationId") Long locationId, @PathVariable("userId") Long userId) {
        ResponseEntity<LocationDetailModel> responseEntity;

        LocationDetailModel locationModel = locationService.getLocationModel(locationId, userId);
        if (locationModel != null) {
            responseEntity = new ResponseEntity<>(locationModel, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @PutMapping("/locations/{locationId}/users/{userId}")
    public void updateLocation(@PathVariable("locationId") Long locationId, @PathVariable("userId") Long userId, @RequestBody LocationModel locationModel) {

    }

    @DeleteMapping("/locations/{locationId}/users/{userId}")
    public void deleteLocation(@PathVariable("locationId") Long locationId, @PathVariable("userId") Long userId, @RequestBody LocationModel locationModel) {

    }


}