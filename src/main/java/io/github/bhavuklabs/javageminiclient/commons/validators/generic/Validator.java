package io.github.bhavuklabs.javageminiclient.commons.validators.generic;

import io.github.bhavuklabs.javageminiclient.commons.exceptions.ValidationException;

public interface Validator<T> {

    void validate(T object) throws ValidationException;
}
