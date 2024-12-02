package io.github.bhavuklabs.commons.validators.generic;

import io.github.bhavuklabs.commons.exceptions.ValidationException;

public interface Validator<T> {

    void validate(T object) throws ValidationException;
}
