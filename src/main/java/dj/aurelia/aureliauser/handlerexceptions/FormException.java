package dj.aurelia.aureliauser.handlerexceptions;

import org.springframework.http.HttpStatus;

public class FormException extends UserException {

    public FormException(String message) {
        super(message);
        setStatus(HttpStatus.BAD_REQUEST);
    }
}
