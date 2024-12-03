package io.github.bhavuklabs.javageminiclient.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bhavuklabs.javageminiclient.commons.exceptions.ValidationException;
import io.github.bhavuklabs.javageminiclient.commons.model.Model;
import io.github.bhavuklabs.javageminiclient.commons.prompt.ResponsePrompt;
import io.github.bhavuklabs.javageminiclient.commons.utilities.Request;
import io.github.bhavuklabs.javageminiclient.commons.utilities.commons.Content;
import io.github.bhavuklabs.javageminiclient.commons.utilities.commons.Part;
import io.github.bhavuklabs.javageminiclient.commons.utilities.request.RequestBody;
import io.github.bhavuklabs.javageminiclient.commons.utilities.response.ResponseBody;
import io.github.bhavuklabs.javageminiclient.commons.validators.generic.Validator;
import io.github.bhavuklabs.javageminiclient.response.ChatResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages interactions with chat-based generative AI APIs.
 *
 * <p>This class facilitates making HTTP requests to chat models, handling request validation,
 * processing responses, and mapping them to a structured {@link ChatResponse} object.
 *
 * <p><b>Key Features:</b>
 * <ul>
 *   <li>Supports custom HTTP headers and methods</li>
 *   <li>Performs request validation before API calls</li>
 *   <li>Robust error handling and response mapping</li>
 * </ul>
 *
 * <p><b>Usage Example:</b>
 * <pre>{@code
 * public class ChatModelDemo {
 *     public static void main(String[] args) {
 *         RestTemplate restTemplate = new RestTemplate();
 *         Validator<Request> validator = new BasicRequestValidator();
 *         ChatModel chatModel = new ChatModel(restTemplate, validator);
 *
 *         Part<String> messagePart = new Part<>(new RequestPrompt<>("Hello, AI!"));
 *         Content content = new Content(List.of(messagePart));
 *         RequestBody requestBody = new RequestBody(List.of(content));
 *
 *         ChatRequest chatRequest = new ChatRequest(
 *             "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent",
 *             "YOUR_API_KEY",
 *             requestBody
 *         ).withHeader("Content-Type", "application/json");
 *
 *         try {
 *             ChatResponse response = chatModel.call(chatRequest);
 *             System.out.println(response.getBody());
 *         } catch (ValidationException e) {
 *             System.err.println("Request validation failed: " + e.getMessage());
 *         }
 *     }
 * }
 * }</pre>
 *
 * @author Venkat
 * @version 1.1
 */
public class ChatModel implements Model {

    private final RestTemplate restTemplate;
    private final Validator<Request> validator;

    /**
     * Constructs a ChatModel with a REST template and request validator.
     *
     * @param restTemplate Spring's REST client for making HTTP requests
     * @param validator Validates incoming request objects before API calls
     */
    public ChatModel(RestTemplate restTemplate, Validator<Request> validator) {
        this.restTemplate = restTemplate;
        this.validator = validator;
    }

