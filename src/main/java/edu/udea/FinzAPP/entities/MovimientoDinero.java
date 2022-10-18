package edu.udea.FinzAPP.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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
@Table(name = "transaccion")
public class MovimientoDinero {

    //ATRIBUTOS:
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int numComprobante;
    @Column
    private double montoMovimiento;
    @Column
    private String conceptoMovimiento;
    @Column
    private LocalDate fechaCreacion;
    @Column
    private LocalDate fechaModificacion;
    @ManyToOne
    private Empleado empleado; //FK

//    @ManyToOne
//    private Empresa empresa; //FK



    //CONSTRUCTOR:
    public MovimientoDinero() {
    }

    public MovimientoDinero(double montoMovimiento, String conceptoMovimiento, int numComprobante, Empleado empleado, Empresa empresa, LocalDate fechaCreacion, LocalDate fechaModificacion) {
        this.montoMovimiento = montoMovimiento;
        this.conceptoMovimiento = conceptoMovimiento;
        this.numComprobante = numComprobante;
        this.empleado = empleado;
//        this.empresa = empresa;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    //GETTERS && SETTERS:
    public double getMontoMovimiento() {
        return montoMovimiento;
    }

    public void setMontoMovimiento(double montoMovimiento) {
        this.montoMovimiento = montoMovimiento;
    }

    public String getConceptoMovimiento() {
        return conceptoMovimiento;
    }

    public void setConceptoMovimiento(String conceptoMovimiento) {
        this.conceptoMovimiento = conceptoMovimiento;
    }

    public int getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(int numComprobante) {
        this.numComprobante = numComprobante;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

//    public Empresa getEmpresa() {
//        return empresa;
//    }
//
//    public void setEmpresa(Empresa empresa) {
//        this.empresa = empresa;
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

    //TO-STRING

    @Override
    public String toString() {
        return "MovimientoDinero{" +
                "numComprobante=" + numComprobante +
                ", montoMovimiento=" + montoMovimiento +
                ", conceptoMovimiento='" + conceptoMovimiento + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", empleado=" + empleado +
                '}';
    }
}
