package io.github.bhavuklabs.javageminiclient.client;

import io.github.bhavuklabs.javageminiclient.models.ChatModel;

/**
 * The {@code ChatClient} class serves as a client for interacting with the {@link ChatModel}.
 * It provides getter and setter methods to manage an instance of {@link ChatModel}.
 *
 * <p>
 * This class is useful for scenarios where dependency injection or configuration management
 * is required to initialize and use the {@code ChatModel}.
 * </p>
 *
 * <h2>Example Usage</h2>
 *
 * <pre>
 * {@code
 * import io.github.bhavuklabs.models.ChatModel;
 * import io.github.bhavuklabs.client.ChatClient;
 * import org.springframework.web.client.RestTemplate;
 * import io.github.bhavuklabs.commons.validators.generic.Validator;
 * import io.github.bhavuklabs.basic.BasicRequestValidator;
 *
 * public class ChatClientExample {
 *     public static void main(String[] args) {
 *         // Initialize dependencies
 *         RestTemplate restTemplate = new RestTemplate();
 *         Validator<Request> validator = new BasicRequestValidator();
 *         ChatModel chatModel = new ChatModel(restTemplate, validator);
 *
 *         // Configure ChatClient
 *         ChatClient chatClient = new ChatClient();
 *         chatClient.setChatModel(chatModel);
 *
 *         // Access the ChatModel
 *         ChatModel clientModel = chatClient.getChatModel();
 *
 *         // Further actions can be performed with the ChatModel instance
 *         System.out.println("ChatModel successfully set in ChatClient!");
 *     }
 * }
 * }
 * </pre>
 *
 * @author Venkat
 * @version 1.0
 * @since 2024
 */
public class ChatClient {

    /**
     * An instance of {@link ChatModel} that this client interacts with.
     */
    private ChatModel chatModel;

    /**
     * Gets the {@link ChatModel} instance currently associated with this client.
     *
     * @return the current {@code ChatModel} instance, or {@code null} if none has been set.
     */
    public ChatModel getChatModel() {
        return chatModel;
    }

    /**
     * Sets the {@link ChatModel} instance for this client.
     *
     * @param chatModel the {@code ChatModel} instance to associate with this client.
     */
    public void setChatModel(ChatModel chatModel) {
        this.chatModel = chatModel;
    }
}
