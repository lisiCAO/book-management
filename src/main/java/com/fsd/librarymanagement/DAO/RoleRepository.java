package com.fsd.librarymanagement.DAO;

import com.fsd.librarymanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {

    // Custom method to find a Role entity by its name
    Role findByName(String roleName);
}
