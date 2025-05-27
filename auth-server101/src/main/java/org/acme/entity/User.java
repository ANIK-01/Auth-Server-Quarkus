package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.acme.models.GenderValues;

import java.security.Security;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    @JsonProperty("id")
    Long id;

    @NotNull
    @JsonProperty("name")
    String name;

    @NotNull
    @JsonProperty("email")
    String email;

    @NotNull
    @JsonProperty("password")
    String password;
    @JsonProperty("gender")
    GenderValues gender;

    @JsonProperty("age")
    int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BcryptUtil.bcryptHash(password);
    }

    public GenderValues getGender() {
        return gender;
    }

    public void setGender(GenderValues gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
