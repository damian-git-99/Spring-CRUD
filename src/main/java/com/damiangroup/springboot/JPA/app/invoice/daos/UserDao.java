package com.damiangroup.springboot.JPA.app.invoice.daos;

import com.damiangroup.springboot.JPA.app.auth.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    @Query("select u from User u where u.username=?1")
    User findUserByUsername(String username);
}
