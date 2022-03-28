package com.mycompany.locationapibe.apiclient;

import com.mycompany.locationapibe.domain.models.LocationModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GeocodeApiService {

    @Value("${geocode.api.baseurl}")
    private String apiBaseUrl;
    @Value("${geocode.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public GeocodeApiService(@Qualifier("restTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Cacheable(cacheNames = "geoCodeCache", key = "#locationModel.userId")
    public void setCoordinates(LocationModel locationModel) {
        final String apiQuery = buildApiQuery(locationModel);

        log.debug("Final API Url is " + apiQuery);
        try {
            GeocodeResponseWrapper geocodeResponseWrapper = restTemplate.getForObject(apiQuery, GeocodeResponseWrapper.class);
            if (geocodeResponseWrapper.getGeocodeList() != null && !geocodeResponseWrapper.getGeocodeList().isEmpty()) {
                GeocodeResponse geocodeList = geocodeResponseWrapper.getGeocodeList().get(0);

                locationModel.setLat(geocodeList.getLatitude().toString());
                locationModel.setLng(geocodeList.getLongitude().toString());
            }
        } catch (Exception e) {
            log.error("Error occur while connecting to geo code api at: " + apiQuery);
        }
    }

    private String buildApiQuery(LocationModel locationModel) {
        StringBuilder apiQuery = new StringBuilder();
        apiQuery.append(apiBaseUrl);
        apiQuery.append("?access_key=");
        apiQuery.append(apiKey);
        apiQuery.append("&query=");
        if (null != locationModel.getStreet()) {
            apiQuery.append(locationModel.getStreet());
            apiQuery.append(" ");
        }
        if (null != locationModel.getCity()) {
            apiQuery.append(locationModel.getCity());
            apiQuery.append(" ");
        }
        if (null != locationModel.getCountry()) {
            apiQuery.append(locationModel.getCountry());
        }

        return apiQuery.toString();
    }

}
