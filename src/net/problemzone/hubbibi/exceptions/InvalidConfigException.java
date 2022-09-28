package net.problemzone.hubbibi.exceptions;

// TODO: Switch to checked exception?
public class InvalidConfigException extends RuntimeException {

    public InvalidConfigException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public InvalidConfigException(String errorMessage) {
        super(errorMessage);
    }

}
