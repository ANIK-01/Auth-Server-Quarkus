package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.acme.models.IndustryTypes;

@Entity
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    @Column(name = "id")
    Long Id;

    @NotNull
    @JsonProperty("name")
    @Column(name = "name")
    String Name;

    @JsonProperty("description")
    @Column(name = "description")
    String Description;

    @NotNull
    @JsonProperty("address")
    @Column(name = "address")
    String Address;

    @NotNull
    @JsonProperty("industry")
    @Column(name = "industry")
    @Enumerated(EnumType.STRING)
    IndustryTypes Industry;

    @NotNull
    @JsonProperty("company_email")
    @Column(name = "company_email")
    String CompanyEmail;

    @JsonProperty("employee_count")
    @Column(name = "employee_count")
    int EmployeeCount;
}