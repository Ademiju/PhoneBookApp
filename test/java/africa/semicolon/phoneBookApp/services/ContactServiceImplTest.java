package africa.semicolon.phoneBookApp.services;

import africa.semicolon.phoneBookApp.data.models.Contact;
import africa.semicolon.phoneBookApp.data.repositories.ContactRepository;
import africa.semicolon.phoneBookApp.dtos.responses.AddContactResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ContactServiceImplTest {
    @Autowired
    private ContactRepository database;
    @Test
    public void aContactCanBeCreatedTest(){
        Contact newContact = new Contact("Ademiju", "08165563818", "hardaymyju@gmail.com");
        assertThat(newContact.getFirstName(),is("Ademiju"));
        database.save(newContact);
        Contact newContact2 = new Contact("Ademiju", "08165563818", "hardaymyju@gmail.com");
        database.save(newContact2);
        assertThat(database.count(),is(2L));

    }


}