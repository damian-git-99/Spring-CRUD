package com.damiangroup.springboot.JPA.app.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import com.damiangroup.springboot.JPA.app.models.Role;
import com.damiangroup.springboot.JPA.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.damiangroup.springboot.JPA.app.daos.UserDao;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public JpaUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if (authorities.isEmpty()) {
            throw new UsernameNotFoundException("User has no authorities");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}
