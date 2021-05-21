package com.bridgelabz;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressBookTest {
    @Test
    public void givenAddressBookData_WhenRetreived_ShouldRetrieveAllContacts() {
        AddressBookDBService addressBookDBService = new AddressBookDBService();
        List<Contact> contactList = addressBookDBService.readData();
        System.out.println(contactList);
        assertEquals(3, contactList.size());
    }
}
