package main.rest.registration.service;

import main.database.mapper.UserDetailsMapper;
import main.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private UserDetailsMapper userDetailsMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsMapper.toUserDetails(userRepository.findItemByName(username));
    }
}
