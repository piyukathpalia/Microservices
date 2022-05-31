package com.mywallet.application.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.util.Date;

@Document(collection = "Customer")
public class Customer {

    @Transient
    public static final String SEQUENCE_NAME = "customer_sequence";

    @Id
    long id;
    @NotBlank(message = "First name should not be empty")
    String firstName;
    @NotBlank(message = "Last name should not be empty")
    String lastName;
    @Email(message = "should be proper email address")
    String email;
    @JsonFormat(pattern = "dd-MM-yyyy")
    Date dob;
    @Digits(integer = 10, fraction = 0, message = "Phone number should be 10 digits")
    long phoneNumber;
    //@Pattern(regexp = "[a-zA-Z0-9]", message = "Post code should be alpha numeric")
    String postCode;
    String password;

    public Customer() {
    }

    public Customer(long id, String firstName, String lastName, String email, Date dob, Long phoneNumber, String postCode, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.postCode = postCode;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", postCode='" + postCode + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
