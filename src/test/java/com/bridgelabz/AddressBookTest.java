package com.bridgelabz;

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
}
