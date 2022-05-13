package africa.semicolon.phoneBookApp.services;

import africa.semicolon.phoneBookApp.data.models.Contact;
import africa.semicolon.phoneBookApp.data.repositories.ContactRepository;
import africa.semicolon.phoneBookApp.dtos.requests.AddContactRequest;
import africa.semicolon.phoneBookApp.dtos.responses.AddContactResponse;
import africa.semicolon.phoneBookApp.exceptions.ContactNotFoundException;
import africa.semicolon.phoneBookApp.exceptions.PhoneBookAppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

class ContactServiceImplTest {
    @Autowired
    public ContactRepository database;

    @Autowired
     ContactService service;

    @Test
    public void contactRequestCanBeFilledTest(){
        AddContactRequest newRequest = new AddContactRequest("Ademiju","08165563818", "hardaymyju@gmail.com");
        newRequest.setMiddleName("Joseph");
        newRequest.setLastName("Taiwo");

        assertThat(newRequest.getFirstName(),is("Ademiju"));
        assertThat(newRequest.getMiddleName(),is("Joseph"));
        assertThat(newRequest.getLastName(),is("Taiwo"));
        assertThat(newRequest.getPhoneNumber(),is("08165563818"));
        assertThat(newRequest.getEmailAddress(),is("hardaymyju@gmail.com"));
    }
    @Test
    public void aContactCanBeCreatedFromRequestFilledTest(){
        AddContactRequest newRequest = new AddContactRequest("Ademiju","08165563818", "hardaymyju@gmail.com");
        newRequest.setMiddleName("Joseph");
        newRequest.setLastName("Taiwo");
        Contact newContact = new Contact(newRequest.getFirstName(),newRequest.getPhoneNumber(), newRequest.getEmailAddress());
        newContact.setMiddleName(newRequest.getMiddleName());
        newContact.setLastName("Taiwo");
        assertThat(newContact.getFirstName(),is("Ademiju"));
        assertThat(newContact.getPhoneNumber(), is("08165563818"));
        assertThat(newContact.getEmailAddress(),is("hardaymyju@gmail.com"));
        assertThat(newContact.getMiddleName(), is("Joseph"));
        assertThat(newContact.getLastName(),is("Taiwo"));
    }
    @Test
    public void contactCreatedCanBeSavedTest(){
        AddContactRequest newRequest = new AddContactRequest("Ademiju","08165563818", "hardaymyju@gmail.com");
        newRequest.setMiddleName("Joseph");
        newRequest.setLastName("Taiwo");
        Contact newContact = new Contact(newRequest.getFirstName(),newRequest.getPhoneNumber(), newRequest.getEmailAddress());
        assertThat(newContact.getFirstName(),is("Ademiju"));
        assertThat(newContact.getPhoneNumber(), is("08165563818"));
        assertThat(newContact.getEmailAddress(),is("hardaymyju@gmail.com"));
        database.save(newContact);
        assertThat(database.count(), is(1L));

    }

    @Test
    public void aContactCanBeCreated_ifAllContactRequestFieldsAreNotFilledTest(){
        AddContactRequest newRequest = new AddContactRequest("Ademiju","08165563818", "hardaymyju@gmail.com");
        Contact newContact = new Contact(newRequest.getFirstName(),newRequest.getPhoneNumber(), newRequest.getEmailAddress());
        assertThat(newContact.getFirstName(),is("Ademiju"));
        assertThat(newContact.getPhoneNumber(), is("08165563818"));
        assertThat(newContact.getEmailAddress(),is("hardaymyju@gmail.com"));
        assertNull(newContact.getMiddleName());
        assertNull(newContact.getLastName());

    }
    @Test

