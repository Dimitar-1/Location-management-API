package com.mycompany.locationapibe.repository;

import com.mycompany.locationapibe.domain.entity.LocationOfferingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationOfferingRepository extends JpaRepository<LocationOfferingEntity, Long> {

    List<LocationOfferingEntity> findByLocationId(Long locationId);
}
