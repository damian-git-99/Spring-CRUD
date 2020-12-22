package com.damiangroup.springboot.JPA.app.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String descripcion;
	private String observacion;
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id") // Generar llave foranea de cliente
	@JsonBackReference
	private Cliente cliente;
	
	//@OneToMany(mappedBy = "factura",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id") // generar la llave foranea en factura_items como es de un solo sentido se crea  la llave foranea en factura_items y no en Factura 
	private List<ItemFactura> listaItemsFactura;
	
	@PrePersist
	public void prePersist(){
        this.createAt = new Date();
    }
	
	public Factura() {
		listaItemsFactura = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getListaItemsFactura() {
		return listaItemsFactura;
	}

	public void setListaItemsFactura(List<ItemFactura> listaItemsFactura) {
		this.listaItemsFactura = listaItemsFactura;
	}
	
	public void addItemFactura(ItemFactura item) {
		listaItemsFactura.add(item);
	}
	
	public Double granTotal() {
		double total = 0;
		for (ItemFactura itemFactura : listaItemsFactura) 
			total += itemFactura.calcularImporte();
		return total;
	}

}
