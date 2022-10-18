package edu.udea.FinzAPP.services;

import edu.udea.FinzAPP.entities.Empleado;
import edu.udea.FinzAPP.entities.MovimientoDinero;
import edu.udea.FinzAPP.entities.Perfil;
import edu.udea.FinzAPP.repositories.EmpleadoRepository;
import edu.udea.FinzAPP.repositories.MovimientoDineroRepository;
import edu.udea.FinzAPP.repositories.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Mari Luz Tabares López
 * @author Edwin Marin Ballesteros
 * @author Henry Daniel Barreto Forero
 *
 */
@Service
public class GestorEmpleado {
    /* GestorEmpleados se encarga de gestionar(buscar, agregar, modificar, eliminar)
       los empleados agregados al sistema junto con su PERFIL
    */

    //ATRIBUTOS: _______________________________________________________________________________________________________
    @Autowired
    private EmpleadoRepository empleadoRepositorio;
    @Lazy
    @Autowired
    private GestorMovimientoDinero gestorMovimientoDinero;

    @Autowired
    private  MovimientoDineroRepository movimientoDineroRepository;
    @Autowired
    private PerfilRepository perfilRepositorio;
    @Autowired
    private GestorPerfil gestorPerfil;
    private final String msgNoExiste = "El Empleado no se encuentra registrado";

    //CONSTRUCTOR: _____________________________________________________________________________________________________
    public GestorEmpleado(){

    }

    //LÓGICA DEL NEGOCIO: **********************************************************************************************

    //PARA CONSULTAR TODOS LOS EMPLEADOS (GET):_________________________________________________________________________
    public List<Empleado> getEmpleados() {

        return empleadoRepositorio.findAll();
    }

    public List<Empleado> getEmpleadoNit(int nit){
        return empleadoRepositorio.findByEmpresaNit(nit);
    }
    //PARA CONSULTAR UN EMPLEADO (GET):_________________________________________________________________________________
    public Empleado getEmpleadoLoginPropio() throws Exception {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            String userName =((UserDetails) principal).getUsername(); //CARTEAR
            return  getEmpleadoCorreo(userName);
        }else{
            String userName = principal.toString();
            return  getEmpleadoCorreo(userName);
        }
    }

//    public Empleado getEmpleadoGoogle(@AuthenticationPrincipal OAuth2User user) throws Exception {
//        if(user != null){
//            Object e = user.getAttributes().get("email");
//            String email = e.toString();
//
//            return getEmpleadoCorreo(email);
//        }
//
//        throw new Exception(msgNoExiste);
//    }

    public Empleado getEmpleado(int numDocumento) throws Exception {


        Optional<Empleado> empleadoBD = empleadoRepositorio.findById(numDocumento);

        if(empleadoBD.isPresent()){
            //existe
            return empleadoBD.get();
        }else{
            //no existe
            throw new Exception(msgNoExiste);
        }
    }
    public Empleado getEmpleadoCorreo(String correo) throws Exception {


        Optional<Empleado> empleadoBD = Optional.ofNullable(empleadoRepositorio.findByCorreo(correo));

        if(empleadoBD.isPresent()){
            //existe
            return empleadoBD.get();
        }else{
            //no existe
            throw new Exception(msgNoExiste);
        }
    }



    //PARA CREAR UN NUEVO EMPLEADO (POST):______________________________________________________________________________
    public String setEmpleado(Empleado empleadoNuevo) throws Exception {
        //si existe el empleado, no debe permitir registrar
        //si no existe, debe permitir registrar
        //se hace uso del método getEmpleado()

        try {
            //si existe
            getEmpleado(empleadoNuevo.getNumDocumento());
            // ***
        } catch (Exception e) {
            //no existe


            LocalDate ahora = LocalDate.now();
            Perfil perfil = new Perfil(empleadoNuevo.getNumDocumento(),"imagen","telefono", empleadoNuevo, ahora,ahora);

            //INFORMACIÓN POR DEFECTO:
            //empleadoNuevo.setRol("ADMIN");
            empleadoNuevo.setUserName(String.valueOf(empleadoNuevo.getNumDocumento()));
            empleadoNuevo.setPassword(String.valueOf(empleadoNuevo.getNumDocumento()));
            empleadoNuevo.setActivo(true);
            empleadoNuevo.setFechaCreacion(ahora);
            empleadoNuevo.setFechaModificacion(ahora);


            //Crea al Empleado(primero) y su Perfil(segundo)
            empleadoRepositorio.save(empleadoNuevo);
            perfilRepositorio.save(perfil);

            //Al momento de guardar el empleado, debe guardar el perfil
            return "Se ha registrado el empleado de manera Exitosa!";
        }

        // ***
        throw new Exception("El empleado ya se encuentra en la base de datos");
    }

    //PARA EDITAR EMPRESA YA EXISTENTE (PATCH):_________________________________________________________________________
    public String updateEmpleado(Empleado empleado_update, int id) throws Exception{
        try {
            Empleado empleadoU = getEmpleado(id);


            if (empleado_update.getNombre() != null && !empleado_update.getNombre().equals("")){
                empleadoU.setNombre(empleado_update.getNombre());
            }
            if (empleado_update.getCorreo() != null && !empleado_update.getCorreo().equals("")){
                empleadoU.setCorreo(empleado_update.getCorreo());
            }
            if (empleado_update.getEmpresa() != null ) {
                empleadoU.setEmpresa(empleado_update.getEmpresa());
            }
            if (empleado_update.getRol() != null && !empleado_update.getRol().equals("")){
                empleadoU.setRol(empleado_update.getRol());
            }

            LocalDate now = LocalDate.now();
            empleadoU.setFechaModificacion(now);

            empleadoRepositorio.save(empleadoU);

            return "Modificación Exitosa!";

        } catch (Exception e) {
            throw new Exception("El empleado no esta en la base de datos");
        }
    }


    //PARA ELIMINAR EMPLEADO YA EXISTENTE (DELETE):_____________________________________________________________________
    public String deleteEmpleado(int id) throws Exception {

        try {

            List<MovimientoDinero> listaTransacciones = gestorMovimientoDinero.getTransaccionesNumDocumento(id);
            Perfil perfil = gestorPerfil.getPerfil(id);
            Empleado empleado = getEmpleado(id);


            //Elimina al Empleado(segundo) y su Perfil(primero)
            movimientoDineroRepository.deleteAll(listaTransacciones);

            //movimientoDineroRepository.deleteByEmpleadoNumDocumento(id);
            perfilRepositorio.delete(perfil);
            empleadoRepositorio.delete(empleado);

            return "El empleado se ha Eliminado exitosamente";

        } catch (Exception e) {
            throw new Exception(msgNoExiste);
        }
    }
}
