package br.com.jprangel.task_manager.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource message) {
        this.messageSource = message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorMessageDTO> dto = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
            dto.add(error);
        });

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ListNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleListNotFoundException(ListNotFoundException e) {
        ErrorMessageDTO error = new ErrorMessageDTO(e.getMessage(), null);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ListAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> handleListAlreadyExistsException(ListAlreadyExistsException e) {
        ErrorMessageDTO error = new ErrorMessageDTO(e.getMessage(), "name");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ListDeletionNotAllowedException.class)
    public ResponseEntity<ErrorMessageDTO> handleListDeletionNotAllowedException(ListDeletionNotAllowedException e) {
        ErrorMessageDTO error = new ErrorMessageDTO(e.getMessage(), null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleTaskNotFoundException(TaskNotFoundException e) {
        ErrorMessageDTO error = new ErrorMessageDTO(e.getMessage(), null);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
