package io.github.bhavuklabs.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.venkat1701.commons.utilities.Response;
import io.github.venkat1701.commons.utilities.response.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the response from a chat request, encapsulating various response attributes such as status code,
 * headers, body, success flag, and error message.
 *
 * @author Venkat
 * @since 1.0
 */
public class ChatResponse implements Response {

    // HTTP status code indicating the result of the request (e.g., 200, 404, 500)
    private int statusCode;

    // HTTP headers for the response as key-value pairs
    private Map<String, String> headers;

    // The body of the response, typically containing data or error information
    private ResponseBody body;

    // A flag indicating whether the response was successful based on the status code
    private boolean successful;

    // A message describing any error that occurred during the request
    private String errorMessage;

    /**
     * Default constructor initializing the headers as an empty map.
     */
    public ChatResponse() {
        this.headers = new HashMap<>();
    }

    /**
     * Constructs a new ChatResponse with the specified parameters.
     *
     * @param statusCode The HTTP status code of the response.
     * @param headers The headers for the response.
     * @param body The body of the response.
     * @param successful A flag indicating whether the request was successful.
     * @param errorMessage An optional error message if the request was unsuccessful.
     */
    public ChatResponse(int statusCode, Map<String, String> headers, ResponseBody body, boolean successful, String errorMessage) {
        this.statusCode = statusCode;
        this.headers = headers != null ? new HashMap<>(headers) : new HashMap<>();
        this.body = body;
        this.successful = successful;
        this.errorMessage = errorMessage;
    }

    /**
     * Sets the status code for the response and automatically determines the success flag.
     *
     * @param statusCode The HTTP status code to set.
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        // Automatically determine success based on status code (2xx range means success)
        this.successful = statusCode >= 200 && statusCode < 300;
    }

    /**
     * Sets the headers for the response. A new map is created if the provided headers are not null.
     *
     * @param headers The headers to set.
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers != null ? new HashMap<>(headers) : new HashMap<>();
    }

    /**
     * Sets the body of the response.
     *
     * @param body The body to set.
     */
    public void setBody(ResponseBody body) {
        this.body = body;
    }

    /**
     * Sets the success flag for the response.
     *
     * @param successful The success flag to set.
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    /**
     * Sets an optional error message for the response.
     *
     * @param errorMessage The error message to set.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Returns the HTTP status code of the response.
     *
     * @return The HTTP status code.
     */
    @Override
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the headers of the response as an unmodifiable map.
     *
     * @return The headers map.
     */
    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    /**
     * Returns the body of the response.
     *
     * @return The body of the response.
     */
    @Override
    public ResponseBody getBody() {
        return body;
    }

    /**
     * Returns true if the request was successful based on the status code.
     *
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean isSuccessfull() {
        return successful;
    }

    /**
     * Returns the error message if the request was unsuccessful.
     *
     * @return The error message.
     */
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Parses the body of the response into a Java object of the specified class using Jackson.
     *
     * @param clazz The class to convert the response body to.
     * @param <T> The type of the object.
     * @return The parsed object.
     * @throws RuntimeException if the body cannot be parsed.
     */
    @JsonIgnore
    public <T> T parseBody(Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(body.toString(), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response body", e);
        }
    }

    /**
     * Builder pattern for constructing a ChatResponse object.
     */
    public static class Builder {
        private final ChatResponse response;

        public Builder() {
            response = new ChatResponse();
        }

        /**
         * Sets the HTTP status code for the response.
         *
         * @param statusCode The HTTP status code to set.
         * @return The builder instance.
         */
        public Builder statusCode(int statusCode) {
            response.setStatusCode(statusCode);
            return this;
        }

        /**
         * Sets the headers for the response.
         *
         * @param headers The headers to set.
         * @return The builder instance.
         */
        public Builder headers(Map<String, String> headers) {
            response.setHeaders(headers);
            return this;
        }

        /**
         * Sets the body for the response.
         *
         * @param body The body to set.
         * @return The builder instance.
         */
        public Builder body(ResponseBody body) {
            response.setBody(body);
            return this;
        }

        /**
         * Sets the success flag for the response.
         *
         * @param successful The success flag to set.
         * @return The builder instance.
         */
        public Builder successful(boolean successful) {
            response.setSuccessful(successful);
            return this;
        }

        /**
         * Sets the error message for the response.
         *
         * @param errorMessage The error message to set.
         * @return The builder instance.
         */
        public Builder errorMessage(String errorMessage) {
            response.setErrorMessage(errorMessage);
            return this;
        }

        /**
         * Builds and returns the constructed ChatResponse object.
         *
         * @return The constructed ChatResponse object.
         */
        public ChatResponse build() {
            return response;
        }
    }

    /**
     * Overrides the equals method for comparing two ChatResponse objects based on their attributes.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatResponse that = (ChatResponse) o;
        return statusCode == that.statusCode &&
                successful == that.successful &&
                Objects.equals(headers, that.headers) &&
                Objects.equals(body, that.body) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    /**
     * Generates the hash code based on the attributes of the response for proper hashing.
     *
     * @return The hash code for the response.
     */
    @Override
    public int hashCode() {
        return Objects.hash(statusCode, headers, body, successful, errorMessage);
    }

    /**
     * Returns a string representation of the ChatResponse object for debugging and logging purposes.
     *
     * @return The string representation of the response.
     */
    @Override
    public String toString() {
        return "ChatResponse{" +
                "statusCode=" + statusCode +
                ", headers=" + headers +
                ", body='" + (body != null ? "***" : "null") + '\'' +
                ", successful=" + successful +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    /**
     * Creates a ChatResponse object from an exception, setting the status code to 500 and including the error message.
     *
     * @param e The exception to convert into a response.
     * @return The constructed ChatResponse object.
     */
    public static ChatResponse fromException(Exception e) {
        return new Builder()
                .statusCode(500)
                .successful(false)
                .errorMessage(e.getMessage())
                .build();
    }
}
