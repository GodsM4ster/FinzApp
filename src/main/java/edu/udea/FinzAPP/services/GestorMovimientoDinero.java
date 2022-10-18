package edu.udea.FinzAPP.services;

import com.nimbusds.jose.proc.SecurityContext;
import edu.udea.FinzAPP.entities.*;
import edu.udea.FinzAPP.repositories.EmpleadoRepository;
import edu.udea.FinzAPP.repositories.MovimientoDineroRepository;
import edu.udea.FinzAPP.repositories.PerfilRepository;
import edu.udea.FinzAPP.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Biviana Medina Trujillo
 * @author Mari Luz Tabares López
 * @author Santiago Medina Trujillo
 * @author Edwin Marin Ballesteros
 * @author Henry Daniel Barreto Forero
 *
 */

@Service
public class GestorMovimientoDinero {

    //ATRIBUTOS:
    @Autowired
    private MovimientoDineroRepository movimientoDineroRepositorio;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private GestorEmpresas gestorEmpresa; //---------------------------> Para dato quemado
    @Autowired
    private GestorEmpleado gestorEmpleado;

    @Autowired
    private  SecurityConfig sec;

    @Autowired
    private MyUserDetailService userDetail;

    private final String msgNoExiste = "La transacción no se encuentra registrada!";

    //private final GestorEmpresas gestorEmpresas = new GestorEmpresas();
    //GestorEmpleados gestorEmpleados = new GestorEmpleados();


    //CONSTRUCTOR:
    public GestorMovimientoDinero(){

    }

    // _________________________________________________________________________________________________________________
    public List<MovimientoDinero> getTransacciones() {
        return movimientoDineroRepositorio.findAll();
    }

    public List<MovimientoDinero> getTransaccionesNumDocumento(int numDocumento){
        return movimientoDineroRepositorio.findByEmpleadoNumDocumento(numDocumento);
    }

    public List<MovimientoDinero> getTransaccionesNit(int empleadoNit) {
        return movimientoDineroRepositorio.findByEmpleadoEmpresaNit(empleadoNit);
    }


    public List<MovimientoDinero> getTransaccionesNit() throws Exception {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(principal);
//        System.out.println(principal.toString());

        if(principal instanceof UserDetails){
            String userName =((UserDetails) principal).getUsername(); //CARTEAR
            //String user = ((UserDetails) principal)

            //google correo -->
            Empleado empleado = gestorEmpleado.getEmpleadoCorreo(userName);

            //System.out.println(empleado.getEmpresa().getNit());

            return movimientoDineroRepositorio.findByEmpleadoEmpresaNit(empleado.getEmpresa().getNit());



        }else{
            String userName = principal.toString();

            Empleado empleado = gestorEmpleado.getEmpleadoCorreo(userName); //PROBLEMA EN ESTA LINEA

            //System.out.println(empleado.getEmpresa().getNit());

           return movimientoDineroRepositorio.findByEmpleadoEmpresaNit(empleado.getEmpresa().getNit());
        }


    }



    //GET - BÚSQUEDA DE TRANSACCIÓN (SANTIAGO):
    public MovimientoDinero getTransaccion(int numComprobante) throws Exception {

        Optional<MovimientoDinero> transaccion = movimientoDineroRepositorio.findById(numComprobante);

        if(transaccion.isPresent()){
            //existe
            return transaccion.get();
        }else {
            //no existe
            throw new Exception(msgNoExiste);
        }


    }


