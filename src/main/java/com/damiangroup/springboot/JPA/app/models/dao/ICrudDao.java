package com.damiangroup.springboot.JPA.app.models.dao;

import java.util.List;

public interface ICrudDao<T>{
	public List<T> findAll();
	public void save(T t);
	public T findOne(Long id);
	public void delete(Long id);
}
