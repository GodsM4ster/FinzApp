package edu.udea.FinzAPP.repositories;

import edu.udea.FinzAPP.entities.Empleado;
import edu.udea.FinzAPP.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    //CONTRATO INTERNO

    public List<Perfil> findByEmpleadoEmpresaNit(int nit);
}