    /**
     * Executes a chat request to the specified API endpoint.
     *
     * @param request The chat request containing endpoint, method, headers, and body
     * @return A structured chat response from the API
     * @throws ValidationException if the request fails initial validation
     */
    @Override
    public ChatResponse call(Request request) throws ValidationException {
        this.validator.validate(request);

        try {
            HttpHeaders headers = this.generateHeaders(request.getHeaders());
            headers.remove("Authorization"); // Securely handle sensitive headers
            HttpEntity<RequestBody> requestEntity = new HttpEntity<>(request.getBody(), headers);
            HttpMethod method = HttpMethod.valueOf(request.getMethod());

            ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                    request.getEndpoint(),
                    method,
                    requestEntity,
                    String.class
            );

            return this.mapToChatResponse(responseEntity);
        } catch (RestClientException e) {
            return createErrorResponse(e);
        }
    }

    /**
     * Generates HTTP headers from the provided request headers map.
     *
     * @param requestHeaders a map of request headers.
     * @return a {@link HttpHeaders} object.
     */
    private HttpHeaders generateHeaders(Map<String, String> requestHeaders) {
        HttpHeaders headers = new HttpHeaders();
        if (requestHeaders == null || !requestHeaders.containsKey("Content-Type")) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        if (requestHeaders != null) {
            requestHeaders.forEach(headers::set);
        }
        return headers;
    }

    /**
     * Maps the API response to a {@link ChatResponse} object.
     *
     * @param responseEntity the raw API response as a {@link ResponseEntity}.
     * @return a {@link ChatResponse} object.
     */
    private ChatResponse mapToChatResponse(ResponseEntity<String> responseEntity) {
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setSuccessful(responseEntity.getStatusCode().is2xxSuccessful());
        chatResponse.setStatusCode(responseEntity.getStatusCode().value());
        Map<String, String> headers = responseEntity.getHeaders().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().isEmpty() ? "" : e.getValue().get(0)
                ));
        chatResponse.setHeaders(headers);
        chatResponse.setBody(this.mapToResponseBody(responseEntity.getBody()));
        return chatResponse;
    }

    /**
     * Maps the raw response body to a structured {@link ResponseBody} object.
     *
     * @param body the raw response body as a string.
     * @return a {@link ResponseBody} object.
     */
    private ResponseBody mapToResponseBody(String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(body);

            // Extract candidates
            List<ResponseBody.Candidate> candidates = new ArrayList<>();
            if (rootNode.has("candidates")) {
                for (JsonNode candidateNode : rootNode.get("candidates")) {
                    List<Content> contentList = parseContent(candidateNode.get("content"));
                    candidates.add(new ResponseBody.Candidate(contentList));
                }
            }

            // Extract and transform usage metadata
            ResponseBody.UsageMetadata usageMetadata = parseUsageMetadata(rootNode.get("usageMetadata"));

            // Extract model version
            String modelVersion = rootNode.path("model").asText("unknown");

            return new ResponseBody(candidates, usageMetadata, modelVersion);
        } catch (Exception e) {
            System.err.println("Error parsing response body: " + e.getMessage());
            return new ResponseBody(Collections.emptyList(), null, "unknown");
        }
    }

    /**
     * Parses the content node to extract a list of {@link Content}.
     *
     * @param contentNode the JSON node containing content information.
     * @return a list of {@link Content}.
     */
    private List<Content> parseContent(JsonNode contentNode) {
        List<Content> contentList = new ArrayList<>();
        if (contentNode != null && contentNode.has("parts")) {
            for (JsonNode partNode : contentNode.get("parts")) {
                String text = partNode.path("text").asText("");
                if (!text.isEmpty()) {
                    Part<String> part = new Part<>(new ResponsePrompt<>(text));
                    contentList.add(new Content(List.of(part)));
                }
            }
        }
        return contentList;
    }

    /**
     * Parses the usage metadata JSON node and transforms it into {@link io.github.bhavuklabs.javageminiclient.commons.utilities.response.ResponseBody.UsageMetadata}.
     *
     * @param usageMetadataNode the JSON node containing usage metadata.
     * @return a {@link io.github.bhavuklabs.javageminiclient.commons.utilities.response.ResponseBody.UsageMetadata} object.
     */
    private ResponseBody.UsageMetadata parseUsageMetadata(JsonNode usageMetadataNode) {
        if (usageMetadataNode == null || !usageMetadataNode.isObject()) {
            return new ResponseBody.UsageMetadata();
        }

        ResponseBody.UsageMetadata usageMetadata = new ResponseBody.UsageMetadata();
        usageMetadataNode.fields().forEachRemaining(entry -> {
            String key = entry.getKey();
            if (entry.getValue().isInt()) {
                int value = entry.getValue().asInt();
                usageMetadata.put(key, value);
            }
        });

        return usageMetadata;
    }

    /**
     * Creates an error response when an exception occurs during the API call.
     *
     * @param e the exception that occurred.
     * @return a {@link ChatResponse} object representing the error response.
     */
    private ChatResponse createErrorResponse(Exception e) {
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setSuccessful(false);
        chatResponse.setStatusCode(500);
        Part<String> part = new Part<>(new ResponsePrompt<>(e.getLocalizedMessage()));
        Content content = new Content(List.of(part));
        ResponseBody.Candidate candidate = new ResponseBody.Candidate(content, "ERROR", 0.0);
        chatResponse.setBody(new ResponseBody(List.of(candidate), null, "gemini-flash-1.5"));
        return chatResponse;
    }
}
