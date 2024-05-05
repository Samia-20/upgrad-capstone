package com.upgrad.patterns.Strategies;

import com.upgrad.patterns.Config.RestServiceGenerator;
import com.upgrad.patterns.Entity.JohnHopkinResponse;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JohnHopkinsStrategy implements IndianDiseaseStat {

	private Logger logger = LoggerFactory.getLogger(JohnHopkinsStrategy.class);

	private RestTemplate restTemplate;

	@Value("${config.john-hopkins-url}")
	private String baseUrl;

	public JohnHopkinsStrategy() {
		restTemplate = RestServiceGenerator.GetInstance();
	}

	@Override
	public String GetActiveCount() {
		try {
			// Get response from the getJohnHopkinResponses method
			JohnHopkinResponse[] responses = getJohnHopkinResponses();

			// Filter the data where country equals "India"
			List<JohnHopkinResponse> indiaData = Arrays.stream(responses)
					.filter(response -> "India".equalsIgnoreCase(response.getCountry()))
					.collect(Collectors.toList());

			// Map the data to "confirmed" values
			List<Float> confirmedValues = indiaData.stream()
					.map(response -> response.getStats().getConfirmed())
					.collect(Collectors.toList());

			// Reduce the data to get a sum of all the "confirmed" values
			Float totalConfirmedCases = confirmedValues.stream()
					.reduce(0.0F, Float::sum);

			// Return the total confirmed cases after rounding it up to 0 decimal places
			return String.format("%.0f", Math.ceil(totalConfirmedCases));
		} catch (Exception e) {
			// Log the error
			logger.error("Error fetching active count from John Hopkins API: " + e.getMessage(), e);
			// Return null or any appropriate value indicating failure
			return null;
		}
	}

	private JohnHopkinResponse[] getJohnHopkinResponses() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);


		return restTemplate.exchange(
				baseUrl, HttpMethod.GET, new HttpEntity<Object>(headers),
				JohnHopkinResponse[].class).getBody();
	}
}
