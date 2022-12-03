package main.rest.model;

import lombok.Data;

import java.util.Set;

@Data
public class UserCredentialsDto {
    private final String username;
    private final String password;
    private final Set<String> roles;
}