    public void contactCreated_WithOnlyRequiredDetails_GivesCorrectResponseTest(){
        AddContactRequest newRequest = new AddContactRequest("Ademiju","08165563818", "hardaymyju@gmail.com");
        newRequest.setMiddleName("Joseph");
        newRequest.setLastName("Taiwo");
        AddContactResponse response = service.saveContact(newRequest);
        assertThat(response.getFullName(),is("Ademiju Joseph Taiwo"));
        assertThat(response.getPhoneNumber(),is("08165563818"));
    }

    @Test

    public void ContactsCreated_ifAllContactRequestFieldsAreNotFilled_GivesCorrectResponseTest(){
        AddContactRequest firstRequest = new AddContactRequest("Ademiju","08165563818", "hardaymyju@gmail.com");
        firstRequest.setMiddleName("Joseph");
        AddContactRequest secondRequest = new AddContactRequest("Increase","09054342321", "bruce@gmail.com");
        secondRequest.setLastName("Uwadiale");
        AddContactRequest thirdRequest = new AddContactRequest("Damilola","08023456789", "damiteaches@gmail.com");

        AddContactResponse response = service.saveContact(firstRequest);
        AddContactResponse secondResponse = service.saveContact(secondRequest);
        AddContactResponse thirdResponse = service.saveContact(thirdRequest);

        assertThat(response.getFullName(),is("Ademiju Joseph"));
        assertThat(response.getPhoneNumber(),is("08165563818"));
        assertThat(secondResponse.getFullName(),is("Increase Uwadiale"));
        assertThat(secondResponse.getPhoneNumber(),is("09054342321"));
        assertThat(thirdResponse.getFullName(),is("Damilola"));
        assertThat(thirdResponse.getPhoneNumber(),is("08023456789"));

    }

    @Test
    public void addContactWithExistingPhoneNumberThrowsAnException(){
        AddContactRequest firstRequest = new AddContactRequest("Ademiju","08165563818", "ademijuwonlo@gmail.com");
        AddContactRequest secondRequest = new AddContactRequest("Ademiju","08165563818", "hardaymyju@gmail.com");
        service.saveContact(secondRequest);
        assertThrows(PhoneBookAppException.class, () -> service.saveContact(firstRequest));
    }

    @Test
    public void addContactWithExistingEmailThrowsAnException(){
        AddContactRequest firstRequest = new AddContactRequest("Ademiju","08165563818", "hardaymyju@gmail.com");
        AddContactRequest secondRequest = new AddContactRequest("Ademiju","08165563819", "hardaymyju@gmail.com");
        service.saveContact(firstRequest);
        assertThrows(PhoneBookAppException.class, () -> service.saveContact(secondRequest));

    }
    @Test
    public void findContactWithEmailIsNotCaseSensitiveTest(){
        AddContactRequest firstRequest = new AddContactRequest("ADEmiju","08165563818", "harDAYmyju@gmail.com");
        service.saveContact(firstRequest);
        Contact contact = service.findContactByEmailAddress("hardAymYju@gmail.com");
        assertThat(contact.getEmailAddress(),is("hardaymyju@gmail.com"));
        assertThat(contact.getPhoneNumber(), is("08165563818"));
        assertThat(contact.getFirstName(), is("ADEmiju"));

    }

    @Test
    public void findContact_WithEmailThatDoesNotExist_ThrowsAnException(){
        AddContactRequest firstRequest = new AddContactRequest("ADEmiju","08165563818", "harDAYmyju@gmail.com");
        service.saveContact(firstRequest);
        assertThrows(ContactNotFoundException.class, () -> service.findContactByEmailAddress("ademijuwonlo@gmail.com"));

    }

