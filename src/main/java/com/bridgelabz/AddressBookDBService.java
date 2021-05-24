package com.bridgelabz;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private static AddressBookDBService addressBookDBService;
    private PreparedStatement addressBookPreparedStatement;

    private AddressBookDBService() {
    }

    public static AddressBookDBService getInstance() {
        if (addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }

    public List<Contact> readData() {
        List<Contact> contactList = new ArrayList<Contact>();
        String sql = "SELECT * FROM address_book;";
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            contactList = this.getDataUsingResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public int updateContact(String firstName, String lastName, String phone, String email) {
        int rowsAffected=0;
        String sql = String.format("UPDATE address_book SET phone = '%s',email = '%s' "
                + "WHERE first_name = '%s' AND last_name = '%s';",phone,email,firstName,lastName);
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }


    public List<Contact> getContactDetailsDB(String firstName,String lastName){
        List<Contact> contactList = new ArrayList<Contact>();
        try {
            Connection connection = this.getConnection();
            String sql = "Select * from address_book WHERE first_name = ? AND last_name = ?;";
            addressBookPreparedStatement = connection.prepareStatement(sql);
            addressBookPreparedStatement.setString(1, firstName);
            addressBookPreparedStatement.setString(2, lastName);
            ResultSet resultSet = addressBookPreparedStatement.executeQuery();
            contactList = this.getDataUsingResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }


    private List<Contact> getDataUsingResultSet(ResultSet resultSet){
        List<Contact> contactList = new ArrayList<Contact>();
        try {
            while(resultSet.next()) {
                String first_Name = resultSet.getString(1);
                String last_Name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String city = resultSet.getString(4);
                String state = resultSet.getString(5);
                String zip = resultSet.getString(6);
                String phoneNo = resultSet.getString(7);
                String email = resultSet.getString(8);
                Contact contact = new Contact(first_Name, last_Name, address, city, state, zip, phoneNo, email);
                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
        String userName = "root";
        String password = "root";
        Connection connection;
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection established: "+connection);
        return connection;
    }
}