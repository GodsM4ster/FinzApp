package edu.udea.FinzAPP.repositories;

import edu.udea.FinzAPP.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Integer> {
    //CONTRATO INTERNO

    public List<Empleado> findByEmpresaNit(int nit);
    public Empleado findByCorreo(String correo);



}