    @Test
    public void findContactByPhoneNumberTest() {
        AddContactRequest firstRequest = new AddContactRequest("ADEmiju", "08165563818", "hardaymyju@gmail.com");
        service.saveContact(firstRequest);
        Contact contact = service.findContactByPhoneNumber("08165563818");
        assertThat(contact.getPhoneNumber(), is("08165563818"));
        assertThat(contact.getFirstName(), is("ADEmiju"));
        assertThat(contact.getEmailAddress(), is("hardaymyju@gmail.com"));

    }
    @Test
    public void findContact_WithPhoneNumberThatDoesNotExist_ThrowsAnException(){
        AddContactRequest firstRequest = new AddContactRequest("Ademiju","08165563818", "hardaymyju@gmail.com");
        service.saveContact(firstRequest);
        assertThrows(ContactNotFoundException.class, () -> service.findContactByPhoneNumber("08123456789"));

    }
    @Test
    public void findContactWithAnyNameFieldTest(){
        AddContactRequest firstRequest = new AddContactRequest("Ademiju","Taiwo","Joseph","08165563818","hardaymyju@gmail.com");
        AddContactRequest secondRequest = new AddContactRequest("Ademiju","Taiwo","Joseph","08123456789","ademijuwonlo@gmail.com");
        service.saveContact(firstRequest);
        service.saveContact(secondRequest);

        List<Contact> contact = service.findContactByFirstNameOrLastNameOrMiddleName("Joseph");
        assertThat(contact.get(0).getPhoneNumber(), is("08165563818"));
        assertThat(contact.get(0).getFirstName(), is("Ademiju"));
        assertThat(contact.get(0).getEmailAddress(), is("hardaymyju@gmail.com"));
        assertThat(contact.get(0).getMiddleName(), is("Joseph"));
        assertThat(contact.get(0).getLastName(), is("Taiwo"));
        assertThat(contact.get(1).getPhoneNumber(), is("08123456789"));
        assertThat(contact.get(1).getFirstName(), is("Ademiju"));
        assertThat(contact.get(1).getEmailAddress(), is("ademijuwonlo@gmail.com"));
        assertThat(contact.get(1).getMiddleName(), is("Joseph"));
        assertThat(contact.get(1).getLastName(), is("Taiwo"));

    }

    @Test
    public void findContact_WithAnyNameFieldThatDoesNotExist_ThrowsAnException(){
        AddContactRequest firstRequest = new AddContactRequest("Ademiju","Taiwo","Joseph","08165563818","hardaymyju@gmail.com");
        service.saveContact(firstRequest);
        assertThrows(ContactNotFoundException.class, () -> service.findContactByFirstNameOrLastNameOrMiddleName("John"));

    }

    @Test
    public void deleteContactByPhoneNumberTest() {
        AddContactRequest firstRequest = new AddContactRequest("ADEmiju", "08165563818", "hardaymyju@gmail.com");
        service.saveContact(firstRequest);
        assertThat(database.count(), is(1L));
        service.deleteContactByMobileNumber("08165563818");
        assertThat(database.count(), is(0L));
    }
    @Test
    public void deleteContact_WithPhoneNumberThatDoesNotExist_ThrowsAnException(){
        AddContactRequest firstRequest = new AddContactRequest("Ademiju","08165563818", "hardaymyju@gmail.com");
        service.saveContact(firstRequest);
        assertThrows(ContactNotFoundException.class, () -> service.deleteContactByMobileNumber("08123456789"));

    }

    @Test
    public void deleteContactWithEmailIsNotCaseSensitiveTest(){
        AddContactRequest firstRequest = new AddContactRequest("ADEmiju","08165563818", "harDAYmyju@gmail.com");
        service.saveContact(firstRequest);
        assertThat(database.count(),is(1L));
        service.deleteContactByEmailAddress("hardAymYju@gmail.com");
        assertThat(database.count(),is(0L));

    }

    @Test
    public void deleteContact_WithEmailThatDoesNotExist_ThrowsAnException(){
        AddContactRequest firstRequest = new AddContactRequest("ADEmiju","08165563818", "harDAYmyju@gmail.com");
        service.saveContact(firstRequest);
        assertThrows(ContactNotFoundException.class, () -> service.deleteContactByEmailAddress("ademijuwonlo@gmail.com"));

    }


}