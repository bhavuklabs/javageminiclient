package io.github.bhavuklabs.javageminiclient.commons.utilities.commons;

import java.util.List;

/**
 * The {@code Content} class represents the content structure used in prompts and responses.
 * It holds a list of {@link Part} objects, which can represent individual pieces of content
 * that are part of a larger response or request.
 *
 * <p>
 * This class encapsulates a collection of {@link Part} objects. Each {@code Part} can hold
 * specific data or text that contributes to the overall content. This structure is commonly
 * used in message or prompt formats, especially in natural language processing or chat-related
 * systems.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * import io.github.venkat1701.commons.utilities.commons.Content;
 * import io.github.venkat1701.commons.utilities.commons.Part;
 * import java.util.List;
 *
 * public class ContentExample {
 *     public static void main(String[] args) {
 *         Part<String> part1 = new Part<>("Part 1 content");
 *         Part<String> part2 = new Part<>("Part 2 content");
 *         Content content = new Content(List.of(part1, part2));
 *
 *         System.out.println(content.getParts()); // Outputs: [Part 1 content, Part 2 content]
 *     }
 * }
 * }
 * </pre>
 *
 * @author Venkat
 * @version 1.0
 * @since 2024
 */
public class Content {

    private List<Part> parts;

    /**
     * Constructs a {@code Content} object with the specified list of {@link Part} objects.
     *
     * @param parts a list of {@link Part} objects to be associated with this content.
     */
    public Content(List<Part> parts) {
        this.parts = parts;
    }

    /**
     * Retrieves the list of {@link Part} objects associated with this content.
     *
     * @return a list of {@link Part} objects.
     */
    public List<Part> getParts() {
        return parts;
    }

    /**
     * Sets the list of {@link Part} objects for this content.
     *
     * @param parts a list of {@link Part} objects to be associated with this content.
     */
    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    /**
     * Returns a string representation of the {@code Content} object.
     * The string contains the content parts.
     *
     * @return a string representing the content.
     */
    @Override
    public String toString() {
        return "Content{" +
                "parts=" + parts +
                '}';
    }
}
