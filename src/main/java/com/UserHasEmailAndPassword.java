package com;

import org.apache.commons.lang3.RandomStringUtils;

public class UserHasEmailAndPassword {
    public String email;
    public String password;
    public static final String EMAIL_POSTFIX = "@yandex.ru";

    public UserHasEmailAndPassword(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserHasEmailAndPassword getRandom() {
        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        String password = RandomStringUtils.randomAlphabetic(10);
        return new UserHasEmailAndPassword(email, password);
    }
}
