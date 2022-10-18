package edu.udea.FinzAPP.services;

import edu.udea.FinzAPP.entities.Perfil;
import edu.udea.FinzAPP.repositories.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GestorPerfil {

    //ATRIBUTOS:
    @Autowired
    private PerfilRepository perfilRepositorio;
    private final String msgNoExiste = "El perfil no existe!";

    public GestorPerfil(){

    }

    public List<Perfil> getPerfiles() {
        return perfilRepositorio.findAll();
    }

    //GET:
    public Perfil getPerfil(int numDocumento) throws Exception {


        Optional<Perfil> perfilBD = perfilRepositorio.findById(numDocumento);

        if(perfilBD.isPresent()){
            //existe
            return perfilBD.get();
        }else{
            //no existe
            throw new Exception(msgNoExiste);
        }
    }

    public List<Perfil> getPerfilNit(int nit){
        return perfilRepositorio.findByEmpleadoEmpresaNit(nit);
    }

    //UPDATE:
    public String updatePerfil(Perfil perfil_update, int id) throws Exception {

        try {
            Perfil perfil = getPerfil(id);



            if(perfil_update.getImagen() != null && !perfil_update.getImagen().equals("")){
                perfil.setImagen(perfil_update.getImagen());
            }

            if(perfil_update.getTelefono() != null && !perfil_update.getTelefono().equals("")){
                perfil.setTelefono(perfil_update.getTelefono());
            }

            LocalDate now = LocalDate.now();
            perfil.setFechaModificacion(now);

            perfilRepositorio.save(perfil);

            return "Perfil actualizado correctamente!";

        } catch (Exception e) {
            throw new Exception(msgNoExiste);
        }



    }

}
