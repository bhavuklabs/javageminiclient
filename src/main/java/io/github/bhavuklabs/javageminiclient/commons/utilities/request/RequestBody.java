package io.github.bhavuklabs.javageminiclient.commons.utilities.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.bhavuklabs.javageminiclient.commons.utilities.commons.Content;

import java.util.List;

/**
 * The {@code RequestBody} class represents the body of a request that contains a list of {@link Content} objects.
 * This class is used to structure the data sent in a request, typically for systems that involve communication
 * between client and server for services like chat or messaging.
 *
 * <p>
 * This class encapsulates a list of {@link Content} objects, which are used as the main data in the request body.
 * It is used in request-building processes where content is structured in parts (for example, prompts or responses).
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * import io.github.bhavuklabs.commons.utilities.commons.Content;
 * import io.github.bhavuklabs.commons.utilities.request.RequestBody;
 *
 * public class RequestBodyExample {
 *     public static void main(String[] args) {
 *         List<Content> contents = new ArrayList<>();
 *         contents.add(new Content(...));  // Add Content instances
 *         RequestBody requestBody = new RequestBody(contents);
 *         System.out.println(requestBody);
 *     }
 * }
 * }
 * </pre>
 *
 * @author Venkat
 * @version 1.0
 */
public class RequestBody {

    @JsonProperty("contents")
    private List<Content> contents;

    /**
     * Constructs a {@code RequestBody} with the given list of {@link Content}.
     *
     * @param contents a list of {@link Content} objects to include in the request body.
     */
    public RequestBody(List<Content> contents) {
        this.contents = contents;
    }

    /**
     * Retrieves the list of {@link Content} objects in the request body.
     *
     * @return the list of {@link Content} objects.
     */
    public List<Content> getContents() {
        return contents;
    }

    /**
     * Sets the list of {@link Content} objects for this request body.
     *
     * @param contents the new list of {@link Content} objects.
     */
    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    /**
     * Returns a string representation of this request body.
     *
     * @return a string representation of the request body.
     */
    @Override
    public String toString() {
        return "RequestBody{" +
                "contents=" + contents +
                '}';
    }
}
