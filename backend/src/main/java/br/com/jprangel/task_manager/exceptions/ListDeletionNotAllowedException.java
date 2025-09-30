package br.com.jprangel.task_manager.exceptions;

public class ListDeletionNotAllowedException extends RuntimeException {
    public ListDeletionNotAllowedException(String message) {
        super(message);
    }
}