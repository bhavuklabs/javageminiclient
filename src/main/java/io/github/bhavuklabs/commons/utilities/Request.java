package io.github.bhavuklabs.commons.utilities;

import io.github.venkat1701.commons.utilities.request.RequestBody;

import java.util.Map;

public interface Request {

    String getURI();
    Map<String, String> getHeaders();
    RequestBody getBody();
    String getMethod();
    boolean validate();
    String getEndpoint();
}
