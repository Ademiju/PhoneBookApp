package africa.semicolon.phoneBookApp.dtos.requests;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class AddContactRequest {

    @NonNull
    private String firstName;
    private String lastName;
    private String middleName;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String emailAddress;

}
