package edu.udea.FinzAPP.services;

import edu.udea.FinzAPP.entities.Empleado;
import edu.udea.FinzAPP.entities.Empresa;
import edu.udea.FinzAPP.entities.MovimientoDinero;
import edu.udea.FinzAPP.entities.Perfil;
import edu.udea.FinzAPP.repositories.EmpleadoRepository;
import edu.udea.FinzAPP.repositories.EmpresaRepository;
import edu.udea.FinzAPP.repositories.MovimientoDineroRepository;
import edu.udea.FinzAPP.repositories.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Biviana Medina Trujillo
 * @author Santiago Medina Trujillo
 *
 */

@Service
public class GestorEmpresas {
    /* GestorEmpresas se encarga de gestionar(buscar, agregar, modificar, eliminar)
       las empresas agregadas al sistema
    */

    //ATRIBUTOS: _______________________________________________________________________________________________________
    @Autowired
    private EmpresaRepository empresaRepositorio;
    @Autowired
    private MovimientoDineroRepository movimientoDineroRepository;
    @Lazy
    @Autowired
    private GestorMovimientoDinero gestorMovimientoDinero;
    @Autowired
    private GestorPerfil gestorPerfil;
    @Autowired
    private EmpleadoRepository empleadoRepositorio;
    @Autowired
    private PerfilRepository perfilRepositorio;

    @Autowired
    private GestorEmpleado gestorEmpleado;




    private final String msgNoExiste = "La empresa no se encuentra registrada!";


    //CONSTRUCTOR: _____________________________________________________________________________________________________
    public GestorEmpresas(){

    }


    //LÓGICA DEL NEGOCIO: ______________________________________________________________________________________________

    //PARA CONSULTAR TODAS LAS EMPRESAS (GET):
    public List<Empresa> getEmpresas() {
        return empresaRepositorio.findAll();
    }


    //PARA CONSULTAR UNA EMPRESA (GET):

    public Empresa getEmpresa(int nit) throws Exception {

        System.out.println(nit);
        Optional<Empresa> empresaBD = empresaRepositorio.findById(nit);

        if(empresaBD.isPresent()){
            //existe
            System.out.println(empresaBD);
            return empresaBD.get();
        }else{
            //no existe
            System.out.println(empresaBD);
            throw new Exception(msgNoExiste);
        }


    }


    //PARA CREAR UNA NUEVA EMPRESA (POST):
    public Empresa setEmpresa(Empresa empresaNueva) throws Exception {
        //si existe la empresa, no debe permitir registrar
        //si no existe, debe permitir registrar
        //se hace uso dem método getEmpresa()

        System.out.println(empresaNueva);
        try {
            //si existe
            getEmpresa(empresaNueva.getNit());
            // ***
        } catch (Exception e) {
            //no existe
            LocalDate now = LocalDate.now();
            empresaNueva.setFechaCreacion(now);
            empresaNueva.setFechaModificacion(now);
            empresaRepositorio.save(empresaNueva);

            return empresaNueva;
        }

        // ***
        throw new Exception("La empresa ya se encuentra registrada");
    }


    //PARA EDITAR EMPRESA YA EXISTENTE:
    public String updateEmpresa(Empresa empresa_update, int id) throws Exception{
        try {
            Empresa empresa = getEmpresa(id);


            if (empresa_update.getNombre() != null && !empresa_update.getNombre().equals("")){
                empresa.setNombre(empresa_update.getNombre());
            }

            if (empresa_update.getDireccion() != null && !empresa_update.getDireccion().equals("")){
                empresa.setDireccion(empresa_update.getDireccion());
            }

            if (empresa_update.getTelefono() != null && !empresa_update.getTelefono().equals("")){
                empresa.setTelefono(empresa_update.getTelefono());
            }

            if (empresa_update.getFechaCreacion() != null){
                empresa.setFechaCreacion(empresa_update.getFechaCreacion());
            }
            if (empresa_update.getFechaModificacion() != null){
                empresa.setFechaModificacion(empresa_update.getFechaModificacion());
            }

            LocalDate now = LocalDate.now();
            empresa.setFechaModificacion(now);
            empresaRepositorio.save(empresa);

            return "Modificación Exitosa!";

        } catch (Exception e) {
            throw new Exception(msgNoExiste);
        }
    }


    //PARA ELIMINAR EMPRESA YA EXISTENTE:
    public String deleteEmpresa(int id) throws Exception {

        try {

            List<MovimientoDinero> listaTransacciones = gestorMovimientoDinero.getTransaccionesNit(id);
            List<Perfil> perfil = gestorPerfil.getPerfilNit(id);
            List<Empleado> listaEmpleados = gestorEmpleado.getEmpleadoNit(id);
            Empresa empresa = getEmpresa(id);

            //Elimina al Empleado(segundo) y su Perfil(primero)
            movimientoDineroRepository.deleteAll(listaTransacciones);
            perfilRepositorio.deleteAll(perfil);
            empleadoRepositorio.deleteAll(listaEmpleados);
            empresaRepositorio.delete(empresa);


            return "El empleado se ha Eliminado exitosamente";

        } catch (Exception e) {
            throw new Exception(msgNoExiste);
        }
    }
}
