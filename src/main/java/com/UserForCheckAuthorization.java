package com;

public class UserForCheckAuthorization {
    public String email;
    public String password;


    public UserForCheckAuthorization(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserForCheckAuthorization from (UserForRegistration userForRegistration)  {
               return new UserForCheckAuthorization (userForRegistration.email, userForRegistration.password);
    }
}
