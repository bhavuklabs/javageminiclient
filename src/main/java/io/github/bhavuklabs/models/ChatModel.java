
package io.github.bhavuklabs.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.venkat1701.commons.exceptions.ValidationException;
import io.github.venkat1701.commons.model.Model;
import io.github.venkat1701.commons.prompt.ResponsePrompt;
import io.github.venkat1701.commons.utilities.Request;
import io.github.venkat1701.commons.utilities.commons.Content;
import io.github.venkat1701.commons.utilities.commons.Part;
import io.github.venkat1701.commons.utilities.request.RequestBody;
import io.github.venkat1701.commons.utilities.response.ResponseBody;
import io.github.venkat1701.commons.validators.generic.Validator;
import io.github.venkat1701.response.ChatResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ChatModel
 *
 * This class is responsible for handling chat-related requests and mapping responses
 * from a REST API to a structured {@link ChatResponse} object. It uses a
 * {@link RestTemplate} to make HTTP requests and a {@link Validator} to validate
 * incoming {@link Request} objects.
 *
 * <p><b>Usage Example:</b>
 <pre>
 * {@code
 * public class ChatModelExample {
 *     public static void main(String[] args) {
 *         // Initialize dependencies
 *         RestTemplate restTemplate = new RestTemplate();
 *         ChatModel chatModel = new ChatModel(restTemplate, new BasicRequestValidator());
 *
 *         // Prepare the request data
 *         Part<String> part = new Part<>(new RequestPrompt<>("Hello, can you assist me?"));
 *         Content content = new Content(List.of(part));
 *         RequestBody requestBody = new RequestBody(List.of(content));
 *
 *         // Configure the request
 *         ChatRequest chatRequest = new ChatRequest(
 *                 "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=",
 *                 "AIzaSyXXXXXX_REPLACE_WITH_VALID_KEY",
 *                 requestBody
 *         ).withHeader("Content-Type", "application/json");
 *
 *         try {
 *             // Invoke the ChatModel
 *             ChatResponse chatResponse = chatModel.call(chatRequest);
 *
 *             // Print the response
 *             System.out.println(chatResponse.getBody());
 *         } catch (Exception e) {
 *             System.err.println("Error: " + e.getMessage());
 *         }
 *     }
 * }
 * }
 * </pre>
 *
 * <p>This class supports custom headers, multiple HTTP methods, and detailed
 * error handling.
 *
 * @author Venkat
 * @version 1.0
 */
public class ChatModel implements Model {

    private final RestTemplate restTemplate;
    private final Validator<Request> validator;

    /**
     * Constructor for ChatModel.
     *
     * @param restTemplate a {@link RestTemplate} instance for making HTTP calls.
     * @param validator    a {@link Validator} instance for validating {@link Request} objects.
     */
    public ChatModel(RestTemplate restTemplate, Validator<Request> validator) {
        this.restTemplate = restTemplate;
        this.validator = validator;
    }

    /**
     * Makes a call to the external chat API and processes the response.
     *
     * @param request the {@link Request} object containing the API request details.
     * @return a {@link ChatResponse} object containing the processed response.
     * @throws ValidationException if the request validation fails.
     */
    @Override
    public ChatResponse call(Request request) throws ValidationException {
        this.validator.validate(request);

        try {
            HttpHeaders headers = this.generateHeaders(request.getHeaders());
            headers.remove("Authorization"); // Ensure sensitive headers are handled appropriately.
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
            System.err.println("Error during API call: " + e.getMessage());
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
            JsonNode jsonNode = objectMapper.readTree(body);

            List<ResponseBody.Candidate> candidates = new ArrayList<>();
            if (jsonNode.has("candidates")) {
                JsonNode candidatesNode = jsonNode.get("candidates");
                for (JsonNode candidateNode : candidatesNode) {
                    List<Content> contentList = new ArrayList<>();
                    if (candidateNode.has("content")) {
                        JsonNode contentNode = candidateNode.get("content");
                        if (contentNode.has("parts")) {
                            for (JsonNode partNode : contentNode.get("parts")) {
                                Part<String> part = new Part<>(new ResponsePrompt<>(partNode.get("text").asText()));
                                Content content = new Content(List.of(part));
                                contentList.add(content);
                            }
                        }
                    }
                    ResponseBody.Candidate candidate = new ResponseBody.Candidate(contentList);
                    candidates.add(candidate);
                }
            }

            Map<String, Integer> usageMetadata = new HashMap<>();
            if (jsonNode.has("usageMetadata")) {
                JsonNode usageNode = jsonNode.get("usageMetadata");
                Iterator<Map.Entry<String, JsonNode>> fields = usageNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    usageMetadata.put(entry.getKey(), entry.getValue().asInt());
                }
            }
            String modelVersion = jsonNode.has("model") ? jsonNode.get("model").asText() : "unknown";
            return new ResponseBody(candidates, usageMetadata, modelVersion);
        } catch (Exception e) {
            System.err.println("Error parsing response body: " + e.getMessage());
            return new ResponseBody(new ArrayList<>(), new HashMap<>(), "unknown");
        }
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
        ResponseBody.Candidate candidate = new ResponseBody.Candidate(List.of(content));
        chatResponse.setBody(new ResponseBody(List.of(candidate), null, "gemini-flash-1.5"));
        return chatResponse;
    }
}