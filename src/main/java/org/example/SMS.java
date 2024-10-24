package org.example;

import lombok.Getter;

public class SMS {
    @Getter
    private String phone;
    private String message;

    @Override
    public String toString() {
        return ("SMS (phone=" + phone + ", message=\"" + message + "\")");
    }

    public SMS(String phone, String message){
        this.phone = phone;
        this.message = message;
    }

    public SMS(Person person, String message) {
        this.phone = person.getPhone();
        this.message = message;
    }
}
