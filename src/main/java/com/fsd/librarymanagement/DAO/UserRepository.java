package com.fsd.librarymanagement.DAO;


import com.fsd.librarymanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom method to check if a User entity exists by its username
    boolean existsByUsername(String username);
}
