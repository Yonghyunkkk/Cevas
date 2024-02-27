package cevas.backend.exception;

import lombok.Getter;

public class CustomException extends RuntimeException{

    @Getter
    private final ErrorInfo errorInfo;

    public CustomException(ErrorInfo errorInfo) {
        super(errorInfo.getMessage());
        this.errorInfo = errorInfo;
    }
}
