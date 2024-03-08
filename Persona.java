package com.example.crud_persona.Models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

public class Persona {
    private int id;

    @NotEmpty(message = "Name shouldnt be empty")
    @Size(min = 2,message = "name should be between 2 and 30 ")
    private String name;
    @Min(value = 0)
    private int age;
    @NotEmpty
    @Email
    private String email;

    public Persona() {
    }

    public Persona(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
