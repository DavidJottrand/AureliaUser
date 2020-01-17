package dj.aurelia.aureliauser.handlerexceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode
public abstract class UserException extends RuntimeException{

    private HttpStatus status;

    public UserException(String message) {
        super(message);
    }
}
