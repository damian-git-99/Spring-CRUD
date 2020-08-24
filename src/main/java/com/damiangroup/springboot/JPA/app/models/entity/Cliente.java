package com.damiangroup.springboot.JPA.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import org.springframework.format.annotation.DateTimeFormat;




@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incrementable
    private Long id;

    // @Column(name = "nombre_cliente") si el nombre en la base de datos cambia
   
    @NotEmpty(message = "El campo Nombre no debe estar vacío")
    private String nombre;
    @NotEmpty(message = "El campo Apellido no debe estar vacío")
    private String apellido;
    @NotEmpty(message = "El campo Email no debe estar vacío")
    @Email(message = "Tiene que ser un Email válido")
    private String email;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE) // formato en el que se va a guardar la fecha
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "El campo Fecha no debe estar vacío")
    private Date createAt;

   /*@PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    

}
