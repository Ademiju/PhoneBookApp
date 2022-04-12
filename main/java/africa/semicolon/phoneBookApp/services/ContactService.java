package africa.semicolon.phoneBookApp.services;

import africa.semicolon.phoneBookApp.data.models.Contact;
import africa.semicolon.phoneBookApp.dtos.requests.AddContactRequest;
import africa.semicolon.phoneBookApp.dtos.responses.AddContactResponse;
import africa.semicolon.phoneBookApp.dtos.responses.FindContactResponse;
import org.apache.catalina.LifecycleState;

import java.util.List;

public interface ContactService {
    AddContactResponse saveContact(AddContactRequest newContactRequest);

    List<Contact> findContactByFirstNameOrLastNameOrMiddleName(String name);

    Contact findContactByPhoneNumber(String phoneNumber);

    List<Contact> findContactByEmailAddress(String emailAddress);

    FindContactResponse findResponseByFirstNameOrLastNameOrMiddleName(String name);

    FindContactResponse findResponseByPhoneNumber(String phoneNumber);

    void deleteContactByEmailAddress(String emailAddress);

    void deleteContactByFullName(String firstName, String lastName);

    void deleteContactByFullName(String firstName, String lastName, String middleName);

    void deleteAllContact();
}
