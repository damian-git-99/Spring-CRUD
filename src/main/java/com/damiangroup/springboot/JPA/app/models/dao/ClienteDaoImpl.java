package com.damiangroup.springboot.JPA.app.models.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClienteDaoImpl implements IClienteDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true) // como es una consulta solo queremos leer datos
    @Override
    public List<Cliente> findAll() {
        return em.createQuery("from Cliente").getResultList();
    }

    @Override
    @Transactional
	public void save(Cliente cliente) {
		// TODO Auto-generated method stub
    	System.out.println(cliente.getId());
    	if (cliente.getId() != null) {
    		//Actualizar datos
    		em.merge(cliente);
		}else {
			//Insertar datos
			em.persist(cliente);
		}
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        Cliente cliente = em.find(Cliente.class, id);
       return cliente;
    }

	@Override
	@Transactional
	public void delete(Long id) {
		Cliente cliente = findOne(id);
		if (cliente != null) {
			em.remove(cliente);
		}
	}
	
	/*
	public void update(Long id) {
		Cliente cliente = findOne(id);
		if (cliente != null) em.merge(cliente);
		
	}
	*/
    
}