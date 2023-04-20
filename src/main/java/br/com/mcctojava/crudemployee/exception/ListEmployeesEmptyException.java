package br.com.mcctojava.crudemployee.exception;

public class ListEmployeesEmptyException extends RuntimeException {

    public ListEmployeesEmptyException(String message) {
        super(message);
    }
}
