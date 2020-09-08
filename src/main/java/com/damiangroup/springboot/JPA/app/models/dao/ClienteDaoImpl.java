package com.damiangroup.springboot.JPA.app.models.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.damiangroup.springboot.JPA.app.models.entity.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	public Optional<Cliente> findById(Long id) {
		Cliente cliente = em.find(Cliente.class, id);
		Optional<Cliente> optional = Optional.of(cliente);
		return optional;
	}

	@Override
	public void deleteById(Long id) {
		Cliente cliente = findById(id).orElse(null);
		if (cliente != null) {
			em.remove(cliente);
		}
	}

	@Override
	public Page<Cliente> findAll(Pageable Pageable) {
		int paginaActual = Pageable.getPageNumber();
		int registrosPorPagina = Pageable.getPageSize();
		//pagina++;  para que coincida con PagingAndSortingRepository
		int comienzoRegistros;
		
		if (paginaActual > 1) comienzoRegistros = (paginaActual * registrosPorPagina - registrosPorPagina);
		else comienzoRegistros = 0;
		
		Query consulta = em.createQuery("from Cliente")
			.setMaxResults(registrosPorPagina) // LIMIT
			.setFirstResult(comienzoRegistros); // OFFSET

		List<Cliente> clientesLista = consulta.getResultList();
		Page<Cliente> clientes = new PageImpl<>(clientesLista);
		return clientes;	
	}

}