package africa.semicolon.phoneBookApp.exceptions;

public class ContactNotFoundException extends PhoneBookAppException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}
