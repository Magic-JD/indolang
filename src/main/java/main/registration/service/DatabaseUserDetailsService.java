package main.registration.service;

import main.database.mapper.UserDetailsMapper;
import main.database.model.DbUserDetails;
import main.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsMapper userDetailsMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DbUserDetails dbUserDetails =
                userRepository.findItemByName(username);
        return userDetailsMapper.toUserDetails(dbUserDetails);
    }
}
