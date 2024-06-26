package org.example.exchangerate.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.exchangerate.dto.external.CurrencyExternalDto;
import org.example.exchangerate.exception.RemoteServiceNotAvailableException;
import org.example.exchangerate.service.CurrencyExternalDataService;
import org.example.exchangerate.util.DateUtils;
import org.example.exchangerate.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CurrencyExternalDataServiceImpl implements CurrencyExternalDataService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyExternalDataServiceImpl.class);
    private static final String DATE_PARAM = "date";
    @Value("${api.url}")
    private String url;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Override
    public List<CurrencyExternalDto> loadDataFromExternalApiByDate(LocalDate date) {
        String dateValue = DateUtils.formatDateToCompactString(date.toString());
        HttpRequest request = HttpRequestUtil.buildRequest(url, DATE_PARAM, dateValue);

        try {
            logger.debug("Sending request to external API: {}", request.uri());
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

            logger.debug("Received response from external API: {}", response);

            CurrencyExternalDto[] responseDtos = objectMapper.readValue(
                    response.body(),
                    CurrencyExternalDto[].class);

            logger.debug("Map response json to CurrencyExternalDto");

            return Arrays.asList(responseDtos);
        } catch (IOException | InterruptedException e) {
            throw new RemoteServiceNotAvailableException("Can't get data from remote service. Please try again later");
        }
    }
}
