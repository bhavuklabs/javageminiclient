package io.github.bhavuklabs.javageminiclient.commons.exceptions;

/**
 * The {@code ValidationException} class is a custom exception used to indicate validation errors
 * during the processing of input data, such as requests or user inputs.
 *
 * <p>
 * This exception can be used to handle scenarios where input fails to meet specified validation
 * criteria, providing a meaningful message that explains the validation failure.
 * </p>
 *
 * <h2>Features</h2>
 * <ul>
 *     <li>Customizable error messages for detailed debugging and error reporting.</li>
 *     <li>Extends {@link Throwable}, making it flexible for error handling strategies.</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 *
 * <pre>
 * {@code
 * import io.github.venkat1701.commons.exceptions.ValidationException;
 *
 * public class ValidationExample {
 *     public static void main(String[] args) {
 *         try {
 *             validateInput(null);
 *         } catch (ValidationException e) {
 *             System.err.println("Validation failed: " + e.getMessage());
 *         }
 *     }
 *
 *     private static void validateInput(String input) throws ValidationException {
 *         if (input == null || input.isEmpty()) {
 *             throw new ValidationException("Input cannot be null or empty.");
 *         }
 *         System.out.println("Input is valid: " + input);
 *     }
 * }
 * }
 * </pre>
 *
 * <h2>Thread Safety</h2>
 * <p>
 * This class is not thread-safe if the {@link #message} field is modified outside of
 * the constructor. Ensure immutable usage for thread safety.
 * </p>
 *
 * @author Venkat
 * @version 1.0
 * @since 2024
 */
public class ValidationException extends Throwable {

    /**
     * A descriptive error message providing details about the validation error.
     */
    private final String message;

    /**
     * Constructs a new {@code ValidationException} with the specified detail message.
     *
     * @param message the detail message explaining the cause of the validation failure.
     */
    public ValidationException(String message) {
        this.message = message;
    }

    /**
     * Retrieves the detail message associated with this exception.
     *
     * @return the detail message.
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
