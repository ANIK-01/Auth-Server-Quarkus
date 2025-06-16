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
    Long Id;

    @NotNull
    @JsonProperty("company_id")
    Long CompanyId;

    @NotNull
    @JsonProperty("name")
    String Name;

    @NotNull
    @JsonProperty("email")
    String Email;

    @NotNull
    @JsonProperty("password")
    String Password;

    @NotNull
    @JsonProperty("role_id")
    String RoleId;

    @JsonProperty("gender")
    GenderValues Gender;

    @JsonProperty("age")
    int Age;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public Long getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(Long companyId) {
        this.CompanyId = companyId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = BcryptUtil.bcryptHash(password);
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }

    public GenderValues getGender() {
        return Gender;
    }

    public void setGender(GenderValues gender) {
        this.Gender = gender;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        this.Age = age;
    }
}
