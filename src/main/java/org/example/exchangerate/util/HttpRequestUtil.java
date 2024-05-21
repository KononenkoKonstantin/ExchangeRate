package org.example.exchangerate.util;

import java.net.URI;
import java.net.http.HttpRequest;
import org.springframework.web.util.UriComponentsBuilder;

public class HttpRequestUtil {
    public static HttpRequest buildRequest(String url, String paramName, String paramValue) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);

        uriBuilder.queryParam(paramName, paramValue);

        URI uri = uriBuilder.build().toUri();

        return HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
    }
}
