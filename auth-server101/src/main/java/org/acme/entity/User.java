package org.acme.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.acme.models.GenderValues;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;

    String name;
    String email;
    String password;
    GenderValues gender;
    int age;

}
