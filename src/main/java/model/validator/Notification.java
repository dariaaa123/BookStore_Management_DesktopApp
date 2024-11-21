package model.validator;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Notification<T> {
    private T result;
    private final List<String> errors;

    public Notification()
    {
        this.errors = new ArrayList<>();
    }
    public void addError (String error)
    {
        this.errors.add(error);
    }
    public boolean hasError()
    {
        return !this.errors.isEmpty();
    }

    public void setResult(T result)
    {
        this.result = result;
    }
    public T getResult()
    {
        return result;
    }
    public String getFormattedErrors()
    {
        return String.join("\n",errors);
    }

}
