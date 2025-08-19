package Entities;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {
    private String customerId;
    private String name;
    private String password;
    private String email;
    private String phone;
    private LocalDate DOB;

    public Customer(){

    }

    public Customer(String customerId, LocalDate DOB, String email, String name, String password, String phone) {
        this.customerId = customerId;
        this.DOB = DOB;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", DOB=" + DOB +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }

        Customer customer = (Customer) obj;
        return Objects.equals(customerId,customer.customerId)  && Objects.equals(email,customer.email);
    }


    @Override
    public int hashCode(){
        return Objects.hash(customerId,email);
    }


}
