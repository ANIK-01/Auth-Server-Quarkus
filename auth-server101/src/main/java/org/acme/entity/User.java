package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.acme.models.GenderValues;

import java.security.Security;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    @Column(name = "id")
    Long Id;

    @NotNull
    @JsonProperty("company_id")
    @Column(name = "company_id")
    Long CompanyId;

    @NotNull
    @JsonProperty("name")
    @Column(name = "name")
    String Name;

    @NotNull
    @JsonProperty("email")
    @Column(name = "email")
    String Email;

    @NotNull
    @JsonProperty("password")
    @Column(name = "password")
    String Password;

    @NotNull
    @JsonProperty("role_id")
    @Column(name = "role_id")
    String RoleId;

    @JsonProperty("gender")
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    GenderValues Gender;

    @JsonProperty("age")
    @Column(name = "age")
    int Age;

}
