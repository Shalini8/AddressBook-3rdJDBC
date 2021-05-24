package com.bridgelabz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
}
