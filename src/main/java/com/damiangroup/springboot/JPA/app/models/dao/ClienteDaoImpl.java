package com.damiangroup.springboot.JPA.app.models.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteDaoImpl implements IClienteDao {

	/*
	 * Un contexto de persistencia maneja un conjunto de entities que contienen
	 * datos que se conservarán en algún almacén de persistencia (por ejemplo, una
	 * base de datos)
	 */
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	public void save(Cliente cliente) {
		// TODO Auto-generated method stub
		System.out.println(cliente.getId());
		if (cliente.getId() != null) {
			// Actualizar datos
			em.merge(cliente);
		} else {
			// Insertar datos
			em.persist(cliente);
		}
	}

	@Override
	public Cliente findOne(Long id) {
		Cliente cliente = em.find(Cliente.class, id);
		return cliente;
	}

	@Override
	public void delete(Long id) {
		Cliente cliente = findOne(id);
		if (cliente != null) {
			em.remove(cliente);
		}
	}

}