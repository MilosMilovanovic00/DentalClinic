package com.example.project.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("FROM User WHERE email = ?1")
    User getUserByEmail(String email);

    @Query("FROM User WHERE id = ?1")
    User getUserById(Long id);

}