    //POST - INGRESAR UNA NUEVA TRANSACCIÓN (MARI LUZ):
    public String setMovimientoDinero(MovimientoDinero transaccionNueva) throws Exception {

        try {
            //si existe
            getTransaccion(transaccionNueva.getNumComprobante());
            // ***
        } catch (Exception e) {
            //no existe

            // DATOS QUEMADOS ************************************************************
//            Empresa empresaPrueba = gestorEmpresa.getEmpresa(1);
//            EnumRolName rol = EnumRolName.ADMIN;
            // ***************************************************************************

            LocalDate now = LocalDate.now();

            transaccionNueva.setFechaCreacion(now);
            transaccionNueva.setFechaModificacion(now);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(principal instanceof UserDetails){
                String userName =((UserDetails) principal).getUsername(); //CARTEAR

                //google correo
                Empleado empleado = gestorEmpleado.getEmpleadoCorreo(userName);


                transaccionNueva.setEmpleado(empleado);
            }else{
                String userName = principal.toString();

                Empleado empleado = gestorEmpleado.getEmpleadoCorreo(userName);
                transaccionNueva.setEmpleado(empleado);
            }


//            Empleado empleado = new Empleado( 1000464799, "Santiago","santiagomedina.123@gmail.com","1000464799","1000464799",true, empresaPrueba, "ADMIN", now, now);
//            Perfil perfil = new Perfil(1000464799,"imagen","telefono", empleado, now,now);
//
//            empleadoRepository.save(empleado);
//            perfilRepository.save(perfil);
            movimientoDineroRepositorio.save(transaccionNueva);

            return "Se ha registrado la transacción de manera Exitosa!";
        }

        // ***
        throw new Exception("La transaccion ya se encuentra registrada");
    }

    public String setMovimientoDineroUser(MovimientoDinero transaccionNueva) throws Exception {

        try {
            //si existe
            getTransaccion(transaccionNueva.getNumComprobante());
            // ***
        } catch (Exception e) {
            //no existe

            LocalDate now = LocalDate.now();

            transaccionNueva.setFechaCreacion(now);
            transaccionNueva.setFechaModificacion(now);

            movimientoDineroRepositorio.save(transaccionNueva);

            return "Se ha registrado la transacción de manera Exitosa!";
        }

        // ***
        throw new Exception("La transaccion ya se encuentra registrada");
    }

    //PATCH - MODIFICAR TRANSACCIÓN (EDWIN):
    public String updateMovimientoDinero(MovimientoDinero movimiento_update, int id) throws Exception{
        try {
            MovimientoDinero movimientoU = getTransaccion(id);


            if (movimiento_update.getMontoMovimiento() > 0){
                movimientoU.setMontoMovimiento(movimiento_update.getMontoMovimiento());
            }

            if (movimiento_update.getConceptoMovimiento() != null && !movimiento_update.getConceptoMovimiento().equals("")){
                movimientoU.setConceptoMovimiento(movimiento_update.getConceptoMovimiento());
            }

            LocalDate now = LocalDate.now();
            movimientoU.setFechaModificacion(now);

            movimientoDineroRepositorio.save(movimientoU);



            return "Modificación Exitosa!";

        } catch (Exception e) {
            throw new Exception(msgNoExiste);
        }
    }

    //DELETE - ELIMINAR TRANSACCIÓN (BIVIANA):
    public String deleteTransaccion(int id) throws Exception {
        /*
        *   a. Si existe el comprobante lo elimina y muestra mensaje
        *   b. Si no existe, no permite eliminar y muestra un mensaje
        *
        */

        try {
            //existe:
            MovimientoDinero transaccion = getTransaccion(id);

            movimientoDineroRepositorio.delete(transaccion);

            return "Transacción eliminada con éxito!";

        } catch (Exception e) {
            //no existe:
            throw new Exception(msgNoExiste);
        }

    }

    public double getTotalEmpresa(List<MovimientoDinero> transacciones){

        double ingreso = 0;
        double egreso =  0;
        double total = 0;

        for(int i = 0; i < transacciones.size() ;i++){

            if(transacciones.get(i).getConceptoMovimiento().equals("INGRESO")){

                ingreso += transacciones.get(i).getMontoMovimiento();

            }
            if(transacciones.get(i).getConceptoMovimiento().equals("EGRESO")){

                egreso += transacciones.get(i).getMontoMovimiento();

            }
        }

        total = ingreso - egreso;

        return total;
    }

}
