package org.busnake.biblioteka_api.Repository;

import org.busnake.biblioteka_api.Model.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
