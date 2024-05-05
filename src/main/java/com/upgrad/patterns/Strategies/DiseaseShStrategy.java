package com.upgrad.patterns.Strategies;

import com.upgrad.patterns.Config.RestServiceGenerator;
import com.upgrad.patterns.Entity.DiseaseShResponse;
import com.upgrad.patterns.Interfaces.IndianDiseaseStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class DiseaseShStrategy implements IndianDiseaseStat {

    private Logger logger = LoggerFactory.getLogger(DiseaseShStrategy.class);

    private RestTemplate restTemplate;

    @Value("${config.diseaseSh-io-url}")
    private String baseUrl;

    public DiseaseShStrategy()
    {
        restTemplate = RestServiceGenerator.GetInstance();
    }

    @Override
    public String GetActiveCount() {
        try {
            // Obtain response from the getDiseaseShResponseResponses() method
            // Store it in an object
            DiseaseShResponse response = getDiseaseShResponseResponses();

            //get the response using the getCases() method
            float activeCases = response.getCases();

            // Return the active cases after rounding it up to 0 decimal places
            return String.format("%.0f", Math.ceil(activeCases));
        } catch (Exception e) {
            // Log the error
            logger.error("Error fetching active count from DiseaseSh API: " + e.getMessage(), e);
            // Return null or any appropriate value indicating failure
            return null;
        }
    }

    private DiseaseShResponse getDiseaseShResponseResponses() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);


        DiseaseShResponse temp = restTemplate.exchange(
                baseUrl, HttpMethod.GET, new HttpEntity<Object>(headers),
                DiseaseShResponse.class).getBody();
        
        System.out.println(temp);
        return temp;
    }
}
