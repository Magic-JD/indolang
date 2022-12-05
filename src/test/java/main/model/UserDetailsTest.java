package main.model;

import main.rest.model.UserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDetailsTest {
    private Set<String> user = Set.of("USER", "ADMIN");
    private UserDetails SUT = new UserDetails(null, null, user, false);

    @Test
    void testGetAuthoritiesReturnsAuthoritiesMatchingTheGivenStrings() {
        Collection<? extends GrantedAuthority> authorities = SUT.getAuthorities();
        assertEquals(user, authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
    }
}