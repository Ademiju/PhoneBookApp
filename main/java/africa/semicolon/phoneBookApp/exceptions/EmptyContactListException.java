package africa.semicolon.phoneBookApp.exceptions;

public class EmptyContactListException extends ContactNotFoundException {
    public EmptyContactListException(String message) {
        super(message);
    }
}
