package io.github.bhavuklabs.javageminiclient.commons.utilities;

import io.github.bhavuklabs.javageminiclient.commons.utilities.response.ResponseBody;

import java.util.Map;

public interface Response {
    int getStatusCode();
    Map<String, String> getHeaders();
    ResponseBody getBody();
    boolean isSuccessfull();
    String getErrorMessage();
}
