package africa.semicolon.phoneBookApp.dtos.responses;

import africa.semicolon.phoneBookApp.dtos.requests.AddContactRequest;
import lombok.Data;

@Data
public class FindContactResponse {
    private String fullName;
    private String phoneNumber;
    private String emailAddress;
}
