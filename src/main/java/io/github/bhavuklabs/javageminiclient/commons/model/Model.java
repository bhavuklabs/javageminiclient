package io.github.bhavuklabs.javageminiclient.commons.model;

import io.github.bhavuklabs.javageminiclient.commons.exceptions.ValidationException;
import io.github.bhavuklabs.javageminiclient.commons.utilities.Request;
import io.github.bhavuklabs.javageminiclient.commons.utilities.Response;

/**
 * The {@code Model} interface defines a contract for processing requests and generating responses
 * in a structured manner. Implementing classes are expected to validate incoming requests and
 * return corresponding responses.
 *
 * <p>
 * This interface provides an abstraction layer for models that perform operations based on a given
 * {@link Request} and produce a {@link Response}. It is designed to enforce standard behavior
 * for request-response processing in the application.
 * </p>
 *
 * <h2>Usage Example</h2>
 *
 * <pre>
 * {@code
 * import io.github.bhavuklabs.commons.model.Model;
 * import io.github.bhavuklabs.commons.exceptions.ValidationException;
 * import io.github.bhavuklabs.commons.utilities.Request;
 * import io.github.bhavuklabs.commons.utilities.Response;
 *
 * public class SimpleModel implements Model {
 *     @Override
 *     public Response call(Request request) throws ValidationException {
 *         // Perform request validation
 *         if (request == null || request.getBody() == null) {
 *             throw new ValidationException("Invalid request: Request or body cannot be null.");
 *         }
 *         // Process the request and return a response
 *         Response response = new Response();
 *         response.setStatusCode(200);
 *         response.setBody("Processed: " + request.getBody());
 *         return response;
 *     }
 * }
 *
 * public class ModelExample {
 *     public static void main(String[] args) {
 *         try {
 *             Model model = new SimpleModel();
 *             Request request = new Request("Hello World");
 *             Response response = model.call(request);
 *             System.out.println("Response: " + response.getBody());
 *         } catch (ValidationException e) {
 *             System.err.println("Validation failed: " + e.getMessage());
 *         }
 *     }
 * }
 * }
 * </pre>
 *
 * @author Venkat
 * @version 1.0
 * @since 2024
 */
public interface Model {

    /**
     * Processes the given {@link Request} and returns a corresponding {@link Response}.
     *
     * <p>
     * Implementing classes should validate the input request and perform operations as needed
     * to generate a response. Any validation errors or processing failures should be encapsulated
     * in a {@link ValidationException}.
     * </p>
     *
     * @param request the request object containing input data for processing.
     * @return the response object generated from the processing of the request.
     * @throws ValidationException if the request fails validation.
     */
    Response call(Request request) throws ValidationException;
}
