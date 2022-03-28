package com.mycompany.locationapibe.apiclient;

import com.mycompany.locationapibe.domain.models.OfferingModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class OfferingApiService {

    @Value("${offering.api.baseurl}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate;

    public OfferingApiService(@Qualifier("restTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OfferingModel fetchOfferingDetail(Long offeringId) {
        final String apiQuery = apiBaseUrl + "/api/v1/offerings/" + offeringId;
        OfferingModel offeringModel = null;

        log.debug("Final API Url is " + apiQuery);
        try {
            offeringModel = restTemplate.getForObject(apiQuery, OfferingModel.class);
        } catch (Exception e) {
            log.error("Error occur while connecting to offering api at: " + apiQuery);
        }

        return offeringModel;
    }
}
