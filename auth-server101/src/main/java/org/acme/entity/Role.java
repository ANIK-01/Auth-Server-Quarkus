package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    @JsonProperty("id")
    @Column(name = "id")
    Long Id;

    @NotNull
    @JsonProperty("name")
    @Column(name = "name")
    String Name;

}
