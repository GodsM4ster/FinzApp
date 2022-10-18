package edu.udea.FinzAPP.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;


@Entity
@Table(name="perfil")
public class Perfil {

    //ATRIBUTOS
    @Id
    private int id;
    @Column
    private String imagen;
    @Column
    private String telefono;
    @OneToOne
    private Empleado empleado;
    @Column
    private LocalDate fechaCreacion;
    @Column
    private LocalDate fechaModificacion;


    //CONSTRUCTOR


    public Perfil() {
    }

    public Perfil(int id, String imagen, String telefono, Empleado empleado, LocalDate fechaCreacion, LocalDate fechaModificacion) {
        this.id = id;
        this.imagen = imagen;
        this.telefono = telefono;
        this.empleado = empleado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }



    //GETTERS && SETTERS


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

}
