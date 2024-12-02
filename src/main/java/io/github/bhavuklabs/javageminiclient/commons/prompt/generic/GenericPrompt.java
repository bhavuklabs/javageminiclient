package io.github.bhavuklabs.javageminiclient.commons.prompt.generic;

import io.github.bhavuklabs.javageminiclient.commons.prompt.RequestPrompt;
import io.github.bhavuklabs.javageminiclient.commons.prompt.ResponsePrompt;

/**
 * The {@code GenericPrompt} interface defines a generic contract for creating and managing prompts
 * with dynamic data types. It provides methods to retrieve and update the prompt content.
 *
 * <p>
 * This interface serves as a blueprint for prompt-like structures, ensuring consistency and
 * type safety across implementations such as {@link RequestPrompt}
 * and {@link ResponsePrompt}.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * import io.github.bhavuklabs.commons.prompt.generic.GenericPrompt;
 *
 * public class GenericPromptExample implements GenericPrompt<String> {
 *
 *     private String text;
 *
 *     public GenericPromptExample(String text) {
 *         this.text = text;
 *     }
 *
 *     @Override
 *     public String getText() {
 *         return text;
 *     }
 *
 *     @Override
 *     public void setText(String text) {
 *         this.text = text;
 *     }
 *
 *     public static void main(String[] args) {
 *         GenericPrompt<String> prompt = new GenericPromptExample("Initial Text");
 *         System.out.println(prompt.getText()); // Outputs: Initial Text
 *
 *         prompt.setText("Updated Text");
 *         System.out.println(prompt.getText()); // Outputs: Updated Text
 *     }
 * }
 * }
 * </pre>
 *
 * @param <Type> The type of the data encapsulated by the prompt (e.g., String, Integer, etc.).
 *
 * @author Venkat
 * @version 1.0
 * @since 2024
 */
public interface GenericPrompt<Type> {

    /**
     * Retrieves the content of the prompt.
     *
     * @return the prompt content of type {@code Type}.
     */
    public Type getText();

    /**
     * Updates the content of the prompt.
     *
     * @param text the new prompt content to be set.
     */
    public void setText(Type text);
}
