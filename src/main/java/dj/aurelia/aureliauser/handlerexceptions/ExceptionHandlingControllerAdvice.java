package dj.aurelia.aureliauser.handlerexceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException ex) {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("status", ex.getStatus().value() + "");
        return ResponseEntity.status(ex.getStatus()).body(map);
    }



}
