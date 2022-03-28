package com.mycompany.offeringapi.repository;

import com.mycompany.offeringapi.domain.entity.OfferingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferingRepository extends JpaRepository<OfferingEntity, Long> {

    Optional<OfferingEntity> findById(Long id);
}
