package com.cbc.design.auth.repositories;

import com.cbc.design.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by cbc on 2018/3/26.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{


    @Query("select u from User u where u.username=?1 or u.phone=?1")
    Optional<User> findByUsernameOrPhone(String usernameOrPhone);

    @Query("select u from User u where u.email=?1")
    Optional<User> findByEmail(String email);

    @Query
    Optional<User> findByPhone(String phone);

    @Query
    Optional<User> findByUsername(String username);

}
