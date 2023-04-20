package com.damiangroup.springboot.JPA.app.models.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.damiangroup.springboot.JPA.app.models.dao.CustomerDao;
import com.damiangroup.springboot.JPA.app.models.dao.IFacturaDao;
import com.damiangroup.springboot.JPA.app.models.dao.IProductoDao;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import com.damiangroup.springboot.JPA.app.models.entity.Factura;
import com.damiangroup.springboot.JPA.app.models.entity.Producto;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private CustomerDao ClienteDao;

	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IFacturaDao facturaDao;

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

	@Override
	@Transactional
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombre(term);
	}

	@Override
	@Transactional
	public List<Producto> findAllProducts() {
		return productoDao.findAll();
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@Transactional
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	
	@Override
	@Transactional
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaDao.deleteById(id);
	}

	

	

}
