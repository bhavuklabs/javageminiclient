package io.github.bhavuklabs.javageminiclient.commons.utilities.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.bhavuklabs.javageminiclient.commons.prompt.generic.GenericPrompt;

/**
 * The {@code Part} class represents a single segment of content, typically used as part of
 * larger content structures such as prompts or responses in a chat or messaging system.
 * This class holds a text-based value, which can either be directly provided as a string or
 * derived from a {@link GenericPrompt}.
 *
 * <p>
 * This class is primarily used to encapsulate a portion of content, which is often text-based.
 * It is used in scenarios like chat applications, prompts, and responses, where multiple parts
 * of content need to be grouped or processed.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * import io.github.bhavuklabs.commons.utilities.commons.Part;
 * import io.github.bhavuklabs.commons.prompt.generic.GenericPrompt;
 *
 * public class PartExample {
 *     public static void main(String[] args) {
 *         Part<String> part = new Part<>("Sample text content");
 *         System.out.println(part.getText());  // Outputs: Sample text content
 *     }
 * }
 * }
 * </pre>
 *
 * @param <Type> The type of the prompt from which the text can be derived.
 * @author Venkat
 * @version 1.0
 * @since 2024
 */
public class Part<Type> {

    @JsonProperty("text")
    private String text;

    /**
     * Constructs a {@code Part} object with the text derived from the provided {@link GenericPrompt}.
     *
     * @param prompt a {@link GenericPrompt} object whose text will be used to initialize the part.
     */
    public Part(GenericPrompt<Type> prompt) {
        this.text = prompt.getText().toString();
    }

    /**
     * Constructs a {@code Part} object with the provided plain text string.
     *
     * @param text the text to be stored in this part.
     */
    public Part(String text) {
        this.text = text;
    }

    /**
     * Retrieves the text stored in this part.
     *
     * @return the text associated with this part.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text for this part.
     *
     * @param text the new text to be associated with this part.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns a string representation of the {@code Part} object.
     * The string includes the text stored in this part, useful for debugging or logging.
     *
     * @return a string representing the part.
     */
    @Override
    public String toString() {
        return "Part{text='" + text + "'}";
    }
}
