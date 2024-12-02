package io.github.bhavuklabs.request;

import io.github.bhavuklabs.commons.utilities.Request;
import io.github.bhavuklabs.commons.utilities.request.RequestBody;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents an HTTP request used for interacting with the chat service API.
 * This class encapsulates the necessary details of the request including the URI,
 * HTTP method, headers, parameters, and body. It implements the {@link Request} interface.
 *
 * @author Venkat
 * @since 1.0
 */
public class ChatRequest implements Request {

    private final String uri;                      // The URI of the request.
    private final String method;                   // The HTTP method of the request (e.g., "GET", "POST").
    private final Map<String, String> headers;     // The headers to be included in the request.
    private final Map<String, String> parameters;  // The parameters for the request URI.
    private final RequestBody body;                // The body of the request.

    /**
     * Constructor to create a ChatRequest with all components (URI, method, headers, parameters, body).
     *
     * @param uri The URI for the request.
     * @param method The HTTP method (GET, POST, etc.).
     * @param headers A map of headers for the request.
     * @param parameters A map of parameters to be appended to the URI.
     * @param body The body of the request (used for methods like POST, PUT, etc.).
     */
    public ChatRequest(String uri, String method, Map<String, String> headers,
                       Map<String, String> parameters, RequestBody body) {
        this.uri = Objects.requireNonNull(uri, "URI cannot be null");  // Ensures URI is non-null.
        this.method = Objects.requireNonNull(method, "HTTP method cannot be null").toUpperCase(); // Ensures method is non-null and converted to uppercase.
        this.headers = headers != null ? new HashMap<>(headers) : new HashMap<>();  // Initializes headers if provided, else empty map.
        this.parameters = parameters != null ? new HashMap<>(parameters) : new HashMap<>();  // Initializes parameters if provided, else empty map.
        this.body = body; // Body can be null for certain methods (like GET).
    }

    /**
     * Constructor for GET requests where the URI is combined with an API key.
     *
     * @param uri The base URI.
     * @param key The API key to be appended to the URI.
     */
    public ChatRequest(String uri, String key) {
        this(uri + key, "GET", null, null, null);  // Uses the GET method with no body or additional parameters.
    }

    /**
     * Constructor for POST requests, where URI is combined with the API key and a body is provided.
     *
     * @param uri The base URI.
     * @param key The API key.
     * @param body The body content for the POST request.
     */
    public ChatRequest(String uri, String key, RequestBody body) {
        this(uri + key, "POST", null, null, body);  // Uses the POST method with a body.
    }

    // Getter methods for retrieving details about the request:

    /**
     * @return The URI of the request.
     */
    @Override
    public String getURI() {
        return uri;
    }

    /**
     * @return A map of the headers associated with the request.
     */
    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);  // Returns an unmodifiable map of headers.
    }

    /**
     * @return The body of the request, which may be null for methods like GET.
     */
    @Override
    public RequestBody getBody() {
        return body;
    }

    /**
     * @return The HTTP method for the request (e.g., "GET", "POST").
     */
    @Override
    public String getMethod() {
        return method;
    }

    /**
     * Validates the request based on its method type and components.
     *
     * @return True if the request is valid, otherwise false.
     */
    @Override
    public boolean validate() {
        switch (method) {
            case "GET":
                return !uri.isEmpty();  // For GET requests, URI must not be empty.
            case "POST":
            case "PUT":
            case "PATCH":
                return !uri.isEmpty() && body != null;  // For POST/PUT/PATCH, URI and body must be present.
            case "DELETE":
                return !uri.isEmpty();  // DELETE requests require a non-empty URI.
            default:
                return false;  // Invalid method.
        }
    }

    /**
     * Constructs the full endpoint URI with parameters if they exist.
     *
     * @return The full URI, including any parameters encoded in the query string.
     */
    @Override
    public String getEndpoint() {
        if (parameters.isEmpty()) {
            return uri;  // Return URI if there are no parameters.
        }
        // If parameters exist, encode them and append to the URI.
        String encodedParams = parameters.entrySet().stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) +
                        "=" +
                        URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        return uri + "?" + encodedParams;
    }

    // Methods for modifying the request by adding headers, parameters, or body:

    /**
     * Creates a new ChatRequest with an added header.
     *
     * @param key The header key.
     * @param value The header value.
     * @return A new ChatRequest with the added header.
     */
    public ChatRequest withHeader(String key, String value) {
        Map<String, String> newHeaders = new HashMap<>(this.headers);  // Copy current headers.
        newHeaders.put(key, value);  // Add the new header.
        return new ChatRequest(this.uri, this.method, newHeaders, this.parameters, this.body);
    }

    /**
     * Creates a new ChatRequest with an added parameter.
     *
     * @param key The parameter key.
     * @param value The parameter value.
     * @return A new ChatRequest with the added parameter.
     */
    public ChatRequest withParameter(String key, String value) {
        Map<String, String> newParameters = new HashMap<>(this.parameters);  // Copy current parameters.
        newParameters.put(key, value);  // Add the new parameter.
        return new ChatRequest(this.uri, this.method, this.headers, newParameters, this.body);
    }

    /**
     * Creates a new ChatRequest with a modified body.
     *
     * @param body The new body for the request.
     * @return A new ChatRequest with the updated body.
     */
    public ChatRequest withBody(RequestBody body) {
        return new ChatRequest(this.uri, this.method, this.headers, this.parameters, body);  // Return a new request with the modified body.
    }

    // Overridden methods for equality comparison and string representation:

    /**
     * Compares the current ChatRequest with another object for equality.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Check if same object.
        if (o == null || getClass() != o.getClass()) return false;  // Check if same class type.
        ChatRequest that = (ChatRequest) o;  // Cast object to ChatRequest.
        return Objects.equals(uri, that.uri) &&
                Objects.equals(method, that.method) &&
                Objects.equals(headers, that.headers) &&
                Objects.equals(parameters, that.parameters) &&
                Objects.equals(body, that.body);  // Check for deep equality of all fields.
    }

    /**
     * Generates a hash code for the ChatRequest object.
     *
     * @return The hash code of the ChatRequest.
     */
    @Override
    public int hashCode() {
        return Objects.hash(uri, method, headers, parameters, body);  // Compute hash code based on fields.
    }

    /**
     * Returns a string representation of the ChatRequest object.
     *
     * @return A string containing details of the ChatRequest.
     */
    @Override
    public String toString() {
        return "ChatRequest{" +
                "uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", parameters=" + parameters +
                ", body='" + (body != null ? "***" : "null") + '\'' +  // Body is hidden for security reasons.
                '}';
    }
}
