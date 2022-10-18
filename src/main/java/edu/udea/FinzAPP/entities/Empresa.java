package edu.udea.FinzAPP.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Biviana Medina Trujillo
 * @author Mari Luz Tabares López
 * @author Santiago Medina Trujillo
 * @author Edwin Marin Ballesteros
 * @author Henry Daniel Barreto Forero
 *
 */
@Entity
@Table(name = "empresa")
public class Empresa {


    //ATRIBUTOS:
    @Id
    private int nit;
    @Column
    private String nombre;
    @Column
    private String direccion;
    @Column
    private String telefono;
    @Column
    private LocalDate fechaCreacion;
    @Column
    private LocalDate fechaModificacion;
    @JsonIgnore
    @OneToMany(mappedBy = "empresa") //No FK
    private List<Empleado> empleados;
//    @JsonIgnore
//    @OneToMany(mappedBy = "empresa") //No FK
//    private List<MovimientoDinero> transacciones;


    //CONSTRUCTORES:
    public Empresa() {
    }

    public Empresa( int nit, String nombre, String direccion, String telefono, List<Empleado> empleados, LocalDate fechaModificacion, LocalDate fechaCreacion){
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.empleados = empleados;
//        this.transacciones = transacciones;
        this.fechaModificacion = fechaModificacion;
        this.fechaCreacion = fechaCreacion;
    }


    //GETTERS && SETTERS:
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

//    public List<MovimientoDinero> getTransacciones() {
//        return transacciones;
//    }
//
//    public void setTransacciones(List<MovimientoDinero> transacciones) {
//        this.transacciones = transacciones;
//    }

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


    //TO-STRING:

    @Override
   public String toString() {
       return "Empresa{" +
              "nit=" + nit +
               ", nombre='" + nombre + '\'' +
               ", direccion='" + direccion + '\'' +
               ", telefono='" + telefono + '\'' +
              ", fechaCreacion=" + fechaCreacion +
               ", fechaModificacion=" + fechaModificacion +
              '}';
    }
}
