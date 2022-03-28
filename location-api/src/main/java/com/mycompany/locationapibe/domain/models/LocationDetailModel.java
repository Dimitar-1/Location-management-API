package com.mycompany.locationapibe.domain.models;

import com.mycompany.locationapibe.constant.LocationType;
import lombok.Data;

import java.util.List;

@Data
public class LocationDetailModel {

    private long locationId;
    private LocationType locationType;
    private String plotNo;
    private String street;
    private String pincode;
    private String city;
    private String state;
    private String country;

    private Long userId;
    private Long openCloseTimeId;
    private List<OfferingModel> offeringModelList;
    private String lat;
    private String lng;
}
