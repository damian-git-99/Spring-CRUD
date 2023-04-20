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
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Cliente> findAll() {
        return em.createQuery("from Cliente").getResultList();
    }

    @Override
    public void save(Cliente cliente) {
        if (cliente.getId() != null) {
            em.merge(cliente);
        } else {
            em.persist(cliente);
        }
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        Cliente cliente = em.find(Cliente.class, id);
        return Optional.ofNullable(cliente);
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
        int numPagina = Pageable.getPageNumber(); // pagina actual
        numPagina++; // sumamos uno a a la pagina actual para que coemince desde 0
        int totalRegistrosAMostrar = Pageable.getPageSize(); // numero total de registros a mostrar
        int inicio = (numPagina - 1) * totalRegistrosAMostrar; // calculamos desde que numero de registro comenzar
        Query consulta = em.createQuery("from Cliente").setMaxResults(totalRegistrosAMostrar) // LIMIT
                .setFirstResult(inicio); // Inicio
        List<Cliente> clientes = consulta.getResultList();
        Page<Cliente> page = new PageImpl<>(clientes, Pageable, totalRegistros());
        return page;
    }

    private long totalRegistros() {
        Query queryTotal = em.createQuery("Select count(id) from Cliente");
        long countResult = (long) queryTotal.getSingleResult();
        return countResult;
    }

}