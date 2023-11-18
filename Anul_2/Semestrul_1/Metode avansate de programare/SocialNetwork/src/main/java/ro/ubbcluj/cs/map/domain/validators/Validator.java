package ro.ubbcluj.cs.map.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}

