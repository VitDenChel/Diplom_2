package com;

import org.apache.commons.lang3.RandomStringUtils;

public class UserForRegistration {
    public String email;
    public String password;
    public String name;
    public static final String EMAIL_POSTFIX = "@yandex.ru";

    public UserForRegistration(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserForRegistration getRandom() {
        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        return new UserForRegistration(email, password, name);
    }
}
