package com.mycompany.locationapibe.repository;

import com.mycompany.locationapibe.domain.entity.LocationEntity;
import com.mycompany.locationapibe.domain.models.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    List<LocationEntity> findAllByUserId(Long userId);

    LocationEntity findByLocationId(Long id);

    LocationEntity findByLocationIdAndUserId(Long locationId, Long userId);
}
