package com.mycompany.locationapibe.domain.models;

import lombok.Data;

@Data
public class OfferingModel {

    private Long id;
    private String offeringName;
    private String description;
    private String eligibilityCriteria;
}
