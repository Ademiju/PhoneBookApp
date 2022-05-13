package africa.semicolon.phoneBookApp.services;

import africa.semicolon.phoneBookApp.data.models.Contact;
import africa.semicolon.phoneBookApp.dtos.requests.AddContactRequest;
import africa.semicolon.phoneBookApp.dtos.responses.AddContactResponse;

import java.util.List;
//@Service
public interface ContactService {
    AddContactResponse saveContact(AddContactRequest newContactRequest);

    List<Contact> findContactByFirstNameOrLastNameOrMiddleName(String name);

    Contact findContactByPhoneNumber(String phoneNumber);

    Contact findContactByEmailAddress(String emailAddress);

    List<Contact> findAllContact();

    void deleteContactByEmailAddress(String emailAddress);

    void deleteContactByMobileNumber(String phoneNumber);

    void deleteAllContact();


}
