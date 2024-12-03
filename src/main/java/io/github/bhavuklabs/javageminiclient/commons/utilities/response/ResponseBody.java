package io.github.bhavuklabs.javageminiclient.commons.utilities.response;

import io.github.bhavuklabs.javageminiclient.commons.utilities.commons.Content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code ResponseBody} class represents the body of a response from an API or service.
 * It contains a list of {@link Candidate} objects, usage metadata, and the model version.
 * This class is used to structure the data returned from an API or service.
 */
public class ResponseBody {

    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;

    /**
     * Constructs a {@code ResponseBody} with the specified candidates, usage metadata, and model version.
     *
     * @param candidates    the list of {@link Candidate} objects representing response results.
     * @param usageMetadata the metadata related to usage of the service.
     * @param modelVersion  the version of the model or system.
     */
    public ResponseBody(List<Candidate> candidates, UsageMetadata usageMetadata, String modelVersion) {
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
     * @return the usage metadata object.
     */
    public UsageMetadata getUsageMetadata() {
        return usageMetadata;
    }

    /**
     * Sets the usage metadata for the response body.
     *
     * @param usageMetadata the new usage metadata.
     */
    public void setUsageMetadata(UsageMetadata usageMetadata) {
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

    @Override
    public String toString() {
        return "ResponseBody{" +
                "candidates=" + candidates +
                ", usageMetadata=" + usageMetadata +
                ", modelVersion='" + modelVersion + '\'' +
                '}';
    }

    /**
     * Represents a candidate result in the response.
     */
    public static class Candidate {
        private Content content;
        private String finishReason;
        private Double avgLogprobs;

        /**
         * Constructs a {@code Candidate} with the specified content, finish reason, and average log probabilities.
         *
         * @param content     the content of the candidate.
         * @param finishReason the reason for stopping the generation.
         * @param avgLogprobs  the average log probabilities.
         */
        public Candidate(Content content, String finishReason, Double avgLogprobs) {
            this.content = content;
            this.finishReason = finishReason;
            this.avgLogprobs = avgLogprobs;
        }

        public Candidate(List<Content> contentList) {
            this.content = contentList.get(0);
        }

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }

        public Double getAvgLogprobs() {
            return avgLogprobs;
        }

        public void setAvgLogprobs(Double avgLogprobs) {
            this.avgLogprobs = avgLogprobs;
        }

        @Override
        public String toString() {
            return "Candidate{" +
                    "content=" + content +
                    ", finishReason='" + finishReason + '\'' +
                    ", avgLogprobs=" + avgLogprobs +
                    '}';
        }
    }

    /**
     * Represents usage metadata for the response.
     */
    public static class UsageMetadata {
        private Map<String, Integer> tokenCounts;

        public UsageMetadata() {
            this.tokenCounts = new HashMap<>();
        }

        public UsageMetadata(Map<String, Integer> tokenCounts) {
            this.tokenCounts = tokenCounts;
        }

        public Map<String, Integer> getTokenCounts() {
            return tokenCounts;
        }

        public void setTokenCounts(Map<String, Integer> tokenCounts) {
            this.tokenCounts = tokenCounts;
        }

        public void put(String key, int value) {
            this.tokenCounts.put(key, value);
        }

        public int get(String key) {
            return this.tokenCounts.getOrDefault(key, 0);
        }

        @Override
        public String toString() {
            return "UsageMetadata{" +
                    "tokenCounts=" + tokenCounts +
                    '}';
        }
    }

}
