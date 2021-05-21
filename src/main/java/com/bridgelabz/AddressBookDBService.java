package com.bridgelabz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
        String userName = "root";
        String password = "root";
        Connection connection;
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection established: "+connection);
        return connection;
    }
    public List<Contact> readData() {
        List<Contact> contactList = new ArrayList<Contact>();
        String sql = "SELECT * FROM userdetails;";
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                Integer user_id = resultSet.getInt(1);
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                Integer zip = resultSet.getInt(7);
                Long phoneNo = resultSet.getLong(8);
                String email = resultSet.getString("email");
                Contact contact = new Contact(user_id,firstName, lastName, address, city, state, zip, phoneNo, email);
                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
}
