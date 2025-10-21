package org.busnake.biblioteka_api.repository;

import org.busnake.biblioteka_api.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);
}
