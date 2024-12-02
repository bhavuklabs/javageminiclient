package io.github.bhavuklabs.commons.utilities.response;

import io.github.venkat1701.commons.utilities.commons.Content;

import java.util.List;
import java.util.Map;

/**
 * The {@code ResponseBody} class represents the body of a response from an API or service.
 * It contains a list of {@link Candidate} objects, usage metadata, and the model version. This class is
 * used to structure the data returned from an API or service, typically containing results or information
 * about the service's execution.
 *
 * <p>
 * The {@code ResponseBody} class is used to store and manage the response data received from services.
 * It contains candidate results (a list of {@link Content} objects), metadata about the usage,
 * and version information about the model or system returning the response.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * import io.github.venkat1701.commons.utilities.commons.Content;
 * import io.github.venkat1701.commons.utilities.response.ResponseBody;
 *
 * public class ResponseBodyExample {
 *     public static void main(String[] args) {
 *         List<Content> contentList = new ArrayList<>();
 *         Map<String, Integer> usageData = Map.of("requests", 5);
 *         ResponseBody responseBody = new ResponseBody(contentList, usageData, "v1.0");
 *         System.out.println(responseBody);
 *     }
 * }
 * }
 * </pre>
 *
 * @author Venkat
 * @since 1.0
 */
public class ResponseBody {

    private List<Candidate> candidates;
    private Map<String, Integer> usageMetadata;
    private String modelVersion;

    /**
     * Constructs a {@code ResponseBody} with the specified list of candidates, usage metadata, and model version.
     *
     * @param candidates the list of {@link Candidate} objects representing the response results.
     * @param usageMetadata the metadata related to usage of the service.
     * @param modelVersion the version of the model or system that processed the request.
     */
    public ResponseBody(List<Candidate> candidates, Map<String, Integer> usageMetadata, String modelVersion) {
        this.candidates = candidates;
        this.usageMetadata = usageMetadata;
        this.modelVersion = modelVersion;
    }

    /**
     * Retrieves the list of {@link Candidate} objects in the response body.
     *
     * @return the list of {@link Candidate} objects.
     */
    public List<Candidate> getCandidates() {
        return candidates;
    }

    /**
     * Sets the list of {@link Candidate} objects in the response body.
     *
     * @param candidates the new list of {@link Candidate} objects.
     */
    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    /**
     * Retrieves the usage metadata in the response body.
     *
     * @return a map containing usage metadata.
     */
    public Map<String, Integer> getUsageMetadata() {
        return usageMetadata;
    }

    /**
     * Sets the usage metadata for the response body.
     *
     * @param usageMetadata the new usage metadata.
     */
    public void setUsageMetadata(Map<String, Integer> usageMetadata) {
        this.usageMetadata = usageMetadata;
    }

    /**
     * Retrieves the model version associated with the response.
     *
     * @return the version of the model or system.
     */
    public String getModelVersion() {
        return modelVersion;
    }

    /**
     * Sets the model version for the response body.
     *
     * @param modelVersion the new model version.
     */
    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    /**
     * Returns a string representation of this response body.
     *
     * @return a string representing the response body.
     */
    @Override
    public String toString() {
        return "ResponseBody{" +
                "candidates=" + candidates +
                ", usageMetadata=" + usageMetadata +
                ", modelVersion='" + modelVersion + '\'' +
                '}';
    }

    /**
     * The {@code Candidate} class represents an individual candidate in the response body.
     * Each candidate contains a list of {@link Content} objects, which represent parts of the candidate response.
     */
    public static class Candidate {
        private List<Content> candidates;

        /**
         * Constructs a {@code Candidate} with the specified list of {@link Content} objects.
         *
         * @param candidates a list of {@link Content} objects representing the candidate's response.
         */
        public Candidate(List<Content> candidates) {
            this.candidates = candidates;
        }

        /**
         * Retrieves the list of {@link Content} objects for this candidate.
         *
         * @return the list of {@link Content} objects.
         */
        public List<Content> getCandidates() {
            return candidates;
        }

        /**
         * Sets the list of {@link Content} objects for this candidate.
         *
         * @param candidates the new list of {@link Content} objects.
         */
        public void setCandidates(List<Content> candidates) {
            this.candidates = candidates;
        }

        /**
         * Returns a string representation of this candidate.
         *
         * @return a string representing the candidate.
         */
        @Override
        public String toString() {
            return "Candidate{" +
                    "candidates=" + candidates +
                    '}';
        }
    }
}
