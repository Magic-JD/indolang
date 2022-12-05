package main.rest.registration.service;

import main.database.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

import static main.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
class UserDetailsServiceTest {

    @Autowired UserDetailsService SUT;
    @Autowired UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testShouldThrowUsernameNotFoundExceptionIfTheUsernameCannotBeFound() {
        assertThrows(UsernameNotFoundException.class, () -> SUT.loadUserByUsername(USERNAME_1));
    }

    @Test
    void testShouldReturnUserDataWhenUserIsInDatabase() {
        userRepository.save(DB_USER_ITEM);
        UserDetails userDetails = SUT.loadUserByUsername(USERNAME_1);
        assertTrue(userDetails.isEnabled());
        assertEquals(USERNAME_1, userDetails.getUsername());
        assertEquals(PASSWORD, userDetails.getPassword());
        assertEquals(ROLES_SET_1, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


}