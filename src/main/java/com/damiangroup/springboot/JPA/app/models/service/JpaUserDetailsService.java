package com.damiangroup.springboot.JPA.app.models.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.damiangroup.springboot.JPA.app.models.dao.IUsuarioDao;
import com.damiangroup.springboot.JPA.app.models.entity.Role;
import com.damiangroup.springboot.JPA.app.models.entity.Usuario;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.fetchByUsername(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("El usuario no existe");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		if (authorities.isEmpty()) {
			throw new UsernameNotFoundException("El usuario no tiene roles asignados");
		}

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), true, true, true,
				authorities);
	}

}
