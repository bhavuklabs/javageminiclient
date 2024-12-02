package io.github.bhavuklabs.commons.utilities;

import io.github.bhavuklabs.commons.utilities.request.RequestBody;

import java.util.Map;

public interface Request {

    String getURI();
    Map<String, String> getHeaders();
    RequestBody getBody();
    String getMethod();
    boolean validate();
    String getEndpoint();
}
