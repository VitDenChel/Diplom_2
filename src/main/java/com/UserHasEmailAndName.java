package com;

import org.apache.commons.lang3.RandomStringUtils;

public class UserHasEmailAndName {
    public String email;
    public String name;
    public static final String EMAIL_POSTFIX = "@yandex.ru";

    public UserHasEmailAndName(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static UserHasEmailAndName getRandom() {
        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        String name = RandomStringUtils.randomAlphabetic(10);
        return new UserHasEmailAndName(email, name);
    }
}
