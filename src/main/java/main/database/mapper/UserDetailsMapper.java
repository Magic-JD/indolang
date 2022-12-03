package main.database.mapper;


import main.database.model.DbUserDetails;
import main.rest.registration.data.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

    public UserDetails toUserDetails(DbUserDetails userItem) {
        return new UserDetails(userItem.getPassword(), userItem.getUsername());
    }
}
