package com.damiangroup.springboot.JPA.app.invoice.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICrudDao<T> {
    public List<T> findAll();

    public Page<T> findAll(Pageable Pageable);

    public void save(T t);

    public Optional<T> findById(Long id);

    public void deleteById(Long id);
}
