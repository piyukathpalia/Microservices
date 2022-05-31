package com.mywallet.application.dto;

public class CustomerCredentials {
    private long id;
    private String password;

    public CustomerCredentials(long id, String password) {
        this.id = id;
        this.password = password;
    }

    public CustomerCredentials() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CustomerCredentials{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
