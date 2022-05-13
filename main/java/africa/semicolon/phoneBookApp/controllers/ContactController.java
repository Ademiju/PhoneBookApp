package africa.semicolon.phoneBookApp.controllers;

import africa.semicolon.phoneBookApp.data.models.Contact;
import africa.semicolon.phoneBookApp.dtos.requests.AddContactRequest;
import africa.semicolon.phoneBookApp.exceptions.ContactNotFoundException;
import africa.semicolon.phoneBookApp.exceptions.EmptyContactListException;
import africa.semicolon.phoneBookApp.exceptions.PhoneBookAppException;
import africa.semicolon.phoneBookApp.services.ContactService;
import africa.semicolon.phoneBookApp.dtos.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/contact")
@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;
    @PostMapping("/addContact")

    public ResponseEntity<?> addNewContact(@RequestBody AddContactRequest request){
        try {

            return new ResponseEntity<>(contactService.saveContact(request), HttpStatus.CREATED);
        }catch (PhoneBookAppException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),HttpStatus.NOT_ACCEPTABLE);
        }

    }
    @GetMapping("/mobile/{phoneNumber}")
    public ResponseEntity<?> getContactByPhoneNumber(@PathVariable String phoneNumber){
        try {
            return new ResponseEntity<>(contactService.findContactByPhoneNumber(phoneNumber), HttpStatus.FOUND) ;
        } catch (ContactNotFoundException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/email/{emailAddress}")
    public ResponseEntity<?> getContactResponseByEmailAddress(@PathVariable String emailAddress){
        try {
            return new ResponseEntity<>(contactService.findContactByEmailAddress(emailAddress), HttpStatus.FOUND) ;
        } catch (ContactNotFoundException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<?> getContactByName(@PathVariable String name){
        try {
            return new ResponseEntity<>(contactService.findContactByFirstNameOrLastNameOrMiddleName(name), HttpStatus.FOUND) ;
        } catch (ContactNotFoundException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<?> findAllContact(){
        try {
            return new ResponseEntity<>(contactService.findAllContact(), HttpStatus.FOUND) ;
        } catch (EmptyContactListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{phoneNumber}")
    public ResponseEntity<?> deleteByPhoneNumber(@PathVariable String phoneNumber){
        try {
            contactService.deleteContactByMobileNumber(phoneNumber);
            return new ResponseEntity<>(new ApiResponse(true,"Deleted Successfully"), HttpStatus.FOUND) ;
        }catch(ContactNotFoundException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/del/{emailAddress}")
    public ResponseEntity<?> deleteByEmailAddress(@PathVariable String emailAddress){
        try {
            contactService.deleteContactByEmailAddress(emailAddress);
            return new ResponseEntity<>(new ApiResponse(true,"Deleted Successfully"), HttpStatus.FOUND) ;
        }catch(ContactNotFoundException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        contactService.deleteAllContact();
    }
}


