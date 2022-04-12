package africa.semicolon.phoneBookApp.dtos.requests;

import lombok.Data;

@Data
public class AddContactRequest {

    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private String emailAddress;

}
