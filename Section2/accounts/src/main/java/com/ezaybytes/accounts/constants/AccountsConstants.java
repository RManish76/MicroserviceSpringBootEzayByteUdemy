package com.ezaybytes.accounts.constants;

public class AccountsConstants {
    
    private AccountsConstants(){
        //restrict instantiation
    }
    //we made the consturcter private so that no one can create an object of this class
    //the reason is, inside this class we are maintaining only constants and we don't want someone poluting
    //this class with some  methods or any other business logic.

    //Every variable is static/constant so that no one change it and we'll use these values without
    //creating the object of this class as we use static vairables with class name itself no need of object
    // it is standard use CAPS_AND_UNDERSCORE only for variable for static final type
    public static final String SAVINGS = "savings";
    public static final String ADDRESS = "123 Main Street, New York";
    public static final String STATUS_201 = "201";
    public static final String MESSAGE_201 = "Account created successfully";
    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200 = "Request processed successfully";
    public static final String STATUS_500 = "500";
    public static final String MESSAGE_500 = "An error occurred. Please try again or contact Dev team";
}
