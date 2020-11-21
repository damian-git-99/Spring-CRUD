package com.damiangroup.springboot.JPA.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.damiangroup.springboot.JPA.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.username=?1")
	public Usuario fetchByUsername(String username);

}
