package br.com.jprangel.task_manager.exceptions;

public class ListAlreadyExistsException extends RuntimeException {
    public ListAlreadyExistsException(String message) {
        super(message);
    }
}
