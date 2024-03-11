package com.fsd.librarymanagement.service;

import com.fsd.librarymanagement.entity.User;

import java.util.List;

public interface UserService {
    void save(User user, String roleName);

     void delete(Long userId);

    List<User> findAllUsers();
}
