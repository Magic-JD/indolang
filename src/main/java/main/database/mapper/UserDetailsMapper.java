package main.database.mapper;


import main.database.model.DbUserItem;
import main.rest.model.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

    public UserDetails toUserDetails(DbUserItem userItem) {
        return new UserDetails(userItem.getPassword(), userItem.getUsername(), userItem.getRoles(), userItem.isEnabled());
    }
}
