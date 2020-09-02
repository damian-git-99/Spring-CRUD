package com.damiangroup.springboot.JPA.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.damiangroup.springboot.JPA.app.models.dao.IClienteDao;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService{
	
	@Autowired
	private IClienteDao ClienteDao;

	@Override
	@Transactional
	public List<Cliente> findAll() {
		return ClienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		ClienteDao.save(cliente);
	}

	@Override
	@Transactional
	public Cliente findOne(Long id) {
		return ClienteDao.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		ClienteDao.delete(id);
	}

}
