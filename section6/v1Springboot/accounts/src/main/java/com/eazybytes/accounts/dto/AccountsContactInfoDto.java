package com.eazybytes.accounts.dto;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Sometimes we want our POJO class or DTO classes to simply act as a data carriers, which means first
 * I'll create an object of this DTO class and someone can read the data from the object of this DTO clas,
 * but they should not be able to change.
 * So whatever fields data that you pass during the object creation, the same values are going to be final.
 * And anyone can read using the getter methods and there won't be any setter methods.
 * So this is the most common scenario and common requirement.
 * So instead of writing all the getter methods and creating a constructor that is going to accept
 * the final java fields, you can simply use this record class.
 * So in order to define the JAVA Fields, first I need to know what is the property name that we have
 * defined inside the application.yml
 * the fields name should be matching what we have defined in applicatiion.yml 
 */

@ConfigurationProperties(prefix = "accounts") //will also create bean of this class
public record AccountsContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
    /**
     * Now beind the scens, the java is going to make these fileds as final and at the same time it is going
     * to generate a getter method and a constructor behind the scense.
     * So there won't be any setter methods, which means whenever you are using record type classes,
     * you can only initialize the data only once and you cannot change that and whatever you have provided
     * during the object creation, it is going to be final.
     * And wour requirement is also not to change these values at runtime, we use record class.
     * 
     * We need to mention "accounts" prefix with help of @Configuration properties. and we need pass the prefix
     * 
     * After that we need to make sure we are mentoning an annotation which is @EnableCongiurationProperties.
     * And against to this annotation, we need to invoke a parameter value and inside this value we need to pass
     * the Pojo class details. And the class name is AccountsContactInfoDto.class
     */
}
