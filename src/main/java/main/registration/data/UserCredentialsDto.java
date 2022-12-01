package main.registration.data;

import lombok.Data;

@Data
public class UserCredentialsDto {
    private final String username;
    private final String password;
}
