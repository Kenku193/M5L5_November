package org.example.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Setter
@Getter
public class Customer {
    private Long id;
    private String login;
    private String password;
}
