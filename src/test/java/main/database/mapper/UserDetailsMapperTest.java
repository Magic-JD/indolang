package main.database.mapper;

import main.database.model.DbUserItem;
import main.rest.model.UserDetails;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDetailsMapperTest {

    UserDetailsMapper SUT = new UserDetailsMapper();
    String username = "username";
    String password = "password";
    Set<String> roles = Set.of("USER");
    DbUserItem dbUserItem = new DbUserItem(username, password, true, roles);

    @Test
    void testConversionToUserDetailsInitialisesAllFields() {
        UserDetails userDetails = SUT.toUserDetails(dbUserItem);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertEquals(roles, userDetails.getRoles());
    }
}