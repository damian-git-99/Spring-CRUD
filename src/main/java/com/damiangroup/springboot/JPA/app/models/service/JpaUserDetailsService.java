package com.damiangroup.springboot.JPA.app.models.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.damiangroup.springboot.JPA.app.models.dao.UserDao;
import com.damiangroup.springboot.JPA.app.models.entity.Role;
import com.damiangroup.springboot.JPA.app.models.entity.User;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao usuarioDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = usuarioDao.fetchByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("El user no existe");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		if (authorities.isEmpty()) {
			throw new UsernameNotFoundException("El user no tiene roles asignados");
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true,
				authorities);
	}

}
