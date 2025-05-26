package main.java.entity;


@Entity
public class User {
    @Id
    @GenerateValue(strategy=GenerationType.AUTO)
    Long id;
    String name;
    String email;
    String password;
    String gender;
    int age;
}
