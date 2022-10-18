package edu.udea.FinzAPP.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
@Table(name="empleado")
public class Empleado {

    //ATRIBUTOS:________________________________________________________________________________________________________
    @Id
    private int numDocumento;
    @Column
    private String nombre;
    @Column(unique = true)
    private String correo;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private LocalDate fechaCreacion;
    @Column
    private LocalDate fechaModificacion;
    @Column
    private boolean activo;
//    @Enumerated(EnumType.STRING)
//    private EnumRolName rol;
    @Column
    private String rol;
    @ManyToOne
    private Empresa empresa;
    @JsonIgnore
    @OneToOne(mappedBy = "empleado")
    private Perfil perfil;
    @JsonIgnore
    @OneToMany(mappedBy = "empleado")
    private List<MovimientoDinero> transacciones;


    //CONSTRUCTOR::_____________________________________________________________________________________________________
    public Empleado() {
    }


    public Empleado( int numDocumento, String nombre, String correo, String userName, String password, boolean activo, Empresa empresa, String rol, LocalDate fechaCreacion, LocalDate fechaModificacion) {
        this.nombre = nombre;
        this.correo = correo;
        this.userName = userName;
        this.password = password;
        this.empresa = empresa;
        this.rol = rol;
        this.numDocumento = numDocumento;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.activo = activo;
    }

    //GETTERS && SETTERS::______________________________________________________________________________________________
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(int numDocumento) {
        this.numDocumento = numDocumento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<MovimientoDinero> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<MovimientoDinero> transacciones) {
        this.transacciones = transacciones;
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    //____________________
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    //TO-STRING:________________________________________________________________________________________________________


    @Override
    public String toString() {
        return "Empleado{" +
                "numDocumento=" + numDocumento +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", activo=" + activo +
                ", rol='" + rol + '\'' +
                ", empresa=" + empresa +
                ", perfil=" + perfil +
                ", transacciones=" + transacciones +
                '}';
    }
}