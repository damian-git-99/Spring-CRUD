package com.damiangroup.springboot.JPA.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.damiangroup.springboot.JPA.app.models.dao.IClienteDaoCrudRepository;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDaoCrudRepository ClienteDao;

	@Override
	@Transactional
	public List<Cliente> findAll() {
		return (List<Cliente>) ClienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		ClienteDao.save(cliente);
	}

	@Override
	@Transactional
	public Cliente findOne(Long id) {
		return ClienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		ClienteDao.deleteById(id);
	}

	@Override
	@Transactional
	public Page<Cliente> findAll(Pageable pageable) {
		return ClienteDao.findAll(pageable);
	}
}