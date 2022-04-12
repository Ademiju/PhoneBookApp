package africa.semicolon.phoneBookApp.services;

import africa.semicolon.phoneBookApp.data.models.Contact;
import africa.semicolon.phoneBookApp.data.repositories.ContactRepository;
import africa.semicolon.phoneBookApp.dtos.requests.AddContactRequest;
import africa.semicolon.phoneBookApp.dtos.responses.AddContactResponse;
import africa.semicolon.phoneBookApp.dtos.responses.FindContactResponse;
import africa.semicolon.phoneBookApp.exceptions.PhoneBookAppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository database;
    @Override
    public AddContactResponse saveContact(AddContactRequest newContactRequest) {
        if(phoneNumberExist(newContactRequest.getPhoneNumber()))
            throw new PhoneBookAppException("Phone number Already Exist");
        if(phoneEmailExist(newContactRequest.getEmailAddress()))
            throw new PhoneBookAppException("Email Address Already Exist");
        String firstName = newContactRequest.getFirstName();
        String lastName = newContactRequest.getLastName();
        String middleName = newContactRequest.getMiddleName();
        String email = newContactRequest.getEmailAddress();
        String mobile = newContactRequest.getPhoneNumber();
        Contact newContact = new Contact(firstName,email, mobile);
        newContact.setLastName(lastName);
        newContact.setMiddleName(middleName);
        database.save(newContact);
        AddContactResponse contactResponse = new AddContactResponse();
        contactResponse.setFullName(firstName+" "+middleName+" "+lastName);
        contactResponse.setPhoneNumber(mobile);

        return contactResponse;
    }

    private boolean phoneEmailExist(String emailAddress) {
        Contact contact = database.findContactByEmailAddress(emailAddress);
        return contact != null;
    }

    private boolean phoneNumberExist(String phoneNumber) {
        Contact contact = database.findContactByPhoneNumber(phoneNumber);
        return contact != null;
    }

    @Override
    public List<Contact> findContactByFirstNameOrLastNameOrMiddleName(String name) {

        return null;
    }

    @Override
    public Contact findContactByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public List<Contact> findContactByEmailAddress(String emailAddress) {
        return null;
    }

    @Override
    public FindContactResponse findResponseByFirstNameOrLastNameOrMiddleName(String name) {
        return null;
    }

    @Override
    public FindContactResponse findResponseByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public void deleteContactByEmailAddress(String emailAddress) {

    }

    @Override
    public void deleteContactByFullName(String firstName, String lastName) {

    }

    @Override
    public void deleteContactByFullName(String firstName, String lastName, String middleName) {

    }

    @Override
    public void deleteAllContact() {

    }
}
