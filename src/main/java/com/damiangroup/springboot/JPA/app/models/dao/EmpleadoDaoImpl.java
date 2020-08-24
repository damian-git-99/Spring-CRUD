package com.damiangroup.springboot.JPA.app.models.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.damiangroup.springboot.JPA.app.models.entity.Empleado;

@Repository
public class EmpleadoDaoImpl implements IEmpleadoDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true) // como es una consulta solo queremos leer datos
	@Override
	public List<Empleado> findAll() {
		return em.createQuery("from Empleado").getResultList();
	}

	@Override
	@Transactional
	public void save(Empleado empleado) {
		em.persist(empleado);
	}

}
