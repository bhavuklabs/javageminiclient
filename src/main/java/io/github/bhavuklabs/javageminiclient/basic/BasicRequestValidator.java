
package io.github.bhavuklabs.javageminiclient.basic;

import io.github.bhavuklabs.javageminiclient.commons.exceptions.ValidationException;
import io.github.bhavuklabs.javageminiclient.commons.utilities.Request;
import io.github.bhavuklabs.javageminiclient.commons.validators.RequestValidator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * BasicRequestValidator
 *
 * This class implements the RequestValidator interface to validate
 * various properties of a Request object, such as URI, headers, and HTTP methods.
 *
 * @author Venkat
 * @version: 1.0
 */
public class BasicRequestValidator implements RequestValidator {

    private static final int MAX_BODY_SIZE = 1024 * 1024; // Maximum allowed body size in bytes (1 MB).

    /**
     * Validates the given Request object.
     *
     * @param object the Request object to validate.
     * @throws ValidationException if any validation rule is violated.
     */
    @Override
    public void validate(Request object) throws ValidationException {
        this.validateURI(object.getURI());
        this.validateHeaders(object.getHeaders());
        this.validateMethod(object.getMethod());
    }

    /**
     * Validates the URI of the request.
     *
     * @param uriString the URI as a string.
     * @throws ValidationException if the URI is null, empty, or invalid.
     */
    private void validateURI(String uriString) throws ValidationException {
        if (uriString == null || uriString.isEmpty()) {
            throw new ValidationException("URI is null or empty");
        }

        try {
            URI uri = new URI(uriString);
            String domain = uri.getHost();

            if (domain == null) {
                throw new ValidationException("Unauthorized domain: " + domain);
            }
        } catch (URISyntaxException e) {
            throw new ValidationException("Invalid URI format: " + uriString);
        }
    }

    /**
     * Validates the HTTP method of the request.
     *
     * @param method the HTTP method as a string.
     * @throws ValidationException if the method is null or empty.
     */
    private void validateMethod(String method) throws ValidationException {
        if (method == null || method.isEmpty()) {
            throw new ValidationException("Method is null or empty");
        }
    }

    /**
     * Validates the headers of the request.
     *
     * @param headers a map of header key-value pairs.
     * @throws ValidationException if the Content-Type header is missing or invalid.
     */
    private void validateHeaders(Map<String, String> headers) throws ValidationException {
        String contentType = headers.get("Content-Type");
        if (contentType == null || !contentType.startsWith("application/json")) {
            throw new ValidationException("Invalid or missing Content-Type header");
        }
    }
}
