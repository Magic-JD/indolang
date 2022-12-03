package main.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@AllArgsConstructor
@Getter
@Document("users")
public class DbUserItem {
    private final String username;
    private final String password;
    private final boolean enabled;
    private final Set<String> roles;
}
