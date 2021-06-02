package com.bridgelabz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {

    public enum IOService{
        DB_IO,REST_IO
    }
    List<Contact> addressBookList;
    AddressBookDBService addressBookDBService = AddressBookDBService.getInstance();

    public AddressBookService() {
    }

    public AddressBookService(List<Contact> contactList) {
        this.addressBookList = new ArrayList<>(contactList);
    }


    public List<Contact> readData() {
        addressBookList = addressBookDBService.readData();
        return addressBookList;
    }

    public boolean updateContact(String firstName, String lastName, String phone, String email,IOService ioService) {
        if(ioService.equals(IOService.DB_IO)) {
            int rows = addressBookDBService.updateContact(firstName,lastName,phone,email);
            if(rows>0)
                return true;
        }
        else if(ioService.equals(IOService.REST_IO)) {
            Contact contactData = this.getContactDetails(firstName, lastName);
            if(contactData!=null) {
                contactData.setEmail(email);
                contactData.setPhoneNo(phone);
                return true;
            }
        }
        return false;
    }
    public boolean checkEmployeePayrollInSyncWithDB(String firstName, String lastName) {
        List<Contact> checkList = null;
         checkList = addressBookDBService.getContactDetailsDB(firstName,lastName);
        return checkList.get(0).equals(getContactDetails(firstName,lastName));

    }

    public Contact getContactDetails(String firstName,String lastName) {
        Contact contactData = this.addressBookList.stream()
                .filter(employee->employee.getFirstName().equals(firstName)&&employee.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
        return contactData;
    }
    public List<Contact> getContactInDateRange(String start, String end) {
        return addressBookDBService.getContactInDateRange(start,end);
    }
    public Map<String,Integer> getCountByCityState(AddressBookDBService.CountType type) {
        return addressBookDBService.getCountByCityState(type);
    }
    public void addContact(String firstName, String lastName, String address, String city, String state,
                           String zip, String phone, String email, LocalDate date, String name, String type) {
        addressBookList.add(addressBookDBService.addContact(firstName,lastName,address,city,state,zip,phone,email,date,name,type));
    }
    public void addContacts(List<Contact> addContactList) {
        Map<Integer,Boolean> additionStatus = new HashMap<Integer, Boolean>();
        addContactList.forEach(contact -> {
            Runnable task = () -> {
                additionStatus.put(contact.hashCode(), false);
                System.out.println("Contact being added:(threads) "+Thread.currentThread().getName());
                this.addContact(contact.getFirstName(),contact.getLastName(),contact.getAddress(),contact.getCity(),contact.getState(),
                        contact.getZip(),contact.getPhoneNo(),contact.getEmail(),contact.getDate(),contact.getName(),contact.getType());
                additionStatus.put(contact.hashCode(), true);
                System.out.println("Contact added: (threads)"+Thread.currentThread().getName());
            };
            Thread thread = new Thread(task,contact.getFirstName());
            thread.start();
        });
        while(additionStatus.containsValue(false)) {
            try {
                Thread.sleep(10);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(addressBookList);
    }
    public long countEntries(IOService ioService) {

        return addressBookList.size();
    }

    public void addContact(Contact contact, IOService ioService) {
        if(ioService.equals(IOService.DB_IO)) {
            this.addContact(contact.getFirstName(), contact.getLastName(), contact.getAddress(), contact.getCity()
                    , contact.getState(), contact.getZip(), contact.getPhoneNo(), contact.getEmail(),
                    contact.getDate(), contact.getName(), contact.getType());
        }
        else if(ioService.equals(IOService.REST_IO)) {
            addressBookList.add(contact);
        }
    }
    public void deleteContactJSONServer(String firstName, String lastName, IOService ioService) {
        if(ioService.equals(IOService.REST_IO)) {
            Contact contactData = this.getContactDetails(firstName, lastName);
            addressBookList.remove(contactData);
        }
    }
}