package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.acme.models.IndustryTypes;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    @JsonProperty("id")
    Long Id;

    @NotNull
    @JsonProperty("name")
    String Name;

    @JsonProperty("description")
    String Description;

    @NotNull
    @JsonProperty("address")
    String Address;

    @NotNull
    @JsonProperty("industry")
    IndustryTypes Industry;

    @NotNull
    @JsonProperty("company_email")
    String CompanyEmail;

    @JsonProperty("employee_count")
    int EmployeeCount;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public IndustryTypes getIndustry() {
        return Industry;
    }

    public void setIndustry(IndustryTypes industry) {
        Industry = industry;
    }

    public String getCompanyEmail() {
        return CompanyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        CompanyEmail = companyEmail;
    }

    public int getEmployeeCount() {
        return EmployeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        EmployeeCount = employeeCount;
    }
}
