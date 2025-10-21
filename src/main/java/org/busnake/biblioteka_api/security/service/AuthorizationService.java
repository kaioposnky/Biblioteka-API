package org.busnake.biblioteka_api.security.service;

import org.busnake.biblioteka_api.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserDetailsRepository repository;

    public AuthorizationService(UserDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }

}
