package io.github.bhavuklabs.javageminiclient.commons.prompt;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.bhavuklabs.javageminiclient.commons.prompt.generic.GenericPrompt;

/**
 * The {@code RequestPrompt} class is a generic implementation of the {@link GenericPrompt} interface,
 * designed to encapsulate a single piece of data (text) that represents the content of a request prompt.
 *
 * <p>
 * This class is used for representing and serializing/deserializing request prompts in a
 * structured format. It is particularly useful in scenarios where input prompts need to be
 * processed dynamically or sent as part of a request payload.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * import io.github.bhavuklabs.commons.prompt.RequestPrompt;
 *
 * public class RequestPromptExample {
 *     public static void main(String[] args) {
 *         // Create a RequestPrompt with String text
 *         RequestPrompt<String> stringPrompt = new RequestPrompt<>("Hello, how are you?");
 *         System.out.println(stringPrompt.getText()); // Outputs: Hello, how are you?
 *
 *         // Create a RequestPrompt with Integer data
 *         RequestPrompt<Integer> integerPrompt = new RequestPrompt<>(12345);
 *         System.out.println(integerPrompt.getText()); // Outputs: 12345
 *
 *         // Update the text in the RequestPrompt
 *         stringPrompt.setText("Updated text");
 *         System.out.println(stringPrompt); // Outputs: RequestPrompt{text=Updated text}
 *     }
 * }
 * }
 * </pre>
 *
 *
 * @param <Type> The type of the data encapsulated in the request prompt (e.g., String, Integer, etc.).
 *
 * @author Venkat
 * @version 1.0
 * @since 2024
 */
public class RequestPrompt<Type> implements GenericPrompt<Type> {

    /**
     * The text content of the request prompt.
     * <p>
     * This field is annotated with {@link JsonProperty} to map it to the "text" key in JSON objects.
     * </p>
     */
    @JsonProperty("text")
    private Type text;

    /**
     * Constructs a {@code RequestPrompt} with the specified text.
     *
     * @param text the content of the request prompt.
     */
    public RequestPrompt(Type text) {
        this.text = text;
    }

    /**
     * Returns the content of the request prompt.
     *
     * @return the text of the prompt.
     */
    public Type getText() {
        return text;
    }

    /**
     * Sets the content of the request prompt.
     *
     * @param text the new text to be set.
     */
    public void setText(Type text) {
        this.text = text;
    }

    /**
     * Returns a string representation of the {@code RequestPrompt}.
     *
     * @return a string in the format {@code RequestPrompt{text=<text>}}.
     */
    @Override
    public String toString() {
        return "RequestPrompt{" +
                "text=" + text.toString() +
                '}';
    }
}
