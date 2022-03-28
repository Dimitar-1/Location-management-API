package com.mycompany.offeringapi.web.controller;

import com.mycompany.offeringapi.domain.model.OfferingModel;
import com.mycompany.offeringapi.exception.BusinessException;
import com.mycompany.offeringapi.service.OfferingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @description: This controller is accountable for providing crud operations.
 * @author: John Doe
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class OfferingController {

    private final OfferingService offeringService;

    @Autowired
    public OfferingController(OfferingService offeringService) {
        this.offeringService = offeringService;
    }

    @GetMapping("/offerings")
    public ResponseEntity<List<OfferingModel>> getAllOfferings() {
        log.debug("Entering method getAllOfferings in web layer");
        List<OfferingModel> allOfferings = offeringService.getAllOfferings();

        log.debug("Exiting method getAllOfferings in web layer");
        return new ResponseEntity<>(allOfferings, HttpStatus.OK);
    }

    @GetMapping("/offerings/{offeringId}")
    public ResponseEntity<OfferingModel> findOfferingById(@PathVariable("offeringId") Long id) throws BusinessException {
        ResponseEntity<OfferingModel> responseEntity;
        log.debug("Entering method findOfferingById in web layer");
        Optional<OfferingModel> offeringById = offeringService.findOfferingById(id);

        responseEntity = new ResponseEntity<>(offeringById.get(), HttpStatus.OK);
        log.debug("Exiting method findOfferingById in web layer");
        return responseEntity;
    }

    @PostMapping("/offerings")
    public ResponseEntity<String> createOffering(@RequestBody OfferingModel offeringModel) throws BusinessException {
        log.debug("Entering method createOffering in web layer");
        ResponseEntity<String> responseEntity;

        boolean isOfferingCreated = offeringService.createOffering(offeringModel);
        if (isOfferingCreated) {
            responseEntity = new ResponseEntity<>("Offering is created successfully. ", HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity<>("Offering is Not Created! ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.debug("Exiting method createOffering in web layer");
        return responseEntity;
    }
}
