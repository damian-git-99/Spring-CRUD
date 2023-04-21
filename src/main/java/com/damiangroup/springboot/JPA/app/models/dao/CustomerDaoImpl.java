package com.damiangroup.springboot.JPA.app.models.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.damiangroup.springboot.JPA.app.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Customer> findAll() {
        return em.createQuery("from Customer").getResultList();
    }

    @Override
    public void save(Customer customer) {
        if (customer.getId() != null) {
            em.merge(customer);
        } else {
            em.persist(customer);
        }
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Customer customer = em.find(Customer.class, id);
        return Optional.ofNullable(customer);
    }

    @Override
    public void deleteById(Long id) {
        Customer customer = findById(id).orElse(null);
        if (customer != null) {
            em.remove(customer);
        }
    }

    @Override
    public Page<Customer> findAll(Pageable Pageable) {
        int numPagina = Pageable.getPageNumber(); // pagina actual
        numPagina++; // sumamos uno a a la pagina actual para que coemince desde 0
        int totalRegistrosAMostrar = Pageable.getPageSize(); // numero total de registros a mostrar
        int inicio = (numPagina - 1) * totalRegistrosAMostrar; // calculamos desde que numero de registro comenzar
        Query consulta = em.createQuery("from Customer").setMaxResults(totalRegistrosAMostrar) // LIMIT
                .setFirstResult(inicio); // Inicio
        List<Customer> customers = consulta.getResultList();
        Page<Customer> page = new PageImpl<>(customers, Pageable, totalRegistros());
        return page;
    }

    private long totalRegistros() {
        Query queryTotal = em.createQuery("Select count(id) from Customer");
        long countResult = (long) queryTotal.getSingleResult();
        return countResult;
    }

}