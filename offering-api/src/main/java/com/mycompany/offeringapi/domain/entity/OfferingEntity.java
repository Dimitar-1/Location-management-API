package com.mycompany.offeringapi.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "OFFERING_TABLE")
@Data
@NoArgsConstructor
public class OfferingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String offeringName;
    @Column
    private String description;
    @Column(name = "eligibility_criteria")
    private String eligibilityCriteria;
}
