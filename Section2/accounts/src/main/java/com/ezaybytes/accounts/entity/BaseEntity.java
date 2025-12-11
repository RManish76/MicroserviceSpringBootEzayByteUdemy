package com.ezaybytes.accounts.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass //tells springJPA that this entity/model class will be used in all other entity/model class
//the @MappedSuperclass annotation indicates to spring data jpa framework that this class is going
//to act as a superclass for all my entites, wherever i'm trying to extend this base entity class
@Data
public class BaseEntity {

    @Column(updatable = false) //tells spring jpa that this column cannot be updated once value is inserted
    private LocalDateTime createdAt;
    
    @Column(updatable = false)
    private String createdBy;
    
    @Column(insertable = false) //insertable false means that whenever a new record is created this column should
    //not populate any value means it should be null. as its update column means we want value here when
    //only the record is updated
    private LocalDateTime updatedAt;
    
    @Column(insertable = false)
    private String updatedBy;

}
