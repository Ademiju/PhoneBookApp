package africa.semicolon.phoneBookApp.data.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Document("Phonebook")
public class Contact {

    @Id
    private String id;
    @NonNull
    private String firstName;
    private String lastName;
    private String middleName;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String emailAddress;
}
