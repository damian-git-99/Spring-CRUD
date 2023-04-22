package com.damiangroup.springboot.JPA.app.daos;

import com.damiangroup.springboot.JPA.app.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    @Query("select u from User u where u.username=?1")
    User findUserByUsername(String username);
}
