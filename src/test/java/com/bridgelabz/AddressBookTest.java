package com.bridgelabz;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressBookTest {
    AddressBookService addressBookService;
    @BeforeEach
    public void init() {
        addressBookService = new AddressBookService();
    }

    @Test
    public void givenAddressBookData_WhenRetreived_ShouldRetrieveAllContacts() {
        List<Contact> contactList = addressBookService.readData();
        System.out.println(contactList);
        assertEquals(5, contactList.size());
    }
    @Test
    public void givenContactDetails_WhenUpdated_ShouldSyncWithDB() {
        addressBookService.updateContact("Meena","rai","123456789","meena@email.com");
        boolean result = addressBookService.checkEmployeePayrollInSyncWithDB("Meena","rai");
        assertTrue(result);
    }
    @Test
    public void givenDateRange_ShouldRetrieveContactsInThatRange() {
        addressBookService.readData();
        List<Contact> contactList = addressBookService.getContactInDateRange("2020-01-01","2020-03-20");
        assertEquals(2,contactList.size());
    }
    @Test
    public void givenStateOrCity_ShouldRetrieveCountOfContactsInThatCityOrState() {
        addressBookService.readData();
        Map<String,Integer> cityMap = addressBookService.getCountByCityState(AddressBookDBService.CountType.CITY);
        Map<String,Integer> stateMap = addressBookService.getCountByCityState(AddressBookDBService.CountType.STATE);
        System.out.println(cityMap);
        System.out.println(stateMap);
        int cityCount = cityMap.get("raipur");
        int stateCount = stateMap.get("Chhattisgarh");
        boolean isValid = cityCount == 1 && stateCount == 2;
        assertTrue(isValid);
    }
    @Test
    public void givenAContact_WhenAdded_ShouldSyncWithDatabase() {
        addressBookService.readData();
         addressBookService.addContact("Shalini", "Pandey", "Asna", "jagdalpur", "Gujrat", "456789", "9191902020",
                "shalu@email.com", LocalDate.now(),"name","Family");
    }
    @Test
    public void given3Contacts_WhenAddedToDatabase_ShouldMatchContactEntries() {
        addressBookService.readData();
        Contact[] contacts = {
                new Contact("Mark", "Zuckerberg", "Street 200", "NY", "New York", "456781", "9292929292",
                        "mark@email.com",LocalDate.now(),"name","Friend"),
                new Contact("Bill", "Gates", "Street 250", "Medina", "Washington", "666781", "8892929291",
                        "mark@email.com",LocalDate.now(),"name","Friend"),
                new Contact("Jeff", "Bezos", "Street 200", "City 8", "Washington", "456781", "7292929292",
                        "jeff@email.com",LocalDate.now(),"name","Family")
        };
        addressBookService.addContacts(Arrays.asList(contacts));
        List<Contact> newList = addressBookService.readData();
        assertEquals(7, newList.size());
    }
    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }


    public Contact[] getContactList() {
        Response response = RestAssured.get("/contacts");
        System.out.println("Employee entries in JSON SERVER : "+response.asString());
        Contact[] arrayOfPerson = new Gson().fromJson(response.asString(), Contact[].class);
        return arrayOfPerson;
    }

    @Test
    public void givenContactsInJSONServer_WhenRetrieved_ShouldMatchTheCount() {
        Contact[] arrayOfContacts = getContactList();
        AddressBookService addressBookService;
        addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
        long entries = addressBookService.countEntries();
        assertEquals(4, entries);
    }

    @Test
    public void givenNewContact_WhenAdded_ShouldMatch201ResponseAndCount() {
        Contact[] arrayOfEmps = getContactList();
        AddressBookService addressBookService = new AddressBookService(Arrays.asList(arrayOfEmps));
        Contact contact = new Contact(0,"Mark", "Zuckerberg", "Street 190", "Bhopal", "MP", "444444", "7777777777",
                "Mark@email.com", LocalDate.now(), "Address Book 2", "Family");
        Response response  = addContactToJSONServer(contact);
        int statusCode = response.getStatusCode();
        assertEquals(201, statusCode);
        contact = new Gson().fromJson(response.asString(), Contact.class);
        addressBookService.addContact(contact, AddressBookService.IOService.REST_IO);
        assertEquals(4, addressBookService.countEntries());
    }

    private Response addContactToJSONServer(Contact contact) {
        System.out.println(contact);
        String contactJson = new Gson().toJson(contact);
        System.out.println(contactJson);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type","application/json");
        request.body(contactJson);
        return request.post("/contacts");
    }
}

