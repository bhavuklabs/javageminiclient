package io.github.bhavuklabs.javageminiclient.commons.prompt;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.bhavuklabs.javageminiclient.commons.prompt.generic.GenericPrompt;

/**
 * The {@code ResponsePrompt} class is a generic implementation of the {@link GenericPrompt} interface,
 * designed to represent a structured response prompt with dynamic data types.
 *
 * <p>
 * This class is used to encapsulate the response prompt text and provides an API to access and modify it.
 * It is particularly useful in scenarios where prompts are part of response payloads in a structured format.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * import io.github.bhavuklabs.commons.prompt.ResponsePrompt;
 *
 * public class ResponsePromptExample {
 *     public static void main(String[] args) {
 *         // Create a ResponsePrompt with String data
 *         ResponsePrompt<String> stringResponse = new ResponsePrompt<>("I can help you with that!");
 *         System.out.println(stringResponse.getText()); // Outputs: I can help you with that!
 *
 *         // Create a ResponsePrompt with Integer data
 *         ResponsePrompt<Integer> integerResponse = new ResponsePrompt<>(12345);
 *         System.out.println(integerResponse.getText()); // Outputs: 12345
 *
 *         // Update the text in the ResponsePrompt
 *         stringResponse.setText("Sure, here’s more information.");
 *         System.out.println(stringResponse); // Outputs: ResponsePrompt{prompt=Sure, here’s more information.}
 *     }
 * }
 * }
 * </pre>
 *
 * @param <Type> The type of the data encapsulated in the response prompt (e.g., String, Integer, etc.).
 *
 * @author Venkat
 * @version 1.0
 * @since 2024
 */
public class ResponsePrompt<Type> implements GenericPrompt<Type> {

    /**
     * The prompt content of the response.
     * <p>
     * This field is annotated with {@link JsonProperty} to map it to the "text" key in JSON objects.
     * </p>
     */
    @JsonProperty("text")
    private Type prompt;

    /**
     * Constructs a {@code ResponsePrompt} with the specified prompt.
     *
     * @param prompt the content of the response prompt.
     */
    public ResponsePrompt(Type prompt) {
        this.prompt = prompt;
    }

    /**
     * Returns the content of the response prompt.
     *
     * @return the prompt text.
     */
    public Type getText() {
        return prompt;
    }

    /**
     * Sets the content of the response prompt.
     *
     * @param prompt the new prompt text to be set.
     */
    public void setText(Type prompt) {
        this.prompt = prompt;
    }

    /**
     * Returns a string representation of the {@code ResponsePrompt}.
     *
     * @return a string in the format {@code ResponsePrompt{prompt=<prompt>}}.
     */
    @Override
    public String toString() {
        return "ResponsePrompt{" +
                "prompt=" + prompt +
                '}';
    }
}
