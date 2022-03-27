package com;

import org.apache.commons.lang3.RandomStringUtils;

public class UserHasPasswordAndName {
    public String password;
    public String name;

    public UserHasPasswordAndName(String password, String name) {
        this.password = password;
        this.name = name;
    }

    public static UserHasPasswordAndName getRandom() {
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        return new UserHasPasswordAndName(password, name);
    }
}
