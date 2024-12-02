package io.github.bhavuklabs.commons.validators.generic;

import io.github.venkat1701.commons.exceptions.ValidationException;

public interface Validator<T> {

    void validate(T object) throws ValidationException;
}
