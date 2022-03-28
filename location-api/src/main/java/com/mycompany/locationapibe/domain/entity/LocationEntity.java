package com.mycompany.locationapibe.domain.entity;

import com.mycompany.locationapibe.constant.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "LOCATION_ENTITY_TABLE")
@Data
@NoArgsConstructor
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationId;

    @Enumerated(value = EnumType.STRING)
    private LocationType locationType;
    private String plotNo;
    private String street;
    private String pincode;
    private String city;
    private String state;
    private String country;

    private Long userId;
    private Long openCloseTimeId;
    private String lat;
    private String lng;
}
